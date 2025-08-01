/**
 * Represents a deck of cards in the Trading Card Inventory System.
 */
public class Deck extends CardHolder {
    public static final int MAX_CARD_COUNT = 10;
    private final String name;
    private final boolean sellable;

    /**
     * Constructs a new Deck with the specified name and type
     * @param name the name of the Deck
     * @param sellable whether this deck can be sold
     */
    protected Deck(String name, boolean sellable) {
        super(name);
        this.name = name;
        this.sellable = sellable;
    }
    /**
     * Checks if this deck is sellable.
     * @return true if the deck is sellable
     */
    public boolean isSellable() {
        return sellable;
    }
    /**
     * Adds a card to the deck if there's space.
     * @param card the card to add
     * @return true if added successfully, false if deck is full
     */
    @Override
    public boolean addCard(Card card) {
        if (getCardsCount() >= MAX_CARD_COUNT) {
            return false;
        }
        return super.addCard(card);
    }
}