public class NoughtsAndCrosses {
    public static void main(String args[]) {
        switch (new CompareBoard().compareSquares(1, 3)) {
            case 1:
                Board board = new Board();
                board.step(0);
                break;
            case 2:
                new Client().activate();
                break;
            case 3:
                new Server().activate();
                break;
        }
    }
}