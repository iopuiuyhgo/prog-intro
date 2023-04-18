package expression;

public class Variable<T> extends AbstractElem<T> {
    private final String var;

    public Variable(String name) {
        super(name);
        this.var = name;
    }
    public T evaluate(T x) {
        return x;
    }

    public T evaluate(T x, T y, T z) {
        switch (var) {
            case "x" -> { return x; }
            case "y" -> { return y; }
            case "z" -> { return z; }
        }
        System.err.println("Error: Variable " + var + " is not defined");
        throw new IllegalArgumentException();
    }
}