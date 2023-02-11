package expression;

public abstract class AbstractExp implements ExprInt {
    protected ExprInt leftExp;
    protected ExprInt rightExp;
    private final String operator;
    private final StringBuilder toStr = new StringBuilder();
    private final String[] wpts;
    public String[] getWpts() {
        return wpts;
    }
    public AbstractExp(ExprInt leftExp, ExprInt rightExp, String operator, String[] wpts) {
        this.leftExp = leftExp;
        this.rightExp = rightExp;
        this.operator = operator;
        this.wpts = wpts;

        addToStr(leftExp.getWpts()[0].split(" "), leftExp.toMiniString(), true);
        addToStr(rightExp.getWpts()[1].split(" "), rightExp.toMiniString(), false);
    }
    private void addToStr(String[] ws, String toMiniString, boolean isLeft) {
        for (int i = 0; i < ws.length; i++) {
            if (ws[i].equals(operator)) {
                toStr.append("(" + toMiniString + ")");
                break;
            }
            if (i == ws.length - 1) {
                toStr.append(toMiniString);
            }
        }
        if (isLeft) {
            toStr.append(" " + operator + " ");
        }
    }
    @Override
    public String toMiniString() {
        return toStr.toString();
    }
    @Override
    public String toString() {
        return "("+ leftExp.toString() + " " + operator + " " + rightExp.toString() + ")";
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
