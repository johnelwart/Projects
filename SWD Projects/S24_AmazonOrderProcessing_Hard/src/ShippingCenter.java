public class ShippingCenter implements Runnable{
    /**
     * Creates the buffer for orders from WebServer
     */
    private final Buffer inputBuffer;

    /**
     * Creates the buffer to section 1
     */
    private final Buffer outputBuffer1;

    /**
     * Creates the buffer to section 2
     */
    private final Buffer outputBuffer2;

    /**
     * Creates a string to hold the input value taken from the buffer
     */
    private String[] inputString;

    /**
     * Holds the shippingCenter number that is added to the end of the order array
     */
    private final String centerNum;

    /**
     * Constructor for the ShippingCenter class
     * @param inputBuffer Initializes the buffer from WebServer
     * @param outputBuffer1 Initializes the buffer that goes to the first section
     * @param outputBuffer2 Initializes the buffer that goes to the second section
     * @param centerNum Initializes the center number to the value passed in from main
     */
    ShippingCenter(Buffer inputBuffer, Buffer outputBuffer1, Buffer outputBuffer2, String centerNum) {
        this.inputBuffer = inputBuffer;
        this.outputBuffer1 = outputBuffer1;
        this.outputBuffer2 = outputBuffer2;
        this.centerNum = centerNum;
    }

    /**
     * The program reads from the buffer and decides if the order goes to the first or second
     * section based on the first letter of the order category. It also adds the center number
     * to the end of the string array
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

            String[] newStringArr = new String[8];

            for(int i = 0; i < inputString.length; i++) {
                newStringArr[i] = inputString[i];
            }

            newStringArr[7] = centerNum;

            if (newStringArr[6].charAt(0) >= 65 && newStringArr[6].charAt(0) <= 80) {
                try {
                    outputBuffer1.setBuffer(newStringArr);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            } else {
                try {
                    outputBuffer2.setBuffer(newStringArr);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        inputString = new String[]{};
        try {
            outputBuffer1.setBuffer(inputString);
            outputBuffer2.setBuffer(inputString);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
