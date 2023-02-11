package game;

import java.util.Map;

public class isValidMove implements Position {
    private static final Map<Cell, Character> SYMBOLS = Map.of(
            Cell.X, 'X',
            Cell.O, 'O',
            Cell.E, '.',
            Cell.S, '#'
    );
    private int m, n, k;
    private Cell[][] cells;
    private Cell turn;

    isValidMove(int m, int n, int k, Cell[][] cells, Cell turn) {
        this.m = m;
        this.n = n;
        this.k = k;
        this.cells = cells;
        this.turn = turn;
    }

    @Override
    public boolean isValid(final Move move) {
        return 0 <= move.getRow() && move.getRow() < m
                && 0 <= move.getColumn() && move.getColumn() < n
                && cells[move.getRow()][move.getColumn()] == Cell.E
                && turn == move.getValue();
    }

    @Override
    public Cell getCell(final int r, final int c) {
        return cells[r][c];
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(" ");
        for (int r = 0; r < n; r++) {
//            sb.append(r);
        }
        for (int r = 0; r < m; r++) {
            sb.append("\n");
//            sb.append(r);
            for (int c = 0; c < n; c++) {
                sb.append(SYMBOLS.get(cells[r][c]));
            }
        }
        return sb.toString();
    }
}
