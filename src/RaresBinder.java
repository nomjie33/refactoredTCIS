import java.math.BigDecimal;

public class RaresBinder extends SellableBinder {
    public RaresBinder(String name) {
        super(name);
    }

    @Override
    public void addCard(Card card) {
        if (card.getRarity() != CardRarity.RARE && card.getRarity() != CardRarity.LEGENDARY) {
            throw new IllegalArgumentException("Rares binder can only contain rare/legendary cards");
        }
        super.addCard(card);
    }

    @Override
    public BigDecimal calculatePrice() {
        BigDecimal subtotal = getCards().stream()
                .map(Card::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return subtotal.multiply(new BigDecimal("1.10")); // 10% handling fee
    }
}