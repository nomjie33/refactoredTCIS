import java.math.BigDecimal;

public class RaresBinder extends SellableBinder {
    public RaresBinder(String name) {
        super(name);
    }

    @Override
    public boolean addCard(Card card) {
        if (card.getRarity() != CardRarity.RARE && card.getRarity() != CardRarity.LEGENDARY) {
            return false; // Fail silently (Controller will show error)
        }
        return super.addCard(card); // Parent handles capacity checks
    }

    @Override
    public BigDecimal calculatePrice() {
        BigDecimal subtotal = getCards().stream()
                .map(Card::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return subtotal.multiply(new BigDecimal("1.10")); // 10% handling fee
    }
}