package expression.exceptions;

import expression.*;
import expression.generic.Operations;

public class CheckedAdd<T> extends Add<T>{
    public CheckedAdd(Operations<T> operations, ExprInt<T> leftExp, ExprInt<T> rightExp) {
        super(operations, leftExp, rightExp);
    }
}
