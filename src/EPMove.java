/** represents an en passant move by a player */
public class EPMove extends Move {
    // square where the pawn to be captured is
    private String pawn;

    /** Constructor for an en passant move */
    public EPMove(ChessSquare to, ChessSquare pawn) {
        super(to, "EPMove");
        this.pawn = ChessBoard.squareToCoordinate(pawn);
    }

    public String getPawn() {
        return pawn;
    }
}
