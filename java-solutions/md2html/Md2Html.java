package md2html;

import java.io.*;

public class Md2Html {
    boolean IsNullOrEmpty(String str) {
        if (str == null) {
            return true;
        } else return str.length() == 0;
    }

    public static void main(String[] args) {
        try {
            Md2Html mdam = new Md2Html();

            BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(new File(args[0])), "UTF-8"));
            String inline;
            String nextline;

            BufferedReader line;
            int symb;

            int countr = 0;

            int abzfl = 0;

            boolean isnewabz = true;
            boolean isecr = false;

            StringBuilder end = new StringBuilder();

            String ls = System.lineSeparator();

            nextline = input.readLine();

            Operator m2d = new Operator();
            m2d.add("*", "em");
            m2d.add("**", "strong");
            m2d.add("_", "em");
            m2d.add("__", "strong");
            m2d.add("--", "s");
            m2d.add("`", "code");
            m2d.add("++", "u");


            while (true) {
                inline = nextline;
                if (inline == null) {
                    break;
                }
                line = new BufferedReader(new StringReader(inline));

                nextline = input.readLine();

                for (int i = 0; i < inline.length(); i++) {
                    symb = line.read();
                    if (!isecr) {
                        m2d.checking((char) symb, end);
                    }

                    if (isnewabz && symb == '#') {
                        countr += 1;
                    } else if (isnewabz && symb != ' ') {
                        isnewabz = false;
                        countr = 0;
                    } else {
                        isnewabz = false;
                    }

                    if (symb == '<') {
                        end.append("&lt;");
                    } else if (symb == '>') {
                        end.append("&gt;");
                    } else if (symb == '&') {
                        end.append("&amp;");
                    } else {
                        end.append((char) symb);
                    }

                    isecr = false;

                    if (symb == '\\') {
                        isecr = true;
                        end.setLength(end.length() - 1);
                    }
                    if (i == inline.length() - 1) {
                        m2d.checking((char) -1, end);
                        if (mdam.IsNullOrEmpty(nextline)) {
                            if (countr > 0) {
                                end.append("</h" + countr + ">");
                                end.replace(abzfl, abzfl + countr + 1, "<h" + countr + ">");
                            } else {
                                end.append("</p>");
                                end.replace(abzfl, abzfl, "<p>");
                            }
                            m2d.reset();
                            countr = 0;
                            isnewabz = true;
                        }
                        end.append(ls);

                        if (isnewabz) {
                            abzfl = end.length();
                        }

                    }
                }

            }
            input.close();

            BufferedWriter out1 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("text.txt"))));
            out1.write(end.toString());
            out1.close();
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(args[1])), "UTF-8"));
            out.write(end.toString());
            out.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
