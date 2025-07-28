import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
/**
 * Represents a trading card with a name, rarity, variant, value, and count.
 * The card's value is automatically adjusted based on its variant.
 */
public class Card {
    private final String name;
    private final CardRarity rarity;
    private final CardVariant variant;
    private final BigDecimal value;
    private int count;
    /**
     * Constructs a new Card with the given attributes.
     * <p>
     * If the rarity is not {@code RARE} or {@code LEGENDARY}, the variant is automatically set to {@code NORMAL}.
     * The value is adjusted based on the variant using a multiplier.
     *
     * @param name   the name of the card
     * @param rarity the rarity of the card
     * @param variant the variant of the card (may be overridden based on rarity)
     * @param value  the base value of the card (must be > 0)
     * @param count  the initial card count (must be >= 0)
     * @throws IllegalArgumentException if value ≤ 0 or count < 0
     */
    public Card(String name, CardRarity rarity, CardVariant variant, BigDecimal value, int count) {
        if(value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Value must be greater than zero.");
        } else if(count < 0) {
            throw new IllegalArgumentException("Count cannot be negative.");
        }

        this.name = name;
        this.rarity = rarity;
        this.count = count;

        // Non-rare and non-legendary cards can only have a Common variant
        if(rarity == CardRarity.RARE || rarity == CardRarity.LEGENDARY) {
            this.variant = variant;
        } else {
            this.variant = CardVariant.NORMAL;
        }

        // Card value multiplier based on variant
        switch(this.variant) {
            case EXTENDED_ART -> this.value = (value.add(value.multiply(new BigDecimal("0.50")))).setScale(2, RoundingMode.HALF_UP);
            case FULL_ART -> this.value = (value.add(value)).setScale(2, RoundingMode.HALF_UP);
            case ALT_ART -> this.value = (value.add(value.multiply(new BigDecimal("2.00")))).setScale(2, RoundingMode.HALF_UP);
            default -> this.value = value.setScale(2, RoundingMode.HALF_UP);
        }
    }
    /**
     * Checks equality based on name (case-insensitive), rarity, variant, and value.
     *
     * @param o the object to compare
     * @return true if the other object is a Card with the same attributes
     */
    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Card)) {
            return false;
        }

        return name.equalsIgnoreCase(((Card) o).name) && rarity == ((Card) o).rarity
                && variant == ((Card) o).variant && value.equals(((Card) o).value);
    }
    /**
     * Returns a hash code based on name, rarity, variant, and value.
     *
     * @return hash code for the card
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, rarity, variant, value);
    }
    /**
     * Gets the card's name.
     *
     * @return card name
     */
    @Override
    public String toString() {
        return name;
    }
    public String getName() {
        return name;
    }
    /**
     * Gets the card's rarity.
     *
     * @return card rarity
     */
    public CardRarity getRarity() {
        return rarity;
    }
    /**
     * Gets the card's variant.
     *
     * @return card variant
     */
    public CardVariant getVariant() {
        return variant;
    }
    /**
     * Gets the final computed value of the card based on its variant.
     *
     * @return card value
     */
    public BigDecimal getValue() {
        return value;
    }
    /**
     * Gets the number of copies of this card.
     *
     * @return card count
     */
    public int getCount() {
        return count;
    }
    /**
     * Sets the number of copies of this card.
     *
     * @param count new count value
     */
    public void setCount(int count) {
        this.count = count;
    }
}