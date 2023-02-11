package expression.exceptions;

import expression.*;

public class CheckedMultiply extends Multiply implements ExprInt {
    public CheckedMultiply (ExprInt leftExp, ExprInt rightExp) {super(leftExp, rightExp);}
}
