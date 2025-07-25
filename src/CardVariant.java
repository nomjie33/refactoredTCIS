/**
 * Represents the variants of a trading card.
 * Each variant has an associated display name.
 */
public enum CardVariant {
    NORMAL("Normal"),
    EXTENDED_ART("Extended-art"),
    FULL_ART("Full-art"),
    ALT_ART("Alt-art");

    private final String name;
    /**
     * Assigns a display name to each possible CardVariant value
     *
     * @param name the display name of the variant
     */
    CardVariant(String name) {
        this.name = name;
    }
    /**
     * Returns the display name of this variant.
     *
     * @return the name of the variant
     */
    public String getName() {
        return name;
    }
}
