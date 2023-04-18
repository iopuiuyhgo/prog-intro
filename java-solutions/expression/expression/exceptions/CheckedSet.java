package expression.exceptions;

import expression.ExprInt;
import expression.Set;
import expression.generic.Operations;

public class CheckedSet<T> extends Set<T> {
    public CheckedSet (Operations<T> operations, ExprInt<T> leftExp, ExprInt<T> rightExp) {
        super(operations, leftExp, rightExp);
    }
}
