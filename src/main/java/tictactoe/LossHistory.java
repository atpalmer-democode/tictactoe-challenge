package tictactoe;

import java.io.Serializable;
import java.util.*;

public class LossHistory implements Serializable {
    private static final long serialVersionUID = 1L;

    private class HistoryNode implements Serializable {
        private static final long serialVersionUID = 1L;

        private final Map<Square, HistoryNode> _children = new HashMap<>();

        private HistoryNode get(Square square) {
            return this._children.get(square);
        }

        private HistoryNode addChild(Square square) {
            HistoryNode result = new HistoryNode();
            this._children.put(square, result);
            return result;
        }

        private int height() {
            return 1 + this._children.values().stream()
                .mapToInt(x -> x.height())
                .max()
                .orElse(0);
        }
    }

    private final HistoryNode lossHistoryTree = new HistoryNode();

    private HistoryNode findLast(ListIterator<Square> iter) {
        HistoryNode currentNode = lossHistoryTree;
        while (iter.hasNext()) {
            Square square = iter.next();
            HistoryNode next = currentNode.get(square);
            if (next == null) {
                iter.previous();
                break;
            }
            currentNode = next;
        }
        return currentNode;
    }

    public boolean isLost(List<Square> moveSequence) {
        Objects.requireNonNull(moveSequence);
        ListIterator<Square> iter = moveSequence.listIterator();
        HistoryNode furthestNode = findLast(iter);

        if (iter.hasNext()) {
            // we have moves played that have never lost
            return false;
        }

        // If the last move in our moveSequence is the 2nd to last move in a losing sequence, it's a losing move
        return furthestNode.height() == 2;
    }

    public void addLoss(List<Square> moveSequence) {
        Objects.requireNonNull(moveSequence);
        ListIterator<Square> iter = moveSequence.listIterator();
        HistoryNode currentNode = findLast(iter);
        while (iter.hasNext()) {
            Square square = iter.next();
            currentNode = currentNode.addChild(square);
        }
    }
}
