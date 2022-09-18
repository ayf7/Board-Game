import java.util.Scanner;

/** Represents a player of a board game */
public class Player {
    // score of the player. Non-negative value with decimal value .0 or .5
    private double score;
    // name of the player. finalized upon initialization of the player object.
    private final String name;

    /** Creates a new player, requires an input for the name upon initialization */
    public Player() {
        score = 0.0;
        Scanner s = new Scanner(System.in);
        System.out.print("Input player name: ");
        name = s.nextLine();
    }

    /** Returns the score of the player */
    public double getScore() {
        return score;
    }

    /** Returns the name of the player */
    public String getName() {
        return name;
    }

    /** Adds a value to the player score. Amount parameter has a non-negative value with
     * decimal value .0 or .5 */
    public void addScore(double amount) {
        score += amount;
    }
}
