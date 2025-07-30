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
     * @param name the name of the card holder (e.g., binder or deck name)
     */
    protected CardHolder(String name) {
        this.name = name;
        this.cards = new ArrayList<>();
    }

    /**
     * Adds a card to the holder if validation passes.
     * @param card the card to be added
     * @return true if card was added successfully
     */
    public boolean addCard(Card card) {
        if (card == null) return false;
        cards.add(card);

        return true;
    }

    /**
     * Removes a card from the holder.
     * @param card the card to be removed
     * @return true if card was removed, false if card wasn't present
     */
    public boolean removeCard(Card card) {
        return cards.remove(card);
    }

    /**
     * Gets the name of this card holder.
     * @return the name of the card holder
     */
    public String getName() {
        return name;
    }

    /**
     * Returns a defensive copy of the cards in this holder.
     * @return a new ArrayList containing all cards
     */
    public ArrayList<Card> getCards() {
        return new ArrayList<>(cards);
    }

    /**
     * Checks if the specified card is in this holder.
     * @param card the card to check for
     * @return true if the card is in the holder
     */
    public boolean containsCard(Card card) {
        return cards.contains(card);
    }

    /**
     * Returns the number of cards currently in the holder.
     * @return boolean value if cardholder is empty
     */
    public int getCardsCount() {
        return cards.size();
    }
    /**
     * Returns if cardholder is empty
     * @return the card count
     */
    public boolean isEmpty() {
        return cards.isEmpty();
    }

    /**
     * Validates if a card can be added to this holder.
     * @param card the card to validate
     * @return true if card is allowed (override in subclasses)
     */
    public boolean canAddCard(Card card) {
        return true;
    }
}