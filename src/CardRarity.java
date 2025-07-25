/**
 * Represents the rarity levels of a trading card.
 * Each rarity level has an associated display name.
 */
public enum CardRarity {
    COMMON("Common"),
    UNCOMMON("Uncommon"),
    RARE("Rare"),
    LEGENDARY("Legendary");

    private final String name;
    /**
     * Assigns a display name to each possible CardRarity value
     *
     * @param name the display name of the rarity
     */
    CardRarity(String name) {
        this.name = name;
    }
    /**
     * Returns the display name of this rarity.
     *
     * @return the name of the rarity
     */
    public String getName() {
        return name;
    }
}