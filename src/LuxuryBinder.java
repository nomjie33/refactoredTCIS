import java.math.BigDecimal;
/**
 * A premium binder for high-value cards with customizable pricing.
 */
public class LuxuryBinder extends SellableBinder {
    private BigDecimal customPrice;
    /**
     * Creates a new LuxuryBinder with the specified name.
     * @param name the name of the binder
     */
    public LuxuryBinder(String name) {
        super(name);
        this.customPrice = null;
    }
    /**
     * Checks if a card qualifies for this luxury binder.
     * @param card the card to check
     * @return true if card is rare/legendary with special variant
     */
    @Override
    public boolean canAddCard(Card card) {
        return (card.getRarity() == CardRarity.RARE ||
                card.getRarity() == CardRarity.LEGENDARY)
                && card.getVariant() != CardVariant.NORMAL;
    }
    /**
     * Sets a custom price for this binder.
     * @param price the requested price
     * @return true if price was set, false if below minimum value
     */
    public boolean setCustomPrice(BigDecimal price) {
        BigDecimal minPrice = calculateBaseValue();

        if (price.compareTo(minPrice) >= 0) {
            this.customPrice = price;
            return true;
        }

        return false;
    }
    /**
     * Calculates the base value of all cards in the binder.
     * @return sum of all card values
     */
    public BigDecimal calculateBaseValue() {
        return getCards().stream()
                .map(Card::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Calculates the total value with 10% handling fee.
     * @return total value including premium
     */
    @Override
    public BigDecimal calculateValue() {
        BigDecimal basePrice = (customPrice != null) ? customPrice : calculateBaseValue();
        return basePrice.multiply(new BigDecimal("1.10")); // Add 10% handling fee
    }
}