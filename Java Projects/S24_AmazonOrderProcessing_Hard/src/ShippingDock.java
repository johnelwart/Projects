public class ShippingDock implements Runnable{
    /**
     * Creates the buffer for orders from the sections
     */
    private final Buffer inputBuffer;

    /**
     * Creates the buffer to delivery truck 1
     */
    private final Buffer outputBuffer1;

    /**
     * Creates the buffer to delivery truck 2
     */
    private final Buffer outputBuffer2;

    /**
     * Creates a string to hold the input value taken from the buffer
     */
    private String[] inputString;

    /**
     * Constructor for the ShippingDock class
     * @param inputBuffer Initializes the buffer from ShippingDock
     * @param outputBuffer1 Initializes the buffer that goes to DeliveryTruck 1
     * @param outputBuffer2 Initializes the buffer that goes to DeliveryTruck 2
     */
    ShippingDock(Buffer inputBuffer, Buffer outputBuffer1, Buffer outputBuffer2) {
        this.inputBuffer = inputBuffer;
        this.outputBuffer1 = outputBuffer1;
        this.outputBuffer2 = outputBuffer2;
    }

    /**
     * Run reads input from the buffer and then loops over the two output buffers until one of them opens
     * up to accept deliveries.
     */
    @Override
    public void run() {
        boolean wait;

        while(true) {
            wait = true;
            try {
                inputString = inputBuffer.bufferGet();

                if (inputString.length == 0) {
                    break;
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            while (wait) {
                if (outputBuffer1.getSize() == 0) {
                    try {
                        outputBuffer1.setBuffer(inputString);
                        wait = false;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                } else if (outputBuffer2.getSize() == 0) {
                    try {
                        outputBuffer2.setBuffer(inputString);
                        wait = false;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
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
