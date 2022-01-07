import java.security.SecureRandom;

public class DeliveryTruck implements Runnable{
    /**
     * Creates a variable that stores a random number
     */
    private static final SecureRandom generator = new SecureRandom();

    /**
     * Creates the buffer for orders from the shipping docks
     */
    private final Buffer inputBuffer;

    /**
     * Holds the truck number
     */
    private final String truckNum;

    /**
     * keeps track of how many orders the truck contains
     */
    private int orderNum = 0;

    /**
     * set to false if there are
     */
    private boolean deliveriesLeft = true;

    /**
     * Constructor for DeliveryTruck
     * @param inputBuffer Initializes the buffer from ShippingDock
     * @param truckNum Initializes the truck number from the value passed in from main
     */
    DeliveryTruck(Buffer inputBuffer, String truckNum) {
        this.inputBuffer = inputBuffer;
        this.truckNum = truckNum;
    }

    /**
     * Run adds orders to a 4x10 multidimensional array and prints the info when 4 deliveries are added or
     * if the program runs out of orders to print. The truck number is also added to the end of the array.
     */
    @Override
    public void run() {
        String[] inputString;
        String[][] deliveries;

        while (deliveriesLeft) {
            deliveries = new String[4][10];

            while (orderNum < 4 && deliveriesLeft) {
                try {
                    inputString = inputBuffer.bufferGet();

                    if (inputString.length == 0) {
                        deliveriesLeft = false;

                    } else {
                        String[] newStringArr = new String[10];

                        for (int i = 0; i < inputString.length; i++) {
                            newStringArr[i] = inputString[i];
                        }
                        newStringArr[9] = truckNum;

                        for (int i = 0; i < deliveries[orderNum].length; i++) {
                            deliveries[orderNum][i] = newStringArr[i];
                        }

                        orderNum++;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            try {
                Thread.sleep(generator.nextInt(10000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            printInfo.toString(deliveries, truckNum);
            System.out.print("\n");

            orderNum = 0;
        }
    }
}
