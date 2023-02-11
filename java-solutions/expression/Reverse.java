package expression;

public class Reverse implements ExprInt{
        private final ExprInt exp;
        public Reverse(ExprInt exp) {
            this.exp = exp;
        }
        public String toString() {
            return "reverse(" + exp.toString() +")";
        }
        public String toMiniString() {
            if (exp instanceof Const || exp instanceof Variable || exp instanceof Negate || exp instanceof Reverse) {
                return "reverse " + exp.toMiniString();
            } else {
                return "reverse(" + exp.toMiniString() + ")";
            }
        }
        @Override
        public String[] getWpts() {
            return new String[] {"", ""};
        }

        @Override
        public int evaluate(int x) {
            return reverse(exp.evaluate(x));
        }
        //    method which reverse the expression 12345-> 54321
        private int reverse(int x) {
            int rev = 0;
            while (x != 0) {
                rev = rev * 10 + x % 10;
                x = x / 10;
            }
            return rev;
        }

        @Override
        public int evaluate(int x, int y, int z) {
            return reverse(exp.evaluate(x, y, z));
        }
}
