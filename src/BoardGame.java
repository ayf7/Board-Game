import java.util.*;

/** General interface for a board game */
interface BoardGame {


    /** play(): manages the game over multiple rounds, manages resetting and score updates */
    void play();

    /** startGame(): starts the board game for a single game, player 1 goes first */
    void startGame();

    /** promptPlayerMove(): prompts the player to move, continues to run until the player inputs a legal move */
    boolean promptPlayerMove();

    /** checkGameEnd(): when a player has no moves left, checks who wins
     * @return true if the game has ended */
    boolean checkGameEnd();

    /** checkGameEnd(): ends the game with an ending message
     * updates score
     * prompts players to play again
     * @param winnerSide the player number of the winner. 1 = player 1, 2 = player 2, 0 = draw (if applicable)
     * @return true if playing again, false if not */
    boolean endGame(int winnerSide);

    /** newGame(): resets everything, updates any scores, starts game again */
    void newGame();


}
