package tictactoe;

import java.io.Serializable;

public class Square implements Serializable {
    private static final long serialVersionUID = 1L;

    private final int _row;
    private final int _column;

    public Square(int row, int column) {
        this._row = Board.ensureValidRow(row);
        this._column = Board.ensureValidColumn(column);
    }

    public int row() {
        return this._row;
    }

    public int column() {
        return this._column;
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", this._row, this._column);
    }

    @Override
    public int hashCode() {
        return (5 * this._row) + this._column;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Square)) {
            return false;
        }
        return ((Square) other)._row == this._row && ((Square) other)._column == this._column;
    }
}
