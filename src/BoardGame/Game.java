package BoardGame;

/** The turn-based mechanics of a board game. Processes the inputs that the players make. */
public interface Game {

    /** Manages the game over multiple rounds, manages resetting and score updates. */
    void play();

    /** Starts the board game for a single game. Player 1 goes first. Continues to
     * run until the game finishes. */
    void startGame();

    /** Prompts the player to move. Continues to run until the player inputs a legal
     * move. */
    boolean promptPlayerMove();

    /** When a player has no moves left, checks who won.
     * @return true if the game has ended */
    boolean checkGameEnd();

    /** Ends the game with an ending message, updates score, and prompts players
     * to play again
     * @param winnerSide the player number of the winner.
     *                   <p>1 = player 1
     *                   <p>2 = player 2
     *                   <p>0 = draw (if applicable)
     * @return true if playing again, false if not */
    boolean endGame(int winnerSide);

    /** Resets the board, updates the player turns, starts game again */
    void newGame();


}
