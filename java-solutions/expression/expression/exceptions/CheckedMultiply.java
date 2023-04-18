package expression.exceptions;

import expression.*;
import expression.generic.Operations;

public class CheckedMultiply<T> extends Multiply<T> {
    public CheckedMultiply (Operations<T> operations, ExprInt<T> leftExp, ExprInt<T> rightExp) {super(operations, leftExp, rightExp);}
}
