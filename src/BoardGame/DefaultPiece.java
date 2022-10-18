package BoardGame;

/** Implementation of the Piece interface for a generic piece. Generic pieces are
 * represented by circles. */
public class DefaultPiece implements Piece {

    /** The side of the piece. Either 1 or 2, corresponds to which side
     owns this piece. */
    private final int side;

    /** Creates a default piece.
     * @param side integer that corresponds to the player number.
     */
    public DefaultPiece(int side) {
        this.side = side;
    }

    @Override
    public int getSide() {
        return side;
    }

    /** Returns a white circle if the side is 1, or returns a black circle if
     * the side is 2. */
    @Override
    public String toString() {
        if (side == 1) {
            return "\u26AA";
        }
        else {
            return "\u26AB";
        }
    }
}
