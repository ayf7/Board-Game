/** Represents a pawn on a specific square*/
public class Pawn extends ChessPiece{

    // epVulnerable: stands for "en-passant vulnerable", indicates whether it
    // can be taken through en passant
    private boolean epVulnerable;

    /**
     * Constructor: creates a Pawn object using the ChessPiece superclass.
     * @param side integer that corresponds to the player number
     */
    public Pawn(int side) {
        super(side, 0);
    }

    public boolean getEPVState() {
        return epVulnerable;
    }

    public void toggleEPVState() {
        epVulnerable = !epVulnerable;
    }

    @Override
    public String toString() {
        if (super.getSide() == 1) {
            return "\u2659";
        }
        return "\u265F";
    }

    @Override
    public void reset() {
        super.reset();
        epVulnerable = false;
    }
}
