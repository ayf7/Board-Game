package BoardGame;

/** A square on the board. Responsible for adding or removing a piece from itself, and
 * receiving any information about pieces that occupy a square to relay the board. */
public interface Square {

    /** Adds a piece to the square
     * @param piece the piece to be added to the square
     * @Pre-condition: piece must not be null and of their correct piece
     */
    void add(Piece piece);

    /** Remove a piece from the square
     * @Pre-condition: square must have 'piece' pointing to a Piece object
     */
    void remove();

    /** Returns whether the square has a piece */
    boolean hasPiece();

    /** Returns the piece object */
    Piece getPiece();

    /** Returns the type of piece.
     * @Pre-condition: square has a piece */
    String pieceType();

    /** Returns the piece side of the square */
    int pieceSide();

    /** Returns the row of the square */
    int getRow();

    /** Returns the column of the square */
    int getCol();


}
