package expression;

public class Const extends AbstractElem implements ExprInt {
    private final int constantInt;

    public Const(int constant) {
        super(String.valueOf(constant));
        this.constantInt = constant;
    }
    public Const(double constant) {
        super(String.valueOf(constant));
        this.constantInt = (int) constant;
    }
    public int evaluate(int x) {
        return constantInt;
    }
    public int evaluate(int x, int y, int z) {
        return constantInt;
    }
}