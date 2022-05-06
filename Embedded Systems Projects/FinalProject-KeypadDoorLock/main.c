/*
 * FinalProject.c
 *
 * Created: 4/23/2022 11:13:51 AM
 * Authors : John Elwart and Tucker Dickson
 */ 

#include <avr/io.h>
#include <string.h>
#include <time.h>
#include <util/delay.h>
#include <stdio.h>
#include "lcd.h"
#include "Keypad.h"

enum state {passwordScreen, menu, newPasswordScreen, 
			alarm, newTimeScreen, newVolumeScreen};		// Controls the state of the program		
enum state progState = passwordScreen;					// Start the program at the password screen state

char password[] = {'1','2','3','4'};					// 1234 is the initial password
int inactivitySeconds = 10;								// 10 second inactivity timeout initially
int volume = 1; 														

/*
Description: This function displays the standard password screen
Inputs: None
Outputs: None
*/
void askForPass(void) {
	// Clear the LCD and set the cursor to home
	lcd_clrscr();
	lcd_home();
	
	// Send the message "Password: " to the LCD
	lcd_puts("Password: ");
	
	// Move the cursor to the next line
	lcd_gotoxy(0,1);
	
	// Send the message "*: del  #: enter" to the LCD
	lcd_puts("*: del  #: enter");
	
	// Move the cursor back up to the first row, right after "Password: "
	lcd_gotoxy(10,0);
}

/*
Description: This function handles the passwordScreen program state. It reads in 
				the user-entered password, checks it against the actual password,
				and sends a message to the LCD stating whether the password entered
				was correct or incorrect.
Inputs: None
Outputs: None
*/
void passwordScreenFunc(void) {
	
	char key = Key_None;			// Temporarily stores each user-entered character
	char guess[4];					// Stores the four-character user guess
	int guessIdx = 0;				// Used to index the guess array
	int incorrectGuesses = 0;		// The number of consecutive incorrect guesses
	
	// Keep looping until the program state is changed from passwordScreen (to either
	// menu if the password is correct, or alarm if three incorrect guesses are entered)
	while(progState == passwordScreen) {
		
		// Read the key value from the keypad
		key = KP_GetKey();
	
		// If a key was pressed...
		if (key != Key_None)
		{
			// If the key pressed was the F key ('#'), process the guess
			if (key == Key_F) {
			
				// Clear the LCD and set the cursor home
				lcd_clrscr();
				lcd_home();
			
				// If the guess entered is the same as the password...
				if (strncmp(password,guess,4) == 0) {
				
					// Send a password correct message to LCD
					lcd_puts("Password");
					lcd_gotoxy(0,1);
					lcd_puts("correct");
				
					// Turn the green LED on
					PORTB ^= 1 << 4;
				
					// Delay for one second to hold the LED and the message on the LCD
					for(int i = 0; i < (1 * 16); i++) {
						_delay_ms(1000);
					}
				
					// Turn the green LED off
					PORTB ^= 1 << 4;
				
					servoUnlock();
					
					// Change the state of the program to menu
					progState = menu;
				}
				
				// If the guess entered does not match the password...
				else {

					// Send a password incorrect message to LCD
					lcd_puts("Password");
					lcd_gotoxy(0,1);
					lcd_puts("incorrect");
					
					// Increment the number of incorrect guesses
					incorrectGuesses = incorrectGuesses + 1;
				
					// Turn the red LED on
					PORTD ^= 1 << 7;
				
					// Delay for one second to hold the LED and the message on the LCD
					for(int i = 0; i < (1 * 16); i++) {
						_delay_ms(1000);
					}
				
					// Turn the red LED off
					PORTD ^= 1 << 7;
				}
			
				// Whether the guess was correct or not, we want to prompt the user to enter another guess
				askForPass();
			
				// Reset the guess array index to zero
				guessIdx = 0;
			
			}
		
			// If the key pressed was the E key ('*'), backspace
			else if (key == Key_E) {
				
				// Clear display and send the password prompt to the LCD
				askForPass();
			
				// Decrement guessIdx
				guessIdx = guessIdx - 1;
			
				// send all of the characters guessed so far, except for the most previous one, to the LCD
				for(int i = 0; i < guessIdx; i++) {
					lcd_putc(guess[i]);
				}
			}
		
			// If the key entered wasn't the pound or asterisk, display it on the LCD
			else {
			
				// Only allow four-character guesses
				if(guessIdx < 4) {
				
					// Send the key to the LCD
					lcd_putc(key);
				
					// Keep track of the guess
					guess[guessIdx] = key;
					guessIdx = guessIdx + 1;
				}
			}
		
			// Wait to proceed until the key is released
			KP_WaitRelease();
		}
		
		// If the user has entered 3 incorrect guesses, freeze the lock and enable the alarm
		if(incorrectGuesses > 2) {
			progState = alarm;	
		}
	}
}

/*
Description: This function handles the menu program state. It displays different
				functions available to the user, including an option to 
				re-activate the lock and an option to change the password.
Inputs: None
Outputs: None
*/
void menuScreenFunc(void) {
	int screenNum = 0;		// Indicates how far the user has scrolled
	int input = 0;			// Looping variable; controls the inner while-loop
	long iterations = 0;	// Counter for automatic re-lock
	long maxIterations = (28860.03 * inactivitySeconds);
	
	// Clear the LCD and set the cursor home
	lcd_clrscr();
	lcd_home();
	
	// Keep looping while the program state is menu (it hasn't been changed
	// to passwordScreen)
	while(progState == menu) {
		
		// If the screenNum is zero, display the page up/down message and option 
		// A on the LCD
		if(screenNum == 0) {
			lcd_home();
			lcd_puts("2: PgUp  8: PgDn");
			lcd_gotoxy(0,1);
			lcd_puts("A: Lock Door");
			
			// Reset the inactivity clock
			iterations = 0;
			
			// Loop until the user presses a key
			while(input == 0) {
				
				// If the user doesn't interact with the circuit for a certain 
				// amount of time, reactivate the lock
				if (iterations > maxIterations) {
					optionA();
					progState = passwordScreen;
					input = 1;
					askForPass();
				}
				iterations = iterations + 1;

				// If the key pressed is key 2, do nothing
				if (KP_GetKey() == Key_2 && screenNum < 5 && screenNum >= 0) {}
				
				// If the key pressed is key 8, 'scroll' the page down
				else if (KP_GetKey() == Key_8) {
					screenNum = screenNum + 1;
					lcd_clrscr();
					_delay_ms(200 * 16);
					input = 1;
				} 
				
				// If the key entered is key A, activate the lock and return to the
				// password prompt screen
				else if (KP_GetKey() == Key_A) {
					optionA();
					progState = passwordScreen;
					input = 1;
					askForPass();
				}
				
				// If the key entered is key B, do nothing
				else if (KP_GetKey() == Key_B) {}
					
				// If the key entered is key C, do nothing
				else if (KP_GetKey() == Key_C) {}
					
				// If the key entered is key D, do nothing
				else if (KP_GetKey() == Key_D) {}
			}
		}
		
		// Reset input so that we can keep receiving information
		input = 0;
		
		// If the screen number is one, display options A and B on the LCD
		if(screenNum == 1) {
			lcd_home();
			lcd_puts("A: Lock Door");
			lcd_gotoxy(0,1);
			lcd_puts("B: New password ");
			
			// Reset the inactivity clock
			iterations = 0;
				
			// Keep looping until the user presses a key
			while(input == 0) {
				
				// If the user doesn't interact with the circuit for a certain
				// amount of time, reactivate the lock
				if (iterations > maxIterations) {
					optionA();
					progState = passwordScreen;
					input = 1;
					askForPass();
				}
				iterations = iterations + 1;
								
				// If the key pressed is key 2 and the screen is not past the top or bottom,
				// 'scroll' the page up
				if (KP_GetKey() == Key_2 && screenNum < 5 && screenNum >= 0) {
					screenNum = screenNum - 1;
					lcd_clrscr();
					_delay_ms(200 * 16);
					input = 1;
				} 
				
				// If the key pressed is key 8 and the screen is not past the top or bottom,
				// 'scroll' the page down
				else if (KP_GetKey() == Key_8 && screenNum < 5 && screenNum >= 0) {
					screenNum = screenNum + 1;
					lcd_clrscr();
					_delay_ms(200 * 16);
					input = 1;
				} 
				
				// If the key pressed is key A, re-activate the lock
				else if (KP_GetKey() == Key_A) {
					optionA();
					progState = passwordScreen;
					input = 1;
					askForPass();
				} 
				
				// If the key pressed is key B, allow the user to change the password
				else if (KP_GetKey() == Key_B) {
					KP_WaitRelease();
					optionB();
					progState = menu;
					input = 1;
					askForPass();
				}
				
				// If the key pressed is key C, do nothing
				else if (KP_GetKey() == Key_C) {}
					
				// If the key entered is key D, do nothing
				else if (KP_GetKey() == Key_D) {}
			}
		}
		
		// Reset input so that we can keep receiving information
		input = 0;
		
		// If the screenNum is two, display options B and C on the LCD
		if(screenNum == 2) {
			lcd_home();
			lcd_puts("B: New password ");
			lcd_gotoxy(0,1);
			lcd_puts("C: Re-lock Time");
			
			// Reset the inactivity clock
			iterations = 0;
		
			// Keep looping until the user presses a key
			while(input == 0) {
				
				// If the user doesn't interact with the circuit for a certain
				// amount of time, reactivate the lock
				if (iterations > maxIterations) {
					optionA();
					progState = passwordScreen;
					input = 1;
					askForPass();
				}
				iterations = iterations + 1;
				
				// If the key pressed is key 2 and the screen is within reasonable bounds,
				// 'scroll' the screen up
				if (KP_GetKey() == Key_2 && screenNum < 5 && screenNum >= 0) {
					screenNum = screenNum - 1;
					lcd_clrscr();
					_delay_ms(200 * 16);
					input = 1;
				}
				
				// If the key pressed is key 8 and the screen is within reasonable bounds,
				// 'scroll' the screen down
				else if (KP_GetKey() == Key_8 && screenNum < 5 && screenNum >= 0) {
					screenNum = screenNum + 1;
					lcd_clrscr();
					_delay_ms(200 * 16);
					input = 1;
				}
				
				// If the key pressed is key A, do nothing
				else if (KP_GetKey() == Key_A) {}
				
				// If the key pressed is key B, allow the user to change the password
				else if (KP_GetKey() == Key_B) {
					KP_WaitRelease();
					optionB();
					progState = menu;
					input = 1;
					askForPass();
				}
				
				// If the key pressed is key C, change the inactivity time out duration
				else if (KP_GetKey() == Key_C) {
					KP_WaitRelease();
					newTimeFunc();
					progState = menu;
					input = 1;
				}
				
				// If the key entered is key D, do nothing
				else if (KP_GetKey() == Key_D) {}
			}
		}
		
		// Reset input so that we can keep receiving information
		input = 0;
	
		// If the screenNum is three, display options C and D on the LCD
		if(screenNum == 3) {
			lcd_home();
			lcd_puts("C: Re-lock Time");
			lcd_gotoxy(0,1);
			lcd_puts("D: Buzzer Volume");
			
			// Reset the inactivity clock
			iterations = 0;
			
			// Keep looping until the user presses a key
			while(input == 0) {
				
				// If the user doesn't interact with the circuit for a certain
				// amount of time, reactivate the lock
				if (iterations > maxIterations) {
					optionA();
					progState = passwordScreen;
					input = 1;
					askForPass();
				}
				iterations = iterations + 1;
				
				// If the key pressed is key 2 and the screen is within reasonable bounds,
				// 'scroll' the screen up
				if (KP_GetKey() == Key_2 && screenNum < 5 && screenNum >= 0) {
					screenNum = screenNum - 1;
					lcd_clrscr();
					_delay_ms(200 * 16);
					input = 1;
				}
				
				// If the key pressed is key 8, do nothing
				else if (KP_GetKey() == Key_8 && screenNum < 5 && screenNum >= 0) {}
				
				// If the key pressed is Key A, do nothing
				else if (KP_GetKey() == Key_A) {}
					
				// If the key pressed is Key B, do nothing
				else if (KP_GetKey() == Key_B) {}
					
				// If the key pressed is Key C, allow the user to change the duration
				// before inactivity timeout
				else if (KP_GetKey() == Key_C) {
					KP_WaitRelease();
					newTimeFunc();
					progState = menu;
					input = 1;
				}
					
				// If the key entered is key D, allow the user to change the volume of the buzzer
				else if (KP_GetKey() == Key_D) {
					KP_WaitRelease();
					newVolumeFunc();
					progState = menu;
					input = 1;
				}
			}
		}
	}
}

/*
Description: This function handles the alarm program state. After three
				incorrect password guesses, the program enters the alarm
				state, where a red LED and buzzer are activated and the 
				lock is frozen until the correct password is entered.
Inputs: None
Outputs: None
*/
void alarmFunc(void) {
	char key = Key_None;		// Temporarily stores each user-entered character
	char guess[4];				// Stores the user's guess
	int guessIdx = 0;			// Used to index the guess array
	int highTime;				// The time (in ms) that the PWM signal is high
	int lowTime;				// The time (in ms) that the PWM signal is low
	
	// Clear the LCD and set the cursor home
	lcd_clrscr();
	lcd_home();
	
	// Send an "Alarm activated" message to the LCD
	lcd_puts("Alarm");
	lcd_gotoxy(0,1);
	lcd_puts("activated");
	
	// Activate the red LED
	PORTD |= 1 << 7;
		
	// Set the PWM signal low
	PORTC &= ~(1 << 4);		
	
	// Keep looping until the correct password is entered	
	while(progState == alarm) {
		
		// Set the PWM signal high
		PORTC |= (1 << 4);
		
		// Delay to hold the signal high
		if (volume == 1) {
			_delay_ms(5);
		} else if (volume == 2) {
			_delay_ms(7);
		} else if (volume == 3) {
			_delay_ms(10);
		}
		
		// Set the PWM signal low
		PORTC &= ~(1 << 4);
		
		// Delay to hold the signal low
		if (volume == 1) {
			_delay_ms(5);
		} else if (volume == 2) {
			_delay_ms(3);
		} else if (volume == 3) {
			_delay_us(0);
		}
		
		// Read a key value
		key = KP_GetKey();
		
		// If a key was pressed
		if (key != Key_None)
		{
			// If the key pressed was the F key ('#'), compare the guess to the actual password
			if (key == Key_F) {
				
				// If the guess entered is the same as the password...
				if (strncmp(password,guess,4) == 0) {
					
					// Clear the LCD and set the cursor home
					lcd_clrscr();
					lcd_home();
					
					// Send an "Alarm deactivated" message to the LCD
					lcd_puts("Alarm");
					lcd_gotoxy(0,1);
					lcd_puts("deactivated");
					
					// Delay for one second to hold the deactivation message
					for(int i = 0; i < (1 * 16); i++) {
						_delay_ms(1000);
					}
										
					// Deactivate the red LED
					PORTD ^= 1 << 7;
	
					// Activate the green LED
					PORTB ^= 1 << 4;
	
					// Delay for half a second to hold the green LED
					_delay_ms(500*16);
	
					// Deactivate the green LED
					PORTB ^= 1 << 4;
					
					// Return to the normal password screen
					askForPass();
					
					// Change the state of the program to menu
					progState = passwordScreen;
				}
				
				// Reset the index for the guess array to zero
				guessIdx = 0;
				
			}
			
			// If the key entered isn't the pound key or the asterisk key
			else {
				
				// Only allow four-character guesses
				if(guessIdx < 4) {
					
					// Keep track of the guess
					guess[guessIdx] = key;
					guessIdx = guessIdx + 1;
				}
			}
			
			// Wait to proceed until the key is released
			KP_WaitRelease();
		}
	}
}


/*
Description: This function handles the newTimeFunc program state. 
				It allows the user to adjust the volume of the 
				buzzer that gets triggered after three incorrect 
				guesses.
Inputs: None
Outputs: None
*/
void newTimeFunc(void) {
						
	// Will use this in a looping condition later
	progState = newTimeScreen;
		
	// Clear the LCD, set the cursor home
	lcd_clrscr();
	lcd_home();
		
	// Send an "Time before auto-lock" message to the LCD
	lcd_puts("Time before auto");
	lcd_gotoxy(0,1);
	lcd_puts("-lock (sec): ");
		
	char key = Key_None;		// Temporarily stores each user-entered character
	char time[3] = "000";		// Stores the user-entered time
	int timeIdx = 0;			// Used to index the time array
		
	// Keep looping until a new time is entered
	while(progState == newTimeScreen) {
			
		// Read a key value
		key = KP_GetKey();
			
		// If a key was pressed...
		if (key != Key_None)
		{
			// If the key pressed was key F ('#'), process the new time
			if (key == Key_F) {
					
				// Clear the LCD, set the cursor home
				lcd_clrscr();
				lcd_home();
					
				// Convert each of the characters in the time array to its integer equivalent
				int int1 = time[0] - '0';
				int int2 = time[1] - '0';
				int int3 = time[2] - '0';
				
				// If the user only entered one character...
				if (timeIdx == 1) {
					inactivitySeconds = int1;
				}
				
				// If the user entered two characters...
				else if (timeIdx == 2) {
					inactivitySeconds = (10 * int1) + int2;
				}
				
				// If the user entered three characters...
				else if (timeIdx == 3) {
					inactivitySeconds = (100 * int1) + (10 * int2) + int3;
				}
				
				// Display the new time
				lcd_puts("Auto-lock time");
				lcd_gotoxy(0,1);
				lcd_puts("is now: ");
					
				for(int i = 0; i < timeIdx; i++) {
					lcd_putc(time[i]);
				}
				
				lcd_puts(" sec");
					
				// Delay for three seconds to hold the new time message on the LCD
				for(int i = 0; i < (3*16); i++) {
					_delay_ms(1000);
				}
					
				// Break out of the function
				return;
			}
				
			// If the key entered was the E key ('*'), backspace
			else if (key == Key_E) {
					
				// Clear display and print new time message
				lcd_clrscr();
				lcd_home();
				lcd_puts("Time before auto");
				lcd_gotoxy(0,1);
				lcd_puts("-lock (sec): ");
					
				// Decrement timeIdx
				timeIdx = timeIdx - 1;
					
				// Display all characters entered for the new time, except the most previous one
				for(int i = 0; i < timeIdx; i++) {
					lcd_putc(time[i]);
				}
			}
				
			// If the key entered isn't the pound key or the asterisk key...
			else {
					
				// Allow maximum of three characters
				if(timeIdx < 3) {
						
					// Print the key on the LCD
					lcd_putc(key);
						
					// Keep track of the input characters
					time[timeIdx] = key;
					timeIdx = timeIdx + 1;
				}
			}
				
			// Wait to proceed until the key is released
			KP_WaitRelease();
		}
	}
}

/*
Description: This function handles toggling the volume of the 
				buzzer after option D is selected.
Inputs: None
Outputs: None
*/
void newVolumeFunc(void) {
	
	// Will use this in a looping condition later
	progState = newVolumeScreen;

	// Clear the LCD, set the cursor home
	lcd_clrscr();
	lcd_home();

	// Send an "Time before auto-lock" message to the LCD
	lcd_puts("Select volume: ");
	lcd_gotoxy(0,1);
	lcd_puts("1(lo), 2, 3(hi)");

	char key = Key_None;		// Temporarily stores each user-entered character

	// Keep looping until a new volume is entered
	while(progState == newVolumeScreen) {
	
		// Read a key value
		key = KP_GetKey();
	
		// If a key was pressed...
		if (key != Key_None)
		{
			// If the key pressed was key 1, set the volume to 1
			if (key == Key_1) {
				volume = 1;
			}
		
			// Otherwise, if the key pressed was 2, set the volume to 2
			else if (key == Key_2) {
				volume = 2;
			}
		
			// Otherwise, if the key pressed was 3, set the volume to 3
			else if (key == Key_3) {
				volume = 3;
			}
			
			// Clear the LCD, set the cursor home
			lcd_clrscr();
			lcd_home();
			
			// Display the new volume
			lcd_puts("Volume level is");
			lcd_gotoxy(0,1);
			lcd_puts("now: ");
			lcd_putc(volume + '0');
			
			// Delay for three seconds to hold the new volume message on the LCD
			for(int i = 0; i < (3*16); i++) {
				_delay_ms(1000);
			}

			// Wait to proceed until the key is released
			KP_WaitRelease();
			
			// Break out of the function
			return;
		}
	}
}

/*
Description: This function handles re-locking the door after option
				A is selected in the menu screen.
Inputs: None
Outputs: None
*/
void optionA(void) {
	
	// Clear the LCD and set the cursor home
	lcd_clrscr();
	lcd_home();
	lcd_puts("Locking");
	
	// Activate the red LED
	PORTD ^= 1 << 7;
	
	// Delay for half a second to hold the LCD message and the LED
	_delay_ms(500*16);
	
	// Clear the LCD, set the cursor home, send new locking message to LCD
	lcd_clrscr();
	lcd_home();
	lcd_puts("Locking.");
	
	// Delay for half a second to hold the new message on the LCD
	_delay_ms(500*16);
	
	// Clear the LCD, set the cursor home, send new locking message to LCD
	lcd_clrscr();
	lcd_home();
	lcd_puts("Locking..");
	
	// Delay for half a second to hold the new message on the LCD
	_delay_ms(500*16);
	
	// Clear the LCD, set the cursor home, send new locking message to LCD
	lcd_clrscr();
	lcd_home();
	lcd_puts("Locking...");
	
	// Call servoLock function to rotate the servo
	servoLock();
	
	// Deactivate the red LED
	PORTD ^= 1 << 7;
	
	// Activate the green LED
	PORTB ^= 1 << 4;
	
	// Delay for half a second to hold the green LED
	_delay_ms(500*16);
	
	// Deactivate the green LED
	PORTB ^= 1 << 4;
	
	return;
}

/*
Description: This function handles changing the password when option
				B is selected from within the menu screen.
Inputs: None
Outputs: None
*/
void optionB(void) {
	
	// Will use this in a looping condition later
	progState = newPasswordScreen;
	
	// Clear the LCD, set the cursor home
	lcd_clrscr();
	lcd_home();
	
	// Send "Enter new password: " message to LCD
	lcd_puts("Enter new");
	lcd_gotoxy(0,1);
	lcd_puts("password: ");
	
	int newPassIdx = 0;			// Used to index new password array
	char newPass[4] = "0000";	// Default value for new password
	char key = Key_None;		// Temporarily stores characters read in
	
	// Keep looping until a new password is entered
	while(progState == newPasswordScreen) {
		
		// Read a key value
		key = KP_GetKey();
		
		// If a key was pressed...
		if (key != Key_None)
		{
			// If the key pressed was key F ('#'), process the new password
			if (key == Key_F) {
				
				// Clear the LCD, set the cursor home
				lcd_clrscr();
				lcd_home();
				
				// Change the password to the new password
				for (int i = 0; i < 4; i++) {
					password[i] = newPass[i];
				}
				
				// Display the new password
				lcd_puts("Password is");
				lcd_gotoxy(0,1);
				lcd_puts("now: ");
				
				for(int i = 0; i < 4; i++) {
					lcd_putc(newPass[i]);
				}
				
				// Delay for three seconds to hold the new password message on the LCD
				for(int i = 0; i < (3*16); i++) {
					_delay_ms(1000);
				}
				
				// Break out of the function
				return;
			}
			
			// If the key entered was the E key ('*'), backspace
			else if (key == Key_E) {
				
				// Clear display and print new password message
				lcd_clrscr();
				lcd_home();
				lcd_puts("Enter new");
				lcd_gotoxy(0,1);
				lcd_puts("password: ");
				
				// Decrement guessIdx
				newPassIdx = newPassIdx - 1;
				
				// Display all characters entered for the new password, except the most previous one
				for(int i = 0; i < newPassIdx; i++) {
					lcd_putc(newPass[i]);
				}
			}
			
			// If the key entered isn't the pound key or the asterisk key...
			else {
				
				// Only allow four-character guesses
				if(newPassIdx < 4) {
					
					// Print the key on the LCD
					lcd_putc(key);
					
					// Keep track of the guess
					newPass[newPassIdx] = key;
					newPassIdx = newPassIdx + 1;
				}
			}
			
			// Wait to proceed until the key is released
			KP_WaitRelease();
		}
	}
}

/*
Description: This function handles activating the lock by rotating
				the servo motor clockwise.
Inputs: None
Outputs: None
*/
void servoLock(void) {
	// Counter used in the while loop below
	int count = 0;		
	
	// Set the servo output pin low
	PORTB &= ~(1 << 5);
	
	// Generate 50 pulses; this is enough to take the servo 
	// through its full range of motion
	while(count < 50) {
		
		// Set the servo output pin high
		PORTB |= (1 << 5);
		
		// Delay for one millisecond
		_delay_ms(16*1);
		
		// Set the servo output pin low
		PORTB &= ~(1 << 5);
		
		// Delay for 19 milliseconds
		_delay_ms(16*19);
		
		count = count + 1;
	}
	return;
}

/*
Description: This function handles deactivating the lock by rotating
				the servo motor counter clockwise.
Inputs: None
Outputs: None
*/
void servoUnlock(void) {
	// The counter used in the while loop below
	int count = 0;
	
	// Set the servo output pin low
	PORTB &= ~(1 << 5);
	
	// Generate 50 pulses; this is enough to take the servo
	// through its full range of motion
	while(count < 50) {
		
		// Set the servo output pin high
		PORTB |= (1 << 5);
		
		// Delay for two milliseconds
		_delay_ms(16*2);
		
		// Set the servo output pin low
		PORTB &= ~(1 << 5);
		
		// Delay for eighteen milliseconds
		_delay_ms(16*18);
		
		count = count + 1;
	}
	return;
}
	
/*
Description: The main function; this function drives the program.
Inputs: None
Outputs: None 
*/
void main(void)
{
    lcd_init(LCD_DISP_ON_CURSOR);	// Initialize the LCD, make the cursor visible
	KP_Setup();						// Initialize the keypad
	
	// Set outputs 
	DDRB = DDRB | 0x30;
	DDRC = DDRC | 0x10;
	DDRD = DDRD | 0x80;

	// Set the buzzer output line low
	PORTC &= ~(1 << 4);
		
	// Display the password prompt on the LCD
	askForPass(); 
	
	// Put the servo in the locked position
	servoLock();
	
	// Loop indefinitely, checking the program state and 
	// calling the corresponding function
	while(1) {
		
		if(progState == passwordScreen) {
			passwordScreenFunc();
		}
		
		else if (progState == menu) {
			menuScreenFunc();
		}
		
		else if (progState == alarm) {
			alarmFunc();
		}
	}
}