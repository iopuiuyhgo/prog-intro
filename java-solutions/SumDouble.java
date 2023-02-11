public class SumDouble {
    public static void main(String[] args) {
        double sum = 0d;
        StringBuilder num = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            String[] count = args[i].split("");
            num = new StringBuilder("");

            for (int i1 = 0; i1 < count.length; i1++) {
                if (Character.isWhitespace(count[i1].charAt(0)) == false && i1 != count.length - 1) {
                    num.append(Character.toString(count[i1].charAt(0)));
                } else {
                    if (Character.isWhitespace(count[i1].charAt(0)) == false) {
                        num.append(Character.toString(count[i1].charAt(0)));
                    }
                    if (num.length() > 0) {
                        sum += Double.parseDouble(num.toString());
                    }
                    num = new StringBuilder("");
                }
            }
        }
        System.out.println(sum);

                /*
                try {
                    if (num.length() == 0 && count[i1].charAt(0) == '-') {
                        num="-";
                    }
                    if (count[i1].charAt(0) == '.') {
                        num+=".";
                    }
                    if (count[i1].charAt(0) == 'e') {
                        num+="e";
                    }
                    if (i1 == count.length - 1) {
                        try {
                            if (count[i1].charAt(0) != '.' && count[i1].charAt(0) != 'e') {
                                Double test1 = Double.parseDouble(count[i1]);
                                num+=count[i1];
                            }
                        } catch (NumberFormatException e) {
                        }
                        if (num != "" && num != "-") {
                            sum+=Double.parseDouble(num);
                        }
                        num = "";
                    }
                    if (count[i1].charAt(0) != '.' && count[i1].charAt(0) != 'e') {

                        Double test = Double.parseDouble(count[i1]);
                        num = num + count[i1];
                    }

                } catch (NumberFormatException e) {
                    if (num != "" && num != "-") {
                        sum+=Double.parseDouble(num);
                        num="";
                    }
                }

            }
        }
        */
    }
}