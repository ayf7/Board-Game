import java.util.*;

/** General interface for a board to be used in a board game */
public interface Board {

    /** createBoard(): sets up the board for a new game. */
    void createBoard();

    /** movePiece(Square, Square): moves a piece from its current location to the new location.
     * @Pre-condition: from-square has a piece, piece in from-square has a legal move to
     * the to-square, and to-square does not have a piece.
     * @param from square that the piece is originally at
     * @param move specific objects with specific details of the move
     */
    void movePiece(String from, Move move);

    /** reset(): resets the board
     * Post-condition: the board is reset to its original configuration for a new game */
    void reset();

    /** findLegalMoves(): finds all legal moves
     * Pre-condition: square at coord must have a piece, piece must be on the side of the player
     * who is moving.
     * @param coord coordinate with the piece that we want to find the legal moves from.
     * @return legal moves as coordinates in ArrayList. */
    ArrayList findLegalMoves(String coord);

    

}
