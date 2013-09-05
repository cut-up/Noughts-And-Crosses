import java.io.Serializable;
import java.util.Random;

class Computer implements Serializable {
    private final int length;
    private final char letters[];
    private final char[][] board;
    private final PrintBoard printBoard;
    private final CompareBoard compareBoard;
    private final Random random;

    Computer(int length, char[] letters, char[][] board, PrintBoard printBoard, CompareBoard compareBoard, Random random) {
        this.length = length;
        this.letters = letters;
        this.board = board;
        this.printBoard = printBoard;
        this.compareBoard = compareBoard;
        this.random = random;
    }

    void step(int robotX, int robotY) {
        int humanX = printBoard.eyeRobot(2, 1);
        int humanY = printBoard.eyeRobot(1, 0);
        if (compareBoard.getDistress(1, 0) != 0 || compareBoard.getDistress(1, 1) != 0 || compareBoard.getDistress(1, 2) != 0 || compareBoard.getDistress(1, 3) != 0) {
            out:
            for (int distressIndex = 0; distressIndex < 4; distressIndex++) {
                switch (compareBoard.getDistress(1, distressIndex)) {
                    case 'H':
                        fallout:
                        for (int boardY = 0; boardY < length; boardY++) {
                            if (board[humanX][boardY] == letters[2]) {
                                for (int secondBoardY = 0, spaceCounter = 0; secondBoardY < length; secondBoardY++) {
                                    if (board[humanX][secondBoardY] == letters[2]) {
                                        spaceCounter++;
                                    }
                                    if (board[humanX][secondBoardY] == letters[1] || spaceCounter > 1) {
                                        break fallout;
                                    }
                                }
                                robotX = humanX;
                                robotY = boardY;
                                break out;
                            }
                        }
                    case 'V':
                        fallout:
                        for (int boardX = 0; boardX < length; boardX++) {
                            if (board[boardX][humanY] == letters[2]) {
                                for (int secondBoardX = 0, spaceCounter = 0; secondBoardX < length; secondBoardX++) {
                                    if (board[secondBoardX][humanY] == letters[2]) {
                                        spaceCounter++;
                                    }
                                    if (board[secondBoardX][humanY] == letters[1] || spaceCounter > 1) {
                                        break fallout;
                                    }
                                }
                                robotX = boardX;
                                robotY = humanY;
                                break out;
                            }
                        }
                    case 'L':
                        fallout:
                        for (int boardX = 0; boardX < length; boardX++) {
                            if (board[boardX][boardX] == letters[2]) {
                                for (int secondBoardX = 0, spaceCounter = 0; secondBoardX < length; secondBoardX++) {
                                    if (board[secondBoardX][secondBoardX] == letters[2]) {
                                        spaceCounter++;
                                    }
                                    if (board[secondBoardX][secondBoardX] == letters[1] || spaceCounter > 1) {
                                        break fallout;
                                    }
                                }
                                robotX = boardX;
                                robotY = boardX;
                                break out;
                            }
                        }
                    case 'R':
                        fallout:
                        for (int boardY = 0, boardX = length - 1; boardY < length; boardY++, boardX--) {
                            if (board[boardX][boardY] == letters[2]) {
                                for (int secondBoardY = 0, spaceCounter = 0, secondBoardX = length - 1; secondBoardY < length; secondBoardY++, secondBoardX--) {
                                    if (board[secondBoardX][secondBoardY] == letters[2]) {
                                        spaceCounter++;
                                    }
                                    if (board[secondBoardX][secondBoardY] == letters[1] || spaceCounter > 1) {
                                        break fallout;
                                    }
                                }
                                robotX = boardX;
                                robotY = boardY;
                                break out;
                            }
                        }
                }
            }
        } else {
            while (board[robotX][robotY] != letters[2]) {
                robotX = random.nextInt(length);
                robotY = random.nextInt(length);
            }
        }
        printBoard.setProgress(robotX, robotY);
        board[robotX][robotY] = letters[1];
        printBoard.moveRobot(robotX, robotY);
    }
}