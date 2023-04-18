package expression.generic;

public class UIntegerExp implements Operations<Integer>{


    @Override
    public Integer add(Integer a, Integer b) {
        return a + b;
    }

    @Override
    public Integer divide(Integer a, Integer b) {
        return a / b;
    }

    @Override
    public Integer multiply(Integer a, Integer b) {
        return a * b;
    }

    @Override
    public Integer subtract(Integer a, Integer b) {
        return a - b;
    }

    @Override
    public Integer negate(Integer a) {
        return - a;
    }

    @Override
    public Integer constant(int a) {
        return a;
    }

    @Override
    public Integer clear(Integer a, Integer b) {
        return a & (~(1<< b)) | (0 << b);
    }

    @Override
    public Integer count(Integer a) {
        int end = 0;
        while (a != 0) {
            end += a & 1;
            a = a >>> 1;
        }
        return end;
    }

    @Override
    public Integer set(Integer a, Integer b) {
        return a & (~(1<< b)) | (1 << b);
    }
}
