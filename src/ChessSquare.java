/** Represents a square or tile on the board */
public class ChessSquare {

    // Piece, represents the piece that occupies the square, null if there is no such piece
    private ChessPiece piece;
    private boolean hasPiece;

    // row and column: represents the row and column of the square
    private int row, col;

    /** Constructor: creates a Square object
     * @param row row of the square on the board
     * @param col column of the square on the board
     * @Pre-condition: requires row and col to be an integer from 0 to the length of the row/column-1
     */
    public ChessSquare(int row, int col) {
        piece = null; // at initialization, all squares are empty
        hasPiece = false;
        this.row = row;
        this.col = col;
    }

    /** add(piece): adds a piece to the square
     * @param piece the piece to be added to the square
     * @Pre-condition: piece must not be null
     */
    public void add(ChessPiece piece) {
        assert piece != null;
        this.piece = piece;
        hasPiece = true;
    }

    /** remove(): remove a piece from the square
     * @Pre-condition: square must have 'piece' pointing to a Piece object
     */
    public void remove() {
        piece = null;
        hasPiece = false;
    }

    public boolean hasPiece() {
        return hasPiece;
    }

    /** getPiece(): returns the piece object */
    public ChessPiece getPiece() {
        assert hasPiece;
        return piece;
    }

    /** pieceType(): returns the type of piece.
     * @Pre-condition: square has a piece */
    public String pieceType() {
        assert hasPiece;
        return piece.getTypeName();
    }

    public int pieceSide() {
        assert hasPiece;
        return piece.getSide();
    }

    @Override
    public String toString() {
        if (piece == null) {
            if ((row + col) % 2 == 0) {
                return "\u2B1B\u200A";
            }
            else {
                return "\u2B1C\u200A";
            }
        }
        else {
            return piece.toString();
        }
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
