public class Sum {
    public static void main(String[] args) {
        int sum=0;
        String num="";
        for (int i = 0; i<args.length; i++) {
            String[] count=args[i].split("");
            num = "";

            for (int i1 = 0; i1<count.length; i1++) {
                try {
                    if (num.length() == 0 && count[i1].charAt(0) == '-') {
                        num="-";
                    }

                    if (i1 == count.length - 1) {
                        try {
                            int test1 = Integer.parseInt(count[i1]);
                            num+=count[i1];
                        } catch (NumberFormatException e) {
                        }
                        if (num != "" && num != "-") {
                            sum+=Integer.parseInt(num);
                        }
                        num = "";
                    }
                    if (count[i1].charAt(0) != '.') {

                        int test = Integer.parseInt(count[i1]);
                        num = num + count[i1];
                    }

                } catch (NumberFormatException e) {
                    if (num != "" && num != "-") {
                        sum+=Integer.parseInt(num);
                        num="";
                    }
                }

            }
        }
        System.out.println(sum);
    }
}