package expression;

import expression.generic.Operations;

public class Count<T> implements ExprInt<T>{
    private final ExprInt<T> exp;
    private final Operations<T> operations;
    public Count(Operations<T> operations, ExprInt<T> exp) {
        this.exp = exp;
        this.operations = operations;
    }
    public String toString() {
        return "count(" + exp + ")";
    }

    @Override
    public T evaluate(T x) {
        return calculate(exp.evaluate(x));
    }

    @Override
    public T evaluate(T x, T y, T z) {
        return calculate(exp.evaluate(x, y, z));
    }
    private T calculate(T e) {
        return operations.count(e);
    }
}
