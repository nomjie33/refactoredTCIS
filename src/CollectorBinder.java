public class CollectorBinder extends Binder {
    public CollectorBinder(String name) {
        super(name);
    }

    @Override
    public void addCard(Card card) {
        if (card.getVariant() == CardVariant.NORMAL ||
                (card.getRarity() != CardRarity.RARE && card.getRarity() != CardRarity.LEGENDARY)) {
            throw new IllegalArgumentException("Collector binder can only contain non-normal rare/legendary cards");
        }
        super.addCard(card);
    }
}