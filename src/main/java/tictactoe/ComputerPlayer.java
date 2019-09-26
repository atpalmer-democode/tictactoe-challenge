package tictactoe;

import java.util.*;

public class ComputerPlayer implements Player {
    private final Mark _mark;
    private final LossHistory _lossHistory;
    private final Random _random;

    public ComputerPlayer(Mark mark, LossHistory lossHistory, Random random) {
        this._mark = mark;
        this._lossHistory = lossHistory;
        this._random = random;
    }

    public ComputerPlayer(Mark mark, LossHistory lossHistory) {
        this(mark, lossHistory, new Random());
    }

    @Override
    public Mark mark() {
        return this._mark;
    }

    @Override
    public Square chooseSquare(Board board) {
        Set<Square> squares = board.availableSquares();

        while (true) {
            Square choice = this.randomSquare(squares);
            List<Square> hypotheticalLine = hypotheticalLine(board.history(), choice);
            if (!this._lossHistory.hasSequence(hypotheticalLine)) {
                return choice;
            }
            squares.remove(choice);
        }
    }

    private Square randomSquare(Set<Square> squares) {
        Object[] squaresArray = squares.toArray();
        int index = this._random.nextInt(squaresArray.length);
        return (Square) squaresArray[index];
    }

    private static List<Square> hypotheticalLine(List<Square> gameHistory, Square square) {
        List<Square> result = new ArrayList<>(gameHistory);
        result.add(square);
        return result;
    }
}
