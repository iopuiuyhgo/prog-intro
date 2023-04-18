package expression;

import expression.exceptions.OverflowException;
import expression.generic.Operations;

public class Negate<T> implements ExprInt<T> {
    private final ExprInt<T> exp;
    private final Operations<T> operations;
    public Negate(Operations<T> operations, ExprInt<T> exp) {
        this.exp = exp;
        this.operations = operations;
    }
    public String toString() {
        return "-(" + exp.toString() + ")";
    }

    public boolean equals(Object expression) {
        if (expression == null) {
            return false;
        }
        return this.toString().equals(expression.toString());
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
        return operations.negate(e);
    }
}
