import javax.swing.JFrame;

public class BlackjackClientTest {
    
    /**
     * Starts a new instance of the Blackjack client using the IP address of the server.
     * @param args Standard parameter.
     */
    public static void main(String[] args) {
        BlackjackClient app;
        
        if (args.length == 0) {
            app = new BlackjackClient("172.17.95.230");
        } else {
            app = new BlackjackClient(args[0]);
        }
        
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}