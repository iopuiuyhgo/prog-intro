package expression;

import expression.exceptions.OverflowException;

public class Multiply extends AbstractExp implements ExprInt {
    public Multiply(ExprInt leftExp, ExprInt rightExp) {
        super(leftExp, rightExp, "*", new String[] {"", "/"});
    }
    public int evaluate(int x) {
        return calculate(leftExp.evaluate(x), rightExp.evaluate(x));
    }
    public int evaluate(int x, int y, int z) {
        return calculate(leftExp.evaluate(x, y, z), rightExp.evaluate(x, y, z));
    }
    private int abs(int c) {
        if (c < 0) {
            return -c;
        }
        return c;
    }
    private int max(int c1, int c2) {
        if (c1 > c2) {
            return c1;
        }
        return c2;
    }
    private int min(int c1, int c2) {
        if (c1 < c2) {
            return c1;
        }
        return c2;
    }
    private int calculate(int l, int r) {
        if ((l == Integer.MIN_VALUE && r < 0) || (r == Integer.MIN_VALUE && l < 0)) {
            throw new OverflowException("Overflow down: " + l + " * " + r + " < -2147483648");
        }
        if (((l > 0 && r > 0) || (l < 0 && r < 0)) && (abs(l) > Integer.MAX_VALUE/abs(r))) {
            throw new OverflowException("Overflow up: " + l + " * " + r + " > 2147483647");
        } else if (((l > 0 && r < 0) || (l < 0 && r > 0)) && (min(l, r) < Integer.MIN_VALUE/max(l, r))) {
            throw new OverflowException("Overflow down: " + l + " * " + r + " < -2147483648");
        }
        return l * r;
    }
}