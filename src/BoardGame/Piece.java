package BoardGame;

public interface Piece {

    /** Returns the side of the piece */
    int getSide();

    /** Returns the piece type, in a string */
    String getTypeName();

    /** Returns whether the piece has moved */
    boolean hasNotMoved();

    /** Sets the unmoved state to true after the pawn moves */
    void move();

    /** Resets the piece to initial state */
    void reset();
}
