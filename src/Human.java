import java.io.Serializable;

class Human implements Serializable {
    private final int length;
    private final char[] letters;
    private final char[][] board;
    private final PrintBoard printBoard;
    private final CompareBoard compareBoard;

    Human(int length, char[] letters, char[][] board, PrintBoard printBoard, CompareBoard compareBoard) {
        this.length = length;
        this.letters = letters;
        this.board = board;
        this.printBoard = printBoard;
        this.compareBoard = compareBoard;
    }

    void step(int boardX, int boardY, char account) {
        while (board[boardX][boardY] != letters[2]) {
            System.out.println(boardX + ", " + boardY + " = " + "'" + board[boardX][boardY] + "'.");
            boardX = compareBoard.compareLimit(0, length);
            boardY = compareBoard.compareLimit(0, length);
        }
        printBoard.setProgress(boardX, boardY);
        board[boardX][boardY] = account;
    }
}