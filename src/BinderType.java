/**
 * Enumerates the different types of binders available in the Trading Card Inventory System.
 * Each binder type represents a specific category with different characteristics and restrictions.
 *
 * <p>The available binder types are:</p>
 * <ul>
 *   <li>{@code BASIC} - A standard binder with no special restrictions</li>
 *   <li>{@code PAUPER} - A binder specifically for common and uncommon cards</li>
 *   <li>{@code RARES} - A binder designed for rare and legendary cards</li>
 *   <li>{@code LUXURY} - A premium binder for high-value cards</li>
 *   <li>{@code COLLECTOR} - A special binder for complete collections</li>
 * </ul>
 *
 * @see Binder
 */
public enum BinderType {
    /**
     * Standard binder that can hold any type of card
     */
    BASIC,

    /**
     * Binder for common and uncommon cards only (no rares or legendaries)
     */
    PAUPER,

    /**
     * Binder specifically for rare and legendary cards
     */
    RARES,

    /**
     * Premium binder for high-value cards (typically full-art and alt-art variants)
     */
    LUXURY,

    /**
     * Special binder for complete collections (all cards of a specific set or theme)
     */
    COLLECTOR
}