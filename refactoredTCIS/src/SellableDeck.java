import java.math.BigDecimal;

/**
 * Sellable deck (can be sold for its card value)
 */
public class SellableDeck extends Deck {
    public SellableDeck(String name) {
        super(name, true);
    }

    public BigDecimal calculateValue() {
        return getCards().stream()
                .map(Card::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}