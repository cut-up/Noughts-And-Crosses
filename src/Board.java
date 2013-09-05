import java.io.Serializable;
import java.util.Random;
import java.util.Scanner;

class Board implements Serializable {
    private static final Scanner scanner = new Scanner(System.in);
    private final int length;
    private final char[] letters;
    private final char[][] board;
    private final PrintBoard printBoard;
    private final CompareBoard compareBoard;
    private final Random random;
    private final Human human;
    private final Computer robot;

    Board() {
        int minBoard = 3;
        int maxBoard = 10;
        String symbols = letters("X", "O", " ");
        length = new CompareBoard().compareSquares(minBoard, maxBoard);
        letters = new char[]{symbols.charAt(0), symbols.charAt(1), symbols.charAt(2)};
        board = new char[length][length];
        printBoard = new PrintBoard(length, letters);
        compareBoard = new CompareBoard(length, letters, board, printBoard);
        random = new Random();
        human = new Human(length, letters, board, printBoard, compareBoard);
        robot = new Computer(length, letters, board, printBoard, compareBoard, random);
        openBoard();
    }

    void openBoard() {
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[x].length; y++) {
                board[x][y] = letters[2];
            }
        }
    }

    void step(int account) {
        out:
        while (compareBoard.getAct()) {
            switch (account) {
                case 0:
                    caseStep(human, 0, false);
                    account++;
                    break;
                case 1:
                    caseStep(robot, 1, false);
                    account--;
                    break;
                case 2:
                    caseStep(human, 0, true);
                    break out;
                case 3:
                    caseStep(human, 1, true);
                    break out;
            }
        }
    }

    void printWins(int who) {
        printBoard.printWins(board, letters[who] + "s");
    }

    void printBoard() {
        printBoard.printBoard(board);
    }

    boolean getAct() {
        return compareBoard.getAct();
    }

    private void caseStep(Object object, int lettersIndex, boolean against) {
        printBoard.printBoard(board);
        if (object.getClass() == human.getClass()) {
            ((Human) object).step(compareBoard.compareLimit(0, length), compareBoard.compareLimit(0, length), letters[lettersIndex]);
        } else {
            ((Computer) object).step(random.nextInt(length), random.nextInt(length));
        }
        compareBoard.compareBoard();
        if (against && getAct()) {
            printBoard.printBoard(board);
        }
    }

    private String letters(String x, String o, String space) {
        System.out.println("Noughts and Crosses!");
        String input = space;
        while (!input.equals(x) && !input.equals(o)) {
            System.out.println(x + "s or " + o + "s?");
            input = scanner.next();
        }
        if (input.equals(x)) {
            input += o + space;
        } else {
            input += x + space;
        }
        return input;
    }
}