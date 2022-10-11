package BoardGame;

public interface Move {

    /** Returns the coordinate of the square that the piece will move to */
    String getTo();

    /** Returns the type of the move */
    String getType();

}