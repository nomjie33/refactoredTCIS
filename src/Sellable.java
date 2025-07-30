import java.math.BigDecimal;

/**
 * Interface for objects that have a monetary value.
 */
public interface Sellable {
    /**
     * Calculates the monetary value of the object.
     * @return the calculated value
     */
    BigDecimal calculateValue();
}