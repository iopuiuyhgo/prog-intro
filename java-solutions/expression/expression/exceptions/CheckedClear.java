package expression.exceptions;

import expression.AbstractExp;
import expression.Clear;
import expression.ExprInt;
import expression.generic.Operations;

public class CheckedClear<T> extends Clear<T> {
    public CheckedClear(Operations<T> operations, ExprInt<T> leftExp, ExprInt<T> rightExp) {
        super(operations, leftExp, rightExp);
    }
}