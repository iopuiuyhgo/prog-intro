package expression;

public class Set extends AbstractExp implements ExprInt {
    public Set(ExprInt leftExp, ExprInt rightExp) {
        super(leftExp, rightExp, "set", new String[] {"* / + -", "* / + -"});
    }
    public int evaluate(int x) {
//        (x & (~(1 << n))) | (y << n)
        return leftExp.evaluate(x) & (~(1<< rightExp.evaluate(x))) | (1 << rightExp.evaluate(x));
    }
    public int evaluate(int x, int y, int z) {
        return leftExp.evaluate(x, y, z) & (~(1<< rightExp.evaluate(x, y, z))) | (1 << rightExp.evaluate(x, y, z));
    }
}
