//package expression.parser;
//
//import expression.*;
//import java.util.ArrayDeque;
//import java.util.HashMap;
//
//public class ExpressionParser implements TripleParser {
//    private final ArrayDeque<TripleExpression> tokens= new ArrayDeque<>();
//    private final ArrayDeque<String> operands = new ArrayDeque<>();
//    private HashMap<Integer, Integer> brackets = new HashMap<>();
//    private final String[] operations = new String[] {"+", "-", "*", "/", "set", "clear"};
//    private final int[] operationPrice = new int[] {2, 2, 1, 1, 3, 3};
//    private final int maxPrice = 3;
//    private String expression;
//    private int pos;
//    private int len;
//
//    public TripleExpression parse(String expression, int position, int length, HashMap<Integer, Integer> brackets) {
//        this.expression = expression;
//        this.len = length;
//        this.pos = position;
//        this.brackets = brackets;
//        tokens.add(getToken());
//        if (pos == len) {
//            return tokens.removeFirst();
//        }
//        while (pos < len) {
//            operands.add(getOperand());
//            tokens.add(getToken());
//        }
//        for (int i = 1; i <= maxPrice; i++) {
//            int len = operands.size();
//            for (int j = 0; j < len; j++) {
//                if (operationPrice[findElement(operations, operands.getFirst())] == i) {
//                    tokens.addFirst(doOperation(operands.removeFirst(), tokens.removeFirst(), tokens.removeFirst()));
//                } else {
//                    operands.addLast(operands.removeFirst());
//                    tokens.addLast(tokens.removeFirst());
//                }
//            }
//            if (operands.size() > 0) {
//                tokens.addLast(tokens.removeFirst());
//            }
//        }
//        return tokens.removeFirst();
//
//    }
//    private int findElement(String[] array, String element) {
//        for (int i = 0; i < array.length; i++) {
//            if (array[i].equals(element)) {
//                return i;
//            }
//        }
//        return -1;
//    }
//    @Override
//    public TripleExpression parse(String expression) {
////        System.out.println(expression);
//        this.pos = 0;
//        this.len = expression.length();
//        findCloseBracket(expression);
//        return parse(expression, 0, expression.length(), brackets);
//    }
//    private void findCloseBracket(String expression) {
//        brackets.clear();
//        int countBracket  = 1;
//        while (pos < len && countBracket > 0) {
//            if (expression.charAt(pos) == '(') {
//                countBracket++;
//                brackets.put(-countBracket, pos);
//            } else if (expression.charAt(pos) == ')') {
//                brackets.put(brackets.get(-countBracket), pos);
//                brackets.remove(-countBracket);
//                countBracket--;
//            }
//            pos++;
//        }
//    }
//    private int getNum() {
//        int num = 0;
//        boolean isNegate = false;
//        if (expression.charAt(pos) == '-') {
//            isNegate = true;
//            pos++;
//        }
//        while (pos < len && Character.isDigit(expression.charAt(pos))) {
//            num = num * 10 + (expression.charAt(pos) - '0');
//            pos++;
//        }
//        if (isNegate) {
//            return -num;
//        }
//        return num;
//    }
//    private String getOperand() {
//        String operand = "";
//        skipWhiteSpaces();
//        while (pos < len && expression.charAt(pos)!='x' && expression.charAt(pos)!='y' && expression.charAt(pos)!='z' && !Character.isDigit(expression.charAt(pos))
//                && expression.charAt(pos) != '(' && expression.charAt(pos) != ')'
//                && !Character.isWhitespace(expression.charAt(pos))){
//            operand += expression.charAt(pos);
//            pos++;
//            if (findElement(operations, operand) != -1) {
//                skipWhiteSpaces();
//                return operand;
//            }
//        }
//        skipWhiteSpaces();
//        if (findElement(operations, operand) != -1) {
//            return operand;
//        } else {
//            throw new IllegalArgumentException("Wrong operand: " + "'" + operand + "'");
//        }
//
//    }
//    private ExprInt doOperation(String operation, TripleExpression firstToken, TripleExpression secondToken) {
//        ExprInt token1 = (ExprInt) firstToken;
//        ExprInt token2 = (ExprInt) secondToken;
//        return switch (operation) {
//            case "+" -> new Add(token1, token2);
//            case "-" -> new Subtract(token1, token2);
//            case "*" -> new Multiply(token1, token2);
//            case "/" -> new Divide(token1, token2);
//            case "set" -> new Set(token1, token2);
//            case "clear" -> new Clear(token1, token2);
//            default -> throw new IllegalArgumentException("Wrong operation: " + "'" + operation + "'");
//        };
//    }
//    private TripleExpression getToken() {
//        TripleExpression token = null;
//        skipWhiteSpaces();
//        if (expression.charAt(pos) == '-' && !Character.isDigit(expression.charAt(pos+1))) {
//            pos++;
//            return new Negate((ExprInt) getToken());
//        } else if (expression.charAt(pos) == 'r' && !Character.isDigit(expression.charAt(pos+7))) {
//            pos+=7;
//            return new Reverse((ExprInt) getToken());
//        }
//        skipWhiteSpaces();
//        if (expression.charAt(pos) == 'x' || expression.charAt(pos) == 'y' || expression.charAt(pos) == 'z') {
//            token =  new Variable(Character.toString(expression.charAt(pos)));
//            pos++;
//        } else if (Character.isDigit(expression.charAt(pos)) || expression.charAt(pos) == '-') {
//            token = new Const(getNum());
//        } else if (expression.charAt(pos) == '(') {
//            token = new ExpressionParser().parse(expression, pos + 1, brackets.get(pos), brackets);
//            pos = brackets.get(pos) + 1;
//        }
//        if (token == null) {
//            throw new IllegalArgumentException("Invalid expression: " + "'" + expression.charAt(pos) + "'");
//        }
//        skipWhiteSpaces();
//        return token;
//    }
//    private void skipWhiteSpaces() {
//        while (pos < len && Character.isWhitespace(expression.charAt(pos))) {
//            pos++;
//        }
//    }
//}