import BoardGame.Piece;

/** Represents a piece on a specific square */
public abstract class ChessPiece implements Piece {

    /** The side of the piece. Either 1 or 2, corresponds to which player
    owns this piece */
    private final int side;

    /** The type of piece. Represented as an integer that corresponds to
    the index of types. */
    protected final int type;

    /**  List of the possible types as a list of string, index corresponding to
    the int type value. */
    protected static String[] typeList;

    /** The state of the piece's movement during the
    game. True if the piece has not moved. */
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

    @Override
    public int getSide() {
        return side;
    }

    @Override
    public String getTypeName() {
        return typeList[type];
    }

    @Override
    public boolean hasNotMoved() {
        return unmoved;
    }

    @Override
    public void move() {
        unmoved = false;
    }

    @Override
    public void reset() {
        unmoved = true;
    }



}
