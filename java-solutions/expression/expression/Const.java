package expression;

public class Const<T> extends AbstractElem<T> {
    private final T constant;

    public Const(T constant) {
        super(String.valueOf(constant));
        this.constant = constant;
    }
    public T evaluate(T x) {
        return constant;
    }
    public T evaluate(T x, T y, T z) {
        return constant;
    }
}