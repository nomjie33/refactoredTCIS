import java.math.BigDecimal;

public class LuxuryBinder extends SellableBinder {
    private BigDecimal customPrice = BigDecimal.valueOf(-1);

    public LuxuryBinder(String name) {
        super(name);
    }

    @Override
    public void addCard(Card card) {
        if (card.getVariant() == CardVariant.NORMAL ||
                (card.getRarity() != CardRarity.RARE && card.getRarity() != CardRarity.LEGENDARY)) {
            throw new IllegalArgumentException("Luxury binder can only contain non-normal rare/legendary cards");
        }
        super.addCard(card);
    }

    public boolean setCustomPrice(BigDecimal price) {
        BigDecimal minPrice = getCards().stream()
                .map(Card::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (price.compareTo(minPrice) >= 0) {
            this.customPrice = price;
            return true;
        }
        return false;
    }

    @Override
    public BigDecimal calculatePrice() {
        BigDecimal basePrice = customPrice.compareTo(BigDecimal.ZERO) > 0 ?
                customPrice :
                getCards().stream()
                        .map(Card::getValue)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
        return basePrice.multiply(new BigDecimal("1.10")); // 10% handling fee
    }
}