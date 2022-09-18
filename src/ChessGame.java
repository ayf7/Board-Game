import java.util.*;

/** Represents a chess game */
public class ChessGame implements BoardGame {

    /* Instance variables: includes 2 player objects, turn number, and player turn */

    // list of player objects, represents the 2 players that are playing the board game
    // white is index 0, black is index 1
    private final Player[] players;

    // playerTurn: integer between 1 and 2, corresponding to the player's playerNum value
    private int playerTurn;

    // chess board in the chess game
    private final ChessBoard board;

    /** Creates a new chess game */
    public ChessGame() {
        players = new Player[]{new Player(), new Player()};
        playerTurn = 1;
        board = new ChessBoard();
    }

    @Override
    public void startGame() {
        // players start making their moves
        boolean nextMove = true;
        System.out.println(board);
        System.out.println(players[playerTurn - 1].getName() + "'s turn to move.");
        while (nextMove) {
            // when no moves exist left, the while loop will break
            nextMove = promptPlayerMove();
        }
    }

    @Override
    public void play() {
        boolean nextGame = true;
        while (nextGame) {
            startGame();
            // determine if the end is a win for either player or a draw
            boolean checkmate = checkGameEnd();
            if (checkmate) { // checkmate
                nextGame = endGame(3 - playerTurn);
            }
            else { // stalemate
                nextGame = endGame(0);
            }
            // reset the board
            if (nextGame) {
                newGame();
            }
        }
    }

    @Override
    public boolean promptPlayerMove() {
        // gathers all legal moves of the player moving
        Hashtable<String, ArrayList<Move>> moves = new Hashtable<>();
        ArrayList<String> coords = board.listOfCoords(playerTurn);

        int count = 0;
        for (String coord : coords) {
            ArrayList<Move> coordMoves = board.findLegalMoves(coord);
            moves.put(coord, coordMoves);
            count += coordMoves.size();
        }

        // if there are no legal moves by the player, run checkmate and stalemate endings
        if (count == 0) {
            return false;
        }

        // if neither checkmate nor stalemate are found, prompt player move
        String from = "";
        boolean invalid = true;

        // asks for the coordinate of the piece to be moved
        System.out.print("Which piece would you like to move? ");
        while (invalid) {
            // ask for the square with the piece the user wishes to move
            Scanner input = new Scanner(System.in);
            String inputCoord = input.nextLine();

            // checks if the input is a coord
            for (String coord : moves.keySet()) {
                if (inputCoord.equals(coord) && moves.get(coord).size() > 0) {
                    from = inputCoord;
                    invalid = false;
                }
            }
            if (invalid) {
                System.out.print("Invalid, try again: ");
            }
        }

        // once input has been found, ask for a second input
        Move move1 = null;
        invalid = true;
        System.out.print("Move piece where? ");
        while (invalid) {
            // ask for an input
            Scanner input = new Scanner(System.in);
            String inputCoord = input.nextLine();
            // checks if the input string is in the list of moves from key "from"
            if (inputCoord.equals("back")) {
                return true;
            }
            for (Move move : moves.get(from)) {
                if (move.getTo().equals(inputCoord)) {
                    move1 = move;
                    invalid = false;
                }
            }
            if (invalid) {
                System.out.print("Invalid, try again: ");
            }
        }
        // move the piece
        board.movePiece(from, move1);
        playerTurn = 3 - playerTurn;
        System.out.println(board);
        System.out.println(players[playerTurn - 1].getName() + "'s turn to move.");
        return true;
    }

    @Override
    public boolean checkGameEnd() {
        if (board.kingInCheck(3-playerTurn)) {
            // checkmate
            System.out.println("Checkmate!");
        }
        else {
            // stalemate
            System.out.println("Stalemate!");
        }
        return true;
    }

    @Override
    public boolean endGame(int winnerSide) {
        // update score
        if (winnerSide > 0) { // if either side won
            players[winnerSide-1].addScore(1);
        }
        else { // if it is a draw
            players[0].addScore(0.5);
            players[0].addScore(0.5);
        }
        // print updated scores
        printStats();
        // asks if the player would like to continue playing
        System.out.print("Play again? (yes/no)");
        Scanner scan = new Scanner(System.in);
        while (true) {
            String input = scan.nextLine();
            if (input.equals("yes")) {
                return true;
            }
            else if (input.equals("no")) {
                return false;
            }
            else {
                System.out.println("Invalid input, try again:");
            }
        }
    }

    @Override
    public void newGame() {
        // reset board and score
        board.reset();
        playerTurn = 1;

        // swap order
        Player temp = players[0];
        players[0] = players[1];
        players[1] = temp;
    }

    /** Prints out the name of each player and their scores. */
    public void printStats() {
        System.out.println(players[0].getName() + ": " + players[0].getScore());
        System.out.println(players[1].getName() + ": " + players[1].getScore());
    }
}

