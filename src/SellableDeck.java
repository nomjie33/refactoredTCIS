import java.math.BigDecimal;

/**
 * Sellable deck (can be sold for its card value)
 */
public class SellableDeck extends Deck implements Sellable{
    /**
     * Creates a new sellable deck with the specified name.
     * @param name the name of the deck
     */
    public SellableDeck(String name) {
        super(name, true);
    }
    /**
     * Calculates the total value of all cards in the deck.
     * @return sum of all card values as BigDecimal
     */
    @Override
    public BigDecimal calculateValue() {
        return getCards().stream()
                .map(Card::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}