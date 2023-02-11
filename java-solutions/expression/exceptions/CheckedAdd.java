package expression.exceptions;

import expression.*;

public class CheckedAdd extends Add implements ExprInt{
    public CheckedAdd(ExprInt leftExp, ExprInt rightExp) {
        super(leftExp, rightExp);
    }
}
