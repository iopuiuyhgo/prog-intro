package expression;

import expression.generic.Operations;

public class Set<T> extends AbstractExp<T> {
    public Set(Operations<T> operations, ExprInt<T> leftExp, ExprInt<T> rightExp) {
        super(operations, leftExp, rightExp);
    }
    public T evaluate(T x) {
//        (x & (~(1 << n))) | (y << n)
        return operations.set(leftExp.evaluate(x), rightExp.evaluate(x));
    }
    public T evaluate(T x, T y, T z) {
        return operations.set(leftExp.evaluate(x, y, z), rightExp.evaluate(x, y, z));
    }

    @Override
    String getOperator() {
        return "set";
    }
}
