import java.lang.Math;
import java.util.ArrayList;

public class Deck {
    private static final ArrayList<Card> deck = new ArrayList<>();
    
    private static final int MIN = 0;
    private static int MAX = 51;
    
    /**
     * Creates the deck of cards and stores every Card instance in an array list
     */
    public static void createDeck() {
        for (int i = 0; i < 4; i++) {
            for (int j = 1; j < 14; j++) {
                deck.add(new Card(i, j));
            }
        }
    }
    
    /**
     * Draws a card from the deck using a random number generator and removes that card from the deck after it is drawn.
     * @return Returns the removed card.
     */
    public static Card drawCard() {
        Card removedCard;
    
        int randNum = (int) (Math.random() * (MAX - MIN + 1) + MIN);
        removedCard = deck.get(randNum);
        deck.remove(randNum);
        MAX--;
        
        return removedCard;
    }
    
//    public static void main(String[] args) {
//        createDeck();
//
//        System.out.print("Printing the deck: \n");
//        for (int i = 0; i < 52; i++) {
//            System.out.println(deck.get(i).toString() + ": " + deck.get(i).getSuit() + ", " + deck.get(i).getRank() + ", " + deck.get(i).getValue());
//        }
//
//        System.out.print("\nDrawing random cards: \n");
//        for (int i = 0; i < 52; i++) {
//            System.out.println(drawCard().toString());
//        }
//    }
}