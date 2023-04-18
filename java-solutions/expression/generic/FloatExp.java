package expression.generic;

import expression.exceptions.CalculationException;

public class FloatExp implements Operations<Float> {
    @Override
    public Float add(Float a, Float b) {
        return a + b;
    }

    @Override
    public Float divide(Float a, Float b) {
        return a / b;
    }

    @Override
    public Float multiply(Float a, Float b) {
        return a * b;
    }

    @Override
    public Float subtract(Float a, Float b) {
        return a - b;
    }

    @Override
    public Float negate(Float a) {
        return - a;
    }

    @Override
    public Float constant(int a) {
        return (float) a;
    }

    @Override
    public Float clear(Float a, Float b) {
        throw new CalculationException("Float isn't support clear");
    }

    @Override
    public Float count(Float a) {
        throw new CalculationException("Float isn't support count");
    }

    @Override
    public Float set(Float a, Float b) {
        throw new CalculationException("Float isn't support set");
    }
}
