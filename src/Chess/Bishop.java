package Chess;

/** Represents a bishop on a specific square. */
public class Bishop extends ChessPiece {

    /** Constructor: creates a Bishop object using the ChessPiece superclass.
     * @param side integer that corresponds to the player number */
    public Bishop(int side) {
        super(side, 2);
    }

    @Override
    public String toString() {
        if (super.getSide() == 1) {
            return "\u2657";
        }
        return "\u265D";
    }
}
