import java.math.BigDecimal;
/**
 * Sellable binder for rare and legendary cards with 10% premium.
 */
public class RaresBinder extends SellableBinder {
    /**
     * Creates a new RaresBinder with given name.
     * @param name the binder name
     */
    public RaresBinder(String name) {
        super(name);
    }
    /**
     * Adds card if it's rare or legendary.
     * @param card the card to add
     * @return true if added successfully
     */
    @Override
    public boolean addCard(Card card) {
        if (card.getRarity() != CardRarity.RARE && card.getRarity() != CardRarity.LEGENDARY) {
            return false; // Fail silently (Controller will show error)
        }
        return super.addCard(card); // Parent handles capacity checks
    }
    /**
     * Calculates total value with 10% premium.
     * @return total value including premium
     */
    @Override
    public BigDecimal calculateValue() {
        BigDecimal subtotal = getCards().stream()
                .map(Card::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return subtotal.multiply(new BigDecimal("1.10")); // 10% handling fee
    }
}