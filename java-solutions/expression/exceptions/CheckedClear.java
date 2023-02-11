package expression.exceptions;

import expression.AbstractExp;
import expression.Clear;
import expression.ExprInt;

public class CheckedClear extends Clear implements ExprInt {
    public CheckedClear(ExprInt leftExp, ExprInt rightExp) {
        super(leftExp, rightExp);
    }
}