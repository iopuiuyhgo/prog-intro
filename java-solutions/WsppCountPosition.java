import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class WsppCountPosition {
    public static void main(String[] args) {
        String ls = Character.toString(System.lineSeparator().charAt(0));
        String nextWord = new String();
        int wordCount = 0;
        int lineNumber = 1;
        int inLineNumber = 0;

        int maxCount = 0;

        try {
            BufferedReader file = new BufferedReader(new InputStreamReader(new FileInputStream(new File(args[0])), "UTF-8"));
            NewScanner in = new NewScanner(file);

            LinkedHashMap<String, int[]> words = new LinkedHashMap<>();
            try {
                while (in.hasNextWordpp()) {
                    wordCount += 1;
                    inLineNumber += 1;
                    nextWord = in.nextWordpp().toLowerCase();

                    if (nextWord.equals(ls)) {
                        wordCount--;
                        lineNumber += 1;
                        inLineNumber = 0;
                        //getOrDefault ??
                    } else {
                        // map.put(k map.getOrDefault(k, 0) + 1)
                        // // if (map.contains) {
                        // //     map.put(k, map.get(k) + 1)
                        // // } else {
                        // //     map.put(k, 1)
                        // // }
                        // getOrDefault
                        if (words.containsKey(nextWord)) {
                            if (words.get(nextWord).length - 3 <= words.get(nextWord)[0] * 2) {
                                words.put(nextWord, Arrays.copyOf(words.get(nextWord), words.get(nextWord).length * 2));
                            }
                            words.get(nextWord)[0] += 1;
                            words.get(nextWord)[(words.get(nextWord)[0] * 2) - 1] = lineNumber;
                            words.get(nextWord)[words.get(nextWord)[0] * 2] = inLineNumber;

                            if (words.get(nextWord)[0] > maxCount) {
                                maxCount = words.get(nextWord)[0];
                            }
                        } else {
                            words.put(nextWord, new int[3]);
                            words.get(nextWord)[0] = 1;
                            words.get(nextWord)[1] = lineNumber;
                            words.get(nextWord)[2] = inLineNumber;

                            if (words.get(nextWord)[0] > maxCount) {
                                maxCount = words.get(nextWord)[0];
                            }
                        }
                    }
                }
            } catch (IOException e) {
                System.err.println(e.getMessage());
            } finally {
                file.close();
            }

            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(args[1])), "UTF-8"));

            ArrayList[] sortWords = new ArrayList[maxCount + 1];
            for (Map.Entry<String, int[]> i : words.entrySet()) {
                if (sortWords[i.getValue()[0]] == null) {
                    sortWords[i.getValue()[0]] = new ArrayList<String>();
                }
                sortWords[i.getValue()[0]].add(i.getKey());
            }
            for (int i = 0; i < sortWords.length; i++) {
                if (sortWords[i] != null) {
                    for (int i1 = 0; i1 < sortWords[i].size(); i1++) {
                        out.write(sortWords[i].get(i1) + " ");
                        out.write(words.get(sortWords[i].get(i1))[0] + "");
                        for (int i2 = 2; i2 < words.get(sortWords[i].get(i1)).length; i2 += 2) {
                            if (words.get(sortWords[i].get(i1))[i2 - 1] == 0) {
                                break;
                            }
                            out.write(" " + words.get(sortWords[i].get(i1))[i2 - 1] + ":" + words.get(sortWords[i].get(i1))[i2]);
                        }
                        out.newLine();
                    }
                }
            }
            // finally?
            out.close();
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } finally {
            //System.err.println("aboba");
        }
    }
}
