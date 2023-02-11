package expression;

public class Clear extends AbstractExp implements ExprInt{
    public Clear(ExprInt leftExp, ExprInt rightExp) {
        super(leftExp, rightExp, "clear", new String[] {"* / + -", "* / + -"});
    }
    public int evaluate(int x) {
        return leftExp.evaluate(x) & (~(1<< rightExp.evaluate(x))) | (0 << rightExp.evaluate(x));
    }
    public int evaluate(int x, int y, int z) {
        return leftExp.evaluate(x, y, z) & (~(1<< rightExp.evaluate(x, y, z))) | (0 << rightExp.evaluate(x, y, z));
    }
}
