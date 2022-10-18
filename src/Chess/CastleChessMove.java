package Chess;

/** Represents a castling move by a player. */
public class CastleChessMove extends ChessMove {

    /** Square of the rook that is moving. */
    private final String rookFrom;

    /** Destination of the rook. */
    private final String rookTo;

    /** Constructor for a castling move. */
    public CastleChessMove(ChessSquare to, ChessSquare rookFrom, ChessSquare rookTo) {
        super(to, "CastleMove");
        this.rookFrom = ChessBoard.squareToCoordinate(rookFrom);
        this.rookTo = ChessBoard.squareToCoordinate(rookTo);
    }

    public String getRookFrom() {
        return rookFrom;
    }

    public String getRookTo() {
        return rookTo;
    }


}
