package expression;

import expression.generic.Operations;

public class Subtract<T> extends AbstractExp<T> {
    public Subtract(Operations<T> operations, ExprInt<T> leftExp, ExprInt<T> rightExp) {
        super(operations, leftExp, rightExp);
    }
    public T evaluate(T x) {
        return calculate(leftExp.evaluate(x), rightExp.evaluate(x));
    }
    public T evaluate(T x, T y, T z) {
        return calculate(leftExp.evaluate(x, y, z), rightExp.evaluate(x, y, z));
    }
    private T calculate(T l, T r) {
        return operations.subtract(l, r);
    }

    @Override
    String getOperator() {
        return "-";
    }
}