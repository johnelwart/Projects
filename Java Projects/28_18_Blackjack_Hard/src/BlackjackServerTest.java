import javax.swing.JFrame;

public class BlackjackServerTest {
    
    /**
     * Creates a new instance of the Blackjack server.
     * @param args Standard parameter.
     */
    public static void main(String[] args) {
        BlackjackServer app = new BlackjackServer();
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.execute();
    }
}