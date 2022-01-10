import java.util.concurrent.ArrayBlockingQueue;

public class Buffer {
    /**
     * Creates the ArrayBlockingQueue that works with string arrays
     */
    private final ArrayBlockingQueue<String[]> buffer;

    /**
     * Constructor for the Buffer class that creates a new ArrayBlockingQueue that has a capacity of 1
     */
    Buffer() {
        buffer = new ArrayBlockingQueue<>(1);
    }

    /**
     * Method that retrieves the value stored in the buffer and returns it to wherever it was called from
     * @return Returns the value stored in the buffer
     * @throws InterruptedException
     */
    public String[] bufferGet() throws InterruptedException {
        return buffer.take();
    }

    /**
     * Sets the value in the buffer using a string array passed from the class it's called from
     * @param input String array passed from the method call
     * @throws InterruptedException
     */
    public void setBuffer(String[] input) throws InterruptedException {
        buffer.put(input);
    }

    /**
     * Gets the current size of the buffer
     * @return returns the size
     */
    public int getSize() {
        return buffer.size();
    }
}
