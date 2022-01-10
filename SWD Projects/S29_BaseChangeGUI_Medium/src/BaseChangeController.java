import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class BaseChangeController {

    @FXML
    private Button calcButton;

    @FXML
    private Slider currBaseSlider;

    @FXML
    private Label currBaseSliderLabel;

    @FXML
    private Label currSliderValue;

    @FXML
    private Slider desBaseSlider;

    @FXML
    private Label desBaseSliderLabel;

    @FXML
    private Label desSliderValue;

    @FXML
    private TextField outputField;

    @FXML
    private Label outputLabel;

    @FXML
    private Label titleBar;

    @FXML
    private TextField valueField;

    @FXML
    private Label valueFieldLabel;

    /**
     * Method that is called when the "Calculate" button is pressed on the GUI. Also contains the code to convert from
     * one base to the other and prints the output to the output field.
     * @param event Default parameter that is never used in this function
     */
    @FXML
    void pressCalc(ActionEvent event) {
        String inValue = valueField.getText();
        String outValue;
        ArrayList<Integer> inputArr;

        inValue = inValue.toUpperCase(Locale.ROOT);

        inputArr = lettersToNums(inValue, inValue.length(), currBase);

//        // Loop for testing purposes
//        for (int i = 0; i < inputArr.size(); i++) {
//            System.out.print(inputArr.get(i) + " ");
//        }
//        System.out.print("\n");

        if (!currBase.equals(desBase)) {
            int base10 = 0;
            int quotient;
            int quotientCopy;
            ArrayList<Integer> remainders = new ArrayList<>();

            int j = inputArr.size() - 1;

            for (int i = 0; i < inputArr.size(); i++) {
                base10 += (inputArr.get(i) * (Math.pow(currBase, j)));
                j--;
            }

            if (desBase != 10) {
                quotient = base10;

                while (quotient > 0) {
                    quotientCopy = quotient;
                    quotient = quotient / desBase;
                    remainders.add((quotientCopy % desBase));
                }

                ArrayList<Integer> temp = new ArrayList<>(remainders);
                remainders.clear();
                for (int i = temp.size() - 1; i > -1; i--) {
                    remainders.add(temp.get(i));
                }

                outValue = numToLetters(remainders);
            } else {
                outValue = String.valueOf(base10);
            }

        } else {
            outValue = numToLetters(inputArr);
        }

        if (!inputArr.isEmpty()) {
            outputField.setText(outValue);
        } else {
            outputField.setText("");
        }
    }

    private static Integer currBase = 10;
    private static Integer desBase = 10;
    private static final NumberFormat currBaseString = NumberFormat.getInstance();
    private static final NumberFormat desBaseString = NumberFormat.getInstance();

    /**
     * Updates the current value of the sliders in the text fields next to them when the user is interacting with them.
     */
    public void initialize() {
        currBaseSlider.valueProperty().addListener(
                new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> ov,
                                        Number oldValue, Number newValue) {
                        currBase = newValue.intValue();
                        currSliderValue.setText(currBaseString.format(currBase));
                    }
                }
        );

        desBaseSlider.valueProperty().addListener(
                new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> ov,
                                        Number oldValue, Number newValue) {
                        desBase = newValue.intValue();
                        desSliderValue.setText(desBaseString.format(desBase));
                    }
                }
        );
    }

    /**
     * If the input has letters in it. This method converts those letters to their respective values to make conversion
     * easier and also checks to see if the input uses any invalid characters and ignores them if there are some.
     * @param input String from the input text field.
     * @param size Size of the input string to ensure the while loop goes over every value in the string.
     * @param base The base of the input to check which values can and cannot be used.
     * @return Returns an ArrayList of integers that the calculator will use during conversion.
     */
    public ArrayList<Integer> lettersToNums(String input, int size, Integer base) {
        ArrayList<Integer> intArray = new ArrayList<>();
        char[] invalidChars = {'W', 'X', 'Y', 'Z'};
        int count = 0;
        int newVal;
        boolean useChar;

        while (count < size) {
            useChar = true;

            newVal = Character.getNumericValue(input.charAt(count));


            for (int i = 0; i < invalidChars.length; i++) {
                if (newVal == invalidChars[i]) {
                    useChar = false;
                }
            }

            if (newVal < base && useChar) {
                intArray.add(newVal);

            }
            count++;
        }

        return intArray;
    }

    /**
     * Used to convert values above 9 to their respective letter values for bases above base 10. This method goes
     * through the whole ArrayList passed in and converts values accordingly.
     * @param inputList ArrayList of integers passed in from the conversion algorithm.
     * @return Returns the formatted string that will be sent to the output text field.
     */
    public String numToLetters(ArrayList<Integer> inputList) {
        StringBuilder output = new StringBuilder();

        for (int i = 0; i < inputList.size(); i++) {
            if (inputList.get(i) >= 10) {
                output.append(Character.toChars(inputList.get(i) + 55));
            } else if (inputList.get(i) >= 0 && inputList.get(i) <= 9) {
                output.append(inputList.get(i));
            }
        }

        return output.toString();
    }
}