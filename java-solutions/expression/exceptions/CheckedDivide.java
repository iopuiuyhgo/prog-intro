package expression.exceptions;

import expression.*;
import expression.generic.IntegerExp;
import expression.generic.Operations;

public class CheckedDivide<T> extends Divide<T> {
    public CheckedDivide(Operations<T> operations, ExprInt<T> leftExp, ExprInt<T> rightExp) {
        super(operations, leftExp, rightExp);
    }
}
