package expression.generic;

import java.math.BigInteger;

public class BigIntegerExp implements Operations<BigInteger> {

    @Override
    public BigInteger add(BigInteger a, BigInteger b) {
        return a.add(b);
    }

    @Override
    public BigInteger divide(BigInteger a, BigInteger b) {
        return a.divide(b);
    }

    @Override
    public BigInteger multiply(BigInteger a, BigInteger b) {
        return a.multiply(b);
    }

    @Override
    public BigInteger subtract(BigInteger a, BigInteger b) {
        return a.subtract(b);
    }

    @Override
    public BigInteger negate(BigInteger a) {
        return a.negate();
    }

    @Override
    public BigInteger constant(int a) {
        return new BigInteger(Integer.toString(a));
    }

    @Override
    public BigInteger clear(BigInteger a, BigInteger b) {
        return a.clearBit(b.intValue());
    }

    @Override
    public BigInteger count(BigInteger a) {
        return constant(a.bitCount());
    }

    @Override
    public BigInteger set(BigInteger a, BigInteger b) {
        return a.setBit(b.intValue());
    }
}
