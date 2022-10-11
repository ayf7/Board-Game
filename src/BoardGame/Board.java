package BoardGame;

import java.util.*;

/** General interface for a board to be used in a board game */
public interface Board {

    /** Sets up the board for a new game. */
    void createBoard();

    /** Moves a piece from its current location to a new location, based on the move
     * @Pre-condition: from-square has a piece, the move is a legal move of that piece
     * @param from square that the piece is originally at
     * @param chessMove specific objects with specific details of the move
     * @Post-condition: move is successfully completed with any piece instance variables updated,
     * ready to move on to the next move
     */
    void movePiece(String from, Move chessMove);

    /** Resets the board to the starting position
     * Post-condition: the board is reset to its original configuration for a new game */
    void reset();

    /** Finds all legal moves of a piece at coord
     * @Pre-condition: square at coord must have a piece, piece must be on the side of the player
     * who is moving
     * @param coord coordinate with the piece that we want to find the legal moves from
     * @return legal moves as move objects in ArrayList */
    ArrayList<? extends Move> findLegalMoves(String coord);

    

}
