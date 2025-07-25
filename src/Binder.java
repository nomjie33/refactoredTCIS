/**
 * Base Binder class (extending CardHolder) with common binder functionality.
 */
public class Binder extends CardHolder {
    public static final int MAX_CARD_COUNT = 20;

    public Binder(String name) {
        super(name);
    }

    // Override addCard to enforce max capacity
    @Override
    public void addCard(Card card) {
        if (getCardsCount() >= MAX_CARD_COUNT) {
            throw new IllegalStateException("Binder is full (max " + MAX_CARD_COUNT + " cards)");
        }
        super.addCard(card);
    }
}