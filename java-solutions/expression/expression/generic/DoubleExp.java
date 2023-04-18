package expression.generic;

import expression.exceptions.CalculationException;

public class DoubleExp implements Operations<Double> {
    @Override
    public Double add(Double a, Double b) {
        return a + b;
    }

    @Override
    public Double divide(Double a, Double b) {
        return a / b;
    }

    @Override
    public Double multiply(Double a, Double b) {
        return a * b;
    }

    @Override
    public Double subtract(Double a, Double b) {
        return a - b;
    }

    @Override
    public Double negate(Double a) {
        return - a;
    }

    @Override
    public Double constant(int a) {
        return (double) a;
    }

    @Override
    public Double clear(Double a, Double b) {
        throw new CalculationException("Double isn't support clear");
    }

    @Override
    public Double count(Double a) {
        throw new CalculationException("Double isn't support count");
    }

    @Override
    public Double set(Double a, Double b) {
        throw new CalculationException("Double isn't support set");
    }
}
