package expression;

import expression.generic.Operations;

public class Clear<T> extends AbstractExp<T>{
    public Clear(Operations<T> operations, ExprInt<T> leftExp, ExprInt<T> rightExp) {
        super(operations, leftExp, rightExp);
    }
    public T evaluate(T x) {
        return operations.clear(leftExp.evaluate(x), rightExp.evaluate(x));
    }
    public T evaluate(T x, T y, T z) {
        return operations.clear(leftExp.evaluate(x, y, z), rightExp.evaluate(x, y, z));
    }

    @Override
    String getOperator() {
        return "clear";
    }
}
