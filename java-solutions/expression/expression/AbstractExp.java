package expression;

import expression.generic.Operations;

public abstract class AbstractExp<T> implements ExprInt<T> {
    protected Operations<T> operations;
    protected ExprInt<T> leftExp;
    protected ExprInt<T> rightExp;
    abstract String getOperator();
    public AbstractExp(Operations<T> operations, ExprInt<T> leftExp, ExprInt<T> rightExp) {
        this.leftExp = leftExp;
        this.rightExp = rightExp;
        this.operations = operations;

    }

    @Override
    public String toString() {
        return "("+ leftExp.toString() + " " + getOperator() + " " + rightExp.toString() + ")";
    }
    @Override
    public boolean equals(Object expression) {
        if (expression == null) {
            return false;
        }
        return this.toString().equals(expression.toString());
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }
}
