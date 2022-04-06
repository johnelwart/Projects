; Lab4.asm
;
; Created: 3/24/2022 4:09:03 PM
; Author : John Elwart & Tucker Dickson
;

;;;;;;;;;;;;;;;;;;; Registers Used ;;;;;;;;;;;;;;;;;;;
; R0  - Implied register for values coming from the LPM instruction. Used to keep character data going to the LCD
; R16 - Remainder
; R17 - Result
; R18 - The duty cycle counter
; R19 - Divisor
; R20 - Indicates whether the RPG has been turned clockwise or counterclockwise
; R21 - Display 'flag that gets manipulated in the toggleLEDs ISR (0x00 -> do not display, 0x01 -> display)
; R22 - This value represents whether or not the LEDs are on (0x01) or off (0x00)
; R23 -
; R24 - Decrement for L20 loop
; R25 - Used in timer counter
; R26 - Timer counter overflow flag
; R27 - Count for timer counter loop
; R28 - Used in timer counter
; R29 - Stores 8 bit commands that are to be sent to the display
; R30 - Lower nibble of message stored in lower Z register
; R31 - Upper nibble of message stored in higher Z register
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

.cseg
.org 0x0000
rjmp start					; Jump over assembler directives so they don't execute

.org 0x0002					; Vector table address for INT0 interrupt		
rjmp toggleLEDs				; Jump to this ISR when INT0 is triggered

.org 0x0008					; Vector table address for PCINT1
rjmp clockwise				; Jump to this ISR when PCINT1 is triggered

.org 0x000A					; Vector table address for PCINT2
rjmp counterclockwise		; Jump to this ISR when PCINT2 is triggered



; Create static strings in program memory
msg:  .db "DC= "
msg2: .db "%                                 LEDs: "
off:  .db "OFF       "
on:   .db "ON        "
      .dw 0

.org 0x0034				  ; Start of the program
start:
	cli

	ldi r16,0			  ; Initialize the timer/counter to zero
	sts TCNT2, r16

	sbi DDRD,3			  ; Make OC2B an output

	ldi r16,200			  ; This sets the top value of the timer/counter
	sts OCR2A, r16		  ; In order to generate a 10kHz signal, we need a TOP of 200 with a prescaler of 8

	ldi r16,100			  ; This is the value which the counter will be compared against
	sts OCR2B, r16

	ldi r16,0x23		  ; Enable fast pwm mode with non-inverting output
	sts TCCR2A,r16

	ldi r16,0x02		  ; Set prescaler 8
	sts TCCR2B,r16

	ldi r16,0x03		  ; The rising edge of INT0 generates an interrupt request
	sts EICRA,r16
 
	ldi r16,0x01		  ; Enable INT0 
	out EIMSK,r16

	ldi r16,0x20		  ; Enable the RPG B (PCINT13) interrupt
	sts PCMSK1,r16

	ldi r16,0x80		  ; Enable the RPG A (PCINT23) interrupt
	sts PCMSK2,r16

	ldi r16,0x06		  ; Configure the pin change interrupt control register to enable the above PCINTs
	sts PCICR,r16

	ldi r16,0x0F	      ; Set pins 0 through 3 on Port C as outputs
	out DDRC,r16

	ldi r16,0x38	      ; Set pins 11 through 13 on port B as outputs
	out DDRB,r16

	ldi r16, 0x00         ; Initialize the division remainder to 0
	ldi r17, 0x00         ; Initialize the division result to 0
	ldi r18, 50           ; Initialize the PWM counter to start at 50
	ldi r20, 0x00         ; Initializes the RPG CCW/CW indicator to 0
	ldi r21, 0x01		  ; Enable the LCD "display flag"; this indicated when to update the LCD display in the mainLoop
	ldi r22, 0x00         ; Turn the LEDs off at the start of execution
	 
	rcall LCDInit		  ; Initialize the LCD
	sei					  ; Enable global interrupt flag

displayLoop:              ; This loop is responsible for loading the Z register with the appropriate message to be displayed to the LCD
	cli
	ldi r29,0x01	      ; Clear the LCD
	rcall writeCommand

	sbi PORTB,5           ; Set rs high so we can write characters

	ldi r24,4	          ; Load r24 with the number of characters in the msg string
	ldi r30,LOW(2*msg)    ; Loads the lower byte into the lower Z register
	ldi r31,HIGH(2*msg)   ; Loads the higher byte into the higher Z register
	rcall displayString   ; Send the data to the LCD

	ldi r16,0x00          ; Resets the division remainder to 0
	ldi r17,0x00          ; Resets the division result to 0
	rcall divide          ; Calls the divide subroutine to seperate the two characters of the PWM counter

	mov r31,r17           ; Moves the result value (tens place) into R31 to be displayed
	rcall displayData     ; Displays the character in R31 to the LCD

	mov r31,r16           ; Moves the remainder value (ones place) into R31 to be displayed
	rcall displayData     ; Displays the character in R31 to the LCD

	ldi r24,40            ; Sets the loop counter to 40 to loop over every character in the string
	ldi r30,LOW(2*msg2)   ; Splits the message into 8 bit parts
	ldi r31,HIGH(2*msg2)
	rcall displayString   ; Loops over all the characters to display them to the LCD

	cpi r22,0x01		  ; If r22 is 0x01, we want to display "LEDs: ON"
	breq showON

	ldi r30,LOW(2*off)	  ; Otherwise, we want to display "LEDs: OFF"
	ldi r31,HIGH(2*off)	

	ldi r16,0x00		  ; Disable fast pwm and non-inverting
	sts TCCR2A,r16
	 
	rjmp showEND

	showON:
		ldi r30,LOW(2*on)	  ; Loads the lower byte into the lower Z register
		ldi r31,HIGH(2*on)	  ; Loads the higher byte into the higher Z register

		ldi r16,0x23		  ; Enable fast pwm and non-inverting
		sts TCCR2A,r16

	showEND:
		ldi r24,10			  ; Load r24 with the number of characters in the on & off strings
		rcall displayString   ; Send the data to the LCD

	sei
	
mainLoop:
	cpi r21,0x01		  ; If r21 is 0x01, the "display flag" is set, so we need to update the LCD
	breq displayLoop
	
	cpi r20,0x01          ; If R20 is 1 indicating a CW rotation, branch to the subroutine that handles that movement
	breq cwDisplay

	cpi r20,0x02          ; If R20 is 2 indicating a CCW rotation, branch to the subroutine that handles that movement
	breq ccwDisplay

	rjmp mainLoop         ; Keep looping indefinitely 

cwDisplay:
	cli					  ; Disable global interrupts so that this routine isn't interrupted
	push r23			  ; Save the value of r23 on the stack

	aHighCW:              ; Waits for the signal from the A terminal to go high before continuing
		sbis PIND,7		  ; This ensures that we don't return to the mainLoop before the RPG has finished
		rjmp aHighCW	  ; 	its rotation

	bHighCW:              ; Waits for the signal from the B terminal to go high before continuing
		sbis PINC,5		  ; This ensures that we don't return to the mainLoop before the RPG has finished
		rjmp bHighCW	  ;		its rotation

	dec r18               ; Decrements the counter by 2 (2% duty cycle decrease)
	dec r18

	lds r16,OCR2B         ; Load the value of OCR2B into r16
	ldi r23,4			  ; Subtract four from r16
	sub r16,r23
	sts OCR2B, r16		  ; Send the new value in r16 back out to OCR2B
						  ; This effectively decreases the duty cycle of the PWM, dimming the LEDs

	ldi r27,100           ; 100ms Timer
	ldi r28,0
	rcall timerLoop

	ldi r20,0x00          ; Resets the RPG rotation indicator

	pop r23				  ; Restore the value of r23
	rjmp displayLoop      ; Jumps to the display loop to update the LCD with the new numbers
	sei

ccwDisplay:
	cli					  ; Disable global interrupts so that this routine isn't interrupted
	push r23			  ; Save the value of r23 on the stack
	
	bHighCCW:             ; Waits for the signal from the B terminal to go high
		sbis PINC,5		  ; This ensures that we don't return to the mainLoop before the RPG has finished
		rjmp bHighCCW	  ; 	its rotation

	aHighCCW:             ; Waits for the signal from the A terminal to go high
		sbis PIND,7		  ; This ensures that we don't return to the mainLoop before the RPG has finished
		rjmp aHighCCW	  ; 	its rotation

	inc r18               ; Increments the counter by 2 (2% duty cycle increase)
	inc r18

	lds r16,OCR2B		  ; Load the value of OCR2B into r16
	ldi r23,4			  ; Add four to r16
	add r16,r23
	sts OCR2B, r16		  ; Send the new value of r16 back to OCR2B
						  ; This effectively increases the duty cycle of the PWM, brightening the LEDs

	ldi r27,100           ; 100ms timer
	ldi r28,0
	rcall timerLoop

	ldi r20,0x00          ; Resets the RPG rotation indicator

	pop r23				  ; Restore the value of r23 from the stack
	rjmp displayLoop      ; Jumps to the display loop to update the LCD with the new numbers
	sei

displayString:			  ; This subroutine is responsible for sending the contents of the Z register to the LCD
	cli
	L20:
		lpm               ; Loads one byte from the Z register into a destination register, r0 is implied with
		                  ; no operand
		swap r0           ; Swap the nibbles in r0 and outputs them through pins 0-3 on Port C
		out PORTC,r0
		rcall LCDStrobe
	
		ldi r27,1         ; 1ms timer
		ldi r28,0
		rcall timerLoop

		swap r0           ; Swap the nibbles in r0 and outputs them through pins 0-3 on Port C
		out PORTC,r0
		rcall LCDStrobe

		ldi r27,1         ; 1ms timer
		ldi r28,0
		rcall timerLoop

		adiw zh:zl,1      ; Adds 1 to the Z-pointer register pair
		dec r24           ; Decrements r24
		brne L20          ; Branches back to the start of the loop if R24=0

	ldi r21,0x00          ; Sets the "Update Display" flag back to 0
	sei
	ret

displayData:
	cli

	swap r31              ; Swaps the nibbles of R31
	out PORTC,r31		  ; Send upper nibble
	rcall LCDStrobe		  ; Strobe Enable line

	ldi r27, 1            ; 1ms timer
	ldi r28,0
	rcall timerLoop

	swap r31              ; Swaps the nibbles of R31
	out PORTC,r31         ; Send lower nibble
	rcall LCDStrobe       ; Strobe Enable line

	ldi r27, 1            ; 1ms timer
	ldi r28,0
	rcall timerLoop

	sei
	ret

LCDStrobe:
	sbi PORTB,3           ; Sets Enable hide

	nop                   ; delay for ~300ns
	nop
	nop
	nop
	nop

	cbi PORTB,3        	  ; Sets Enable low

	ldi r27, 2         	  ; 2ms timer
	ldi r28, 0
	rcall timerLoop

	ret

LCDInit:
	ldi r27, 100	      ; 100ms timer
	ldi r28, 0
	rcall timerLoop

	cbi PORTB,5	          ; Sets Register Select low

	cbi PORTB,3           ; Sets Enable lwo

	ldi r29,0x03          ; Sets device to 8 bit mode
	out PORTC,r29
	rcall LCDStrobe

	ldi r27,5	          ; Wait 5ms
	ldi r28,0
	rcall timerLoop

	ldi r29,0x03          ; Sets device to 8 bit mode
	out PORTC,r29
	rcall LCDStrobe

	ldi r27,1             ; Wait 1ms
	ldi r28,0
	rcall timerLoop

	ldi r29,0x03          ; Sets device to 8 bit mode
	out PORTC,r29
	rcall LCDStrobe

	ldi r27,1             ; Wait 1ms
	ldi r28,0
	rcall timerLoop

	ldi r29,0x02	      ; Sets device to 4-bit mode
	out PORTC,r29
	rcall LCDStrobe

	ldi r27,5             ; Wait 5ms
	ldi r28,0
	rcall timerLoop

	ldi r29,0x28	      ; Write command "set interface"
	rcall writeCommand

	ldi r29,0x08	      ; Write command "enable display/cursor"
	rcall writeCommand

	ldi r29,0x01	      ; Write command "clear and home"
	rcall writeCommand

	ldi r29,0x06	      ; Write command "set cursor move direction"
	rcall writeCommand

	ldi r29,0x0C	      ; Write command "turn on display"
	rcall writeCommand

	ret

writeCommand:
	cli

	cbi PORTB,5           ; Sets Register Select low

	swap r29	          ; Write upper nibble to LCD
	out PORTC,r29
	rcall LCDStrobe

	swap r29	          ; Write lower nibble to LCD
	out PORTC,r29
	rcall LCDStrobe

	sei
	ret

divide:
	cli
	ldi r19, 10           ; 10 will always be the divisor
	mov R23,R18           ; Moves a copy of the current counter to R23 to be pushed on the stack
	push R23

	div8u:	
		sub	r16,r16		  ; Clear remainder and carry
		ldi	R29,9		  ; Initiate loop counter
	d8u_1:	
		rol r17
		rol	r18			  ; Shift left dividend
		dec	R29			  ; Decrement counter
		brne d8u_2	      ; If done

		ldi r23,0x30      ; Loads hex 30 into R23 to be added to the result and remainder to convert it to ASCII value
		add r16,r23
		add r17,r23

		pop R23           ; Pops the value of the counter back into R23 and moves the value back to R18
		mov R18, R23

		ret				  ; Return

	d8u_2:	
		rol	r16		      ; Shift dividend into remainder
		sub	r16,r19		  ; Remainder = remainder - divisor
		brcc	d8u_3	  ; If result negative
		add	r16,r19		  ; Restore remainder
		clc				  ; Clear carry to be shifted into result
		rjmp	d8u_1	  ; Else
	d8u_3:	
		sec				  ; Set carry to be shifted into result
		rjmp	d8u_1
	sei

toggleLEDs:
	cli					  ; Clear interrupt enable flag

	cpi r22, 0x01         ; If the LEDs were previously on branch to a tag that turns them off
	breq LEDsOff
	
	sbi PORTD,3           ; Otherwise, turn them on
	ldi r22,0x01		  ; Set the "display flag" so that the LCD will be updated when we return to the mainLoop

	rjmp LEDsDone         ; Jump to the end of the subroutine

	LEDsOff:              ; Sets the bit in R22 indicating the LED needs to be turned off in the main loop
		cbi PORTD,3
		ldi r22, 0x00

	LEDsDone:	
		ldi r21,0x01
		sei				  ; Set the interrupt enable flag

	reti

clockwise:                ; Subroutine to handle the clockwise RPG movement
	cli

	cpi r22,0x00          ; If R22 is 0, branch to the end of the loop since the LEDs are off
	breq endCW

	cpi r18,2             ; If R18 is 2, branch to the end because the counter is at its lower bound
	breq endCW

	cpi r18,100            ; If R18 is 98, branch to the end because the counter is at its upper bound
	breq endCW

	ldi r20,0x01          ; If none of the above conditions are true, set R20 to 1 to indicate a CW turn

	endCW:
	sei 
	reti

counterclockwise:         ; Subroutine to handle the counter clockwise RPG movement
	cli

	cpi r22,0x00          ; If R22 is 0, branch to the end of the loop since the LEDs are off
	breq endCCW

	cpi r18,2             ; If R18 is 2, branch to the end because the counter is at its lower bound
	breq endCW

	cpi r18,100            ; If R18 is 98, branch to the end because the counter is at its upper bound
	breq endCW

	ldi r20,0x02          ; If none of the above conditions are true, set R20 to 2 to indicate a CCW turn 

	endCCW:
	sei 
	reti

timerLoop:
	cli
	ldi r25, 0x03
	out TCCR0B, r25
	rcall timer
	dec r27
	brne timerLoop
	sei
	ret

timer:
	push r25

	in r25, TCCR0B
	ldi r26, 0x00
	out TCCR0B, r26

	in r26, TIFR0
	sbr r26, 1 << TOV0
	out TIFR0, r26

	out TCNT0, r28
	out TCCR0B, r25
	wait:
		in r26, TIFR0
		sbrs r26, TOV0
		rjmp wait

	pop r25
	ret
