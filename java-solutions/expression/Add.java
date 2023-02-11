package expression;

import expression.exceptions.CalculationException;
import expression.exceptions.OverflowException;

import java.io.IOException;
import java.util.IllegalFormatException;

public class Add extends AbstractExp implements ExprInt {
    public Add(ExprInt leftExp, ExprInt rightExp) {
        super(leftExp, rightExp, "+", new String[] {"* /", "- * /"});
    }
    public int evaluate(int x) {
        return calculate(leftExp.evaluate(x), rightExp.evaluate(x));
    }
    public int evaluate(int x, int y, int z) {
        return calculate(leftExp.evaluate(x, y, z), rightExp.evaluate(x, y, z));
    }
    private int calculate(int l, int r) {
        if (l > 0 && r > 0) {
            if (Integer.MAX_VALUE - l < r) {
                throw new OverflowException("Overflow up: " + l + " + " + r + " > 2147483647");
            }
        } else if (l < 0 && r < 0) {
            if (Integer.MIN_VALUE - l > r) {
                throw new OverflowException("Overflow down: " + l + " + " + r + " < -2147483648");
            }
        }
        return l + r;
    }
}