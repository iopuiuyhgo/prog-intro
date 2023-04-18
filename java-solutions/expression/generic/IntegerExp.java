package expression.generic;

import com.sun.jdi.connect.Connector;
import expression.exceptions.OverflowException;

public class IntegerExp implements Operations <Integer>{

    @Override
    public Integer add(Integer a, Integer b) {
        if (a > 0 && b > 0) {
            if (Integer.MAX_VALUE - a < b) {
                throw new OverflowException("Overflow up: " + a + " + " + b + " > 2147483647");
            }
        } else if (a < 0 && b < 0) {
            if (Integer.MIN_VALUE - a > b) {
                throw new OverflowException("Overflow down: " + a + " + " + b + " < -2147483648");
            }
        }
        return a + b;
    }

    @Override
    public Integer divide(Integer a, Integer b) {
        if (a == -2147483648 && b == -1) {
            throw new OverflowException("Overflow up: " + a + " / " + b + " > 2147483647");
        } else if (b == 0) {
            throw new OverflowException("Division by zero: " + a + " / " + b);
        }
        return a/b;
    }

    @Override
    public Integer multiply(Integer a, Integer b) {
        if ((a == Integer.MIN_VALUE && b < 0) || (b == Integer.MIN_VALUE && a < 0)) {
            throw new OverflowException("Overflow down: " + a + " * " + b + " < -2147483648");
        }
        if (((a > 0 && b > 0) || (a < 0 && b < 0)) && (abs(a) > Integer.MAX_VALUE/abs(b))) {
            throw new OverflowException("Overflow up: " + a + " * " + b + " > 2147483647");
        } else if (((a > 0 && b < 0) || (a < 0 && b > 0)) && (min(a, b) < Integer.MIN_VALUE/max(a, b))) {
            throw new OverflowException("Overflow down: " + a + " * " + b + " < -2147483648");
        }
        return a * b;
    }

    @Override
    public Integer subtract(Integer a, Integer b) {
        if (a >= 0 && b <= 0) {
            if (a - Integer.MAX_VALUE > b) {
                throw new OverflowException("Overflow up: " + a + " - " + b + " > 2147483647");
            }
        } else if (a <= 0 && b >= 0) {
            if (Integer.MIN_VALUE + b > a) {
                throw new OverflowException("Overflow down: " + a + " - " + b + " < -2147483648");
            }
        }
        return a - b;
    }
    public Integer clear(Integer a, Integer b) {
        return a & (~(1<< b)) | (0 << b);
    }
    public Integer set(Integer a, Integer b) {
        return a & (~(1<< b)) | (1 << b);
    }

    @Override
    public Integer negate(Integer a) {
        if (a == -2147483648) {
            throw new OverflowException("Overflow up: -(-2147483648) > 2147483647");
        }
        return -a;
    }
    public Integer count(Integer a) {
        int end = 0;
        while (a != 0) {
            end += a & 1;
            a = a >>> 1;
        }
        return end;
    }

    @Override
    public Integer constant(int a) {
        return a;
    }

    private int abs(int c) {
        return Math.abs(c);
    }
    private int max(int c1, int c2) {
        return Math.max(c1, c2);
    }
    private int min(int c1, int c2) {
        return Math.min(c1, c2);
    }
}
