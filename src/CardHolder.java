import java.util.*;
/**
 * Abstract class representing a generic holder of cards (e.g., a binder or deck).
 * Stores a name and a collection of {@link Card} objects.
 */
public abstract class CardHolder {
    private final String name;
    private final ArrayList<Card> cards;
    /**
     * Constructs a CardHolder with the specified name.
     *
     * @param name the name of the card holder (e.g., binder or deck name)
     */
    CardHolder(String name) {
        this.name = name;
        cards = new ArrayList<>();
    }
    /**
     * Adds a card to the holder.
     *
     * @param card the card to be added
     */
    public void addCard(Card card) {
        cards.add(card);
    }
    /**
     * Removes a card from the holder.
     *
     * @param card the card to be removed
     */
    public void removeCard(Card card) {
        cards.remove(card);
    }
    /**
     * Gets the name of this card holder.
     *
     * @return the name of the card holder
     */
    public String getName() {
        return name;
    }
    /**
     * Returns a copy of the list of cards in this holder.
     * Modifications to the returned list will not affect the original list.
     *
     * @return a copy of the card list
     */
    public ArrayList<Card> getCards() {return new ArrayList<>(cards);}
    /**
     * Checks if the specified card is in this holder.
     *
     * @param card the card to check for
     * @return true if the card is in the holder, false otherwise
     */
    public boolean containsCard(Card card) {
        return cards.contains(card);
    }
    /**
     * Returns the number of cards currently in the holder.
     *
     * @return the card count
     */
    public int getCardsCount() {
        return cards.size();
    }
}