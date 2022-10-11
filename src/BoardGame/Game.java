package BoardGame;

/** General interface for a board game */
public interface Game {

    /** Manages the game over multiple rounds, manages resetting and score updates */
    void play();

    /** Starts the board game for a single game, player 1 goes first */
    void startGame();

    /** Prompts the player to move, continues to run until the player inputs a legal move */
    boolean promptPlayerMove();

    /** When a player has no moves left, checks who wins
     * @return true if the game has ended */
    boolean checkGameEnd();

    /** Ends the game with an ending message
     * updates score
     * prompts players to play again
     * @param winnerSide the player number of the winner.
     *                   <p>1 = player 1
     *                   <p>2 = player 2
     *                   <p>0 = draw (if applicable)
     * @return true if playing again, false if not */
    boolean endGame(int winnerSide);

    /** Resets everything, updates any scores, starts game again */
    void newGame();


}
