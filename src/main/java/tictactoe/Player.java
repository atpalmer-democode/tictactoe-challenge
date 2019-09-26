package tictactoe;

public interface Player {
    Mark mark();

    Square chooseSquare(Board board);
}
