package expression;

public interface Expression<T> {

    T evaluate(T x);
}
