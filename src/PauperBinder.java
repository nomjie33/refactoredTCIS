import java.math.BigDecimal;
/** Sellable binder for common/uncommon cards only. */
public class PauperBinder extends SellableBinder {
    public PauperBinder(String name) {
        super(name);
    }
    /**
     * Adds card if it's common/uncommon.
     * @param card the card to add
     * @return true if added successfully
     */
    @Override
    public boolean addCard(Card card) {
        if (card.getRarity() != CardRarity.COMMON && card.getRarity() != CardRarity.UNCOMMON) {
            return false; // Fail silently (Controller will show error)
        }
        return super.addCard(card); // Parent handles capacity checks
    }
    /** @return sum of all card values */
    @Override
    public BigDecimal calculateValue() {
        return getCards().stream()
                .map(Card::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}