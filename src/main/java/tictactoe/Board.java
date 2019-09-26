package tictactoe;

import java.util.*;

public class Board {
    public static final int ROW_MIN = 1;
    public static final int ROW_MAX = 3;
    public static final int COL_MIN = 1;
    public static final int COL_MAX = 3;

    private static final int[][][] winPatterns = {
        // across
        {{1, 1}, {1, 2}, {1, 3}},
        {{2, 1}, {2, 2}, {2, 3}},
        {{3, 1}, {3, 2}, {3, 3}},

        // down
        {{1, 1}, {2, 1}, {3, 1}},
        {{1, 2}, {2, 2}, {3, 2}},
        {{1, 3}, {2, 3}, {3, 3}},

        // diagonal
        {{1, 1}, {2, 2}, {3, 3}},
        {{1, 3}, {2, 2}, {3, 1}},
    };

    private final Mark[][] board = new Mark[][]{
        {Mark.EMPTY, Mark.EMPTY, Mark.EMPTY},
        {Mark.EMPTY, Mark.EMPTY, Mark.EMPTY},
        {Mark.EMPTY, Mark.EMPTY, Mark.EMPTY},
    };

    private final List<Square> _history = new ArrayList<>();

    private Mark getAt(int row, int column) {
        return this.board[row - 1][column - 1];
    }

    private void setAt(int row, int column, Mark mark) {
        this.board[row - 1][column - 1] = mark;
    }

    public List<Square> history() {
        return Collections.unmodifiableList(this._history);
    }

    public static int ensureValidRow(int row) {
        if (row < ROW_MIN || row > ROW_MAX) {
            throw new IllegalArgumentException("row coordinate must be 1, 2, or 3");
        }
        return row;
    }

    public static int ensureValidColumn(int column) {
        if (column < COL_MIN || column > COL_MAX) {
            throw new IllegalArgumentException("column coordinate must be 1, 2, or 3");
        }
        return column;
    }

    public boolean hasWin(Mark mark) {
        patterns:
        for (int[][] winSquares : winPatterns) {
            for (int[] squareCoords : winSquares) {
                if (this.getAt(squareCoords[0], squareCoords[1]) != mark) {
                    continue patterns;
                }
            }
            return true;
        }
        return false;
    }

    public Set<Square> availableSquares() {
        Set<Square> result = new HashSet<>();
        for (int row = Board.ROW_MIN; row <= Board.ROW_MAX; ++row) {
            for (int column = Board.COL_MIN; column <= Board.COL_MAX; ++column) {
                if (!this.hasAvailable(row, column)) {
                    continue;
                }
                result.add(new Square(row, column));
            }
        }
        return result;
    }

    public boolean hasAvailable(int row, int column) {
        return this.getAt(row, column) == Mark.EMPTY;
    }

    public boolean hasAvailable(Square square) {
        Objects.requireNonNull(square, "square cannot be null");
        return this.hasAvailable(square.row(), square.column());
    }

    public boolean isFull() {
        return this.availableSquares().isEmpty();
    }

    public void mark(Square square, Mark mark) {
        Objects.requireNonNull(square, "square cannot be null");
        Objects.requireNonNull(mark, "mark cannot be null");
        Mark existingMark = this.getAt(square.row(), square.column());
        if (existingMark != Mark.EMPTY) {
            throw new IllegalArgumentException(String.format("Square %s is already marked %s", square, mark));
        }
        this.setAt(square.row(), square.column(), mark);
        this._history.add(square);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Mark[] row : board) {
            for (Mark mark : row) {
                result.append(mark.display()).append(' ');
            }
            result.append(System.lineSeparator());
        }
        return result.toString();
    }
}
