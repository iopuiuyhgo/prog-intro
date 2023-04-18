package expression.generic;

import expression.exceptions.CalculationException;

public class ShortExp implements Operations<Short> {
    @Override
    public Short add(Short a, Short b) {
        return (short) (a + b);
    }

    @Override
    public Short divide(Short a, Short b) {
        return (short) (a / b);
    }

    @Override
    public Short multiply(Short a, Short b) {
        return (short) (a * b);
    }

    @Override
    public Short subtract(Short a, Short b) {
        return (short) (a - b);
    }

    @Override
    public Short negate(Short a) {
        return (short) - a;
    }

    @Override
    public Short constant(int a) {
        return (short) a;
    }

    @Override
    public Short clear(Short a, Short b) {
        return (short) (a & (~(1<< b)) | (1 << b));
    }

    @Override
    public Short count(Short a) {
        int end = 0;
        while (a != 0) {
            end += a & 1;
            a = (short) (a >>> 1);
        }
        return (short) end;
    }

    @Override
    public Short set(Short a, Short b) {
        return (short) (a & (~(1<< b)) | (0 << b));
    }
}
