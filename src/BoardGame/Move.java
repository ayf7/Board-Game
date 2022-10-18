package BoardGame;

/** The concept of a move in a board game. Wraps information about the specifics of
 * a move to be processed by different modules. */
public interface Move {

    /** Returns the coordinate of the square that the piece will move to */
    String getTo();

    /** Returns the type of the move */
    String getType();

}