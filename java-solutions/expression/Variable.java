package expression;

public class Variable extends AbstractElem implements ExprInt {
    private final String var;

    public Variable(String name) {
        super(name);
        this.var = name;
    }
    public int evaluate(int x) {
        return x;
    }
    public double evaluate(double x) {
        return x;
    }

    public int evaluate(int x, int y, int z) {
        switch (var) {
            case "x" -> { return x; }
            case "y" -> { return y; }
            case "z" -> { return z; }
        }
        System.err.println("Error: Variable " + var + " is not defined");
        throw new IllegalArgumentException();
    }
}