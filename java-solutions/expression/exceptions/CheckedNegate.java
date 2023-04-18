package expression.exceptions;

import expression.*;
import expression.generic.Operations;

public class CheckedNegate<T> extends Negate<T> {
    public CheckedNegate(Operations<T> operations, ExprInt<T> exp) {
        super(operations, exp);
    }
}
