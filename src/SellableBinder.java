import java.math.BigDecimal;
/**
 * Abstract base class for sellable binders that implements the Sellable interface.
 * Provides common functionality for all sellable binder types.
 */
public abstract class SellableBinder extends Binder implements Sellable {
    /**
     * Creates a new sellable binder with the specified name.
     * @param name the name of the binder
     */
    public SellableBinder(String name) {
        super(name);
    }
    /**
     * Calculates the total value of the binder's contents.
     * Must be implemented by concrete subclasses.
     * @return the calculated monetary value
     */
    @Override
    public abstract BigDecimal calculateValue();
}