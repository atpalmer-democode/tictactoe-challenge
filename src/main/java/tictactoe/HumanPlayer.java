package tictactoe;

import java.util.Objects;

public class HumanPlayer implements Player {
    private final Mark _mark;

    public HumanPlayer(Mark mark) {
        this._mark = mark;
    }

    @Override
    public Mark mark() {
        return this._mark;
    }

    @Override
    public Square chooseSquare(Board board) {
        Objects.requireNonNull(board);
        Square selection;
        while (true) {
            selection = IOHelper.readCoordinates();
            if (board.hasAvailable(selection)) {
                break;
            }
            IOHelper.error("That square is already marked. Try again.");
        }
        return selection;
    }
}
