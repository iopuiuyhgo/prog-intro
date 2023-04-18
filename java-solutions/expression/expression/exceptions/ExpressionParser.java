package expression.exceptions;

import expression.*;
import expression.generic.Operations;

import java.util.ArrayDeque;
import java.util.HashMap;

public class ExpressionParser<T> implements TripleParser<T> {
    private final ArrayDeque<TripleExpression<T>> tokens= new ArrayDeque<>();
    private final ArrayDeque<String> operands = new ArrayDeque<>();
    private HashMap<Integer, Integer> brackets = new HashMap<>();
    private final String[] operations = new String[] {"+", "-", "*", "/", "set", "clear"};
    private final int[] operationPrice = new int[] {2, 2, 1, 1, 3, 3};
    private final int maxPrice = 3;
    private String expression;
    private int pos;
    private int len;
    private Operations<T> oper;

    private TripleExpression<T> parse(String expression, int position, int length, HashMap<Integer, Integer> brackets, Operations<T> oper) {
        tokens.clear();
        operands.clear();
        this.expression = expression;
        this.len = length;
        this.pos = position;
        this.brackets = brackets;
        this.oper = oper;
        tokens.add(getToken());
        if (pos == len) {
            return tokens.removeFirst();
        }
        while (pos < len) {
            operands.add(getOperand());
            tokens.add(getToken());
        }
        for (int i = 1; i <= maxPrice; i++) {
            int len = operands.size();
            for (int j = 0; j < len; j++) {
                if (operationPrice[findElement(operations, operands.getFirst())] == i) {
                    tokens.addFirst(doOperation(operands.removeFirst(), tokens.removeFirst(), tokens.removeFirst()));
                } else {
                    operands.addLast(operands.removeFirst());
                    tokens.addLast(tokens.removeFirst());
                }
            }
            if (operands.size() > 0) {
                tokens.addLast(tokens.removeFirst());
            }
        }
        return tokens.removeFirst();

    }
    private int findElement(String[] array, String element) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(element)) {
                return i;
            }
        }
        return -1;
    }
    @Override
    public TripleExpression<T> parse(String expression, Operations<T> oper) {
//        System.out.println(expression);
        this.pos = 0;
        this.len = expression.length();
        this.oper = oper;
        findCloseBracket(expression);
        return parse(expression, 0, expression.length(), brackets, oper);
    }
    private void findCloseBracket(String expression) {
        brackets.clear();
        int countBracket  = 1;
        while (pos < len && countBracket > 0) {
            if (expression.charAt(pos) == '(') {
                countBracket++;
                brackets.put(-countBracket, pos);
            } else if (expression.charAt(pos) == ')') {
                brackets.put(brackets.get(-countBracket), pos);
                brackets.remove(-countBracket);
                countBracket--;
            }
            pos++;
        }
        if (countBracket > 1) {
            throw new StructureException("in '" + expression + "' No closing parenthesis ");
        }
        if (countBracket < 1) {
            throw new StructureException("in '" + expression + "' No opening parenthesis");
        }
    }
    private int getNum() {
        int num = 0;
        boolean isNegate = false;
        if (expression.charAt(pos) == '-') {
            isNegate = true;
            pos++;
        }
        while (pos < len && Character.isDigit(expression.charAt(pos))) {
            if (num == 214748364) {
                if ((expression.charAt(pos) - '0') == 8) {
                    pos++;
                    if ((pos == len || !Character.isDigit(expression.charAt(pos))) && isNegate) {
                        return -2147483648;
                    } else {
                        throw new OverflowException("in '" + expression + "' entered too large number");
                    }
                }
                if ((expression.charAt(pos) - '0') == 9) {
                    throw new OverflowException("in '" + expression + "' entered too large number");
                }
            }
            if (num > 214748364) {
                throw new OverflowException("in '" + expression + "' entered too large number");
            }
            num = num * 10 + (expression.charAt(pos) - '0');
            pos++;
        }
        if (isNegate) {
            return -num;
        }
        return num;
    }
    private String getOperand() {
        String operand = "";
        skipWhiteSpaces();
        while (pos < len && expression.charAt(pos)!='x' && expression.charAt(pos)!='y' && expression.charAt(pos)!='z' && !Character.isDigit(expression.charAt(pos))
                && expression.charAt(pos) != '(' && expression.charAt(pos) != ')'
                && !Character.isWhitespace(expression.charAt(pos))){
            operand += expression.charAt(pos);
            pos++;
            if (pos == len) {
                throw new StructureException("in '" + new StringBuilder(expression).replace(pos, pos, "~~~> <~~~") + "' missed argument in " + (pos+1) + " position");
            }
            if (operand.equals("set") || operand.equals("clear")) {
                if (!Character.isWhitespace(expression.charAt(pos)) && expression.charAt(pos) != '-' && expression.charAt(pos) != '(') {
                    throw new StructureException("in '" + new StringBuilder(expression).replace(pos, pos, "~~~><~~~") + "' missed whitespace symbol");
                }
            }
            if ((operand.equals("s") || operand.equals("c")) && !Character.isWhitespace(expression.charAt(pos-2)) && expression.charAt(pos-2) != ')') {
                throw new StructureException("in '" + new StringBuilder(expression).replace(pos-1, pos-1, "~~~><~~~") + "' missed whitespace symbol");
            }

            if (findElement(operations, operand) != -1) {
                skipWhiteSpaces();
                return operand;
            } else {
                if (!operand.equals(new StringBuilder("set").replace((operand.length()%4), 4, "").toString()) &&
                        !operand.equals(new StringBuilder("clear").replace(operand.length(), 6, "").toString())) {
//                    System.out.println(3-(operand.length()%4));
//                    System.out.println(operand);
//                    System.out.println(new StringBuilder("set").replace((operand.length()%4), 3, ""));
//                    System.out.println(operand.equals(new StringBuilder("set").replace((operand.length()%4), 3, "")));
                    throw new StructureException("in '" + new StringBuilder(expression).replace(pos - 1, pos, "~~~> " + operand + " <~~~") + "' invalid operand: '" + operand + "' in " + (pos + 1) + " position");
                }
            }
        }
        skipWhiteSpaces();
        if (findElement(operations, operand) != -1) {
            return operand;
        } else {
            throw new StructureException("in '" + new StringBuilder(expression).replace(pos, pos, "~~~> <~~~") + "' missed operand: in " + (pos+1) + " position");
        }

    }
    private ExprInt<T> doOperation(String operation, TripleExpression<T> firstToken, TripleExpression<T> secondToken) {
        ExprInt<T> token1 = (ExprInt<T>) firstToken;
        ExprInt<T> token2 = (ExprInt<T>) secondToken;
        return switch (operation) {
            case "+" -> new Add<T>(oper, token1, token2);
            case "-" -> new Subtract<T>(oper, token1, token2);
            case "*" -> new Multiply<T>(oper, token1, token2);
            case "/" -> new Divide<T>(oper, token1, token2);
            case "set" -> new Set<T>(oper, token1, token2);
            case "clear" -> new Clear<T>(oper, token1, token2);
            default -> throw new IllegalArgumentException("Wrong operation: " + "'" + operation + "'");
        };
    }
    private TripleExpression<T> getToken() {
        if (pos == len) {
            throw new StructureException("in '" + new StringBuilder(expression).replace(pos, pos, "~~~> <~~~") + "' missed argument in " + (pos+1) + " position");
        }
        TripleExpression<T> token = null;
        skipWhiteSpaces();
        if (expression.charAt(pos) == '-') {
            if (pos == len-1) {
                throw new StructureException("in '" + new StringBuilder(expression).replace(pos, pos+1, "~~~> " + expression.charAt(pos) + " <~~~") + "' invalid argument: " + "'" + expression.charAt(pos) + "' in " + (pos+1) + " position");
            }
            if (!Character.isDigit(expression.charAt(pos+1))) {
                pos++;
                return new Negate<T>(oper, (ExprInt<T>) getToken());
            }
        } else if (expression.charAt(pos) == 'c'){
            skipWhiteSpaces();
            if (len - pos < 6) {
                throw new StructureException("in '" + new StringBuilder(expression).replace(pos+5, pos+5, "~~~> <~~~") + "' missed argument in " + (pos+5) + " position");
            } else if (new StringBuilder(expression).replace(0, pos, "").replace(5, Integer.MAX_VALUE, "").toString().equals("count")) {
                if (!Character.isWhitespace(expression.charAt(pos+5)) && expression.charAt(pos+5) != '-' && expression.charAt(pos+5) != '(') {
                    throw new StructureException("in '" + new StringBuilder(expression).replace(pos+5, pos+5, "~~~><~~~") + "' missed whitespace symbol in " + (pos+5) + " position");
                }
                pos+=5;
                skipWhiteSpaces();
                if (len == pos) {
                    throw new StructureException("in '" + new StringBuilder(expression).replace(pos, pos, "~~~> <~~~") + "' missed argument in " + (pos+1) + " position");
                }
                return new Count<T>(oper, (ExprInt) getToken());
            } else {
                throw new StructureException("in '" + new StringBuilder(expression).replace(pos, pos+1, "~~~> " + expression.charAt(pos) + " <~~~") + "' invalid argument: " + "'" + expression.charAt(pos) + "' in " + (pos+1) + " position");
            }

        }
        skipWhiteSpaces();
        if (expression.charAt(pos) == 'x' || expression.charAt(pos) == 'y' || expression.charAt(pos) == 'z') {
            token =  new Variable<T>(Character.toString(expression.charAt(pos)));
            pos++;
        } else if (Character.isDigit(expression.charAt(pos)) || expression.charAt(pos) == '-') {
            token = new Const<T>(oper.constant(getNum()));
        } else if (expression.charAt(pos) == '(') {
            token = new ExpressionParser<T>().parse(expression, pos + 1, brackets.get(pos), brackets, oper);
            pos = brackets.get(pos) + 1;
        }
        if (token == null) {
            throw new StructureException("in '" + new StringBuilder(expression).replace(pos, pos+1, "~~~> " + expression.charAt(pos) + " <~~~") + "' invalid argument: " + "'" + expression.charAt(pos) + "' in " + (pos+1) + " position");
        }
        skipWhiteSpaces();
        return token;
    }
    private void skipWhiteSpaces() {
        while (pos < len && Character.isWhitespace(expression.charAt(pos))) {
            pos++;
        }
    }
}