package expression.exceptions;

import expression.ExprInt;
import expression.Set;

public class CheckedSet extends Set implements ExprInt {
    public CheckedSet (ExprInt leftExp, ExprInt rightExp) {
        super(leftExp, rightExp);
    }
}
