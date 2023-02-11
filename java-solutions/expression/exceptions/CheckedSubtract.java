package expression.exceptions;

import expression.*;

public class CheckedSubtract extends Subtract implements ExprInt {
    public CheckedSubtract(ExprInt leftExp, ExprInt rightExp) {
        super(leftExp, rightExp);
    }
}