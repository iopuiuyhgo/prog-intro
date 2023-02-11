package md2html;

import java.util.ArrayList;

public class Operator {
    ArrayList<Integer> lastentry = new ArrayList<>();
    ArrayList<String> operators = new ArrayList<>();
    ArrayList<String> choperators = new ArrayList<>();
    ArrayList<Integer> count = new ArrayList<>();
    ArrayList<Boolean> switcher = new ArrayList<>();

    char lastsymb;

    public Operator() {
    }

    public void add(String in, String out) {
        lastentry.add(-1);
        operators.add(in);
        choperators.add(out);
        count.add(0);
        switcher.add(false);
    }

    public void reset() {
        for (int i = 0; i < count.size(); i++) {
            count.set(i, 0);
            switcher.set(i, false);
        }
    }

    public void checking(char symb, StringBuilder end) {
        if (symb != lastsymb) {
            for (int i = 0; i < count.size(); i++) {
                if (count.get(i) == operators.get(i).length()) {
                    if (switcher.get(i)) {
                        end.replace(lastentry.get(i) - operators.get(i).length(), lastentry.get(i), "<" + choperators.get(i) + ">");
                        end.replace(end.length() - operators.get(i).length(), end.length(), "</" + choperators.get(i) + ">");
                        switcher.set(i, false);
                        count.set(i, 0);
                    } else {
                        lastentry.set(i, end.length());
                        switcher.set(i, true);
                        count.set(i, 0);
                    }
                } else {
                    count.set(i, 0);
                }

            }
        }


        for (int i = 0; i < count.size(); i++) {
            if (operators.get(i).charAt(0) == symb) {
                count.set(i, count.get(i) + 1);
            }
        }
        lastsymb = symb;
    }


}
