/** Represents a king on a specific square*/
public class King extends ChessPiece{

    /**
     * Constructor: creates a King object using the ChessPiece superclass
     * @param side integer that corresponds to the player number
     */
    public King(int side) {
        super(side, 5);
    }

    @Override
    public String toString() {
        if (super.getSide() == 1) {
            return "\u2654";
        }
        return "\u265A";
    }
}
