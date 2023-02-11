package expression.exceptions;

import expression.*;

public class CheckedNegate extends Negate implements ExprInt {
    public CheckedNegate(ExprInt exp) {
        super(exp);
    }
}
