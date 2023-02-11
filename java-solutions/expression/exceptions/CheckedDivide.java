package expression.exceptions;

import expression.*;
public class CheckedDivide extends Divide implements ExprInt {
    public CheckedDivide(ExprInt leftExp, ExprInt rightExp) {
        super(leftExp, rightExp);
    }
}
