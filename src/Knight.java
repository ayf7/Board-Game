/** Represents a knight on a specific square*/
public class Knight extends ChessPiece{

    /**
     * Constructor: creates a Knight object using the ChessPiece superclass
     * @param side integer that corresponds to the player number
     */
    public Knight(int side) {
        super(side, 1);
    }

    @Override
    public String toString() {
        if (super.getSide() == 1) {
            return "\u2658";
        }
        return "\u265E";
    }
}
