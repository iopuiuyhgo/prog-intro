package expression.generic;

import expression.ExprInt;
import expression.TripleExpression;
import expression.exceptions.ExpressionParser;
import expression.exceptions.OverflowException;

import java.util.HashMap;
import java.util.Map;

public class GenericTabulator implements Tabulator {

    private HashMap<String, Operations<?>> modes = new HashMap<>(Map.of(
            "i", new IntegerExp(),
            "u", new UIntegerExp(),
            "d", new DoubleExp(),
            "bi", new BigIntegerExp(),
            "f", new FloatExp(),
            "s", new ShortExp()
    ));
    @Override
    public Object[][][] tabulate(String m, String expression, int x1, int x2, int y1, int y2, int z1, int z2) {
        Operations<?> mode = modes.get(m);
        return doRes(mode, expression, x1, x2, y1, y2, z1, z2);
    }
    private <T> Object[][][] doRes(Operations<T> mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) {
        Object[][][] result = new Object[x2 - x1 + 1][y2 - y1 + 1][z2 - z1 + 1];
        TripleExpression<T> parser = new ExpressionParser<T>().parse(expression, mode);
        for (int i = 0; i <= x2 - x1; i++) {
            for (int j = 0; j <= y2 - y1; j++) {
                for (int k = 0; k <= z2 - z1; k++) {
                    try {
                        result[i][j][k] = parser.evaluate(mode.constant(x1 + i),
                                mode.constant(y1 + j), mode.constant(z1 + k));
                    } catch (OverflowException | ArithmeticException e) {
                        result[i][j][k] = null;
                    }
                }
            }
        }
        return result;
    }
}
