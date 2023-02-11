package expression;

public class Count implements ExprInt{
    private final ExprInt exp;
    public Count(ExprInt exp) {
        this.exp = exp;
    }
    public String toString() {
        return "count(" + exp + ")";
    }
    public String toMiniString() {
        if (exp instanceof Const || exp instanceof Variable || exp instanceof Negate || exp instanceof Reverse) {
            return "count " + exp.toMiniString();
        } else {
            return "count(" + exp.toMiniString() + ")";
        }
    }

    @Override
    public String[] getWpts() {
        return new String[] {"", ""};
    }

    @Override
    public int evaluate(int x) {
        return calculate(exp.evaluate(x));
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return calculate(exp.evaluate(x, y, z));
    }
    private int calculate(int e) {
        int end = 0;
        while (e != 0) {
            end += e & 1;
            e = e >>> 1;
        }
        return end;
    }
}
