import java.io.Serializable;

class PrintBoard implements Serializable {
    private final int length;
    private final char letters[];
    private int counter;
    private int progressCounter;
    private int[][] progress;

    PrintBoard(int length, char[] letters) {
        this.length = length;
        this.letters = letters;
        counter = length * length;
        progress = new int[counter][2];
        openProgress();
    }

    void printBoard(char[][] board) {
        System.out.println("[" + counter + "]");
        for (int boardX = 0; boardX < length; boardX++) {
            System.out.print("[" + boardX + "]");
            for (int boardY = 0; boardY < board.length; boardY++) {
                System.out.print("[" + board[boardX][boardY] + "]");
            }
            System.out.println();
        }
        System.out.print("[ ]");
        for (int boardX = 0; boardX < board.length; boardX++) {
            System.out.print("[" + boardX + "]");
        }
        System.out.println();
    }

    void setProgress(int progressX, int progressY) {
        progress[progressCounter][0] = progressX;
        progress[progressCounter][1] = progressY;
        counter--;
        progressCounter++;
    }

    void returnBoard(char[][] board) {
        if (progressCounter >= 2) {
            for (int progressX = 0; progressX < 2; progressX++) {
                counter++;
                progressCounter--;
                board[progress[progressCounter][0]][progress[progressCounter][1]] = letters[2];
                for (int progressY = 0; progressY < 2; progressY++) {
                    progress[progressCounter][progressY] = -1;
                }
            }
            printBoard(board);
        } else {
            System.out.println("No more moves.");
        }
    }

    void moveRobot(int robotX, int robotY) {
        System.out.println(letters[1] + "s: " + robotX + ", " + robotY + ".");
    }

    int getProgressCounter() {
        return progressCounter;
    }

    int eyeRobot(int beginIndex, int endIndex) {
        String see = "";
        for (int[] p : progress) {
            if (p[0] != -1) {
                see += p[0];
                see += p[1];
            }
        }
        return Integer.parseInt(see.substring(see.length() - beginIndex, see.length() - endIndex));
    }

    boolean printWins(char[][] board, String who) {
        printBoard(board);
        System.out.println(who + " wins!");
        return false;
    }

    String except() throws StringIndexOutOfBoundsException {
        String except = "";
        for (int[] p : progress) {
            if (p[0] != -1) {
                except += p[0] + ", ";
                except += p[1] + "; ";
            }
        }
        return except.substring(0, except.length() - 2);
    }

    private void openProgress() {
        for (int x = 0; x < progress.length; x++) {
            for (int y = 0; y < progress[x].length; y++) {
                progress[x][y] = -1;
            }
        }
    }
}