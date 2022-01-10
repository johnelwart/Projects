import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ArabicToRoman extends JFrame implements KeyListener {
    private final JTextArea arabicNumber;  // Declares a new text area named "arabicNumber"
    private final JTextArea romanNumeral;  // Declares a new text area named "romanNumeral"
    private String arabic = "";  // Declares and defines a new string for storing the text from a text field
    private String roman = "";

    /**
     * Constructor to declare new JTextAreas and add them to the GUI as well as adjust the font size
     * in the text areas.
     */

    public ArabicToRoman() {
        super("Arabic to Roman");   // JFrame title
        setLayout(new FlowLayout());

        Font font = new Font ("Arial", Font.PLAIN, 30);  // Creates an object with the font settings
        Font font2 = new Font ("Arial", Font.PLAIN, 22);  // Creates an object with the font settings

        arabicNumber = new JTextArea(1,10); // Creates a new text area called arabicNumber
        arabicNumber.setFont(font);  // Sets the font using the font object declared earlier
        add(arabicNumber);  // Adds the arabicNumber text area to the JFrame
        
        romanNumeral = new JTextArea(1,10);  // Creates a new text area called romanNumeral
        romanNumeral.setFont(font);  // Sets the font using the font object
        add(romanNumeral);  // Adds roman numeral to the JFrame
    
        final JTextArea key;
        key = new JTextArea(1,1);
        key.setFont(font2);
        key.setText("I - 1, V - 5, X - 10, L - 50, C - 100, D - 500, M - 1000");
        key.setEditable(false);
        add(key);
        
        arabicNumber.addKeyListener(this);  // Keylisteners for both text areas
        romanNumeral.addKeyListener(this);
    }

    /**
     * Handles events passed into it. This handles the keycode of the last key pressed. For this
     * project I only needed keyReleased so the keyPressed method is left empty.
     *
     * @param event Keycode of the last key pressed.
     */

    @Override
    public void keyPressed(KeyEvent event) {

    }

    /**
     * The keyReleased method handles any events that are passed into it, in this case it's the
     * key code of the last one released. These handle backspace and all the letters and numbers. as well
     * as making sure shift is not added to the input strings if it is used.
     *
     * @param event Keycode of the last key released.
     */
    @Override
    public void keyReleased(KeyEvent event) {

        boolean isValid = false;  // Boolean value to keep track if a char entered is valid or not
        String[] validChars = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "I", "V",
                               "X", "L", "C", "D", "M"};  // Valid characters

        // Checks is the entered characters are valid and sets isValid accordingly
        int i = 0;
        while (!isValid && i < validChars.length) {
            isValid = KeyEvent.getKeyText(event.getKeyCode()).equals(validChars[i]);
            i++;
        }

        // Checks if the key released is backspace and handles the event accordingly
        if (KeyEvent.getKeyText(event.getKeyCode()).equals("Backspace")) {

            // If the key events come from the arabicNumber text area
            if (event.getSource() == arabicNumber && arabic.length() != 0) {
                arabic = arabic.substring(0, arabic.length() - 1);
                System.out.println("Arabic Text Field: " + arabic);
                
                romanNumeral.setText(convert(arabic).toUpperCase());
            }

            // If the key events come from the romanNumeral text area
            if (event.getSource() == romanNumeral && roman.length() != 0) {
                roman = roman.substring(0, roman.length() - 1);
                System.out.println("Roman Text Field: " + roman);
                
                arabicNumber.setText(convert(roman).toUpperCase());
            }
        } else {

            // If the key events come from the arabicNumber text area
            if (event.getSource() == arabicNumber && isValid) {
                if (!KeyEvent.getKeyText(event.getKeyCode()).equals("Shift")) {
                    arabic = arabic + String.format("%s", KeyEvent.getKeyText(event.getKeyCode()));
                    arabic = validate(arabic);
                    System.out.println("Arabic Text Field: " + arabic);
    
                    romanNumeral.setText(convert(arabic).toUpperCase());
                    arabicNumber.setText(arabic);
                }
            }

            // If the key events come from the romanNumeral text area
            if (event.getSource() == romanNumeral && isValid) {
                if (!KeyEvent.getKeyText(event.getKeyCode()).equals("Shift")) {
                    roman = roman + String.format("%s", KeyEvent.getKeyText(event.getKeyCode()));
                    roman = validate(roman);
                    System.out.println("Roman Text Field: " + roman);
    
                    arabicNumber.setText(convert(roman).toUpperCase());
                    romanNumeral.setText(roman);
                }
            }
        }
    }

    /**
     * Handles events passed into it. This handles the keycode of the last key typed. For this
     * project I only needed keyReleased so the keyPressed method is left empty.
     *
     * @param event Keycode of the last key typed.
     */

    @Override
    public void keyTyped(KeyEvent event) {

    }

    /**
     * The validate method takes in text from one of the GUI test areas as a parameter and checks if
     * it represents and invalid sequence of roman numerals, such cases include: 1, 5, 9, 40, 90, 100,
     * 400, 500, 900, and 1000. If one of these cases is reached, the method manipulates the input string
     * and the returns the validated string.
     *
     * @param input input from one of the JTextAreas.
     * @return the original string or the manipulated one based on if there was and changes made
     * because of invalid characters.
     */

    public String validate (String input) {
        char [] inputArr;  // Array that stores all the characters from inputString.

        inputArr = input.toCharArray();
    
        if (inputArr.length >= 2) {
            for (int i = 0; i < inputArr.length - 1; i++) {
            
                // Checks for invalid forms of "I"
                if (inputArr[i] == 'I') {
                
                    // Checks for "IL"
                    if (inputArr[i + 1] == 'L') {
                        input = input.substring(0, i);
                        input = input + "LI";
    
                        input = validate(input);
                    
                        // Checks for "IC"
                    } else if (inputArr[i + 1] == 'C') {
                        input = input.substring(0, i);
                        input = input + "CI";
    
                        input = validate(input);
                    
                        // Checks for "ID"
                    } else if (inputArr[i + 1] == 'D') {
                        input = input.substring(0, i);
                        input = input + "DI";
    
                        input = validate(input);
                    
                        // Checks for "IM"
                    } else if (inputArr[i + 1] == 'M') {
                        input = input.substring(0, i);
                        input = input + "MI";
    
                        input = validate(input);
                    }
                
                    // Checks for invalid forms with "V"
                } else if (inputArr[i] == 'V') {
                
                    // Checks for "VX"
                    if (inputArr[i + 1] == 'X') {
                        input = input.substring(0, i);
                        input = input + "XV";
    
                        input = validate(input);
                    
                        // Checks for "VL"
                    } else if (inputArr[i + 1] == 'L') {
                        input = input.substring(0, i);
                        input = input + "LV";
    
                        input = validate(input);
                    
                        // Checks for "VC"
                    } else if (inputArr[i + 1] == 'C') {
                        input = input.substring(0, i);
                        input = input + "CV";
    
                        input = validate(input);
                    
                        // Checks for "VD"
                    } else if (inputArr[i + 1] == 'D') {
                        input = input.substring(0, i);
                        input = input + "DV";
    
                        input = validate(input);
                    
                        // Checks for "VM"
                    } else if (inputArr[i + 1] == 'M') {
                        input = input.substring(0, i);
                        input = input + "MV";
    
                        input = validate(input);
                    }
                
                    // Checks for invalid forms of "X"
                } else if (inputArr[i] == 'X') {
                
                    // Checks for "XD"
                    if (inputArr[i + 1] == 'D') {
                        input = input.substring(0, i);
                        input = input + "DX";
    
                        input = validate(input);
                    
                        //Checks for "XM"
                    } else if (inputArr[i + 1] == 'M') {
                        input = input.substring(0, i);
                        input = input + "MX";
    
                        input = validate(input);
                    }
                
                    // Checks for invalid forms of "L"
                } else if (inputArr[i] == 'L') {
                
                    // Checks for "LC"
                    if (inputArr[i + 1] == 'C') {
                        input = input.substring(0, i);
                        input = input + "CL";
    
                        input = validate(input);
                    
                        // Checks for "LD"
                    } else if (inputArr[i + 1] == 'D') {
                        input = input.substring(0, i);
                        input = input + "DL";
    
                        input = validate(input);
                    
                        // Checks for "LM"
                    } else if (inputArr[i + 1] == 'M') {
                        input = input.substring(0, i);
                        input = input + "ML";
    
                        input = validate(input);
                    } else if (inputArr[i + 1] == 'L'){
                        input = input.substring(0, i);
                        input = input + "C";
    
                        input = validate(input);
                    }
                
                    // Checks for invalid form of "M"
                } else if (inputArr[i] == 'D') {
                
                    // Checks for "DM"
                    if (inputArr[i + 1] == 'M') {
                        input = input.substring(0, i);
                        input = input + "MD";
    
                        input = validate(input);
                    } else if (inputArr[i + 1] == 'D') {
                        input = input.substring(0, i);
                        input = input + "M";
    
                        input = validate(input);
                    }
                }
            }
        }
    
        if (inputArr.length >= 3){
            for (int i = 0; i < inputArr.length - 2; i++){
            
                // Checks if the key sequence from index i is "IVI"
                if (inputArr[i] == 'I' && inputArr[i + 1] == 'V' && inputArr[i + 2] == 'I') {
                
                    // Corrects invalid 5
                    input = input.substring(0, i);
                    input = input + "V";
                    
                    input = validate(input);
                
                    // Checks if the key sequence from index i is "IXI"
                } else if (inputArr[i] == 'I' && inputArr[i + 1] == 'X' && inputArr[i + 2] == 'I') {
                
                    // Corrects invalid 10
                    input = input.substring(0, i);
                    input = input + "X";
                    
                    input = validate(input);
                
                    // Checks if the key sequence from index i is "XLX"
                } else if (inputArr[i] == 'X' && inputArr[i + 1] == 'L' && inputArr[i + 2] == 'X') {
                
                    // Corrects invalid 50
                    input = input.substring(0, i);
                    input = input + "L";
    
                    input = validate(input);
                
                    // Checks if the key sequence from index i is "XCX"
                } else if (inputArr[i] == 'X' && inputArr[i + 1] == 'C' && inputArr[i + 2] == 'X') {
                
                    // Corrects invalid 100
                    input = input.substring(0, i);
                    input = input + "C";
    
                    input = validate(input);
                
                    // Checks if the key sequence from index i is "CDC"
                } else if (inputArr[i] == 'C' && inputArr[i + 1] == 'D' && inputArr[i + 2] == 'C') {
                
                    // Corrects invalid 500
                    input = input.substring(0, i);
                    input = input + "D";
    
                    input = validate(input);
                
                    // Checks if the key sequence from index i is "CMC"
                } else if (inputArr[i] == 'C' && inputArr[i + 1] == 'M' && inputArr[i + 2] == 'C') {
                
                    // Corrects invalid 1000
                    input = input.substring(0, i);
                    input = input + "M";
    
                    input = validate(input);
                }
            }
        }
        
        if (inputArr.length >= 4) {
            for (int i = 0; i < inputArr.length - 3; i++) {

                // Checks if the key sequence from index i is "IIII"
                if (inputArr[i] == 'I' && inputArr[i + 1] == 'I' && inputArr[i + 2] == 'I' && inputArr[i + 3] == 'I') {

                    // Corrects invalid 4 and 9
                    if (i != 0 && inputArr[i - 1] == 'V') {
                        input = input.substring(0, i - 1);
                        input = input + "IX";
    
                    } else {
                        input = input.substring(0, i);
                        input = input + "IV";
    
                    }
                    input = validate(input);
    
                    // Checks if the key sequence from index i is "XXXX"
                } else if (inputArr[i] == 'X' && inputArr[i + 1] == 'X' && inputArr[i + 2] == 'X' && inputArr[i + 3] == 'X') {

                    // Corrects invalid 40 and 90
                    if (i != 0 && inputArr[i - 1] == 'L') {
                        input = input.substring(0, i - 1);
                        input = input + "XC";
    
                    } else {
                        input = input.substring(0, i);
                        input = input + "XL";
    
                    }
                    input = validate(input);
    
                    // Checks if the key sequence from index i is "CCCC"
                } else if (inputArr[i] == 'C' && inputArr[i + 1] == 'C' && inputArr[i + 2] == 'C' && inputArr[i + 3] == 'C') {

                    // Corrects invalid 400 and 900
                    if (i != 0 && inputArr[i - 1] == 'D') {
                        input = input.substring(0, i - 1);
                        input = input + "CM";
                    } else {
                        input = input.substring(0, i);
                        input = input + "CD";
                    }
                    input = validate(input);
                    
                }
            }
        }

        return input;
    }

    /**
     * The convert method converts either arabic numbers or roman numerals entered into either
     * of the JTextAreas. It decides which way is correct by converting the input string into an
     * array of characters and then checks to see if the first value in the array is a numeric digit.
     *
     *
     * @param inputString String from either of the JTextAreas.
     * @return Either the arabic or roman output.
     */

    public String convert(String inputString) {
        int[] values = new int[4]; // Stores the numeric values from inputString
        boolean isDigit = false;  // Keeps track if an input string is in roman or arabic
        String output = ""; // Output that gets returned at the end of the method

        // Converts input string to a character array and checks if the first value is a numeric
        // digit
        char[] input = inputString.toCharArray();
        if(!inputString.isBlank()) {
            if (Character.isDigit(input[0])) {
                isDigit = true;
            }
        }

        /* If the first value is a digit then this block of code executes, the values from the character
        string are stored in values and the program decides which place each value holds based of the
        length of values.
         */
        if (isDigit) {
            for (int i = 0; i < input.length; i++){
                int temp = Character.getNumericValue(input[i]);
                values[i] = temp;
            }

            int onesDigit = 0, tensDigit = 0, hundredsDigit = 0, thousandsDigit = 0;

            if (input.length == 1) {   // If the length is 1, the only digit represents the ones place.
                onesDigit = values[input.length - 1];

            } else if (input.length == 2){   // If the length is 2, the digits represent the ones
                onesDigit = values[input.length - 1];  // and tens digit.
                tensDigit = values[input.length - 2];

            } else if (input.length == 3){  // If the length is 3, the digits represent the ones, tens,
                onesDigit = values[input.length - 1];  // and hundreds place.
                tensDigit = values[input.length - 2];
                hundredsDigit = values[input.length - 3];

            } else if (input.length == 4){  // If the length is 4, the digits represents the ones, tens,
                onesDigit = values[input.length - 1];  // and thousands place.
                tensDigit = values[input.length - 2];
                hundredsDigit = values[input.length - 3];
                thousandsDigit = values[input.length - 4];

            }

            // The following code constructs the output string based on conditional statements

            // Beginning of the Arabic to Roman conversion
            // Adds "M" for every 1000. ex: thousandsDigit = 2 => MM
            for (int i = 1; i <= thousandsDigit; i++){
                output = output.concat("M");

            }

            // Adds "C" for every 100. ex: hundredsDigit = 2 => CC
            // Also considers that 400, 500, and 900 have special conditions.
            for (int i = 1; i <= hundredsDigit; i++){
                if (hundredsDigit == 4){
                    output = output.concat("CD");
                    break;

                } else if (hundredsDigit == 9) {
                    output = output.concat("CM");
                    break;

                } else if (hundredsDigit >= 5) {
                    output = output.concat("D");
                    hundredsDigit -= 5;
                    i = 0;

                } else {
                    output = output.concat("C");

                }
            }

            // Adds "X" for every 10. ex: hundredsDigit = 2 => XX
            // Also considers that 40, 50, and 90 have special conditions.
            for (int i = 1; i <= tensDigit; i++){
                if (tensDigit == 4){
                    output = output.concat("XL");
                    break;

                } else if (tensDigit == 9) {
                    output = output.concat("XC");
                    break;

                } else if (tensDigit >= 5) {
                    output = output.concat("L");
                    tensDigit -= 5;
                    i = 0;

                } else {
                    output = output.concat("X");

                }
            }

            // Adds "I" for every 1. ex: hundredsDigit = 2 => II
            // Also considers that 4, 5, and 9 have special conditions.
            for (int i = 1; i <= onesDigit; i++){
                if (onesDigit == 4){
                    output = output.concat("IV");
                    break;

                } else if (onesDigit == 9) {
                    output = output.concat("IX");
                    break;

                } else if (onesDigit >= 5) {
                    output = output.concat("V");
                    onesDigit -= 5;
                    i = 0;

                } else {
                    output = output.concat("I");

                }
            }
        } else {     // Beginning of the Roman to Arabic conversion
            int outputNum = 0;

            // Goes through all values in the character string to convert to arabic
            for (int i = 0; i < input.length; i++){

                // Adds 1000 to outputNum if "M" is displayed or accounts for 900's special case
                if (input[i] == 'M') {
                    if (i != 0 && input[i - 1] == 'C') {
                        outputNum += 800;
                    } else {
                        outputNum += 1000;
                    }
                }

                // Adds 500 to outputNum if "D" is displayed or accounts for 400's special case
                if (input[i] == 'D') {
                    if (i != 0 && input[i - 1] == 'C') {
                        outputNum += 300;
                    } else {
                        outputNum += 500;
                    }
                }

                // Adds 100 to outputNum if "C" is displayed or accounts for 90's special case
                if (input[i] == 'C') {
                    if (i != 0 && input[i - 1] == 'X') {
                        outputNum += 80;
                    } else {
                        outputNum += 100;
                    }
                }

                // Adds 50 to outputNum if "L" is displayed or accounts for 40's special case
                if (input[i] == 'L') {
                    if (i != 0 && input[i - 1] == 'X') {
                        outputNum += 30;
                    } else {
                        outputNum += 50;
                    }
                }

                // Adds 10 to outputNum if "X" is displayed or accounts for 9's special case
                if (input[i] == 'X') {
                    if (i != 0 && input[i - 1] == 'I') {
                        outputNum += 8;
                    } else {
                        outputNum += 10;
                    }
                }

                // Adds 5 to outputNum if "V" is displayed or accounts for 4's special case
                if (input[i] == 'V') {
                    if (i != 0 && input[i - 1] == 'I') {
                        outputNum += 3;
                    } else {
                        outputNum += 5;
                    }
                }

                // Adds 1 to outputNum if "I" is displayed
                if (input[i] == 'I') {
                    outputNum += 1;
                }
            }

            // Inserts the value of outputNum into the string output for the method to return
            output = String.valueOf(outputNum);
        }

        // If output is 0, nothing is displayed in the boxes
        if (output.equals("0")) {
            output = "";
        }

        System.out.println("Output: " + output);  //Test
        System.out.print("\n");

        return output;
    }
}