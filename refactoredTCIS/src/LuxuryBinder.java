import java.math.BigDecimal;

public class LuxuryBinder extends SellableBinder {
    private BigDecimal customPrice;

    public LuxuryBinder(String name) {
        super(name);
        this.customPrice = null;
    }

    @Override
    public boolean canAddCard(Card card) {
        return (card.getRarity() == CardRarity.RARE ||
                card.getRarity() == CardRarity.LEGENDARY)
                && card.getVariant() != CardVariant.NORMAL;
    }

    public boolean setCustomPrice(BigDecimal price) {
        BigDecimal minPrice = calculateBaseValue();
        if (price.compareTo(minPrice) >= 0) {
            this.customPrice = price;
            return true;
        }
        return false;
    }

    public BigDecimal calculateBaseValue() {
        return getCards().stream()
                .map(Card::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal calculatePrice() {
        BigDecimal basePrice = (customPrice != null) ? customPrice : calculateBaseValue();
        return basePrice.multiply(new BigDecimal("1.10")); // Add 10% handling fee
    }
}