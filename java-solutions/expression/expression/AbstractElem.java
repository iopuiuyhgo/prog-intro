package expression;

public abstract class AbstractElem<T> implements ExprInt<T> {
    String object;
    public AbstractElem(String object) {
        this.object = object;
    }
    @Override
    public String toString() {
        return object;
    }
    @Override
    public boolean equals(Object expression) {
        if (expression == null) {
            return false;
        }
        return this.toString().equals(expression.toString());
    }
    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }
}
