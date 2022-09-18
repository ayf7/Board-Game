/** Represents a queen on a specific square*/
public class Queen extends ChessPiece{

    /**
     * Constructor: creates a Queen object using the ChessPiece superclass
     * @param side : integer that corresponds to the player number
     */
    public Queen(int side) {
        super(side, 4);
    }

    @Override
    public String toString() {
        if (super.getSide() == 1) {
            return "\u2655";
        }
        return "\u265B";
    }
}
