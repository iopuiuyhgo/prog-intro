package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringReader;
import java.util.Scanner;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 * @author Konstantin Tsitsin (iopuiuyhgo@gmail.com)
 */
public class HumanPlayer implements Player {
    private final PrintStream out;
    private final Scanner in;
    private BufferedReader line;

    public HumanPlayer(final PrintStream out, final Scanner in) {
        this.out = out;
        this.in = in;
    }

    public HumanPlayer() {
        this(System.out, new Scanner(System.in));
    }

    private boolean isNum(char symb) {
        return Character.getNumericValue(symb) > -1 && Character.getNumericValue(symb) < 10;
    }

    @Override
    public Move move(final Position position, final Cell cell) {
        while (true) {
            out.println("Position");
            out.println(position);
            out.println(cell + "'s move");
            out.println("Enter row and column");
            line = new BufferedReader(new StringReader(in.nextLine()));
            int symb;
            StringBuilder row = new StringBuilder();
            StringBuilder col = new StringBuilder();
            boolean switcher = true;
            boolean isFine = false;
            try {
                symb = line.read();
                while (symb != -1) {
                    isFine = false;

                    if (!switcher && isNum((char) symb)) {
                        col.append((char) symb);
                    } else if (!switcher) {
                        break;
                    }

                    if (switcher && isNum((char) symb)) {
                        row.append((char) symb);
                    } else if (switcher && (row.length() == 0 || symb != ' ')) {
                        break;
                    } else if (switcher && row.length() != 0 && symb == ' ') {
                        switcher = false;
                    }
                    symb = line.read();

                    isFine = true;

                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            if (row.toString().length() == 0 || col.toString().length() == 0) {
                isFine = false;
            }
            if (isFine) {
                final Move move = new Move(Integer.parseInt(row.toString()), Integer.parseInt(col.toString()), cell);
                if (position.isValid(move)) {
                    return move;
                }
            }
            System.out.println("incorrect input!");
        }
    }
}
