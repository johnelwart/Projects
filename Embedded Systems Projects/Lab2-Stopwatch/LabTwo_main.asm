; Lab2.asm
;
; Created: 2/6/2022 3:01:06 PM
; Author: John Elwart and Tucker Dickson
;

/*  Registers being used:

	R16 - Holds the value for the right display
	R17 - Status register
	R18 - Holds the value for the left display
	R19 - Determine if the stopwatch runs, toggled by the button (0 for pause, 1 for run)
	R20 - Determines which mode the stopwatch is currently in (0 for 0.0, 1 for 0 0)
	R21 - Decrement for debounce hold loop
	R22 - 
	R23 - Counter for the outer loop in delay 1ms and 10ms subroutine
	R24 - Counter for the middle loop in delay 1ms and 10ms subroutine
	R25 - Counter for the inner loop in delay 1ms and 10ms subroutine
	R26 - Keeps track of zeroes in debounce subroutines
	R27 - Keeps track of ones in debounce subroutines
	R28 - Decrement for debounce subroutine loop
	R29 - Decrement for the delay 100ms  subroutine loop
	R30 - Decrement for the delay 1s subroutine loop
	R31 -
*/

; Outputs
sbi DDRB,0		; SER
sbi DDRB,1		; SRCLK
sbi DDRB,2		; RCLK

; Inputs
cbi DDRB,3		; Button 1
cbi DDRB,4      ; Button 2

; Load R16 with zero initially and set the on/off toggle to 0
ldi R16, 0b00111111
ldi R18, 0b10111111
ldi R19, 0
ldi R20, 0
rcall display

; Start main program
start:
	sbis PINB, 4
	rjmp debounce2

	sbic PINB, 3
	rjmp debounce1

	cpi R19, 0b00000000
	breq start

	rcall display

	sbrs R20, 0            ; If the LSB of R20 is 1, meaning mode 2 is selected, the delay_tenth call is skipped
	rcall delay_tenth

	sbrc R20, 0            ; If the LSB of R20 is 0, meaning mode 1 is selected, the delay_second call is skipped
	rcall delay_second

	sbrc R20, 0            ; If the LSB of R20 is 1 the MSB of R18 is set to 1 for comparsion purposes
	sbr R18, 0b10000000

	; Start of comparisons for right display
	; If R16 has the pattern for 0
	cpi R16, 0b00111111
	breq case_0A

	; If R16 has the pattern for 1
	cpi R16, 0b00000110
	breq case_1A

	; If R16 has the pattern for 2
	cpi R16, 0b01011011
	breq case_2A

	; If R16 has the pattern for 3
	cpi R16, 0b01001111
	breq case_3A

	; If R16 has the pattern for 4
	cpi R16, 0b01100110
	breq case_4A

	; If R16 has the pattern for 5
	cpi R16, 0b01101101
	breq case_5A

	; If R16 has the pattern for 6
	cpi R16, 0b01111101
	breq case_6A

	; If R16 has the pattern for 7
	cpi R16, 0b00000111
	breq case_7A

	; If R16 has the pattern for 8
	cpi R16, 0b01111111
	breq case_8A

	; If R16 has the pattern for 9
	cpi R16, 0b01101111
	breq case_9A

; Start of case A subroutines
; Load the pattern for 1
case_0A:
	ldi R16, 0b00000110
	rjmp start
	
; Load the pattern for 2
case_1A:
	ldi R16, 0b01011011
	rjmp start

; Load the pattern for 3
case_2A:
	ldi R16, 0b01001111
	rjmp start

; Load the pattern for 4
case_3A:
	ldi R16, 0b01100110
	rjmp start

; Load the pattern for 5
case_4A:
	ldi R16, 0b01101101
	rjmp start

; Load the pattern for 6
case_5A:
	ldi R16, 0b01111101
	rjmp start

; Load the pattern for 7
case_6A:
	ldi R16, 0b00000111
	rjmp start

; Load the pattern for 8
case_7A:
	ldi R16, 0b01111111
	rjmp start

; Load the pattern for 9
case_8A:
	ldi R16, 0b01101111
	rjmp start

; Load the pattern for 0 on the right display and then determine the value
; to be displayed on the left display
case_9A:
	ldi R16, 0b00111111

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

; Set counting to off, load the pattern for 9, call display,
; delay for 1 second, then jump to the blink subroutine
case_9B:
	ldi R19, 0
	ldi R16, 0b01101111
	ldi R18, 0b11101111
	rcall display
	rcall delay_second_end

	rjmp blinkAtEnd

; Subroutine to flash the time at 9.9
blinkAtEnd:

	ldi R16, 0b00000000         ; Load 0 into both registers
	ldi R18, 0b10000000
	rcall display               ; Display zeroes
	rcall delay_second_end      ; Delay for 1 second

	ldi R16, 0b01101111         ; Load 9 into both registers
	ldi R18, 0b11101111
	rcall display               ; Display nines
	rcall delay_second_end      ; Delay for 1 second

	rjmp blinkAtEnd             ; Jump back to beginning of loop

; Subroutine to display values in R16 and R18 to the displays
display:
	sbrc R20, 0            ; If mode 2 is selected, the MSB of R18 is cleared to get rid of the decimal place
	cbr R18, 0b10000000

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
	
end1:
	; Generating SRCLK pulse
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

end:
	; Generating SRCLK pulse
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


; Debounce sub routine button one (start/stop)
debounce1:
	ldi R26,0			        ; Register that keeps track of zeroes (button not pressed)
	ldi R27,0			        ; Register that keeps track of ones (button pressed)
	ldi R28,10			        ; Decrement

	dbLoop:
		sbis PINB,3		        ; Skip next line if I\O register is set (button not pushed)
		inc R26			        ; Increment zeroes counter

		sbic PINB,3		        ; Skip next line if I\O register is cleared (button pushed)
		inc R27			        ; Increment ones counter

		rcall delay_1ms         ; Calls the 10ms delay subroutine

		dec R28                 ; Decrements R28 representing the loop running 1 time
		brne dbLoop                ; Returns to beginning of loop if [R28] > 0

	cp R27,R26                  ; Compares R26 (zeroes) and R27 (ones)
	brlo skipToEnd1             ; Only executes if Rd (R27) < Rr (R26)

	buttonOnePressed:           ; Subroutine to execute the action on a button press more reliably
	sbis PINB, 3                ; Skip next line if I\O register is set (button not pushed)
	sbic PINB, 3                ; Skip next line if I\O register is cleared (button pushed)
	rjmp buttonOnePressed       ; Jumps back to start of subroutine

	cpi R19, 0                  ; Compares R19 to 0
	breq setBit1                ; Branches to setBit1 if the condition is true

	ldi R19, 0                  ; If the condition is false, load 0 to R19
	rjmp start                  ; Jumps back to start

	setBit1:
		ldi R19, 1              ; Loads 1 into R19

	skipToEnd1:
		rjmp start              ; Returns to main

; Debounce sub routine button two (reset)
debounce2:
	ldi R26,0			        ; Register that keeps track of zeroes (button not pressed)
	ldi R27,0			        ; Register that keeps track of ones (button pressed)
	ldi R28,10			        ; Decrement for press loop
	ldi R21,100                 ; Decrement for press and hold loop

	dbLoop2:
		sbis PINB,4		        ; Skip next line if I\O register is set (button pushed)
		inc R26			        ; Increment zeroes counter

		sbic PINB,4		        ; Skip next line if I\O register is cleared (button not pushed)
		inc R27			        ; Increment ones counter

		rcall delay_1ms         ; Calls the 10ms delay subroutine

		dec R28                 ; Decrements R28 representing the loop running 1 time
		brne dbLoop2                ; Returns to beginning of loop if [R28] > 0

	cp R26,R27                  ; Compares R26 (zeroes) and R27 (ones)
	brlo skipToEnd2                  ; Only executes if Rd (R27) < Rr (R26)

	dbLoop2Hold:

		sbic PINB,4		        ; Skip next line if I\O register is cleared (button pushed)
		rjmp buttonTwoPressed	; Jump to buttonTwoPressed subroutine if the button is released before the
		                        ; hold loop finishes executing
		rcall delay             ; Calls the 10ms delay

		dec R21
		brne dbLoop2Hold              

	rjmp buttonTwoHeld          ; If the loop completes, the hold subroutine is executed
			

	buttonTwoPressed:           ; Subroutine to execute action after the button is released and held loop does not finish
		sbis PINB, 4                ; Skip next line if I\O register is set (button not pushed)
		rjmp buttonTwoPressed       ; If the button is still pushed jump back to buttonTwoPressed

		ldi R16, 0b00111111         ; Loads zeroes into both registers
		ldi R18, 0b10111111
		ldi R19, 0                  ; Sets counting to off

		rcall display               ; Displays zeroes on the displays
		rjmp skipToEnd2

	buttonTwoHeld:					; Subroutine to switch the modes of the timer
		sbis PINB, 4
		rjmp buttonTwoHeld          ; Waits until the button is released to execute the action

		rcall animation             ; Plays the transition animation

		cpi R20, 0                  ; If R20 is clear, set the bit to 1
		breq setBit2

		ldi R20, 0                  ; Otherwise set R20 equal to 0 and jumps to the subroutine that loads the zeroes to the
		rjmp twoToOne               ; display with the decimal point

	setBit2:
		ldi R20, 1                  ; Sets R20 to 1 and jumps to the subroutine that loads zeroes without the decimal point
		rjmp oneToTwo               ; to the displays

	oneToTwo:
		ldi R16, 0b00111111         ; Loads zeroes into both registers without the decimal point in R18
		ldi R18, 0b00111111
		rjmp pause

	twoToOne:
		ldi R16, 0b00111111         ; Loads zeroes into both registers with the decimal point in R18
		ldi R18, 0b10111111

	pause:
		ldi R19, 0                  ; Sets counting to off

		rcall display

	buttonTwoWait:				    ; Waits until the button is released before returning to the start of the program
		sbis PINB, 4
		rjmp buttonTwoWait

	skipToEnd2:
		rjmp start					; Returns to start of program

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

; Generate a delay of ~10 ms
delay:
	ldi   r23,5                ; 5
	d1: ldi   r24,79           ; 79
		d2: ldi   r25,100      ; 100
			d3: dec   r25
				nop                
				brne  d3 
			nop
			nop
			dec   r24
			brne  d2
		dec   r23
		brne  d1
	ret

; Generate a delay of ~100 ms by calling delay subroutine ten times
delay_tenth:
	ldi R29, 10

	loop2:
		rcall delay
		dec R29

		sbis PINB, 4
		rjmp debounce2

		sbic PINB, 3
		rjmp debounce1

		brne loop2
	ret

; Same delay as delay_tenth but has button one disabed for the blinking at the overflow
delay_tenth_end:
	ldi R29, 10

	loop21:
		rcall delay
		dec R29

		sbis PINB, 4
		rjmp debounce2

		brne loop21
	ret

; Generate a delay of ~1 second by calling dislay_tenth subroutine ten times
delay_second:
	ldi R30, 10

	loop3:
		rcall delay_tenth
		dec R30
		brne loop3
	ret

; Same delay as delay_second but has button one disabed for the blinking at the overflow
delay_second_end:
	ldi R30, 10

	loop31:
		rcall delay_tenth_end
		dec R30
		brne loop31
	ret

; Generate a delay of ~1 ms by for the debounce algorithms
delay_1ms:
	ldi   r23,5                ; 5
	d4: ldi   r24,30           ; 30
		d5: ldi   r25,25       ; 25
			d6: dec   r25
				nop                
				brne  d6 
			nop
			nop
			dec   r24
			brne  d5
		dec   r23
		brne  d4
	ret