package expression.generic;

public interface Operations<T> {
    T add(T a, T b);
    T divide(T a, T b);
    T multiply(T a, T b);
    T subtract(T a, T b);
    T negate(T a);
    T constant(int a);
    T clear(T a, T b);
    T count(T a);
    T set(T a, T b);
}
