import BoardGame.Piece;
import BoardGame.Square;

/** Represents a square or tile on the board */
public class ChessSquare implements Square {

    /** The piece that occupies the square. Null if there is no such piece */
    private ChessPiece piece;

    /** Determines if this square has a piece */
    private boolean hasPiece;

    /** Row of the square */
    private final int row;

    /** Column of the square */
    private final int col;

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

    @Override
    public void add(Piece piece) {
        assert piece != null;
        this.piece = (ChessPiece) piece;
        hasPiece = true;
    }

    @Override
    public void remove() {
        piece = null;
        hasPiece = false;
    }

    @Override
    public boolean hasPiece() {
        return hasPiece;
    }

    @Override
    public ChessPiece getPiece() {
        assert hasPiece;
        return piece;
    }

    @Override
    public String pieceType() {
        assert hasPiece;
        return piece.getTypeName();
    }

    @Override
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

    @Override
    public int getRow() {
        return row;
    }

    @Override
    public int getCol() {
        return col;
    }
}
