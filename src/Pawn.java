/** Represents a pawn on a specific square*/
public class Pawn extends ChessPiece{

    /** Stands for "en-passant vulnerable", indicates whether it
    // can be taken through en passant */
    private boolean epVulnerable;

    /** Creates a Pawn object using the ChessPiece superclass.
     * @param side integer that corresponds to the player number */
    public Pawn(int side) {
        super(side, 0);
    }

    /** Returns the EPV state of a pawn */
    public boolean getEPVState() {
        return epVulnerable;
    }

    /** Changes the EPV state of the pawn */
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
