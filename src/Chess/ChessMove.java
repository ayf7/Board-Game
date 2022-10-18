package Chess;

import BoardGame.Move;
import BoardGame.Square;

/** Represents a possible move by a player. */
public class ChessMove implements Move {

    /** Coordinate of the square that the piece will move to. */
    private final String to;

    /**  The type of chess move, including standard moves, en passant,
     * castling, and promotions. */
    private final String type;

    /** Constructor for a default move. */
    public ChessMove(Square to) {
        this.to = ChessBoard.squareToCoordinate(to);
        this.type = "Move";
    }

    /** Constructor for a special move where type is specified. */
    public ChessMove(Square to, String type) {
        this.to = ChessBoard.squareToCoordinate(to);
        this.type = type;
    }

    @Override
    public String getTo() {
        return to;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "C: \"" + to +  "\" Type: \"" + type + "\"";
    }

}
