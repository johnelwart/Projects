// John Elwart
// 1/31/21
// Homework 1
// Blackjack dice game, the player closest to or at 21 wins and busts if the score goes over 21
// Inputs: Asks the user whether they want to stay or roll the dice from the game.
// Outputs: Text from the game

#include <iostream>
#include <ctime>
using namespace std;

// Dice roll function
// Description: Simulates a dice roll using a random number generator
// Inputs: None
// Outputs: randNum: int: A random number between 1 and 6.

int diceRoll()
{
    int randNum = (rand() % 6) + 1; // Creates the random number and stores it in randNum

    return randNum;
}

int main()
{
    //Variables
    int diceOne,                   //Value of dice 1
        diceTwo,                   //Value of dice 2
        turnNum = 1,               //Keeps track of the current turn number for the player or dealer
        totalPointsPlayer = 0,     //Total score for the player
        totalPointsDealer = 0;     //Total score for the dealer
    char choice,                   //User input after asking whether the player wants to roll again or not
         Winner;                   //Used to print out unique game over screen

    //Constants
    const int MAX_VALUE = 21,      //Game winning value
              MAX_TWO_DICE = 17;   //Max score before one dice is used

    srand(time((0)));  //"seeding" the rand function

    //States of the game
    enum gameState {PLAYER_TURN, DEALER_TURN, GAME_OVER};
    gameState turn = PLAYER_TURN;

    //Print the instructions for the game
    cout << "|---------------------------================ GAME RULES ================--------------------------|\n"
            "| 1. The game will start with two, six sided, dice being rolled. You can then choose to stay with |\n"
            "|    what you rolled or roll the dice again. Everytime the dice are rolled the numbers facing up  |\n"
            "|    are the points earned for the round.                                                         |\n"
            "| 2. If the current point total for the player is greater than 17, only one dice is rolled.       |\n"
            "| 3. If the number of points the player earns exceeds 21, the player loses.                       |\n"
            "| 4. If the player chooses to stay and 21 has not been exceeded, it is the dealers turn.          |\n"
            "| 5. The dealer follows rules 1 - 3 as the player does. If the dealers total points exceed 21     |\n"
            "|    The player wins.                                                                             |\n"
            "| 6. The game ties if the player and dealers points are the same and not 21                       |\n"
            "|---------------------------======== PRESS THE ENTER KEY TO BEGIN! =====--------------------------|" << endl;
    cin.get();
    cout << endl;

    //Start of PLAYER_TURN
    while (turn != GAME_OVER)
    {
        do {
            cout << "Player turn: " << turnNum << endl;

            //Dice rolls and total point calculation
            if (totalPointsPlayer < MAX_TWO_DICE)             //Less than 17, two dice are rolled
            {
                diceOne = diceRoll();
                diceTwo = diceRoll();
                cout << "Dice 1 is a " << diceOne << " and Dice 2 is a " << diceTwo << " ." << endl;
                totalPointsPlayer = totalPointsPlayer + diceOne + diceTwo;
            }
            else if (totalPointsPlayer >= MAX_TWO_DICE)       //More than 17, one dice is rolled
            {
                diceOne = diceRoll();
                cout << "Dice 1 is a " << diceOne << endl;
                totalPointsPlayer = totalPointsPlayer + diceOne;
            }

            //Output for each roll
            cout << "Total points: " << totalPointsPlayer << endl;

            //Checking for conditions in which the state would change
            if (totalPointsPlayer < MAX_VALUE)                //Checks for score less than 21
            {
                do{
                    cout << "Do you want to roll again? (y/n):";
                    cin >> choice;

                    if (choice == 'n')
                        turn = DEALER_TURN;

                    if (choice != 'y' && choice != 'n')
                        cout << "Invalid input!" << endl;
                } while (choice != 'y' && choice != 'n');

                cout << endl;
            }
            else if (totalPointsPlayer > MAX_VALUE)           //Checks for score greater than 21
            {
                Winner = 'd';
                turn = GAME_OVER;
            }
            else if (totalPointsPlayer == MAX_VALUE)          //Checks for score equal to 21
            {
                Winner = 'p';
                turn = GAME_OVER;
            }

            turnNum++;

        } while (choice == 'y' && totalPointsPlayer < MAX_VALUE && totalPointsPlayer != MAX_VALUE);
        //End of PLAYER_TURN

        //Start of DEALER_TURN
        if (turn == DEALER_TURN) {
            turnNum = 1;

            cout << "----==== End of the players turn! ====----" << endl << endl;
            cout << "----=== Beginning of dealers turn! ===----" << endl << endl;

            do {
                cout << "Dealer turn: " << turnNum << endl;

                //Dice rolls and total point calculation
                if (totalPointsDealer < MAX_TWO_DICE)   //Less than 17, two dice are rolled
                {
                    diceOne = diceRoll();
                    diceTwo = diceRoll();
                    cout << "Dice 1 is a " << diceOne << " and Dice 2 is a " << diceTwo << " ." << endl;
                    totalPointsDealer = totalPointsDealer + diceOne + diceTwo;
                }
                else if (totalPointsDealer >= MAX_TWO_DICE)   //More than 17, one dice is rolled
                {
                    diceOne = diceRoll();
                    cout << "Dice 1 is a " << diceOne << endl;
                    totalPointsDealer = totalPointsDealer + diceOne;
                }

                //Output for each roll
                cout << "Total points: " << totalPointsDealer << endl;

                //Checks for conditions resulting in a game state change
                if (totalPointsDealer > totalPointsPlayer && totalPointsDealer < MAX_VALUE)  //Checks for a dealer score greater
                {                                                                             //than player but less than 21
                    Winner = 'd';
                    cout << endl;
                    cout << "----==== End of the dealers turn! ====---" << endl;
                    turn = GAME_OVER;
                }
                else if (totalPointsDealer > MAX_VALUE)            //Checks for dealer score greater than 21
                {
                    Winner = 'p';
                    cout << endl;
                    cout << "----==== End of the dealers turn! ====----" << endl;
                    turn = GAME_OVER;
                }
                else if (totalPointsDealer == MAX_VALUE)           //checks for dealer value equal to 21
                {
                    Winner = 'd';
                    cout << endl;
                    cout << "----==== End of the dealers turn! ====---" << endl;
                    turn = GAME_OVER;
                }
                else if (totalPointsDealer == totalPointsPlayer)           //checks for dealer value equal to 21
                {
                    Winner = 't';
                    cout << endl;
                    cout << "----==== End of the dealers turn! ====---" << endl;
                    turn = GAME_OVER;
                }

                turnNum++;
                cout << endl;
            } while (totalPointsDealer < totalPointsPlayer && totalPointsDealer < MAX_VALUE);
        }
    }
    //End of DEALER_TURN

    //Start of GAME_OVER
    if(turn == GAME_OVER)
    {
        cout << endl;
        if (Winner == 'p')
        {
            cout << "You Won!!! Congrats" << endl << endl;

            cout << "-------======= FINAL SCORES =======--------" << endl;
            cout << "Your total points: " << totalPointsPlayer << endl;
            cout << "Dealers total points: " << totalPointsDealer << endl;
        }
        else if (Winner == 'd')
        {
            cout << "The Dealer Won, Sorry" << endl << endl;

            cout << "-------======= FINAL SCORES =======--------" << endl;
            cout << "Dealers total points: " << totalPointsDealer << endl;
            cout << "Your total points: " << totalPointsPlayer << endl;
        }
        else if (Winner == 't')
        {
            cout << "You Tied with the Dealer!" << endl << endl;

            cout << "-------======= FINAL SCORES =======--------" << endl;
            cout << "Your total points: " << totalPointsPlayer << endl;
            cout << "Dealers total points: " << totalPointsDealer << endl;
        }
    }
    //End of DEALER_TURN

    return 0;
}