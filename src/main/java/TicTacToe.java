import java.io.IOException;
import java.util.Objects;

public class TicTacToe {
    private final Board _board;
    private final LossHistory _lossHistory;
    private final Player[] _players;

    public TicTacToe(Board board, LossHistory lossHistory, Player[] players) {
        this._board = Objects.requireNonNull(board);
        this._lossHistory = Objects.requireNonNull(lossHistory);
        this._players = Objects.requireNonNull(players);
    }

    public static TicTacToe create() throws IOException {
        Board board = new Board();
        LossHistory lossHistory = IOHelper.loadLossHistory(IOHelper.lossHistoryFilename());
        Player[] players = {
            new HumanPlayer(Mark.X),
            new ComputerPlayer(Mark.O, lossHistory),
        };
        return new TicTacToe(board, lossHistory, players);
    }

    private void play() throws IOException {
        while (true) {
            for (Player player : this._players) {
                IOHelper.showBoard(this._board);
                if (this._board.isFull()) {
                    IOHelper.gameOver(this._board);
                    return;
                }
                Square choice = player.chooseSquare(this._board);
                this._board.mark(choice, player.mark());
                boolean hasWin = this._board.hasWin(player.mark());
                if (hasWin) {
                    this._lossHistory.addLoss(this._board.history());
                    IOHelper.persistLossHistory(this._lossHistory, IOHelper.lossHistoryFilename());
                    IOHelper.gameOver(player, this._board);
                    return;
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        TicTacToe game = TicTacToe.create();
        game.play();
    }
}
