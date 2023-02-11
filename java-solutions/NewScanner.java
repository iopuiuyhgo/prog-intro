import java.io.*;

public class NewScanner {
    private char[] buf;
    private int size = 0;
    private final int SBUF = 1024;
    private BufferedReader in;
    private StringBuilder nextword;
    private int pointBuf = 0;
    private char ls = System.lineSeparator().charAt(0);

    public NewScanner(InputStream in) {
        this(new BufferedReader(new InputStreamReader(in)));
    }

    public NewScanner(String st) {
        this(new BufferedReader(new StringReader(st)));
    }

    public NewScanner(BufferedReader in) {
        buf = new char[SBUF];
        this.in = in;
    }


    public boolean hasNext() throws IOException {
        while (true) {
            try {
                for (int i = pointBuf; i < size; i++) {
                    if ((Character.isWhitespace(buf[pointBuf])) && (buf[pointBuf] != ls)) {
                        pointBuf += 1;
                    } else {
                        return true;
                    }
                }
                nbuf();
                if (size == -1) {
                    return false;
                }
            } catch (IOException e) {
                return false;
            }
        }
    }

    public String next() throws IOException {
        nextword = new StringBuilder();
        if (this.hasNext()) {
            if (buf[pointBuf] == ls) {
                pointBuf += 1;
                return Character.toString(ls);
            }
            while (!Character.isWhitespace(buf[pointBuf])) {
                if (buf[pointBuf] == ls) {
                    return nextword.toString();
                }
                nextword.append(buf[pointBuf]);
                if (pointBuf == size - 1) {
                    if (hasNext()) {
                        try {
                            nbuf();
                            if (!Character.isWhitespace(buf[pointBuf])) {
                                nextword.append(buf[pointBuf]);
                            } else {
                                pointBuf -= 1;
                            }
                        } catch (IOException e) {
                            System.err.println(e.getMessage());
                        }
                    } else {
                        return nextword.toString();
                    }
                }
                pointBuf += 1;
            }
        }
        return nextword.toString();
    }

    public boolean hasNextWordpp() throws IOException {
        while (true) {
            try {
                for (int i = pointBuf; i < size; i++) {
                    if (!((Character.isLetter(buf[pointBuf])) || (Character.getType(buf[pointBuf]) == 20) || ((char) buf[pointBuf] == '\'')) && (buf[pointBuf] != ls)) {
                        pointBuf += 1;
                    } else {
                        return true;
                    }
                }
                nbuf();
                if (size == -1) {
                    return false;
                }
            } catch (IOException e) {
                return false;
            }
        }
    }

    public String nextWordpp() throws IOException {
        nextword = new StringBuilder();
        if (this.hasNextWordpp()) {
            if (buf[pointBuf] == ls) {
                pointBuf += 1;
                return Character.toString(ls);
            }

            while ((Character.isLetter(buf[pointBuf])) || (Character.getType(buf[pointBuf]) == 20) || ((char) buf[pointBuf] == '\'')) {

                if (buf[pointBuf] == ls) {
                    return nextword.toString();
                }
                nextword.append(buf[pointBuf]);
                if (pointBuf == size - 1) {
                    if (hasNextWordpp()) {
                        try {
                            nbuf();
                            if ((Character.isLetter(buf[pointBuf])) || (Character.getType(buf[pointBuf]) == 20) || ((char) buf[pointBuf] == '\'')) {
                                nextword.append(buf[pointBuf]);
                            } else {
                                pointBuf -= 1;
                            }
                        } catch (IOException e) {
                            System.err.println(e.getMessage());
                        }
                    } else {
                        return nextword.toString();
                    }
                }
                pointBuf += 1;
            }
        }
        return nextword.toString();
    }

    private void nbuf() throws IOException {
        size = in.read(buf, 0, SBUF);
        pointBuf = 0;
    }

    public void close() {
        try {
            in.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

}