package Chess;

import BoardGame.DefaultPiece;

/** Represents a chess piece on a specific square. */
public abstract class ChessPiece extends DefaultPiece {

    /** The type of piece. Represented as an integer that corresponds to
    the index of types. */
    protected final int type;

    /**  List of the possible type names. Index corresponds to
    the int type value. */
    protected static String[] typeList;

    /** The state of the piece's movement during the
    game. True if the piece has n   ot moved. */
    private boolean unmoved;

    /** Creates a chess piece.
     * @param side integer that corresponds to the player number.
     * @param type integer that corresponds to the index in the types list.
     */
    public ChessPiece(int side, int type) {
        super(side);
        this.type = type;
        typeList = new String[]{"pawn", "knight", "bishop", "rook",
                "queen","king"};
        unmoved = true;
    }

    /** Returns the name of the piece. */
    public String getTypeName() {
        return typeList[type];
    }

    /** Returns whether the piece has moved */
    public boolean hasNotMoved() {
        return unmoved;
    }

    /** Sets the unmoved state to true after the pawn moves. */
    public void move() {
        unmoved = false;
    }

    /** Resets the piece to initial state. */
    public void reset() {
        unmoved = true;
    }



}
