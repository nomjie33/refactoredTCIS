import java.math.BigDecimal;

/**
 * Base Binder class (extending CardHolder) with common binder functionality.
 * Now includes validation hooks for subclasses.
 */
public class Binder extends CardHolder {
    public static final int MAX_CARD_COUNT = 20;
    private final String name;

    public Binder(String name) {
        super(name);
        this.name = name;
    }

    /**
     * Attempts to add a card with validation
     * @param card The card to add
     * @return true if added successfully, false if invalid or full
     */
    @Override
    public boolean addCard(Card card) {
        if (!canAddCard(card)) {
            return false;
        }
        if (getCardsCount() >= MAX_CARD_COUNT) {
            return false;
        }

        return super.addCard(card);
    }

    /**
     * Validates if a card can be added (override in subclasses)
     * @param card The card to check
     * @return true if card is allowed
     */
    public boolean canAddCard(Card card) {
        return true; // Default: all cards allowed
    }

    /**
     * For sellable binders - calculates sale price
     * @return price or null if not sellable
     */
    public BigDecimal calculatePrice() {
        return null; // Default: not sellable
    }

    public String getName() {
        return name;
    }

    public boolean isSellable() {
        return (this instanceof PauperBinder) ||
                (this instanceof RaresBinder) ||
                (this instanceof LuxuryBinder);
    }
}