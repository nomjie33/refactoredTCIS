import java.math.BigDecimal;

public class PauperBinder extends SellableBinder {
    public PauperBinder(String name) {
        super(name);
    }

    @Override
    public boolean addCard(Card card) {
        if (card.getRarity() != CardRarity.COMMON && card.getRarity() != CardRarity.UNCOMMON) {
            return false; // Fail silently (Controller will show error)
        }
        return super.addCard(card); // Parent handles capacity checks
    }

    @Override
    public BigDecimal calculatePrice() {
        return getCards().stream()
                .map(Card::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}