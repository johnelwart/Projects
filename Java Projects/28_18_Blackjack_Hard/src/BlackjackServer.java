import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Formatter;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// Port # between 23550 and 23554

public class BlackjackServer extends JFrame {
    private final static int PLAYER1 = 0;
    private final static int PLAYER2 = 1;
    
    /**
     * Each player is stored in this array.
     */
    private Player[] players;
    
    /**
     * Current player
     */
    private int currentPlayer;
    
    /**
     * Condition that is set in the gameOver method that will indicate which method should be printed out to the dealer
     * text area.
     */
    private int winCondition;
    
    
    /**
     * Dealer score that is updated after each card is drawn
     */
    private int dealerScore = 0;
    
    /**
     * Indicates if the dealer has played yet. 0 if they have not, 1 if they have.
     */
    private int dealerPlayed = 0;
    
    
    /**
     * Executes the run method in Player class.
     */
    private ExecutorService runGame;
    
    /**
     * Locks the game for synchronization purposes.
     */
    private Lock gameLock;
    
    /**
     * Indicates if both players are connected or not.
     */
    private Condition playerConnected;
    
    /**
     * Waits for the other players turn to be over
     */
    private Condition otherPlayerTurn;
    
    /**
     * Creates a server to let other players connect to it.
     */
    private ServerSocket blackjackServer;
    
    
    /**
     * Text area showing the player ID. in this case the dealer.
     */
    private JTextField playerID;
    
    /**
     * Text area that displays drawn cards, server status, or game results.
     */
    private JTextArea outputWin;
    
    /**
     * New JFrame for the dialog window to show up on.
     */
    private JFrame window;
    
    /**
     * Dialog window that allows the user to choose between the two ace values if one is drawn.
     */
    private JOptionPane popup;
    
    
    /**
     * The constructor for this class creates new threads for each of the clients and creates new instances of gameLock, playerConnected, otherPlayerTurn
     * and the players array. It also initializes the currentPlayer variable to Player one. It then creates a new deck of cards to be used in the game and a
     * new server with a specified port number and queue length. It also creates a new text field and text area for the dealer, sets the properties
     * and adds them to the JFrame.
     */
    BlackjackServer() {
        super("Blackjack Game");

        runGame = Executors.newFixedThreadPool(2);
        gameLock = new ReentrantLock();

        playerConnected = gameLock.newCondition();
        otherPlayerTurn = gameLock.newCondition();
        
        players = new Player[2];
        currentPlayer = PLAYER1;
        
        Deck.createDeck();
    
        try {
            blackjackServer = new ServerSocket(23552, 2);
        }
        catch (IOException ioException) {
            ioException.printStackTrace();
            System.exit(1);
        }
    
        outputWin = new JTextArea(10, 30);
        outputWin.setEditable(false);
        add(new JScrollPane(outputWin), BorderLayout.CENTER);
        outputWin.setText("Server awaiting connections... \n");
    
        playerID = new JTextField();
        playerID.setEditable(false);
        add(playerID, BorderLayout.NORTH);
        playerID.setText("Dealer");
        
        setSize(300, 300);
        setVisible(true);
    }
    
    /**
     * waits for two players to join the game. When they join the queue the server accepts them and adds them to the player array.
     * When two players have connected, it unlocks the game and proceeds with normal execution.
     */
    public void execute() {
        for (int i = 0; i < players.length; i++) {
            try {
                players[i] = new Player(blackjackServer.accept(), i);
                runGame.execute(players[i]);
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
        
        gameLock.lock();
        
        try {
            players[PLAYER1].setSuspended(false);
            playerConnected.signal();
        } finally {
            gameLock.unlock();
        }
    }
    
    /**
     * When called, this method will print the given string to the text area.
     * @param displayMessage Message to be printed.
     */
    public void displayMessage(final String displayMessage) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                outputWin.append(displayMessage);
            }
        }
        );
    }
    
    /**
     * Checks if the game is still playable or if the player should be able to play. If they arent, it will wait for
     * until it is that players turn.
     * @param player Player number that is passed in when the method is called. Determines if they are able to play.
     * @return Returns if the game is still playable (true or false).
     */
    public boolean validatePlayerMove(int player) {
        
        while (player != currentPlayer) {
            gameLock.lock();
            
            try {
                otherPlayerTurn.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                gameLock.unlock();
            }
        }
    
        return isPlayable(currentPlayer);
    }   // End of move method
    
    /**
     * Checks if the current player has gone over or is at 21 and returns that value (true or false).
     * @param currPlayer Player number passed in.
     * @return Returns true or false representing if the player is at or over 21.
     */
    public boolean isPlayable(int currPlayer) {
        return players[currPlayer].playerScore <= 21;
    }
    
    /**
     * Checks the state of the game. If any of the players score is equal to 21 or all 3 players have gone, the game is
     * set to zero, if player 1 and 2 have gone the game is set to 1 to allow the dealer to play. Otherwise, the state
     * remains at 2.
     * @return Returns the state of the game.
     */
    public int isGameOver() {
        if (players[0].playerScore == 21) {
            return 0;
        } else if (players[1].playerScore == 21) {
            return 0;
        } else if (dealerScore == 21) {
            return 0;
        } else if (players[0].hasPlayed == 1 && players[1].hasPlayed == 1 && dealerPlayed == 1) {
            return 0;
        } else if (players[0].hasPlayed == 1 && players[1].hasPlayed == 1) {
            return 1;
        } else {
            return 2;
        }
    }
    
    /**
     * If a game ending condition has been met, This method will set the win condition to a respective value for the dealer to
     * print out and return a message that will be flushed into the stream for the client to display. Also factors in if any
     * of the players have busted and compares the remaining two scores.
     * @return Returns the string that will be sent to the client.
     */
    public String gameOver() {
        if (players[0].playerScore == 21) {
            winCondition = 0;
            return("Game over. P1\n");
            
        } else if (players[1].playerScore == 21) {
            winCondition = 1;
            return("Game over. P2\n");
            
        } else if (dealerScore == 21) {
            winCondition = 2;
            return("Game over. P3\n");
            
        } else if (players[0].playerScore == 0) {
            if (players[1].playerScore > dealerScore) {
                winCondition = 1;
                return("Game over. P2\n");
                
            } else if (players[1].playerScore < dealerScore) {
                winCondition = 2;
                return("Game over. P3\n");
                
            } else if (players[1].playerScore == dealerScore) {
                winCondition = 3;
                return("Tie\n");
            } else {
                return "";
            }
            
        } else if (players[1].playerScore == 0) {
            if (players[0].playerScore > dealerScore) {
                winCondition = 0;
                return("Game over. P2\n");
        
            } else if (players[0].playerScore < dealerScore) {
                winCondition = 2;
                return("Game over. P3\n");
        
            } else if (players[0].playerScore == dealerScore) {
                winCondition = 3;
                return("Tie\n");
            } else {
                return "";
            }
            
        } else if (dealerScore == 0) {
            if (players[0].playerScore > players[1].playerScore) {
                winCondition = 0;
                return("Game over. P2\n");
        
            } else if (players[0].playerScore < players[1].playerScore) {
                winCondition = 2;
                return("Game over. P3\n");
        
            } else if (players[0].playerScore == players[1].playerScore) {
                winCondition = 3;
                return("Tie\n");
            } else {
                return "";
            }
            
        } else if (players[0].playerScore != 0 && players[1].playerScore != 0 && dealerScore != 0) {
            if (players[0].playerScore > players[1].playerScore && players[0].playerScore > dealerScore) {
                winCondition = 0;
                return("Game over. P1\n");
        
            } else if (players[1].playerScore > players[0].playerScore && players[1].playerScore > dealerScore) {
                winCondition = 1;
                return("Game over. P2\n");
        
            } else if (dealerScore > players[0].playerScore && dealerScore > players[1].playerScore && dealerScore < 21) {
                winCondition = 2;
                return("Game over. P3\n");
        
            } else {
                return "";
            }
        } else {
            return "";
        }
    }
    
    // 0 is P1 win
    // 1 is P2 win
    // 2 is P3 win
    // 3 is Tie
    
    private class Player implements Runnable{
        /**
         * Connection to the client
         */
        private Socket connection;
    
        /**
         * Message received by the server from the stream.
         */
        private Scanner input;
    
        /**
         * Output that will be flushed to the stream for the clients to process.
         */
        private Formatter output;
    
    
        /**
         * Player number that is assigned to each of the clients.
         */
        private int playerNumber;
    
        /**
         * Score for each of the players.
         */
        private int playerScore;
    
        /**
         * Value that determines whether a thread is suspended.
         */
        private boolean suspended = true;
    
        /**
         * Stores the string read in from the stream by the scanner.
         */
        private String command;
    
        /**
         * Indicates if that instance of a player has played yet. Set to 1 when they have.
         */
        private int hasPlayed = 0;
    
        /**
         * Constructor that defines the socket and the player number for each player created. Also creates a new scanner and formatter for
         * each player to communicate with.
         * @param socket Connect to the client for each player
         * @param playerNumber Each players number. (1 or 2)
         */
        public Player(Socket socket, int playerNumber) {
            this.playerNumber = playerNumber;
            this.connection = socket;
            
            try {
                input = new Scanner(connection.getInputStream());
                output = new Formatter(connection.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
    
        /**
         * Outputs "Player Moved" to indicate to the next player that the previous one has gone.
         */
        public void otherPlayerMoved() {
            output.format("Player Moved\n");
            output.flush();
        }
    
        /**
         * When this method is executed It processes each command sent from the clients and determines who should be playing
         * at a given time. When all the players have gone the method ends and closes the connections between the players.
         */
        @Override
        public void run() {
            try {
                displayMessage("Player " + (playerNumber + 1) + " connected\n");
                output.format("%s\n", (playerNumber + 1));
                output.flush();
    
                if (playerNumber == PLAYER1) {
                    output.format("%s\n%s", "Player 1 connected",
                            "Waiting for another player\n");
                    output.flush();
        
                    gameLock.lock();
        
                    try {
                        while (suspended) {
                            playerConnected.await();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        gameLock.unlock();
                    }
        
                    output.format("Other player connected. Your move.\n\n");
                    output.flush();
        
                } else {
                    output.format("Player 2 connected, please wait\n");
                    output.flush();
                }
    
                while (isGameOver() == 2) {
        
                    if (input.hasNext()) {
                        command = input.nextLine();
                    }
        
                    if (command.equals("Draw card")) {
                        int val = 0;
            
                        Card drawnCard = Deck.drawCard();
                        int drawnCardVal;
            
                        if (drawnCard.getRank() == 1) {
                            output.format("Ace value\n");
                            output.flush();
                
                            setSuspended(true);
                
                            while (suspended) {
                                if (input.hasNext()) {
                                    val = input.nextInt();
                                    setSuspended(false);
                                }
                            }
                            drawnCardVal = val;
                
                        } else {
                            drawnCardVal = drawnCard.getValue();
                
                        }
                        players[currentPlayer].playerScore = players[currentPlayer].playerScore + drawnCardVal;
            
                        if (validatePlayerMove(playerNumber)) {
                
                            output.format("Draw card.\n");
                            output.format("%d\n", drawnCardVal);
                            output.format("%s\n", "Drawn card: " + drawnCard);
                            output.flush();
                
                        } else {
                            
                            if (players[1].hasPlayed != 1) {
                                
                                players[currentPlayer].playerScore = 0;
                                players[currentPlayer].hasPlayed = 1;
                                
                                output.format("Bust\n");
                                output.flush();
    
                                currentPlayer = (currentPlayer + 1) % 2;
    
                                players[currentPlayer].otherPlayerMoved();
    
                                gameLock.lock();
    
                                try {
                                    otherPlayerTurn.signal();
                                } finally {
                                    gameLock.unlock();
                                }
                            } else {
                                displayMessage("Opponent moved, your turn\n\n");
                            }
                        }
            
                    } else if (command.equals("Stand")) {
                        players[currentPlayer].hasPlayed = 1;
            
                        if (players[1].hasPlayed != 1) {
                            currentPlayer = (currentPlayer + 1) % 2;
                
                            players[currentPlayer].otherPlayerMoved();
                
                            gameLock.lock();
                
                            try {
                                otherPlayerTurn.signal();
                            } finally {
                                gameLock.unlock();
                            }
                        } else {
                            displayMessage("Opponent moved, your turn\n\n");
                        }
                    }
                }
                
                while (isGameOver() != 0) {
                    if (isGameOver() == 1 && players[0].playerScore != 0 || players[1].playerScore != 0) {
                        while (dealerScore < 21) {
                            if (players[0].playerScore >= dealerScore || players[1].playerScore >= dealerScore) {
                                Card drawnCard = Deck.drawCard();
                                int val = 0, drawnCardVal = 0;
                
                                if (drawnCard.getRank() == 1) {
                                    
                                    if (dealerScore <= 20 && dealerScore > 10) {
                                        val = 1;
                                    } else if (dealerScore <= 10) {
                                        val = 11;
                                    }
                    
                                    drawnCardVal = val;
                    
                                } else {
                                    drawnCardVal = drawnCard.getValue();
                    
                                }
                
                                dealerScore = dealerScore + drawnCardVal;
                                
                                displayMessage("Dealer's total score: " + dealerScore + "\n");
                                displayMessage("Card drawn: " + drawnCard + "\n");
                                if (dealerScore > 21) {
                                    dealerScore = 0;
                                    break;
                                }
                                
                            } else {
                                break;
                            }
                        }
                    } else {
                        dealerScore = 1;
                    }
    
                    dealerPlayed = 1;
                    
                    gameOver();
                }
    
                if (winCondition == 0) {
                    displayMessage("Player 1 won");
                } else if (winCondition == 1) {
                    displayMessage("Player 2 won");
                } else if (winCondition == 2) {
                    displayMessage("The dealer won");
                } else if (winCondition == 3) {
                    displayMessage("Tie game");
                }
    
                currentPlayer = (currentPlayer + 1) % 2;
    
                gameLock.lock();
    
                try {
                    otherPlayerTurn.signal();
                } finally {
                    gameLock.unlock();
                }
    
                output.format(gameOver());
                output.flush();
                
            } finally {
                try {
                    connection.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    System.exit(1);
                }
            }
        }
    
        /**
         * Sets the suspended status of a thread.
         * @param status desired state of the thread.
         */
        public void setSuspended(boolean status) {
            suspended = status;
        }
    }
}