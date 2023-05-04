/*
 * main.c
 * Created: 4/11/2022 12:24:16 PM
 * Author: John Elwart
 */ 

#define FOSC 16000000
#define BAUD 9600
#define MYUBRR FOSC/16/BAUD-1
#define F_CPU 8000000

#include <avr/io.h>
#include <string.h>
#include <math.h>
#include <stdio.h>
#include <util/delay.h>
#include "twimaster.c"

int voltage[64] = {128,141,153,165,177,188,199,209,219,227,234,241,246,250,254,255,
				   255,255,254,250,246,241,234,227,219,209,199,188,177,165,153,141,
				   128,115,103,91,79,68,57,47,37,29,22,15,10,6,2,1,
				   0,1,2,6,10,15,22,29,37,47,57,68,79,91,103,115};

/*
This function initializes the USART within the micro controller.
It was taken from the ATMega328P data sheet on ICON.
*/
void USART_Init(unsigned int ubrr) {
	// Set baud rate
	UBRR0H = (unsigned char)(ubrr>>8);
	UBRR0L = (unsigned char)ubrr;
	
	// Enable receiver and transmitter
	UCSR0B = (1<<RXEN0)|(1<<TXEN0);
	
	// Set frame format: 8data, 2stop bit
	UCSR0C = (1<<USBS0)|(3<<UCSZ00);
}

void ADC_Init(void) {
	/*
	Bit 6 - Sets the power supply voltage as the reference voltage for the ADC
	Bit 3:0 - Sets the ADC pin to PC0 (0000)
	*/
	ADMUX = 0b01000000;
	
	/*
	Bit 7 - ADC Enable
	Bit 6 - Set high at every new conversion
	Bit 2:0 - ADC Prescaler 128
	*/
	ADCSRA = 0b10000111;
}

/*
This function transmits one character of data to be displayed within the Arduino output window.
It was taken from the ATMega328P data sheet on ICON.
*/
void USART_Transmit(unsigned char data) {
	// Wait for empty transmit buffer
	while (!(UCSR0A & (1<<UDRE0)));
	
	// Put data into buffer, sends the data
	UDR0 = data;
}

/*
This function receives and returns one user-input character from the Arduino command line.
It was taken from the ATMega328P data sheet on ICON.
*/
unsigned char USART_Receive(void) {
	// Wait for data to be received
	while (!(UCSR0A & (1<<RXC0)));
	
	// Get and return received data from buffer
	return UDR0;
}

/*
This function repeatedly calls USART_Transmit, effectively allowing us 
to transmit a string of characters to the Arduino output window.
*/
void sendString(char string[]) {
	for(int i = 0; i < strlen(string); i++) {
		USART_Transmit(string[i]);
	}
}

void getVoltage(void) {
	float adcVal;
	char sVoltage[50];
	
	// Set the ADSC bit, initiating the start of a single conversion
	ADCSRA = 0b11000111;
	
	// Wait for the new ADSC value to latch
	while(ADCSRA & (1<<ADSC));
	
	// Read in the value of the ADC data registers (ADCH:ADCL)
	adcVal = ADC;
	
	// Convert the value read in to a voltage reading
	adcVal = (5*adcVal)/1024.0;
	
	// Convert the float voltage to a string
	dtostrf(adcVal, 5, 3, sVoltage);

	// Send the voltage string to the Arduino terminal
	sendString("v = ");
	sendString(sVoltage);
	sendString(" V\n");
}

/*
This function gets called whenever the user enters an 'M' command.
It takes a variable number of voltage measurements and displays the
results in the Arduino output window. The number of measurements to
be taken and the time to delay between measurements is determined
by the two input parameters.
*/
void getVoltageMultiple(int numIntervals, int lengthIntervals)
{
	// Start our time at zero
	int t = 0;

	// Iterate numIntervals number of times
	for(int i = 0; i < numIntervals; i++) {
		// Compute the char equivalent of t, stick it in a character array
		char sIntervals[50];
		itoa(t,sIntervals,10);
		
		/*
		Display a message of the following format to the Arduino output window:
		"t = <x> s, v = <y> V"
		where x is the current time and y is the voltage measurement
		*/
		sendString("t = ");
		sendString(sIntervals);
		sendString(" s, ");
		getVoltage();
		
		// Increment t by the interval duration
		t = t + lengthIntervals;
		
		/*
		Delay the program for lengthIntervals seconds
		note: we must multiply lengthIntervals by 16 to account for the clock speed (16 MHz)
		*/
		for(int j = 0; j < (lengthIntervals * 16); j++)
		{
			_delay_ms(1000);
		}
	}
}

/*
This function is called whenever an 'S' command is asserted. Its purpose
is to set the output voltage level on one of the two output pins on the
DAC. The output pin and the voltage level are specified by the function's
two parameters.
*/
void talkToDAC(int channel, float voltage) {
	/*
	Assert the start signal and send the DAC address
	bits 0 and 3-7 are factory programmed and their values are specified in the DAC data sheet (Figure 6 page 8)
	bits 1 and 2 need to be zero, since AD0 and AD1 on our DAC are grounded
	*/
	i2c_start(0b01011000);
	
	/*
	 Create the bit string to be passed into the command write to the DAC
	 this will differ depending on what channel needs to be set (DAC data sheet figure 7 page 9):
		- bits 5-7 are reserved and must always be zero
		- bit 4 is the reset bit; set if you want to reset the DAC outputs, otherwise clear
		- bit 3 is the power down bit; set if you want to power down the DAC, otherwise clear
		- bits 2 and 1 are don't cares
		- bit 0 determines the output pin to set (0 for OUT0, 1 for OUT1)
	*/
	char command;
	if(channel == 0) {
		command = 0b00000000;
	} else {
		command = 0b00000001;
	}
	
	// Write the command byte to the DAC
	i2c_write(command);
	
	// Calculate the byte that needs to be written to the DAC to set the output voltage
	// using the rewritten formula given in the unipolar code table in the DAC data sheet
	int outputWriteVal = (255 * voltage) / 5;
	
	// Write the output byte to the DAC
	i2c_write(outputWriteVal);
	
	// Assert the stop signal
	i2c_stop();
}

/*
This function is called at the end of readW. It creates the waveform by iterating over the voltage
array and passes each decimal value to the DAC over i2 and then delays for a uniform amount of time
for each interval.
*/
void createWaveform (int channel, double freq, int num) {
	
	// Determines the delay based off the passed in frequency
	double totTime = 1.0/freq;
	int delay = (totTime / 64.0) * 1000000.0;
	char command;
	
	// Loops over the voltage array for num amount of times and sends each value to the DAC over i2c
	for (int i = 0; i < num; i++) {
		for (int j = 0; j < sizeof(voltage) / sizeof(voltage[0]); j++) {
			/*
			Assert the start signal and send the DAC address
			bits 0 and 3-7 are factory programmed and their values are specified in the data sheet
			bits 1 and 2 need to be zero, since AD0 and AD1 on our DAC are grounded
			*/
			i2c_start(0b01011000);
			
			/*
			 Create the bit string to be passed into the command write to the DAC
			 this will differ depending on what channel needs to be set:
				- bits 5-7 are reserved and must always be zero
				- bit 4 is the reset bit; set if you want to reset the DAC outputs, otherwise clear
				- bit 3 is the power down bit; set if you want to power down the DAC, otherwise clear
				- bits 2 and 1 are don't cares
				- bit 0 determines the output pin to set (0 for OUT0, 1 for OUT1)
			*/
			if (channel == 0) {
				command = 0b00000000;
			} else {
				command = 0b00000001;
			}
			
			// Write the command byte to the DAC
			i2c_write(command);
			
			// Write the output byte to the DAC
			i2c_write(voltage[j]);
			
			// Generate delay
			for (int k = 0; k < delay; k++) {
				_delay_us(1);
			}
			
			// Assert the stop signal
			i2c_stop();
		}
	}
	// Indicate when the waveform generation is done
	sendString("\nDone\n");
}

/*
This function is called when an 'M' character is received in the main function.
It reads in the remaining characters for an 'M' command, converts the two 
parameters from chars to ints, and then sends them getVoltageMultiple 
to be processed.
*/
void readM(void) {
	char intervals1char,
		 intervals2char,
		 length1char,
		 length2char;
	int intervals1int = 0,
		intervals2int = 0,
		length1int = 0,
		length2int = 0,
	    intervals = 0,		// The number of measurements (n)
	    length = 0;			// Time between measurements (dt)			
	
	// Read in the comma that comes after 'M'	
	USART_Receive();
	
	/*
	Read in the next two characters after the comma
	assuming the format specified in the lab description:
		- intervals1char must be a digit
		- intervals2char may either be a digit or a comma
	*/
	intervals1char = USART_Receive();
	intervals2char = USART_Receive();
	
	// If intervals2char is a digit...
	if (intervals2char != ',') {
		// Convert intervals1char and intervals2char to ints
		intervals1int = intervals1char - '0';
		intervals2int = intervals2char - '0';
		
		// Use the two integers to compute the two-digit number of measurements
		intervals = 10 * intervals1int + intervals2int;
		
		// Read in the comma after the second digit
		USART_Receive();
	} else {
		// Convert intervals1char to an int
		intervals1int = intervals1char - '0';
		
		// Intervals1int will the be total number of intervals
		intervals = intervals1int;
	}
	
	/*
	At this point, we've read in the first parameter and processed it...
	now, read in the second parameter in a very similar manner
	*/
	length1char = USART_Receive();
	length2char = USART_Receive();
	
	// Must check for newline instead of comma
	if (length2char != '\n') {
		length1int = length1char - '0';
		length2int = length2char - '0';
		length = 10 * length1int + length2int;
	} else {
		length1int = length1char - '0';
		length = length1int;
	}
	
	// Make sure the entered number of intervals is valid
	if ((intervals < 2) || (intervals > 20)) {
		sendString("Invalid intervals input...\n");
		return;
	}
	
	// Make sure the entered interval length is valid
	if ((length < 1) || (length > 10)) {
		sendString("Invalid length input...\n");
		return;
	}
	
	// Pass our two numbers to getVoltageMultiple to display the output
	getVoltageMultiple(intervals, length);
}

//This function gets called whenever an 'S' command is entered.
void readS(void) {
	char channelChar,
	     voltageChar0,
		 decimal,
		 voltageChar2,
		 voltageChar3;
	int voltageInt0,
		voltageInt2,
		voltageInt3,
		channelInt;
	float dacVoltage;
	
	// Read in the first comma
	USART_Receive();
	
	// Read in the channel character (c)
	channelChar = USART_Receive();
	
	// Read in the second comma
	USART_Receive();
	
	// Read in the 3-digit voltage value (v) (the decimal as well)
	voltageChar0 = USART_Receive();
	decimal = USART_Receive();
	voltageChar2 = USART_Receive();
	voltageChar3 = USART_Receive();
	
	// Convert each of the three voltage digit characters to its corresponding int value
	voltageInt0 = voltageChar0 - '0';
	voltageInt2 = voltageChar2 - '0';
	voltageInt3 = voltageChar3 - '0';
	
	// Use the three voltage integers to compute the actual voltage value
	dacVoltage = voltageInt0 + (0.1 * voltageInt2) + (0.01 * voltageInt3);
		
	// Convert the channel char ('0' or '1') to its corresponding int value
	channelInt = channelChar - '0';
	
	// Make sure the channel input is valid
	if ((channelInt != 1) && (channelInt != 0)) {
		sendString("Invalid channel input...\n");
		return;
	}
	
	// Make sure the voltage input is valid
	if ((dacVoltage > 5.00) || (dacVoltage < 0.00)) {
		sendString("Invalid voltage input...\n");
		return;
	}
	
	// Display a message of the following format to the Arduino output window:
	// "DAC channel <c> set to <n.nn> V"
	// where c is the channel to be set and n.nn is the three-digit voltage
	sendString("DAC channel ");
	USART_Transmit(channelChar);
	sendString(" set to ");
	USART_Transmit(voltageChar0);
	USART_Transmit(decimal);
	USART_Transmit(voltageChar2);
	USART_Transmit(voltageChar3);
	sendString(" V\n");
		
	// Pass the channel int and the voltage value to talkToDAC, which will communicate with the DAC 
	talkToDAC(channelInt, dacVoltage);
}

void readW(void) {
	int channelInt,
	    frequencyInt0,
		frequencyInt1,
	    numWaves,
	    digits,
	    frequency;
	char channelChar,
	     frequencyChar0,
		 frequencyChar1,
		 numWavesChar0 = ' ',
		 numWavesChar1 = ' ',
		 numWavesChar2 = ' ';
	
	// read in the first comma
	USART_Receive();
		
	// read in the channel character (c)
	channelChar = USART_Receive();
		
	// read in the second comma
	USART_Receive();
	
	// Read in the two characters that make up the frequency value
	frequencyChar0 = USART_Receive();
	frequencyChar1 = USART_Receive();
		
	// read in the third comma
	USART_Receive();
	
	// Convert the two frequency characters to integers and combine them to make one frequency value
	frequencyInt0 = frequencyChar0 - '0';
	frequencyInt1 = frequencyChar1 - '0';
	frequency = (10 * frequencyInt0) + frequencyInt1;
		
	// Read in the characters for the number of consecutive waves
	numWavesChar0 = USART_Receive();
	numWavesChar1 = USART_Receive();
	if (numWavesChar1 != '\n') {
		numWavesChar2 = USART_Receive();
	}

	// Create the full number of waves to pass into the createWaveform function
	if (numWavesChar1 == '\n') {
		numWaves = numWavesChar0 - '0';
		digits = 1;
	} else if (numWavesChar2 == '\n') {
		numWaves = ((numWavesChar0 - '0') * 10) + (numWavesChar1 - '0');
		digits = 2;
	} else {
		numWaves = ((numWavesChar0 - '0') * 100) + ((numWavesChar1 - '0') * 10) + (numWavesChar2 - '0');
		digits = 3;
	}
	
	// Convert the channel character into a integer
	channelInt = channelChar - '0';
	
	// Make sure the channel input is valid
	if ((channelInt != 1) && (channelInt != 0)) {
		sendString("Invalid channel input...\n");
		return;
	}
	
	// Make sure the frequency is valid
	if ((frequency < 10) || (frequency > 20)) {
		sendString("Invalid frequency input...\n");
		return;
	}
	
	// Make sure the number of waveforms is valid
	if ((numWaves < 1) || (numWaves > 100)) {
		sendString("Invalid number of waves...\n");
		return;
	}
	
	// Print the feedback message to the serial monitor
	sendString("Generating ");
	if (digits == 1) {
		USART_Transmit(numWavesChar0);
	} else if (digits == 2) {
		USART_Transmit(numWavesChar0);
		USART_Transmit(numWavesChar1);
	} else if (digits == 3) {
		USART_Transmit(numWavesChar0);
		USART_Transmit(numWavesChar1);
		USART_Transmit(numWavesChar2);
	}
	sendString(" sine wave cycles with f=");
	USART_Transmit(frequencyChar0);
	USART_Transmit(frequencyChar1);
	sendString(" Hz on DAC channel ");
	USART_Transmit(channelChar);
	
	// Call the function to create the sine waveform
	createWaveform(channelInt, (double)frequency, numWaves);	
}

/*
This is the main function of the program. Its sole purpose is to
initialize the USART, ADC, and i2c, and repeatedly check for either
a 'G', 'M', 'S', or 'W' from the Arduino command line.
*/
int main(void) {
	USART_Init(MYUBRR);	// initialize USART
	ADC_Init();			// initialize ADC
	i2c_init();			// initialize i2c
	
	// Loop indefinitely
	while (1) {
		// Get a character from the Arduino command line
		char letter = USART_Receive();
		
		// Call the appropriate function, depending on what the character is
		if (letter == 'G') {
			getVoltage();
			USART_Transmit('\n');
		} else if (letter == 'M') {
			readM();
			USART_Transmit('\n');
		} else if (letter == 'S') {
			readS();
			USART_Transmit('\n');
		} else if (letter == 'W') {
			readW();
			USART_Transmit('\n');
		}
	}
}