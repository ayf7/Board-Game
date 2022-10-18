package BoardGame;

/** A piece on the board. Contains information about the piece that must be used by the board
 * to understand any configuration. Does not include the position of the piece, as that
 * responsibility is given exclusively to the board. */
public interface Piece {

    /** Returns the side of the piece */
    int getSide();
}
