import java.io.*;
import java.util.*;

public class WordStatWordsSuffix {
    public static void main(String[] args){
        int rd = -1;
        int[] countWords = new int[1];
        StringBuilder word = new StringBuilder("");
        String[] words = new String[1];
        int point = 0;
        int size = 1;

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(new File(args[0])), "UTF-8"));
            while (true) {
                rd = in.read();
                if ((Character.isLetter(rd)) || (Character.getType(rd) == 20) || ((char) rd == '\'')) {
                    word.append((char) rd);
                } else if (word.toString() != "") {

                    if (size == point) {
                        size *= 2;
                        words = Arrays.copyOf(words, size);
                        countWords = Arrays.copyOf(countWords, size);
                    }
                    if (word.length()>3) {
                        word = new StringBuilder(word.substring(word.length() - 3, word.length()));
                    }
                    for (int i = 0; i < point + 1; i++) {
                        if (i == point) {
                            words[point] = word.toString().toLowerCase();
                            countWords[i]++;
                            point++;
                            break;
                        } else if (words[i].equals(word.toString().toLowerCase())) {
                            countWords[i]++;
                            break;
                        }
                    }

                    word = new StringBuilder();
                }
                if (rd == -1) {
                    break;
                }
            }
            in.close();

            words = Arrays.copyOf(words, point);
            countWords = Arrays.copyOf(countWords, point);

            String tmpw = "";
            int tmpc = 0;
            int max = 0;
            for (int i = 0; i < point; i++) {
                max = 0;
                for (int i1 = 0; i1 < point - i; i1++) {
                    if (words[max].compareTo(words[i1]) < 0) {
                        max = i1;
                    }
                }
                tmpw = words[point - i - 1];
                tmpc = countWords[point - i - 1];
                words[point - i - 1] = words[max];
                countWords[point - i - 1] = countWords[max];
                words[max] = tmpw;
                countWords[max] = tmpc;
            }
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(args[1])), "UTF-8"));
            for (int i = 0; i < point; i++) {
                if (words[i].length() > 0) {
                    out.write(words[i] + " " + countWords[i] + "\n");
                }
            }
            out.close();

        } catch (FileNotFoundException e) {
            System.out.println("err");
        } catch(IOException e){
            System.err.println("err1");
        }

                /*
            String tmp = "";
            int tmpnum = 0;
            int num = words.length - 1;
            for (int i = 0; i < words.length; i++) {
                for (int i1 = 0; i1 < num; i1++) {
                    if (words[num - i1] != null) {
                        if (words[num - i1].compareTo(words[num - i1 - 1]) < 0) {
                            for (int i2 = 0; i2 < words.length; i2++) {
                                System.err.println(words[i2] + " " + countWords[i2]);
                            }
                            System.err.println();

                            tmp = words[num - i1];
                            words[num - i1] = words[num - i1 - 1];
                            words[num - i1 - 1] = tmp;

                            tmpnum = countWords[num - i];
                            countWords[num - i1] = countWords[num - i1 - 1];
                            countWords[num - i1 - 1] = tmpnum;
                        }
                    }
                }
                num-=1;
            }

 */
        /*
        for (int i = 0; i<point;i++) {
            System.out.println(words[i] + " " + countWords[i]);
        }*/

/*
        StringBuilder word = new StringBuilder();
        String[] words = new String[1];
        int[] count = new int[1];
        int point = 0;
        int size = 1;
        String inp = "sassus";
        Scanner in = new Scanner(inp);
        while (in.hasNextLine()) {
            String line = in.nextLine();
            word = new StringBuilder();
            for (int i = 0; i < line.length(); i++) {
                if ((Character.isLetter(line.charAt(i))) || (line.charAt(i) == '\'') || (line.charAt(i) == '-')) {
                    word.append(line.charAt(i));
                } else {
                    if (word.toString() != "") {
                        for (int i1 = 0; i1 < words.length; i1++) {
                            if (words[i1] == word.toString()) {
                                count[i1] += 1;
                                break;
                            }
                            if (i1 == words.length-1) {
                                if (size < i1 + 2) {
                                    size *= 2;
                                    words = Arrays.copyOf(words, size);
                                    count = Arrays.copyOf(count, size);
                                }
                                words[i1 + 1] = word.toString();
                                count[i1 + 1] = 1;
                            }
                        }
                    }
                }
            }
        }

         */
    }
}