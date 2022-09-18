/** Represents a piece on a specific square */
public abstract class ChessPiece {

    // side: the side of the piece. Either 1 or 2, corresponds to which player
    // owns this piece
    private final int side;

    // type: the type of piece. Represented as an integer that corresponds to
    // the index of types.
    protected final int type;

    // list of the possible types as a list of string, index corresponding to
    // the int type value.
    protected static String[] typeList;

    // unmoved: the state of the piece's movement during the
    // game. True if the piece has not moved.
    private boolean unmoved;

    /** Creates a chess piece (not of type ChessPiece)
     * @param side integer that corresponds to the player number
     * @param type integer that corresponds to the index in the types list
     */
    public ChessPiece(int side, int type) {
        this.side = side;
        this.type = type;
        typeList = new String[]{"pawn", "knight", "bishop", "rook",
                "queen","king"};
        unmoved = true;
    }

    /** Returns the side of the piece */
    public int getSide() {
        return side;
    }

    /** Returns the piece type, in a string */
    public String getTypeName() {
        return typeList[type];
    }

    /** Returns whether the piece has moved */
    public boolean hasNotMoved() {
        return unmoved;
    }

    /** Sets the unmoved state to true after the pawn moves */
    public void move() {
        unmoved = false;
    }

    /** Resets the piece to initial state */
    public void reset() {
        unmoved = true;
    }



}
