/**
 * A specialized binder for collector-grade cards that only accepts rare/legendary cards with special variants.
 */
public class CollectorBinder extends Binder {
    /**
     * Creates a new CollectorBinder with the specified name.
     *
     * @param name the name of the binder
     */
    public CollectorBinder(String name) {
        super(name);
    }
    /**
     * Adds a card to the binder if it meets collector criteria.
     *
     * @param card the card to add
     * @return true if card was added, false if card didn't meet requirements
     */
    @Override
    public boolean addCard(Card card) {
        if(card.getVariant() == CardVariant.NORMAL ||(card.getRarity() != CardRarity.RARE && card.getRarity() != CardRarity.LEGENDARY)) {
            return false;
        }

        return super.addCard(card);
    }
}