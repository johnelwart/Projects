public class Card {
    /**
     * Suit of a card created.
     */
    private final int suit;
    
    /**
     * Rank of a card created.
     */
    private final int rank;
    
    /**
     * Value of a card created.
     */
    private final int value;
    
    /**
     * Array of strings containing all the possible suits
     */
    private final String[] SUITS = {"Hearts", "Clubs", "Spades", "Diamonds"};
    
    /**
     * Array of Strings representing the possible ranks.
     */
    private final String[] RANKS = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};
    
    /**
     * Constructor to set the suit and rank and value of the new card
     * @param suit Suit number passed in.
     * @param rank Rank number passed in.
     */
    public Card (int suit, int rank) {
        this.suit = suit;
        this.rank = rank;
        
        if (rank == 1) {
            value = 11;
        } else if (rank == 11 || rank == 12 || rank == 13) {
            value = 10;
        } else {
            value = rank;
        }
    }
    
    /**
     * Displays all the values of a selected card in the string format.
     * @return The card in string format.
     */
    public String toString() {
        return RANKS[rank - 1] + " of " + SUITS[suit];
    }
    
    /**
     * Returns the suit of the card in integer format.
     * @return Returns the suit of a selected card.
     */
    public int getSuit() {
        return suit;
    }
    
    /**
     * Returns the rank of a card in integer format.
     * @return Returns the rank of the card.
     */
    public int getRank() {
        return rank;
    }
    
    /**
     * Returns the value of a card.
     * @return Returns the value.
     */
    public int getValue() {
        return value;
    }
}
