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

    public boolean hasSequence(List<Square> moveSequence) {
        Objects.requireNonNull(moveSequence);
        ListIterator<Square> iter = moveSequence.listIterator();
        findLast(iter);
        boolean hasMoreMoves = iter.hasNext();
        return !hasMoreMoves;
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
