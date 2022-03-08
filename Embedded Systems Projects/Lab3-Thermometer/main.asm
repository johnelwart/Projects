; Lab3.asm
;
; Created: 2/27/2022 9:56:23 AM
; Author : John Elwart & Tucker Dickson

;	Registers being used

;	R16 - Holds the value for the right display
;	R17 - Status register
;	R18 - Holds the value for the left display
;	R19 - Mode choice. 0 if on current temp mode (Tc), 1 if on desired temp mode (Td)
;	R20 - Saved value for the ones place of desired temp
;	R21 - Saved value for the tens place of desired temp
;	R22 - Saves the current temperature
;	R23 - Constant for the division
;		  Holds value moved into for conversion at end of tempSensor
;	R24 - Result of the division
;	R25 - Remainder of the division
;	R26 - Keeps track of 1s from the A terminal of the RPG in the debounce subroutines
;         If the button is pressed
;         Counter for the outer loop of the delays
;	R27 - Keeps track of 1s from the B terminal of the RPG in the debounce subroutines 
;		  If the button is not pressed
;         Counter for the middle loop of the delays
;         Used as the decrement in timerLoop
;	R28 - Decrement for all three debounce subroutine loops
;         Counter for the inner loop of the delays
;		  Used for count in timerLoop
;	R29 - Loop counter for various loops in code
;         Loop counter for the division loop
;	R30 - Used in timer and timer loop as tmp and tmp1 substitute
;	R31 - Used in timer and timer loop as tmp2 substitute

	; Definitions for the divide subroutine
	.def	drem8u	=r25		; Remainder
	.def	dres8u	=r24		; Result
	.def	dd8u	=r22		; Dividend
	.def	dv8u	=r23		; Divisor

; Outputs
sbi DDRB, 0            ; SER
sbi DDRB, 1            ; SRCLK
sbi DDRB, 2            ; RCLK
sbi DDRD, 3			   ; LED control signal

; Inputs
cbi DDRB, 3            ; Button 1
sbi PORTB, 3 

cbi PINB, 5			   ; LED input

cbi DDRD, 4            ; Rotary Pulse Generator A
sbi PORTD, 4

cbi DDRD, 5		       ; Rotary Pulse Generator B
sbi PORTD, 5

cbi PORTD,3            ; Sets the LED to off by default

ldi R19, 0b00000000    ; Sets the mode to current temperature mode
ldi R20, 0b10111111    ; Sets the saved desired ones place to zero with a decimal place
ldi R21, 0b10111111    ; Sets the saved desired tens place to zero with a decimal place

start:                 ; Start of main program

rcall display

	sbis PIND,4        ; Checks for a signal from the A terminal of the RPG
	rjmp debounce2     ; Jumps to the incrementing debounce

continue:
	sbis PIND,5        ; Checks for a signal from the B terminal of the RPG
	rjmp debounce3     ; Jumps to the decrementing debounce

	sbic PINB, 3       ; Checks for input from the push button
	rjmp debounce1     ; Jumps to the button debounce

	rcall display

	cpi R19, 0         ; If in mode 1, branch to a subroutine that utilizes the temperature sensor
	breq useSensor

	rjmp start         ; If not, jump back to start

useSensor:		
		rcall tempSensor      ; Calls tempSensor subroutine to update the temp and check if the LED needs to be on
		rcall display
		rcall delay_second    ; Delays for 1 second before checking again
		rjmp start            ; Jumps back to start

divide:
	ldi dv8u, 10

	div8u:	
		sub	drem8u,drem8u		; Clear remainder and carry
		ldi	R29,9				; Initiate loop counter
	d8u_1:	
		rol dres8u
		rol	dd8u				; Shift left dividend
		dec	R29					; Decrement counter
		brne	d8u_2			; If done

		mov R16, drem8u         ; Move the reminder into R16 to convert and display
		mov R18, dres8u         ; Move the result into R18 to convert and display

		ret						; Return
	d8u_2:	
		rol	drem8u				; Shift dividend into remainder
		sub	drem8u,dv8u			; Remainder = remainder - divisor
		brcc	d8u_3			; If result negative
		add	drem8u,dv8u			; Restore remainder
		clc						; Clear carry to be shifted into result
		rjmp	d8u_1			; Else
	d8u_3:	
		sec						; Set carry to be shifted into result
		rjmp	d8u_1

mode:                       ; Return point after the button debounce
	cpi R19, 0              ; Checks if the mode is in 1 or 2
	brne switch             ; If in Mode 2, branch to switch to set the decimal places

	cbr R16, 0b10000000     ; Clears the decimal places in both displays
	cbr R18, 0b10000000
	rjmp start

	switch:
	sbr R16, 0b10000000     ; Sets the decimal places in both displays
	sbr R18, 0b10000000
	rjmp start

increment:
	; Start of comparisons for right display
	; If R16 has the pattern for 0
	cpi R16, 0b10111111
	breq case_0A

	; If R16 has the pattern for 1
	cpi R16, 0b10000110
	breq case_1A

	; If R16 has the pattern for 2
	cpi R16, 0b11011011
	breq case_2A

	; If R16 has the pattern for 3
	cpi R16, 0b11001111
	breq case_3A

	; If R16 has the pattern for 4
	cpi R16, 0b11100110
	breq case_4A

	; If R16 has the pattern for 5
	cpi R16, 0b11101101
	breq case_5A

	; If R16 has the pattern for 6
	cpi R16, 0b11111101
	breq case_6A

	; If R16 has the pattern for 7
	cpi R16, 0b10000111
	breq case_7A

	; If R16 has the pattern for 8
	cpi R16, 0b11111111
	breq case_8A

	; If R16 has the pattern for 9
	cpi R16, 0b11101111
	breq case_9A

; Start of case A subroutines
; Load the pattern for 1
case_0A:
	ldi R16, 0b10000110
	rjmp start
	
; Load the pattern for 2
case_1A:
	ldi R16, 0b11011011
	rjmp start

; Load the pattern for 3
case_2A:
	ldi R16, 0b11001111
	rjmp start

; Load the pattern for 4
case_3A:
	ldi R16, 0b11100110
	rjmp start

; Load the pattern for 5
case_4A:
	ldi R16, 0b11101101
	rjmp start

; Load the pattern for 6
case_5A:
	ldi R16, 0b11111101
	rjmp start

; Load the pattern for 7
case_6A:
	ldi R16, 0b10000111
	rjmp start

; Load the pattern for 8
case_7A:
	ldi R16, 0b11111111
	rjmp start

; Load the pattern for 9
case_8A:
	ldi R16, 0b11101111
	rjmp start

; Load the pattern for 0 on the right display and then determine the value
; to be displayed on the left display
case_9A:
	ldi R16, 0b10111111

	; If R18 has the pattern for 0
	cpi R18, 0b10111111
	breq case_0B

	; If R18 has the pattern for 1
	cpi R18, 0b10000110
	breq case_1B

	; If R18 has the pattern for 2
	cpi R18, 0b11011011
	breq case_2B

	; If R18 has the pattern for 3
	cpi R18, 0b11001111
	breq case_3B

	; If R18 has the pattern for 4
	cpi R18, 0b11100110
	breq case_4B

	; If R18 has the pattern for 5
	cpi R18, 0b11101101
	breq case_5B

	; If R18 has the pattern for 6
	cpi R18, 0b11111101
	breq case_6B

	; If R18 has the pattern for 7
	cpi R18, 0b10000111
	breq case_7B

	; If R18 has the pattern for 8
	cpi R18, 0b11111111
	breq case_8B

	; If R18 has the pattern for 9
	cpi R18, 0b11101111
	breq case_9B

; Start of case B subroutines
; Load the pattern for 1
case_0B:
	ldi R18, 0b10000110
	rjmp start
	
; Load the pattern for 2
case_1B:
	ldi R18, 0b11011011
	rjmp start

; Load the pattern for 3
case_2B:
	ldi R18, 0b11001111
	rjmp start

; Load the pattern for 4
case_3B:
	ldi R18, 0b11100110
	rjmp start

; Load the pattern for 5
case_4B:
	ldi R18, 0b11101101
	rjmp start

; Load the pattern for 6
case_5B:
	ldi R18, 0b11111101
	rjmp start

; Load the pattern for 7
case_6B:
	ldi R18, 0b10000111
	rjmp start

; Load the pattern for 8
case_7B:
	ldi R18, 0b11111111
	rjmp start

; Load the pattern for 9
case_8B:
	ldi R18, 0b11101111
	rjmp start

; Loads the pattern for 9 on both displays to keep it in bounds
case_9B:
	ldi R16, 0b11101111
	ldi R18, 0b11101111
	rjmp start

decrement:
	; if R16 has the pattern for 9
	cpi R16, 0b11101111
	breq case_9AD

	; if R16 has the pattern for 8
	cpi R16, 0b11111111
	breq case_8AD

	; if R16 has the pattern for 7
	cpi R16, 0b10000111
	breq case_7AD

	; if R16 has the pattern for 6
	cpi R16, 0b11111101
	breq case_6AD

	; if R16 has the pattern for 5
	cpi R16, 0b11101101
	breq case_5AD

	; if R16 has the pattern for 4
	cpi R16, 0b11100110
	breq case_4AD

	; if R16 has the pattern for 3
	cpi R16, 0b11001111
	breq case_3AD

	; if R16 has the pattern for 2
	cpi R16, 0b11011011
	breq case_2AD

	; if R16 has the pattern for 1
	cpi R16, 0b10000110
	breq case_1AD

	; if R16 has the pattern for 0
	cpi R16, 0b10111111
	breq case_0AD

; load the pattern for 8
case_9AD:
	ldi R16, 0b11111111
	rjmp start

; load the pattern for 7
case_8AD:
	ldi R16, 0b10000111
	rjmp start

; load the pattern for 6
case_7AD:
	ldi R16, 0b11111101
	rjmp start

; load the pattern for 5
case_6AD:
	ldi R16, 0b11101101
	rjmp start

; load the pattern for 4
case_5AD:
	ldi R16, 0b11100110
	rjmp start

; load the pattern for 3
case_4AD:
	ldi R16, 0b11001111
	rjmp start

; load the pattern for 2
case_3AD:
	ldi R16, 0b11011011
	rjmp start

; load the pattern for 1
case_2AD:
	ldi R16, 0b10000110
	rjmp start

; load the pattern for 0
case_1AD:
	ldi R16, 0b10111111
	rjmp start

; load the pattern for 9 and check the value for R18
case_0AD:
	ldi R16, 0b11101111

	; if R18 has the pattern for 9
	cpi R18, 0b11101111
	breq case_9BD

	; if R18 has the pattern for 8
	cpi R18, 0b11111111
	breq case_8BD

	; if R18 has the pattern for 7
	cpi R18, 0b10000111
	breq case_7BD

	; if R18 has the pattern for 6
	cpi R18, 0b11111101
	breq case_6BD

	; if R18 has the pattern for 5
	cpi R18, 0b11101101
	breq case_5BD

	; if R18 has the pattern for 4
	cpi R18, 0b11100110
	breq case_4BD

	; if R18 has the pattern for 3
	cpi R18, 0b11001111
	breq case_3BD

	; if R18 has the pattern for 2
	cpi R18, 0b11011011
	breq case_2BD

	; if R18 has the pattern for 1
	cpi R18, 0b10000110
	breq case_1BD

	; if R18 has the pattern for 0
	cpi R18, 0b10111111
	breq case_0BD

; load the pattern for 8
case_9BD:
	ldi R18, 0b11111111
	rjmp start

; load the pattern for 7
case_8BD:
	ldi R18, 0b10000111
	rjmp start

; load the pattern for 6
case_7BD:
	ldi R18, 0b11111101
	rjmp start

; load the pattern for 5
case_6BD:
	ldi R18, 0b11101101
	rjmp start

; load the pattern for 4
case_5BD:
	ldi R18, 0b11100110
	rjmp start

; load the pattern for 3
case_4BD:
	ldi R18, 0b11001111
	rjmp start

; load the pattern for 2
case_3BD:
	ldi R18, 0b11011011
	rjmp start

; load the pattern for 1
case_2BD:
	ldi R18, 0b10000110
	rjmp start

; load the pattern for 0
case_1BD:
	ldi R18, 0b10111111
	rjmp start

; load the pattern for 0 to both displays and keeps the value in bounds
case_0BD:
	ldi R18, 0b10111111
	ldi R16, 0b10111111
	rjmp start


; Subroutine to display values in R16 and R18 to the displays
display:
	push R18   
	push R16
	push R17
	in R17, SREG
	push R17

	ldi R17, 8             ; Loop --> test all 8 bits

                           ; Second 7-segment display
loop1:
	rol R18                ; Rotate left trough Carry
	BRCS set_ser_in_2      ; Branch if Carry is set
	
	cbi PORTB,0            ; Setting SER to 0
	rjmp end1

set_ser_in_2:
	sbi PORTB,0            ; Setting SER to 1
	
end1:                      ; Generating SRCLK pulse
	sbi PORTB,1
	
	nop
	nop
	nop
	
	cbi PORTB,1

	dec R17
	brne loop1


	ldi R17, 8             ; Loop --> test all 8 bits

                           ; First 7-segment display
loop:
	rol R16                ; Rotate left trough Carry
	BRCS set_ser_in_1      ; Branch if Carry is set
	
	cbi PORTB,0            ; Setting SER to 0
	rjmp end

set_ser_in_1:
	sbi PORTB,0            ; Setting SER to 1

end:                       ; Generating SRCLK pulse
	sbi PORTB,1
	
	nop
	nop
	nop

	cbi PORTB,1

	dec R17
	brne loop

	; Generating RCLK pulse
	sbi PORTB,2

	nop
	nop
	nop

	cbi PORTB,2

	; Restore registers from stack
	pop R17
	out SREG, R17
	pop R17
	pop R16
	pop R18
	ret

; Debounce sub routine button one (switch modes, Tc/Td)
debounce1:
	ldi R26,0			        ; Register that keeps track of zeroes (button pressed)
	ldi R27,0			        ; Register that keeps track of ones (button not pressed)
	ldi R28,10			        ; Decrement

	dbLoop:
		sbis PINB,3		        ; Skip next line if I\O register is set (button not pushed)
		inc R26			        ; Increment zeroes counter

		sbic PINB,3		        ; Skip next line if I\O register is cleared (button pushed)
		inc R27			        ; Increment ones counter

		rcall delay_short         ; Calls the delay subroutine

		dec R28                 ; Decrements R28 representing the loop running 1 time
		brne dbLoop             ; Returns to beginning of loop if [R28] > 0

	cp R27,R26                  ; Compares R26 (zeroes) and R27 (ones)
	brlo skipToEnd1             ; Only executes if Rd (R27) < Rr (R26)

	buttonOnePressed:           ; Subroutine to execute the action on a button press more reliably
	sbis PINB, 3                ; Skip next line if I\O register is set (button not pushed)
	sbic PINB, 3                ; Skip next line if I\O register is cleared (button pushed)
	rjmp buttonOnePressed       ; Jumps back to start of subroutine

	cpi R19, 0                  ; Compares R19 to 0
	breq setBit1                ; Branches to setBit1 if the condition is true

	ldi R19, 0                  ; If the condition is false, load 0 to R19

	mov R21, R18                ; Move the current value of R18 to R21 to save it
	mov R20, R16                ; Move the current value of R16 to R20 to save it

	rcall animation

	rcall display
	rjmp mode                   ; Jumps to mode to update the displays

	setBit1:
		ldi R19, 1              ; Loads 1 into R19

		rcall animation

		mov R16, R20            ; Moves the saved ones value into R16
		mov R18, R21            ; Moves the saved tens value into R18

		rcall display
		rjmp mode               ; Jumps back to mode to update decimal point

	skipToEnd1:
		rjmp start              ; Returns to main

debounce2:
	ldi R26,0			        ; Register that keeps track of 1s from the A terminal
	ldi R27,0			        ; Register that keeps track of 1s from the B terminal
	ldi R28,10			        ; Decrement

	db2Loop:
		sbis PIND,4		        ; Skip next line if I\O register is set (signal from A terminal)
		inc R26			        ; Increment As counter

		sbis PIND,5		        ; Skip next line if I\O register is set (signal from B terminal)
		inc R27			        ; Increment Bs counter

		rcall delay_short         ; Calls the delay subroutine

		dec R28                 ; Decrements R28 representing the loop running 1 time
		brne db2Loop            ; Returns to beginning of loop if [R28] > 0

	cp R27,R26                  ; Compares R26 (zeroes) and R27 (ones)
	brlo skipToEnd2             ; Only executes if Rd (R27) < Rr (R26)

	rcall holdInc               ; Calls the subroutine to check the rest of the states for dec

	sbrc R19, 0                 ; Checks if the counter is in the appropriate mode to change
	rjmp increment              ; Jumps to the increment subroutine to check and set the value of the registers

	skipToEnd2:
		rjmp start			    ; Returns to start of program


debounce3:
	ldi R26,0			        ; Register that keeps track of 1s from the A terminal
	ldi R27,0			        ; Register that keeps track of 1s from the B terminal
	ldi R28,10			        ; Decrement

	db3Loop:
		sbis PIND,4		        ; Skip next line if I\O register is set (signal from A terminal)
		inc R26			        ; Increment As counter

		sbis PIND,5		        ; Skip next line if I\O register is set (signal from B terminal)
		inc R27			        ; Increment Bs counter

		rcall delay_short         ; Calls the delay subroutine

		dec R28                 ; Decrements R28 representing the loop running 1 time
		brne db3Loop            ; Returns to beginning of loop if [R28] > 0

	cp R26,R27                  ; Compares R26 (zeroes) and R27 (ones)
	brlo skipToEnd3             ; Only executes if Rd (R27) < Rr (R26)

	rcall holdDec               ; Calls the subroutine to check the rest of the states for dec

	sbrc R19, 0                 ; Checks if the counter is in the appropriate mode to change
	rjmp decrement              ; Jumps to the decrement subroutine to check and set the value of the registers

	skipToEnd3:
		rjmp continue		    ; Returns to start of program


holdInc:
	; Checking for state 3
	hold3b:
	sbic PIND, 5                ; Stays in this loop while there is a signal from the B terminal
	rjmp hold3a

	hold3a:
	sbic PIND, 4                ; Stays in this loop while there is a signal from the A terminal
	rjmp hold3b

	; Checking for state 4
	hold4b:
	sbic PIND, 5                ; Stays in this loop while there is a signal from the B terminal
	rjmp hold4b

	hold4a:
	sbis PIND, 4                ; Stays in this loop while there is no signal from the A terminal
	rjmp hold4a

	; Checking for state 5
	hold5b:
	sbis PIND, 5                ; Stays in this loop while there is no signal from the B terminal
	rjmp hold5b

	hold5a:
	sbis PIND, 4                ; Stays in this loop while there is no signal from the A terminal
	rjmp hold5a

	ret

holdDec:
	; Checking for state 3
	hold3bd:
	sbic PIND, 5                ; Stays in this loop while there is a signal from the B terminal
	rjmp hold3ad

	hold3ad:
	sbic PIND, 4                ; Stays in this loop while there is a signal from the A terminal
	rjmp hold3bd

	; Checking for state 4
	hold4bd:
	sbis PIND, 5                ; Stays in this loop while there is no signal from the B terminal
	rjmp hold4bd

	hold4ad:
	sbic PIND, 4                ; Stays in this loop while there is a signal from the A terminal
	rjmp hold4ad

	; Checking for state 5
	hold5bd:
	sbis PIND, 5                ; Stays in this loop while there is no signal from the B terminal
	rjmp hold5bd

	hold5ad:
	sbis PIND, 4                ; Stays in this loop while there is no signal from the A terminal
	rjmp hold5ad

	ret

tempSensor:
	push R23					; Back up R23 on the stack
	clr R22

	sbi DDRB, 4					; Set temperature sensor as an output
	cbi PORTB, 4                ; Pulls the line low to get data from the sensor

	ldi r27,30
	ldi r28, 0
	rcall timerLoop

	cbi DDRB, 4                 ; Releases the line by setting the pin to an input for the sensor

	hold1:
		sbis PINB, 4			; Wait to continue until the line is high (state 3 -> state 4)
		rjmp hold1

	hold2:			
		sbic PINB, 4			; Wait to continue until the line is high (state 4 -> state 5)
		rjmp hold2

	hold3:
		sbis PINB, 4			; Wait to continue until the line is high (state 5 -> state 6)
		rjmp hold3

	hold4:
		sbic PINB, 4			; Wait to continue until the line is high (state 6 -> state 7)
		rjmp hold4

	ldi R23, 16
	humidityLoop:				; This loop handles reading in the 16 humidity bits
		hold5:
			sbis PINB, 4		; Wait to continue until the line is low
			rjmp hold5

		hold6: 
			sbic PINB, 4		; Wait to continue until the line is high
			rjmp hold6

		dec R23
		brne humidityLoop

	ldi R23, 8
	temperatureLoop:				; This loop handles reading in the 8 humidity bits
		hold7:
			sbis PINB, 4			; Wait to continue until the line is high (state 11 -> state 12)
			rjmp hold7

		rcall delay_40_microsec		; Delay 40 microseconds

		sbis PINB, 4				; if the line is low; record a 0 and branch back to start of loop
		rjmp zero

		rjmp one					; If thr bit is still high after the delay, record a 1 and branch back to the start of the loop

		zero:
			lsl R22					; Rotate the carry bit into R22

			dec R23
			brne temperatureLoop
			rjmp endOfLoop

		one:
			lsl R22					; Rotate the carry bit into R22
			sbr R22, 0b00000001     ; Set the least significant bit to 1

			hold10:			
				sbic PINB, 4		; Wait to continue until the line drops low again		
				rjmp hold10

			dec R23
			brne temperatureLoop

	endOfLoop:

	rcall divide                    ; Calls divide to seperate the 8 bit number into two 8 bit numbers for each display

	cbr R20, 0b10000000             ; Clears the decimal place in both saved values for comparison purposes
	cbr R21, 0b10000000

	mov R23, R20					; Convert the 7-seg binary string for r20 to its decimal equivalent
	rcall deconvert
	mov R20, R23

	mov R23, R21					; Convert the 7-seg binary string for r21 to its decimal equivalent
	rcall deconvert
	mov R21, R23

	cp R18, R21						; Compare actual tens place to desired tens place
	brlo ledOn                      ; If the actual temp tens place is less than the desired, branch to a subroutine that turns the LED on

	cp R18, R21                     ; Compare actual tens place to desired tens place 
	breq compareOnes				; If the actual temp is equal to the desired, branch to a subroutine that compares the ones place values

	ledOff:
	cbi PORTD,3						; Set the LED to off
	rjmp end5

	ledOn:
	sbi PORTD,3						; Set the LED to on
	rjmp end5

	compareOnes:
	cp R16,R20						; Compare actual ones place to desired ones place
	brlo ledOn						; If the actual temp ones place is less than the desired, branch to a subroutine that turns the LED on

	cp R16, R20						; Compare actual ones place to desired ones place
	brge ledOff						; If the actual temp is greater than or equal to branch to the LED turn off subroutine

	end5:

	mov R23, R20					; Convert the decimal binary value in r20 to its 7-seg equivalent
	rcall convert
	mov R20, R23

	mov R23, R21					; Convert the decimal binary value in r21 to its 7-seg equivalent
	rcall convert
	mov R21, R23

	sbr R20, 0b10000000             ; Sets the decimal place bits back into the saved desired values
	sbr R21, 0b10000000

	mov R23, R16                    ; Converts the ones place results of the division into its 7-seg equivelent
	rcall convert
	mov R16, R23

	mov R23, R18					; Converts the tens place results of the division into its 7-seg equivelent
	rcall convert
	mov R18, R23

	pop R23							; Restore R23
ret

; Generate a delay of ~10 ms by for the debounce algorithms
delay:
	ldi   R26,5                ; 5
	d1: ldi   R27,79           ; 79
		d2: ldi   R28,100      ; 100
			d3: dec   R28
				nop                
				brne  d3 
			nop
			nop
			dec   R27
			brne  d2
		dec   R26
		brne  d1
ret

delay_short:
	push R26                   ; Pushes R26, R27, and R28 on the stack to preserve their previous value
	push R27
	push R28

	ldi   R26,1                ; 5
	d4: ldi   R27,5            ; 30
		d5: ldi   R28,5        ; 25
			d6: dec   R28
				nop                
				brne  d6 
			nop
			nop
			dec   R27
			brne  d5
		dec   R26
		brne  d4

	pop R28                    ; Restore the previous value to R26, R27, and R28
	pop R27
	pop R26
ret

delay_tenth:
	push R29                   ; Push R29 on the stack to preserve thier previous value
	ldi R29, 10

	loop2:
		rcall delay

			sbic PINB, 3       ; Checks for input from the push button
			rjmp debounce1     ; Jumps to the button debounce

		dec R29

		brne loop2
		pop r29                ; Restore the previous value of R29 from the stack
ret

delay_second:
	ldi R29, 10

	loop3:
		rcall delay_tenth
		dec R29
		brne loop3
ret

delay_40_microsec:
	ldi   R26,1     
	d7: ldi   R27,8  
		d8: ldi   R28,20 
			d9: dec   R28
				nop           
				brne  d9 
			dec   R27
			brne  d8
		dec   R26
		brne  d7
ret

delay_10_millisec:
	ldi   R26,1     
	d10: ldi   R27,50  
		d11: ldi   R28,50 
			d12: dec   R28
				nop           
				brne  d12 
			dec   R27
			brne  d11
		dec   R26
		brne  d10
ret

timerLoop:
	ldi R30, 0x03
	out TCCR0B, R30
	rcall timer
	dec R27
	brne timerLoop
	ret

; Checks the overflow flag (TOV0) in the TIFR0 register to produce a more reliable delay
timer:
	push R30

	in R30, TCCR0B              ; Loads 0 into TCCR0B throught R31
	ldi R31, 0x00
	out TCCR0B, R31

	in R31, TIFR0               ; Clears the overflow register
	sbr R31, 1 << TOV0
	out TIFR0, R31

	out TCNT0, R28				; Starts the timer with the new count (R28)
	out TCCR0B, R30

	wait:
		in R31, TIFR0
		sbrs R31, TOV0          ; Checks the overflow flag
		rjmp wait

	pop R30
	ret

animation:                      ; Transition animation that plays when the modes are switched
                             
	ldi R18, 0b00000000         ; Start of LEDs lighting up across displays
	ldi R16, 0b00000110
	rcall display
	rcall delay_tenth

	ldi R18, 0b00000000
	ldi R16, 0b01001111
	rcall display
	rcall delay_tenth

	ldi R18, 0b00000000
	ldi R16, 0b01111111
	rcall display
	rcall delay_tenth

	ldi R18, 0b10000110
	ldi R16, 0b01111111
	rcall display
	rcall delay_tenth

	ldi R18, 0b11001111
	ldi R16, 0b01111111
	rcall display
	rcall delay_tenth

	ldi R18, 0b11111111
	ldi R16, 0b01111111
	rcall display
	rcall delay_tenth

	ldi R18, 0b11111111         ; Start of LEDs turning off across the displays
	ldi R16, 0b00111001
	rcall display
	rcall delay_tenth

	ldi R18, 0b11111111
	ldi R16, 0b00110000
	rcall display
	rcall delay_tenth

	ldi R18, 0b11111111
	ldi R16, 0b00000000
	rcall display
	rcall delay_tenth

	ldi R18, 0b01111001
	ldi R16, 0b00000000
	rcall display
	rcall delay_tenth

	ldi R18, 0b00110000
	ldi R16, 0b00000000
	rcall display
	rcall delay_tenth

	ldi R18, 0b00000000
	ldi R16, 0b00000000
	rcall display
	rcall delay_tenth
	ret

convert:						; Subroutine to convert a decimal value into its seven segment display pattern
	cpi R23, 0
	breq conCase0

	cpi R23, 1
	breq conCase1

	cpi R23, 2
	breq conCase2

	cpi R23, 3
	breq conCase3

	cpi R23, 4
	breq conCase4

	cpi R23, 5
	breq conCase5

	cpi R23, 6
	breq conCase6

	cpi R23, 7
	breq conCase7

	cpi R23, 8
	breq conCase8

	cpi R23, 9
	breq conCase9

	conCase0:
		ldi R23, 0b00111111     ; Loads the zero pattern to R23
		ret

	conCase1:
		ldi R23, 0b00000110     ; Loads the one pattern to R23
		ret

	conCase2:
		ldi R23, 0b01011011     ; Loads the two pattern to R23
		ret

	conCase3:
		ldi R23, 0b01001111     ; Loads the three pattern to R23
		ret

	conCase4:
		ldi R23, 0b01100110     ; Loads the four pattern to R23
		ret

	conCase5:
		ldi R23, 0b01101101     ; Loads the five pattern to R23
		ret

	conCase6:
		ldi R23, 0b01111101     ; Loads the six pattern to R23
		ret

	conCase7:
		ldi R23, 0b00000111     ; Loads the seven pattern to R23
		ret

	conCase8:
		ldi R23, 0b01111111     ; Loads the eight pattern to R23
		ret

	conCase9:
		ldi R23, 0b01101111     ; Loads the nine pattern to R23
		ret

deconvert:                      ; Subroutine to 
	cpi R23, 0b00111111         ; Checks for the zero pattern in R23
	breq deconCase0

	cpi R23, 0b00000110         ; Checks for the one pattern in R23
	breq deconCase1

	cpi R23, 0b01011011         ; Checks for the two pattern in R23
	breq deconCase2

	cpi R23, 0b01001111         ; Checks for the three pattern in R23
	breq deconCase3

	cpi R23, 0b01100110         ; Checks for the four pattern in R23
	breq deconCase4

	cpi R23, 0b01101101         ; Checks for the five pattern in R23
	breq deconCase5

	cpi R23, 0b01111101			; Checks for the six pattern in R23
	breq deconCase6

	cpi R23, 0b00000111			; Checks for the seven pattern in R23
	breq deconCase7

	cpi R23, 0b01111111			; Checks for the eight pattern in R23
	breq deconCase8

	cpi R23, 0b01101111			; Checks for the nine pattern in R23
	breq deconCase9

	deconCase0:
		ldi R23, 0
		ret

	deconCase1:
		ldi R23, 1
		ret

	deconCase2:
		ldi R23, 2
		ret

	deconCase3:
		ldi R23, 3
		ret

	deconCase4:
		ldi R23, 4
		ret

	deconCase5:
		ldi R23, 5
		ret

	deconCase6:
		ldi R23, 6
		ret

	deconCase7:
		ldi R23, 7
		ret

	deconCase8:
		ldi R23, 8
		ret

	deconCase9:
		ldi R23, 9
		ret