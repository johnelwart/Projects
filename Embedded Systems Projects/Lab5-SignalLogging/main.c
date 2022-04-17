/* Lab05.c
 *
 * Created: 4/11/2022 12:24:16 PM
 * Authors: John Elwart and Tucker Dickson
 */ 

#include <avr/io.h>
#include <string.h>
#include <math.h>
#include <stdio.h>
#include <util/delay.h>
#include "twimaster.c"

#define FOSC 16000000				// Clock speed
#define BAUD 9600					// Baud rate
#define MYUBRR FOSC/16/BAUD-1

/* This function initializes the USART within the micro controller.
   It was taken from the ATMega328P data sheet on ICON. */
void USART_Init(unsigned int ubrr) {
	/*Set baud rate */
	UBRR0H = (unsigned char)(ubrr>>8);
	UBRR0L = (unsigned char)ubrr;
	
	// Enable receiver and transmitter
	UCSR0B = (1<<RXEN0) | (1<<TXEN0);
	
	// Set frame format: 8data, 2stop bit
	UCSR0C = (1<<USBS0) | (3<<UCSZ00);
}



void ADC_Init(void) {
	ADMUX = 0b01000000;
	ADCSRA = 0b10000111;
}



/* This function transmits one character of data to be displayed within the Arduino output window.
   It was taken from the ATMega328P data sheet on ICON. */
void USART_Transmit(unsigned char data) {
	while (!( UCSR0A & (1<<UDRE0)));       // Wait for empty transmit buffer
	UDR0 = data;                           // Put data into buffer, sends the data
}



/* This function receives and returns one user-input character from the Arduino command line.
   It was taken from the ATMega328P data sheet on ICON. */
unsigned char USART_Receive(void) {
	while (!(UCSR0A & (1<<RXC0)));         // Wait for data to be received
	return UDR0;                           // Get and return received data from buffer
}



/* This function repeatedly calls USART_Transmit, effectively allowing us 
   to transmit a string of characters to the Arduino output window. */
void sendString(char string[]) {
	for (int i = 0; i < strlen(string); i++) {
		USART_Transmit(string[i]);
	}
}



// This function gets called whenever a 'G' character is recieved in the main function.
void getVoltage(void) {
	ADCSRA = 0b11000111;
	while (ADCSRA & (1<<ADSC));
	
	float adcVal;
	adcVal = ADC;
	adcVal = (5*adcVal) / 1024.0;
	
	char sVoltage[50];		
	ftoa(adcVal, sVoltage, 3);

	sendString("v = ");
	sendString(sVoltage);
	sendString(" V\n");
}



/* This function is called when an 'M' character is received in the main function.
   It reads in the remaining characters for an 'M' command, converts the two 
   parameters from chars to ints, and then sends them getVoltageMultiple 
   to be processed. */
void readM(void) {
	int intervals = 0;		// The number of measurements (n)
	int length = 0;			// Time between measurements (dt)			
	
	// read in the comma that comes after 'M'	
	char comma = USART_Receive();
	
	/* Read in the next two characters after the comma
	   assuming the format specified in the lab description:
		- Intervals1char must be a digit
		- Intervals2char may either be a digit or a comma */
	char intervals1char = USART_Receive();
	char intervals2char = USART_Receive();
	
	// If intervals2char is a digit...
	if (intervals2char != ',') {
		// Convert intervals1char and intervals2char to ints
		int intervals1int = intervals1char - '0';
		int intervals2int = intervals2char - '0';
		
		// Use the two ints to compute the two-digit number of measurements
		intervals = 10 * intervals1int + intervals2int;
		
		// Read in the comma after the second digit
		comma = USART_Receive();
	}
	// Otherwise, intervals2char is a comma...
	else {
		// convert intervals1char to an int
		int intervals1int = intervals1char - '0';
		
		// intervals1int will the be total number of intervals
		intervals = intervals1int;
	}
	
	// Now, read in the second parameter in a very similar manner
	char length1char = USART_Receive();
	char length2char = USART_Receive();
	
	// must check for newline instead of comma
	if (length2char != '\n') {
		int length1int = length1char - '0';
		int length2int = length2char - '0';
		length = 10 * length1int + length2int;
	}
	else {
		int length1int = length1char - '0';
		length = length1int;
	}
	
	// Pass our two numbers to getVoltageMultiple to display the output
	getVoltageMultiple(intervals, length);
}



// This function gets called whenever an 'S' command is entered.
void readS(void) {
	int channel = 0;		// The DAC channel whose voltage will be set (OUT0 or OUT1)
	float voltage = 0;		// The voltage level to which the DAC channel will be set
	
	// Read in the first comma
	char comma = USART_Receive();
	
	// Read in the channel character (c)
	char channelChar = USART_Receive();
	
	// Read in the second comma
	comma = USART_Receive();
	
	// Read in the 3-digit voltage value (v) (the comma as well)
	char voltageChar0 = USART_Receive();
	char decimal = USART_Receive();
	char voltageChar1 = USART_Receive();
	char voltageChar2 = USART_Receive();
	
	/* Display a message of the following format to the Arduino output window:
	   "DAC channel <c> set to <n.nn> V"
	   where c is the channel to be set and n.nn is the three-digit voltage */
	sendString("DAC channel ");
	USART_Transmit(channelChar);
	sendString(" set to ");
	USART_Transmit(voltageChar0);
	USART_Transmit(decimal);
	USART_Transmit(voltageChar1);
	USART_Transmit(voltageChar2);
	sendString(" V\n");	
	
	// Convert each of the three voltage digit characters to its corresponding int value
	int voltageInt0 = voltageChar0 - '0';
	int voltageInt1 = voltageChar1 - '0';
	int voltageInt2 = voltageChar2 - '0';
	
	// Use the three voltage ints to compute the actual voltage value
	float dacVoltage = voltageInt0 + (0.1 * voltageInt1) + (0.01 * voltageInt2);
	
	// Convert the channel char ('0' or '1') to its corresponding int value
	int channelInt = channelChar - '0';
	
	// Pass the channel int and the voltage value to talkToDAC, which will communicate with the DAC 
	talkToDAC(channelInt, dacVoltage);
}



/* This function gets called whenever the user enters an 'M' command.
   It takes a variable number of voltage measurements and displays the
   results in the Arduino output window. The number of measurements to 
   be taken and the time to delay between measurements is determined
   by the two input parameters. */
void getVoltageMultiple(int numIntervals, int lengthIntervals) {
	// start our time at zero
	int t = 0;

	// iterate numIntervals number of times
	for (int i = 0; i < numIntervals; i++) {
		
		// compute the char equivalent of t, stick it in a character array
		char sIntervals[50];
		itoa(t,sIntervals,10);
		
		/* Display a message of the following format to the Arduino output window:
		   "t = <x> s, v = <y> V"
		   where x is the current time and y is the voltage measurement */
		sendString("t = ");
		sendString(sIntervals);
		sendString(" s, ");
		getVoltage();
		
		// increment t by the interval duration
		t = t + lengthIntervals;
		
		/* Delay the program for lengthIntervals seconds
		   Note: we must multiply lengthIntervals by 16 to account for the clock speed (16 MHz) */
		for (int j = 0; j < (lengthIntervals * 16); j++) {
			_delay_ms(1000);
		}
	}	
}



/* This function is called whenever an 'S' command is asserted. Its purpose
   is to set the output voltage level on one of the two output pins on the 
   DAC. The output pin and the voltage level are specified by the function's
   two parameters. */
void talkToDAC(int channel, float voltage) {
	
	/* Assert the start signal and send the DAC address
	    - Bits 0 and 3-7 are factory programmed and their values are specified in the data sheet
	    - Bits 1 and 2 need to be zero, since AD0 and AD1 on our DAC are grounded */
	char startResp = i2c_start(0b01011000);
		
	/* Create the bit string to be passed into the command write to the DAC
	   this will differ depending on what channel needs to be set:
	  	- Bits 5-7 are reserved and must always be zero
	  	- Bit 4 is the reset bit; set if you want to reset the DAC outputs, otherwise clear
	  	- Bit 3 is the power down bit; set if you want to power down the DAC, otherwise clear
	  	- Bits 2 and 1 are don't cares
	  	- Bit 0 determines the output pin to set (0 for OUT0, 1 for OUT1) */
	char command;
	
	if (channel == 0) {
		command = 0b00000000;
	}
	else {
		command = 0b00000001;
	}
	
	// write the command byte to the DAC
	char commandResp = i2c_write(command);
		
	/* calculate the byte that needs to be written to the DAC to set the output voltage
	   using the rewritten formula given in the unipolar code table in the DAC data sheet */
	int outputWriteVal = (256 * voltage) / 5;
	
	// write the output byte to the DAC
	char outputResp = i2c_write(outputWriteVal);
	
	// assert the stop signal
	i2c_stop();
}



// code for reverse, intToStr, and ftoa from: https://www.geeksforgeeks.org/convert-floating-point-number-string/
void reverse(char* str, int len) {
	int i = 0, j = len - 1, temp;
	
	while (i < j) {
		temp = str[i];
		str[i] = str[j];
		str[j] = temp;
		i++;
		j--;
	}
}



/* Converts a given integer x to string str[]. d is the number of digits required in the output.
   If d is more than the number of digits in x, then 0s are added at the beginning. */
int intToStr(int x, char str[], int d) {
	int i = 0;
	
	while (x) {
		str[i++] = (x % 10) + '0';
		x = x / 10;
	}
	
	/* If number of digits required is more, then
	   add 0s at the beginning */
	while (i < d) {
	str[i++] = '0';
	}
	
	reverse(str, i);
	str[i] = '\0';
	return i;
}



void ftoa(float n, char* res, int afterpoint) {
	// Extract integer part
	int ipart = (int)n;
	
	// Extract floating part
	float fpart = n - (float)ipart;
	
	// convert integer part to string
	int i = intToStr(ipart, res, 0);
	
	// check for display option after point
	if (afterpoint != 0) {
		res[i] = '.'; // add dot
		
		/* Get the value of fraction part up to given number
		   of points after dot. The third parameter
		   is needed to handle cases like 233.007 */
		fpart = fpart * pow(10, afterpoint);
		
		intToStr((int)fpart, res + i + 1, afterpoint);
	}
}



/* This is the main function of the program. Its sole purpose is to
   initialize the USART, ADC, and i2c, and repeatedly check for either
   a 'G', 'M', or 'S' from the Arduino command line. */
int main(void) {
	USART_Init(MYUBRR);	// initialize USART
	ADC_Init();			// initialize ADC
	i2c_init();			// initialize i2c
	
	// loop indefinitely
	while (1) {
		// get a character from the Arduino command line
		char letter = USART_Receive();
		
		// call the appropriate function depending on what the character is
		if (letter == 'G') {
			getVoltage();
			USART_Transmit('\n');
		}
		else if (letter == 'M') {
			readM();
			USART_Transmit('\n');
		}
		else if (letter == 'S') {
			readS();
			USART_Transmit('\n');
		}
	}
}