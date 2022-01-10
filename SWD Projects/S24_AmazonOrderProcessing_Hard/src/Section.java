import java.security.SecureRandom;

public class Section implements Runnable {
    /**
     * Creates a variable that stores a random number
     */
    private static final SecureRandom generator = new SecureRandom();

    /**
     * Creates the buffer for orders from ShippingCenter
     */
    private final Buffer inputBuffer;

    /**
     * Creates the buffer to the shipping dock
     */
    private final Buffer outputBuffer;

    /**
     * Creates a string to hold the input value taken from the buffer
     */
    private String[] inputString;

    /**
     * Holds the shippingCenter number that is added to the end of the order array
     */
    private final String sectionNum;

    /**
     * Constructor for the Section class
     * @param inputBuffer Initializes the buffer from ShippingCenter
     * @param outputBuffer1 Initializes the buffer that goes to the ShippingDock
     * @param sectionNum Initializes the section number to the value passed in from main
     */
    Section(Buffer inputBuffer, Buffer outputBuffer1, String sectionNum) {
        this.inputBuffer = inputBuffer;
        this.outputBuffer = outputBuffer1;
        this.sectionNum = sectionNum;
    }

    /**
     * Run reads input from the buffer and adds the section number to the end of the string array
     */
    @Override
    public void run() {

        while(true) {
            try {
                inputString = inputBuffer.bufferGet();

                if (inputString.length == 0) {
                    break;
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            String[] newStringArr = new String[9];

            for(int i = 0; i < inputString.length; i++) {
                newStringArr[i] = inputString[i];
            }

            newStringArr[8] = sectionNum;

            try {
                outputBuffer.setBuffer(newStringArr);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(generator.nextInt(5000));
            } catch (InterruptedException e) {
                e.printStackTrace();

            }
        }

        inputString = new String[]{};
        try {
            outputBuffer.setBuffer(inputString);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
