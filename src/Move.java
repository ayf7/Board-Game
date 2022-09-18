/** Represents a possible move by a player */
public class Move {
    // coordinate of the square that the piece will move to
    private final String to;
    // what type of move the move is
    private final String type;

    /** Constructor for a default move */
    public Move(ChessSquare to) {
        this.to = ChessBoard.squareToCoordinate(to);
        this.type = "Move";
    }

    /** Constructor for a special move where type is specified:
     * "EP", "Castle", "Capture", Promotion", "EPV," */
    public Move(ChessSquare to, String type) {
        this.to = ChessBoard.squareToCoordinate(to);
        this.type = type;
    }

    public String getTo() {
        return to;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "C: \"" + to +  "\" Type: \"" + type + "\"";
    }

}
