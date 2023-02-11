package expression;

import expression.exceptions.OverflowException;
public class Negate implements ExprInt {
    private final ExprInt exp;
    public Negate(ExprInt exp) {
        this.exp = exp;
    }
    public String toString() {
        return "-(" + exp.toString() + ")";
    }
    public String toMiniString() {
        if (exp instanceof Const || exp instanceof Variable || exp instanceof Negate || exp instanceof Reverse) {
            return "- " + exp.toMiniString();
        } else {
            return "-(" + exp.toMiniString() + ")";
        }
    }
    @Override
    public String[] getWpts() {
        return new String[] {"", ""};
    }
    public boolean equals(Object expression) {
        if (expression == null) {
            return false;
        }
        return this.toString().equals(expression.toString());
    }

    @Override
    public int evaluate(int x) {
        return calculate(exp.evaluate(x));
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return calculate(exp.evaluate(x, y, z));
    }

    private int calculate(int e) {
        if (e == -2147483648) {
            throw new OverflowException("Overflow up: -(-2147483648) > 2147483647");
        }
        return -e;
    }
}
