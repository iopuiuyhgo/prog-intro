package game;

import java.util.Arrays;

/**
 * @author Konstantin Tsitsin (iopuiuyhgo@gmail.com)
 */
public class MnkBoard implements Board {
    private final Cell[][] cells;
    private Cell turn;

    private final int m, n, k;
    private int ctempty;

    public void doL(int x, int y) {
        cells[x][y] = Cell.S;
    }

    public MnkBoard(int m, int n, int k) {
        this.cells = new Cell[m][n];  //
        this.m = m;  //
        this.n = n;  //
        this.k = k;  //
        ctempty = m * n;

        for (Cell[] row : cells) {
            Arrays.fill(row, Cell.E);
        }
        turn = Cell.X;
    }

    @Override
    public Position getPosition() {
        return new isValidMove(m, n, k, cells, turn);
    }

//    @Override
//    public Position getPosition() {
//        return this;
//    }

    @Override
    public Cell getCell() {
        return turn;
    }


    private boolean[] isF(int gr, int gc, int i) {
        boolean[] end = new boolean[4];
        for (int j = 0; j < 4; j++) {
            double isMN = (-1 + Math.pow(-1, j)) / 2;
            if (j < 2) {
                end[j] = (Math.pow(-1.0, j) * gr) - i >= isMN * (m - 1);
            } else {
                end[j] = (Math.pow(-1.0, j) * gc) - i >= isMN * (n - 1);
            }
        }

        return end;
    }

    private int function1(int j) {
// 0->-1, 1->1, 2->0, 3->0, 4->-1, 5->1, 6->-1, 7->1
        return (int) ((72 * Math.pow(j, 7) - 35 * 49 * Math.pow(j, 6) + 21 * 769 * Math.pow(j, 5) - 35 * 2185 * Math.pow(j, 4) + 21 * 9103 * Math.pow(j, 3) - 70 * 3419 * Math.pow(j, 2) + 24 * 5009 * j - 5040) / 5040);
    }

    private int function2(int j) {
// 0->0, 1->0, 2->-1, 3->1, 4->-1, 5->1, 6->1, 7->-1
        return (int) ((8 * 13 * Math.pow(j, 7) - 7 * 367 * Math.pow(j, 6) + 7 * 3581 * Math.pow(j, 5) - 35 * 3497 * Math.pow(j, 4) + 7 * 44423 * Math.pow(j, 3) - 28 * 13627 * Math.pow(j, 2) + 36 * 4733 * j) / 5040);
    }

    private boolean intToBoolean(int i) {
        return i == 0 ? false : true;
    }

    private int booleanToInt(boolean b) {
        return b ? 1 : 0;
    }

    private int[] isVGD(boolean[] isctm, int[] ctVGD, int gr, int gc, int i) {
        int k = 0;
        int[] end = new int[12];
        for (boolean j : isctm) {
            if (j && cells[gr + function1(k) * i][gc + function2(k) * i] == turn) {
                ctVGD[k / 2]++;
            } else if (j) {
                isctm[k] = false;

            }
            end[k] = booleanToInt(isctm[k]);
            k += 1;
        }
        for (int j = 8; j < 12; j++) {
            end[j] = ctVGD[j - 8];
        }
        return end;
    }

    @Override
    public Result makeMove(final Move move) {
        if (!getPosition().isValid(move)) {
            return Result.LOSE;
        }

        cells[move.getRow()][move.getColumn()] = move.getValue();
        ctempty -= 1;

        boolean[] ctry = new boolean[8];
        for (int i = 0; i < 8; i++) {
            ctry[i] = true;
        }
        int[] ctctry = new int[4];
        for (int i = 0; i < 4; i++) {
            ctctry[i] = 1;
        }
        int[] vgd;
        boolean[] Fs;
        for (int i = 1; i < k; i++) {
            Fs = isF(move.getRow(), move.getColumn(), i);//n s w e
            for (int i1 = 0; i1 < 4; i1++) {
                ctry[i1] &= Fs[i1];
                ctry[4 + i1] &= Fs[i1 % 2] && Fs[(-i1 * i1 + 3 * i1 + 4) / 2];
            }
            vgd = isVGD(ctry, ctctry, move.getRow(), move.getColumn(), i);
            for (int i1 = 0; i1 < 8; i1++) { //n s w e nw se ne sw
                ctry[i1] = intToBoolean(vgd[i1]);
            }
            for (int i1 = 0; i1 < 4; i1++) { //v h d1 d2
                ctctry[i1] = vgd[i1 + 8];
            }
        }
        if (ctctry[0] >= k || ctctry[1] >= k || ctctry[2] >= k || ctctry[3] >= k) {
            return Result.WIN;
        }

        if (ctempty == 0) {
            return Result.DRAW;
        }

        // дополнительные ходы (34-35)
        if (ctctry[0] >= 4 || ctctry[1] >= 4 || ctctry[2] >= 4 || ctctry[3] >= 4) {
            return Result.NEWMOVE;
        }

        if (turn == Cell.X) {
            turn = Cell.O;
        } else if (turn == Cell.O) {
            turn = Cell.X;
        }
        return Result.UNKNOWN;
    }

    public void cleanBoard() {

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                cells[i][j] = Cell.E;
//                System.out.println(i + " " + j);
            }
        }
        turn = Cell.X;
        ctempty = m * n;
    }
}

