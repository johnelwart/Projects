import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.Socket;
import java.io.IOException;
import java.util.Formatter;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public class BlackjackClient extends JFrame implements Runnable, ActionListener{
    /**
     * Text field that displays the player number.
     */
    private JTextField playerID;
    
    /**
     * Text area that will display any outputs of the game including any card that is drawn, game results, etc.
     */
    private JTextArea displayArea;
    
    /**
     * Button that represents the player requesting to be dealt a new card.
     */
    private JButton hitButton;
    
    /**
     * Button that indicates the player wanting to end their turn with the current hand they have.
     */
    private JButton standButton;
    
    /**
     * New JFrame for the dialog window to show up on.
     */
    private JFrame window;
    
    /**
     * Dialog window that allows the user to choose between the two ace values if one is drawn.
     */
    private JOptionPane popup;
    
    
    /**
     * Connection between the client and the server.
     */
    private Socket connection;
    
    /**
     * Stores any string read from the stream between the server and the clients.
     */
    private Scanner input;
    
    /**
     * Outputs a message to the stream for the server to read.
     */
    private Formatter output;
    
    
    /**
     * Stores the IP address of the server
     */
    private String blackjackHost;
    
    /**
     * Player number of this client
     */
    private String playerNum;
    
    /**
     * Total of this players current hand
     */
    private int handTotal = 0;
    
    /**
     * Indicates if it is this players turn
     */
    private boolean isTurn;
    
    /**
     * Constant to define player one using a string
     */
    private final String PLAYER_ONE = "1";
    
    /**
     * Constant to define player two using a string
     */
    private final String PLAYER_TWO = "2";
    
    /**
     * Ace value chosen by the player that is added to the hand total.
     */
    int aceVal = 1;
    
    /**
     * Constructor for each of the clients. Inside it creates new GUI components and sets the properties of them then
     * adds them to the JFrame. It also sets the JFrame as "visible" and calls the startClient method.
     * @param host IP address of the server you intend on connecting to.
     */
    public BlackjackClient(String host) {
        super("Blackjack Game");
        blackjackHost = host;
        
        displayArea = new JTextArea(10, 30);
        displayArea.setEditable(false);
        add(new JScrollPane(displayArea), BorderLayout.SOUTH);

        hitButton = new JButton("Hit");
        hitButton.addActionListener(this);
        hitButton.setPreferredSize(new Dimension(150,5));
        add(hitButton, BorderLayout.EAST);
        
        standButton = new JButton("Stand");
        standButton.addActionListener(this);
        standButton.setPreferredSize(new Dimension(150,5));
        add(standButton, BorderLayout.WEST);
        
        playerID = new JTextField();
        playerID.setEditable(false);
        add(playerID, BorderLayout.NORTH);

        popup = new JOptionPane();

        
        setSize(300, 300);
        setVisible(true);
        
        startClient();
    }
    
    /**
     * This method creates a new connection between the client and the desired server using the IP address of that
     * server and the port number and creates a new tread for this client. It also sets up the input and output between
     * the client and server for communicating what each should be doing.
     */
    public void startClient() {
        try {
            connection = new Socket(
                    InetAddress.getByName(blackjackHost), 23552
            );
            
            input = new Scanner(connection.getInputStream());
            output = new Formatter(connection.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        ExecutorService worker = Executors.newFixedThreadPool(1);
        worker.execute(this);
    }
    
    /**
     * When the connection from startClient is established this method automatically runs when the thread is executed.
     * The method reads the output of the server containing the player number and sets that value accordingly and displays
     * it on the GUI for the player to see. By default, the first player to draw is player one and the method will continue
     * to look for outputs from the server while it is that players turn.
     */
    @Override
    public void run() {
        playerNum = input.nextLine();
        
        SwingUtilities.invokeLater(
                new Runnable() {
                    @Override
                    public void run() {
                        playerID.setText("You are player \"" + playerNum + "\"");
                    }
                }
        );
        
        isTurn = (playerNum.equals(PLAYER_ONE));
        
        while(true) {
            if (input.hasNextLine()) {
                processMessage(input.nextLine());
            }
        }
    }
    
    /**
     * This method processes any method received by the client from the server. These messages include "Draw card.",
     * "Ace value" which instructs the user to choose a value for the ace if one is chosen, "Player Moved" which informs
     * the next player that the previous one has gone, "Game over. (P1/P2/P3)" informs the player if a winning condition
     * has been met and outputs the results to the text area, "Tie" informs the player if there is a tie, and "bust"
     * indicates if the previous player went over 21.
     * @param message Message received from the server to be processed.
     */
    public void processMessage(String message) {
        if (message.equals("Draw card.")) {
            int cardVal = input.nextInt();
            handTotal = handTotal + cardVal;
            displayMessage("Player " + playerNum + "'s total score: " + handTotal + "\n");
            displayMessage(input.nextLine());

        } else if (message.equals("Ace value")) {
            String[] options = {"1", "11"};
            int choice = JOptionPane.showOptionDialog(window, "Choose the value for this ace", "Ace Value Selection", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

            if (choice == 0) {
                aceVal = 1;
            } else if (choice == 1) {
                aceVal = 11;
            }

            output.format("%d\n", aceVal);
            output.flush();

        } else if (message.equals("Player Moved")){
            displayMessage("Opponent moved, your turn\n\n");
            isTurn = true;
        } else if (message.equals("Game over. P1")) {
            displayMessage("\nPlayer 1 won");

        } else if (message.equals("Game over. P2")) {
            displayMessage("\nPlayer 2 won");

        } else if (message.equals("Game over. P3")) {
            displayMessage("\nThe dealer won");

        } else if (message.equals("Tie")) {
            displayMessage("\nTie game");

        } else if (message.equals("Bust")) {
            displayMessage("\nPlayer " + playerNum + " Busted");

        } else {
            displayMessage(message + "\n");
        }
    }
    
    /**
     * When called, this method will print the given string to the text area.
     * @param displayMessage Message to be printed.
     */
    public void displayMessage(final String displayMessage) {
        SwingUtilities.invokeLater(
                new Runnable() {
                    @Override
                    public void run() {
                        displayArea.append(displayMessage);
                    }
                }
        );
    }
    
    /**
     * This method listens for any button presses and will output the appropriate command based on what button is
     * pressed and if it is the players turn.
     * @param e Event that is received by the action listener.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == hitButton) {
            if (isTurn) {
                output.format("Draw card\n");
                output.flush();
            }
        } else {
//            System.out.println("Test Stand");
            if (isTurn) {
                output.format("Stand\n");
                output.flush();
            }
        }
    }
}