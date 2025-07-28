public class CollectorBinder extends Binder {
    public CollectorBinder(String name) {
        super(name);
    }

    @Override
    public boolean addCard(Card card) {
        if (card.getVariant() == CardVariant.NORMAL ||(card.getRarity() != CardRarity.RARE && card.getRarity() != CardRarity.LEGENDARY)) {
            return false; // Fail silently (Controller will show error)
        }
        return super.addCard(card); // Parent handles capacity checks
    }
}