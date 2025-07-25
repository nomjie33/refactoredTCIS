import java.math.BigDecimal;

public abstract class SellableBinder extends Binder {
    public SellableBinder(String name) {
        super(name);
    }

    public abstract BigDecimal calculatePrice();

    public final BigDecimal sell() {
        return calculatePrice();
    }
}