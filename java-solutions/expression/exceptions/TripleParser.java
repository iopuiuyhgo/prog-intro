package expression.exceptions;

import expression.TripleExpression;
import expression.generic.Operations;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
@FunctionalInterface
public interface TripleParser<T> {
    TripleExpression<T> parse(String expression, Operations<T> oper) throws Exception;
}
