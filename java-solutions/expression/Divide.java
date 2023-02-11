package expression;

import expression.exceptions.OverflowException;

public class Divide extends AbstractExp implements ExprInt {
    public Divide(ExprInt leftExp, ExprInt rightExp) {
        super(leftExp, rightExp, "/", new String[] {"", "* /"});
    }
    public int evaluate(int x) {
        return calculate(leftExp.evaluate(x), rightExp.evaluate(x));
    }
    public int evaluate(int x, int y, int z) {
        return calculate(leftExp.evaluate(x, y, z), rightExp.evaluate(x, y, z));
    }
    private int calculate(int l, int r) {
        if (l == -2147483648 && r == -1) {
            throw new OverflowException("Overflow up: " + l + " / " + r + " > 2147483647");
        } else if (r == 0) {
            throw new OverflowException("Division by zero: " + l + " / " + r);
        }
        return l/r;
    }
}