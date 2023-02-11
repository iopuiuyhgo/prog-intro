import java.io.*;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class Wspp {
    public static void main(String[] args) {
        String ls = Character.toString(System.lineSeparator().charAt(0));
        String nextWord = new String();
        int wordCount = 0;
/*
        int qwerty = 0;
        String qwerty1 = "";

 */

        try {
            BufferedReader file = new BufferedReader(new InputStreamReader(new FileInputStream(new File(args[0])), "UTF-8"));
            NewScanner in = new NewScanner(file);

            LinkedHashMap<String, int[]> words = new LinkedHashMap<>();
            while (in.hasNextWordpp()) {
                wordCount += 1;
                nextWord = in.nextWordpp().toLowerCase();
                //System.err.println(nextWord);
                /*
                if (qwerty == 0) {
                    qwerty+=1;
                    qwerty1 = nextWord;
                } else if (qwerty1.equals(nextWord)) {
                    //System.err.println(wordCount);
                }
                System.err.println(wordCount);

                 */

                if (nextWord.equals(ls)) {
                    wordCount--;
                    //getOrDefault
                } else {
                    if (words.containsKey(nextWord)) {
                        if (words.get(nextWord).length - 1 == words.get(nextWord)[0]) {
                            words.put(nextWord, Arrays.copyOf(words.get(nextWord), words.get(nextWord).length * 2));
                        }
                        words.get(nextWord)[0] += 1;
                        words.get(nextWord)[words.get(nextWord)[0]] = wordCount;
                    } else {
                        words.put(nextWord, new int[2]);
                        words.get(nextWord)[0] = 1;
                        words.get(nextWord)[1] = wordCount;
                    }
                }
            }

            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(args[1])), "UTF-8"));
            int[] value;
            for (Map.Entry<String, int[]> i : words.entrySet()) {
                //System.err.print(i.getKey());
                out.write(i.getKey());
                value = i.getValue();
                for (int i1 = 0; i1 < value.length; i1++) {
                    if (value[i1] == 0) {
                        break;
                    }
                    //System.err.print(" " + value[i1]);
                    out.write(" " + value[i1]);
                }
                //System.err.println();
                out.newLine();
            }
            //System.err.println("test");
            //System.err.println(words.keySet().toArray());
            file.close();
            out.close();
        } catch (FileNotFoundException e) {
            System.err.println("error FileNotFoundException");
        } catch (IOException e) {
            System.err.println("error IOException");
        } finally {
            //System.err.println("aboba");
        }
    }
}
