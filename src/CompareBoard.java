import java.io.Serializable;
import java.util.Scanner;

class CompareBoard implements Serializable {
    private static final Scanner scanner = new Scanner(System.in);
    private int length;
    private char letters[];
    private char[][] board;
    private PrintBoard printBoard;
    private char[][] distress;
    private boolean act;
    private int humanCounter;
    private int computerCounter;
    private boolean last;

    CompareBoard() {
        distress = new char[][]{{'H', 'V', 'L', 'R'}, {0, 0, 0, 0}};
        act = true;
    }

    CompareBoard(int length, char[] letters, char[][] board, PrintBoard printBoard) {
        this();
        this.length = length;
        this.letters = letters;
        this.board = board;
        this.printBoard = printBoard;
    }

    void compareBoard() {
        last = false;
        for (int boardX = 0; boardX < length; boardX++) {
            for (int boardY = 0; boardY < length; boardY++) {
                counterLine(board[boardX][boardY], distress[0][0], boardY);
            }
            for (int boardY = 0; boardY < length; boardY++) {
                counterLine(board[boardY][boardX], distress[0][1], boardY);
            }
        }
        for (int boardX = 0; boardX < length; boardX++) {
            counterLine(board[boardX][boardX], distress[0][2], boardX);
        }
        last = true;
        for (int boardX = 0, boardY = length - 1; boardX < length; boardX++, boardY--) {
            counterLine(board[boardY][boardX], distress[0][3], boardX);
        }
    }

    int compareSquares(int min, int max) {
        if (min == 3) {
            System.out.println("How many squares?");
        } else {
            System.out.println("1. Human vs. Robot");
            System.out.println("2. Client side");
            System.out.println("3. Server side");
        }
        return compareLimit(min, max + 1);
    }

    int compareLimit(int min, int max) {
        int scannerParseInt;
        while (true) {
            scannerParseInt = parseScanner();
            if (scannerParseInt >= min && scannerParseInt < max) {
                return scannerParseInt;
            } else if (scannerParseInt == -1 && printBoard != null && printBoard.getProgressCounter() >= 2) {
                System.out.println("Number between " + min + " and " + (max - 1) + ", except: " + printBoard.except() + ".");
                System.out.println("Letter 'B' to return.");
            } else if (scannerParseInt == -1) {
                System.out.println("Number between " + min + " and " + (max - 1) + ".");
            } else {
                System.out.println(scannerParseInt + " > " + (max - 1) + " or < " + min + ".");
            }
        }
    }

    char getDistress(int numberX, int numberY) {
        return distress[numberX][numberY];
    }

    boolean getAct() {
        return act;
    }

    private void counterLine(char square, char where, int number) {
        if (square == letters[0]) {
            humanCounter++;
        } else if (square == letters[1]) {
            computerCounter++;
        }
        if (number == length - 1 && act) {
            compareWins(where);
        }
    }

    private void compareWins(char where) {
        if (humanCounter == length) {
            act = printBoard.printWins(board, letters[0] + "s");
        } else if (computerCounter == length) {
            act = printBoard.printWins(board, letters[1] + "s");
        } else if (printBoard.getProgressCounter() == length * length && last) {
            act = printBoard.printWins(board, "No one");
        } else if (humanCounter == length - 1 && where == distress[0][0]) {
            distress[1][0] = where;
        } else if (humanCounter == length - 1 && where == distress[0][1]) {
            distress[1][1] = where;
        } else if (humanCounter == length - 1 && where == distress[0][2]) {
            distress[1][2] = where;
        } else if (humanCounter == length - 1 && where == distress[0][3]) {
            distress[1][3] = where;
        }
        humanCounter = 0;
        computerCounter = 0;
    }

    private int parseScanner() {
        String input = scanner.next();
        try {
            while (input.equals("B") && printBoard != null) {
                printBoard.returnBoard(board);
                input = scanner.next();
            }
            return Integer.parseInt(input);
        } catch (NumberFormatException exception) {
            return -1;
        }
    }
}