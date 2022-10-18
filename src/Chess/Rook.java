package Chess;

/** Represents a rook on a specific square. */
public class Rook extends ChessPiece{

    /** Constructor: creates a Rook object using the ChessPiece superclass.
     * @param side integer that corresponds to the player number */
    public Rook(int side) {
        super(side, 3);
    }

    @Override
    public String toString() {
        if (super.getSide() == 1) {
            return "\u2656";
        }
        return "\u265C";
    }
}
