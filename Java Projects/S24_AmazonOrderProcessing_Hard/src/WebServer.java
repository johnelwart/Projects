import java.io.*;
import java.util.Scanner;

public class WebServer implements Runnable{

    /**
     * Creates the buffer to shipping center 1
     */
    private final Buffer outputBuffer1;

    /**
     * Creates the buffer to shipping center 2
     */
    private final Buffer outputBuffer2;

    /**
     * Constructor for the WebServer class
     * @param outputBuffer1 Initializes the buffer passed in from main to go to center 1
     * @param outputBuffer2 Initializes the buffer passed in from main to go to center 2
     */
    WebServer(Buffer outputBuffer1, Buffer outputBuffer2) {
        this.outputBuffer1 = outputBuffer1;
        this.outputBuffer2 = outputBuffer2;
    }

    /**
     * Run executes when executorService executes a new thread. The orders are read in from the csv file
     * then sorted according to city and placed in the respective shipping center buffer.
     */
    @Override
    public void run() {
        String[] inputStrings;
        String input;

        InputStream inFile = WebServer.class.getResourceAsStream("OrderFile.csv");
        Scanner scnr = new Scanner(inFile);

        scnr.nextLine();
        while (scnr.hasNextLine()) {
            input = scnr.nextLine();

            final int begin = 0; // Beginning of the string
            int end = 1;  // Current value being analyzed for commas, also used to mark the end point for substrings
            int i = 0;  // Current index of inputString[]
            inputStrings = new String[7];

            while (input != null) {
                if (end != input.length()) {
                    if (input.charAt(end) == ',') {
                        inputStrings[i] = input.substring(begin, end);
                        input = input.substring(end + 1);
                        i++;
                        end = 0;
                    }
                } else {
                    inputStrings[i] = input.substring(begin, end);
                    input = null;
                }
                end++;
            }

            if (inputStrings[1].equals("Los Angeles") || inputStrings[1].equals("San Fransisco")
                || inputStrings[1].equals("Seattle") || inputStrings[1].equals("Denver")) {

                try {
                    outputBuffer1.setBuffer(inputStrings);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            } else {
                try {
                    outputBuffer2.setBuffer(inputStrings);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        inputStrings = new String[]{};
        try {
            outputBuffer1.setBuffer(inputStrings);
            outputBuffer2.setBuffer(inputStrings);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
