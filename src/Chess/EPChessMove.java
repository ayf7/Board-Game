package Chess;

/** Represents an en passant move by a player. */
public class EPChessMove extends ChessMove {

    /** Square where the pawn to be captured is. */
    private final String pawn;

    /** Constructor for an en passant move. */
    public EPChessMove(ChessSquare to, ChessSquare pawn) {
        super(to, "EPMove");
        this.pawn = ChessBoard.squareToCoordinate(pawn);
    }

    public String getPawn() {
        return pawn;
    }
}
