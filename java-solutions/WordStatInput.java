import java.io.*;
import java.util.*;

public class WordStatInput {
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
                if (rd == -1) {
                    break;
                }
                if ((Character.isLetter(rd)) || (Character.getType(rd) == 20) || ((char) rd == '\'')) {
                    word.append((char) rd);
                } else if (word.toString() != "") {

                    if (size == point) {
                        size *= 2;
                        words = Arrays.copyOf(words, size);
                        countWords = Arrays.copyOf(countWords, size);
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
            }
            in.close();

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
    }
}