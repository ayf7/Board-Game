package Chess;

import BoardGame.Board;
import BoardGame.Move;
import BoardGame.Square;
import java.util.*;

/** Represents the chess board of a chess game, implements the board interface. */
public class ChessBoard implements Board {

    /** 2D array of square objects, representing the board. */
    protected ChessSquare[][] board;

    /** Immutable 2D array of pieces for permanent reference.
     <p>
     * Row 0 is the list of pieces for player 1, row 1 is the list of pieces for player 2.
     <p>
     * Columns 0-7 are the pawns, columns 8-15 are the pieces in order from a/h1 rook to a/h8 rook. */
    private final ChessPiece[][] pieceList;

    /** Reference to the last piece to have moved. */
    private ChessPiece lastPiece;

    /** Creates a new chess board object with the default starting position. */
    public ChessBoard() {
        board = new ChessSquare[8][8];

        // create square objects for the board
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                board[row][col] = new ChessSquare(row, col);
            }
        }
        // piece list setup
        pieceList = new ChessPiece[2][16];
        for (int side = 0; side < 2; side++) {
            // pawns
            for (int i = 0; i < 8; i++) {
                pieceList[side][i] = new Pawn(side+1);
            }
            // pieces
            pieceList[side][8] = new Rook(side+1);
            pieceList[side][9] = new Knight(side+1);
            pieceList[side][10] = new Bishop(side+1);
            pieceList[side][11] = new Queen(side+1);
            pieceList[side][12] = new King(side+1);
            pieceList[side][13] = new Bishop(side+1);
            pieceList[side][14] = new Knight(side+1);
            pieceList[side][15] = new Rook(side+1);
        }
        createBoard();

        // for continuity, set last piece to an arbitrary black piece
        lastPiece = pieceList[1][12];
    }

    @Override
    public void createBoard() {
        for (int col = 0; col < 8; col++) {
            board[1][col].add(pieceList[0][col]);
            board[6][col].add(pieceList[1][col]);
        }
        for (int col = 0; col < 8; col++) {
            board[0][col].add(pieceList[0][col+8]);
            board[7][col].add(pieceList[1][col+8]);
        }
    }

    @Override
    public void movePiece(String from, Move chessMove) {
        // updates EPV states if necessary
        if (lastPiece.getTypeName().equals("pawn") && ((Pawn) lastPiece).getEPVState()) {
            ((Pawn) lastPiece).toggleEPVState();
        }

        // move the piece
        coordinateToSquare(chessMove.getTo()).add(coordinateToSquare(from).getPiece());
        coordinateToSquare(from).remove();

        // updates instance variables and values
        lastPiece = coordinateToSquare(chessMove.getTo()).getPiece();
        int side = lastPiece.getSide();
        lastPiece.move();

        // additional changes that need to be made if necessary - promotion, castling, en passant
        switch (chessMove.getType()) {

            // remove the pawn that is en passant-ed
            case "EPMove" -> coordinateToSquare(((EPChessMove) chessMove).getPawn()).remove();
            case "CastleMove" -> {

                // move the corresponding rook
                coordinateToSquare(((CastleChessMove) chessMove).getRookTo()).add(
                        coordinateToSquare(((CastleChessMove) chessMove).getRookFrom()).getPiece());
                coordinateToSquare(((CastleChessMove) chessMove).getRookFrom()).remove();
            }
            case "Promote" -> {
                Scanner scan = new Scanner(System.in);
                String newType;
                boolean valid = false;
                while (!valid) {
                    System.out.println("What piece would you like to promote to?");
                    newType = scan.nextLine();
                    switch (newType) {
                        case "knight" -> {
                            coordinateToSquare(chessMove.getTo()).add(new Knight(side));
                            valid = true;
                        }
                        case "bishop" -> {
                            coordinateToSquare(chessMove.getTo()).add(new Bishop(side));
                            valid = true;
                        }
                        case "rook" -> {
                            coordinateToSquare(chessMove.getTo()).add(new Rook(side));
                            valid = true;
                        }
                        case "queen" -> {
                            coordinateToSquare(chessMove.getTo()).add(new Queen(side));
                            valid = true;
                        }
                    }
                }
            }
            case "EPV" -> ((Pawn) lastPiece).toggleEPVState();
        }
    }

    @Override
    public ArrayList<ChessMove> findLegalMoves(String coord) {
        // retrieves the list of moves the piece can move to regardless of checks
        ArrayList<ChessMove> chessMoveList = findMoves(coord);
        if (chessMoveList == null) {
            return null;
        }

        // gathers variables
        ChessSquare square = coordinateToSquare(coord);
        ChessPiece piece = square.getPiece();
        String type = coordinateToSquare(coord).pieceType();
        int col = square.getCol();
        int row = square.getRow();

        // if the move leads to the player in check (true), remove that from the arraylist
        for (int i = chessMoveList.size()-1; i >= 0; i--) {
            if (runHypothetical(coord, chessMoveList.get(i))) {
                chessMoveList.remove(i);
            }
        }

        // add king castle moves
        if (type.equals("king") && piece.hasNotMoved()) {
            // create a temporary array list store new lists to add as we traverse the moveList array
            ArrayList<ChessMove> additionalChessMoves = new ArrayList<>();
            for (ChessMove chessMove : chessMoveList) {
                for (int i = -1; i <= 1; i += 2) {
                    // if one of the horizontal moves is still legal after the removal phase
                    int colScan = col + i;
                    if (board[row][colScan] == coordinateToSquare(chessMove.getTo())) {
                        // clear the entire horizontal row until it reaches the rook or goes out of bounds
                        boolean next = true;
                        while (next) {
                            colScan += i;
                            if (!inRange(row, colScan)) {
                                next = false;
                            }
                            // if the scan reaches a piece, checks if the piece is a rook that not moved
                            // note: same type is implied if the piece has not moved
                            else if (board[row][colScan].getPiece() != null) {
                                if (board[row][colScan].getPiece().getTypeName().equals("rook") &&
                                        board[row][colScan].getPiece().hasNotMoved()) {
                                    additionalChessMoves.add(new CastleChessMove(board[row][col + 2*i], board[row][colScan], board[row][col+i]));
                                }
                                next = false;
                            }
                        }
                    }
                }
            }
            // run hypothetical for these castle moves and add the new castle moves to the main move list
            for (int i = additionalChessMoves.size()-1; i >= 0; i--) {
                if (!runHypothetical(coord, additionalChessMoves.get(i))) {
                    chessMoveList.add(additionalChessMoves.get(i));
                }
            }
        }
        return chessMoveList;
    }

    @Override
    public void reset() {
        // remove all pieces on any square present
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (board[row][col].hasPiece()) {
                    board[row][col].remove();
                }
            }
        }
        for (ChessPiece[] chessPieces : pieceList) {
            for (int col = 0; col < pieceList[0].length; col++) {
                chessPieces[col].reset();
            }
        }
        // recreate the board
        createBoard();
    }

    @Override
    public String toString() {
        StringBuilder boardString = new StringBuilder();
        for (int row = 7; row >= 1; row--) {
            for (int col = 0; col < 8; col++) {
                boardString.append(board[row][col]);
            }
            boardString.append("\n");
        }
        for (int col = 0; col < 8; col++) {
            boardString.append(board[0][col]);
        }
        return boardString.toString();
    }

    /** Finds all squares a piece at the specified coordinate covers, not necessarily where it
     * can legally move to.
     * @Pre-condition: square at coord must have a piece
     * @param coord coordinate with the piece that we want to find the occupying squares
     * @return covered squares as move objects in ArrayList */
    public ArrayList<ChessMove> findMoves(String coord) {
        // list of coordinate strings to be returned
        ChessSquare square = coordinateToSquare(coord);
        ChessPiece piece = square.getPiece();

        // if the piece is null (square has no piece), return null
        if (piece == null) {
            return null;
        }

        // if the piece exists, find all legal moves regardless of check/pin status
        // ArrayList to store all the valid squares
        ArrayList<ChessMove> chessMoveList = new ArrayList<>();

        // gathers all necessary info from the square and piece
        String type = coordinateToSquare(coord).pieceType();
        int col = square.getCol();
        int row = square.getRow();
        int side = piece.getSide();

        // PAWN ALGORITHM
        if (type.equals("pawn")) {
            // NOTE: MUST INCLUDE EN-PASSANT, PROMOTION
            int i; // direction at which the piece moves. white moves upwards, black moves downwards
            if (side == 1) { // white pawn
                i = 1;
            }
            else { // black pawn
                i = -1;
            }
            // moving forward one tile
            // checks if the row above is within range and there is no piece there
            if (inRange(row+i, col) && !board[row+i][col].hasPiece()) {
                // checks if the move is promotional
                if (row+i == 7) {
                    chessMoveList.add(new ChessMove(board[row+i][col], "Promote"));
                }
                else {
                    chessMoveList.add(new ChessMove(board[row+i][col]));
                }

                // moving forward two tiles
                // checks if the row 2 tiles above is within range and there is no piece there
                if (inRange(row+2*i, col) && piece.hasNotMoved()) {
                    if (!board[row+2*i][col].hasPiece()) {
                        chessMoveList.add(new ChessMove(board[row+2*i][col], "EPV"));
                    }
                }
            }
            // diagonal captures
            // checks if rows and columns are within range, the diagonal square has a
            // piece, and the piece is on the opposite side
            for (int j = -1; j <= 1; j+=2) { // j = -1 or 1: j's represent the two diagonal sides
                if (inRange(row+i, col+j) && board[row+i][col+j].hasPiece()
                        && board[row+i][col+j].pieceSide() == 3-side) {
                    // checks if the capture is promotional
                    if (row+i == 7) {
                        chessMoveList.add(new ChessMove(board[row+i][col+j], "Promote"));
                    }
                    else {
                        chessMoveList.add(new ChessMove(board[row+i][col+j], "Capture"));
                    }
                }
            }

            // en passant mechanism
            // checks if in range, the side square has a piece, the piece is a pawn that is
            // en-passant vulnerable, and the piece is on the opposite side
            // note that en-passant vulnerable implies the square to be moved to is empty
            for (int j = -1; j <= 1; j+=2) { // j = -1 or 1: j's represent the two diagonal sides
                if (inRange(row, col+j) && board[row][col+j].hasPiece()
                        && board[row][col+j].pieceType().equals("pawn")
                        && ((Pawn) board[row][col+j].getPiece()).getEPVState()
                        && board[row][col+j].pieceSide() == 3-side) {
                    chessMoveList.add(new EPChessMove(board[row+i][col+j], board[row][col+j]));
                }
            }
        }
        // OTHER PIECE ALGORITHM
        else {
            // list of directions that the piece can go to, directions donated by an integer list of a length 2
            int[][] vectors;
            boolean repeat;
            // gets vector and repeat state
            switch (type) {
                case "knight" -> {  // knight vectors
                    vectors = new int[][]{{1, 2}, {1, -2}, {-1, 2}, {-1, -2}, {2, 1}, {2, -1},
                            {-2, 1}, {-2, -1}};
                    repeat = false;
                }
                case "bishop" -> {  // bishop vectors
                    vectors = new int[][]{{1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
                    repeat = true;
                }
                case "rook" -> {  // rook vectors
                    vectors = new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
                    repeat = true;
                }
                case "queen" -> {  // queen vectors
                    vectors = new int[][]{{1, 1}, {1, -1}, {-1, 1}, {-1, -1}, {1, 0}, {0, 1},
                            {-1, 0}, {0, -1}};
                    repeat = true;
                }
                default -> {  // king vectors
                    vectors = new int[][]{{1, 1}, {1, -1}, {-1, 1}, {-1, -1}, {1, 0}, {0, 1},
                            {-1, 0}, {0, -1}};
                    repeat = false;
                }
            }

            for (int[] vector : vectors) {
                boolean next = true;
                // creates temporary "sweeping" variables, stemming from col and row
                int tempCol = col;
                int tempRow = row;
                while (next) {
                    // if no repeat, loop condition is automatically set to false and only one loop occurs
                    next = repeat;
                    // creates new coordinates
                    tempCol += vector[0];
                    tempRow += vector[1];
                    // checks if the new coordinates are in range
                    if (inRange(tempRow, tempCol)) {
                        // checks if there is a piece there
                        if (board[tempRow][tempCol].hasPiece()) {
                            // if a piece is there, if the piece is of opposite side add it to the list
                            // because it can be captured
                            if (board[tempRow][tempCol].pieceSide() == 3-side) {
                                chessMoveList.add(new ChessMove(board[tempRow][tempCol], "Capture"));
                            }
                            // end the loop condition if it has not already
                            next = false;
                        }
                        // if there is no piece, add the square
                        else {
                            chessMoveList.add(new ChessMove(board[tempRow][tempCol]));
                        }
                    }
                    // if the coordinate is out of bounds, end the loop condition
                    else {
                        next = false;
                    }
                }
            }
        }
        return chessMoveList;
    }

    /** Checks if the row and column are in range of the 2D board. Private method because
     * it should be used exclusively by the ChessBoard.
     * @param row row being checked
     * @param col column being checked
     * @return true if the rows and columns are in the 2D board, false if not
     */
    private boolean inRange(int row, int col) {
        return (0 <= row && row <= 7 && 0 <= col && col <= 7);
    }

    /** Takes a move object and determines whether that move will lead to check, which is not
     * allowed.
     * @Pre-condition: coord must have the piece to be moved, move is the corresponding move that
     * the piece can legally make
     * @param chessMove the move that is being tested
     * @return true if the king is in check after doing the move, false if it is not
     */
    private boolean runHypothetical(String coord, ChessMove chessMove) {

        // gets the number of the other side using the coordinate of the piece to be moved
        int other = 3 - coordinateToSquare(coord).getPiece().getSide();

        // determine whether the move is legal (the king will not be in check)

        // create a new board that copies the current piece configuration of the board, the move
        // will be tested on this board to preserve the actual board's position
        ChessBoardTester testBoard = new ChessBoardTester(board);

        // performs the move
        testBoard.movePiece(coord, chessMove);

        // determines whether this piece results in check
        return testBoard.kingInCheck(other);
    }

    /** Checks if the king of either side is in check at any given position.
     * @Pre-condition: the king of "other" must not be in check itself
     * @param other the side we are determining if in check
     * @return true if the side is in check, false if not
     */
    public boolean kingInCheck(int other) {
        // scans the entire board for the other side's pieces
        for (ChessSquare[] row : board) {
            for (ChessSquare square : row) {

                // if the square has a piece that is of the opposite side
                if (square.getPiece() != null && square.getPiece().getSide() == other) {

                    // for each of the other side's pieces, the findMoves argument is run to find all
                    // squares the piece can observe (not necessarily where it can move to legally)
                    ArrayList<ChessMove> list = findMoves(ChessBoard.squareToCoordinate(square));

                    // checks if each move of that piece targets the side's king
                    for (ChessMove otherChessMove : list) {
                        if (coordinateToSquare(otherChessMove.getTo()).getPiece() != null &&
                                coordinateToSquare(otherChessMove.getTo()).pieceType().equals("king")) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /** Takes a coordinate combination (such as a3) and returns the
     * corresponding Square in the 2D array that is at that coordinate.
     * @Pre-condition: coord must be a valid coordinate: {a-h}{1-8}, case sensitive
     * @param coord standard chess coordinate system in the form of a string
     * @return corresponding Square object
     */
    public ChessSquare coordinateToSquare(String coord) {
        // gets ASCII value of the first character (a-h) and converts it to the appropriate
        // col number
        int col = coord.charAt(0)- 97;
        // takes the second character (in the form of a substring) and converts it to the
        // appropriate row number
        int row = Integer.parseInt(coord.substring(1))-1;
        return board[row][col];
    }
    /** Takes a square object and returns the corresponding string coordinate
     * in the 2D array.
     * @Post-condition: returning string must be a valid coordinate: {a-h}{1-8}, case sensitive
     * @param square square that we wish to get the coordinate of
     * @return corresponding string at that row and column
     */
    public static String squareToCoordinate(Square square) {
        // gets row and column numbers
        int row = square.getRow();
        int col = square.getCol();
        // converts them into respective ASCII characters
        char letter = (char)(col + 97);
        char number = (char)(row + 49);
        return letter + Character.toString(number);
    }

    /** Returns a list of the coordinates where all of one side's pieces are.
     * @Pre-condition: side must be 1 or 2
     * @return a list of squares of that side
     */
    public ArrayList<String> listOfCoords(int side) {
        // list that contains all the coordinates
        ArrayList<String> list = new ArrayList<>();

        // traverses the entire board - may optimize this function
        for (ChessSquare[] row : board) {
            for (ChessSquare square : row) {
                // if the square has a piece that is the desired type, add it to the list
                if (square.hasPiece() && square.pieceSide() == side) {
                    list.add(squareToCoordinate(square));
                }
            }
        }
        return list;
    }

    /** Special subclass of ChessBoard used primarily for testing the
     * legality of moves in runHypothetical. Creates a duplicate copy of the ChessBoard at any instance.
     * Contains an overriden movePiece with fewer processes, custom constructor. */
    private static class ChessBoardTester extends ChessBoard {

        /** Constructor of a tester chess board, pieces initialized in the same configuration
         * as the parameter. Does not account for EPV or unmoved states.
         * @param startPosition the 2D array of ChessSquares that we wish to test on */
        ChessBoardTester(ChessSquare[][] startPosition) {
            board = new ChessSquare[8][8];
            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    board[row][col] = new ChessSquare(row, col);
                }
            }
            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    if (startPosition[row][col].getPiece() != null) {
                        int side = startPosition[row][col].getPiece().getSide();
                        String type = startPosition[row][col].getPiece().getTypeName();
                        switch (type) {
                            case "pawn" -> board[row][col].add(new Pawn(side));
                            case "knight" -> board[row][col].add(new Knight(side));
                            case "bishop" -> board[row][col].add(new Bishop(side));
                            case "rook" -> board[row][col].add(new Rook(side));
                            case "queen" -> board[row][col].add(new Queen(side));
                            default -> // remaining king object
                                    board[row][col].add(new King(side));
                        }
                    }
                }
            }
        }

        @Override
        public void movePiece(String from, Move chessMove) {
            // move the piece
            coordinateToSquare(chessMove.getTo()).add(coordinateToSquare(from).getPiece());
            coordinateToSquare(from).remove();

            // additional changes that need to be made if necessary - promotion, castling, en passant
            switch (chessMove.getType()) {
                case "EPMove" -> coordinateToSquare(((EPChessMove) chessMove).getPawn()).remove();
                case "CastleMove" -> {
                    coordinateToSquare(((CastleChessMove) chessMove).getRookTo()).add(
                            coordinateToSquare(((CastleChessMove) chessMove).getRookFrom()).getPiece());
                    coordinateToSquare(((CastleChessMove) chessMove).getRookFrom()).remove();
                }
            }
        }
    }
}