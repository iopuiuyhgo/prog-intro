package expression.exceptions;

import expression.*;
import expression.generic.Operations;

public class CheckedSubtract<T> extends Subtract<T> {
    public CheckedSubtract(Operations<T> operations, ExprInt<T> leftExp, ExprInt<T> rightExp) {
        super(operations, leftExp, rightExp);
    }
}