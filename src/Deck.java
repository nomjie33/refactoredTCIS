/**
 * Represents a deck of cards in the Trading Card Inventory System.
 * A deck is a type of {@link CardHolder} and has a specific maximum card capacity.
 */
public class Deck extends CardHolder {
    public static final int MAX_CARD_COUNT = 10;
    /**
     * Constructs a new Deck with the specified name.
     *
     * @param name the name of the Deck
     */
    public Deck(String name) {
        super(name);
    }
}