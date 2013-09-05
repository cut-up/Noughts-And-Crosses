import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Scanner;

class Client {
    private final Scanner scanner;

    Client() {
        scanner = new Scanner(System.in);
    }

    void activate() {
        Board board;
        System.out.println("Server address:");
        String address = scanner.next();
        BufferedInputStream bIS;
        ObjectInputStream oIS = null;
        BufferedOutputStream bOS;
        ObjectOutputStream oOS = null;
        try {
            Socket socket = new Socket(address, 65535);
            System.out.println("Server is connected!");
            while (true) {
                bIS = new BufferedInputStream(socket.getInputStream());
                oIS = new ObjectInputStream(bIS);
                board = (Board) oIS.readObject();
                if (board.getAct()) {
                    board.step(3);
                } else {
                    board.printWins(0);
                    break;
                }
                bOS = new BufferedOutputStream(socket.getOutputStream());
                oOS = new ObjectOutputStream(bOS);
                oOS.writeObject(board);
                oOS.flush();
                if (board.getAct()) {
                    System.out.println("Wait...");
                } else {
                    break;
                }
            }
        } catch (EOFException exception) {
            System.out.println("Server is unavailable.");
        } catch (ConnectException exception) {
            System.out.println("Connection refused.");
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            try {
                if (oIS != null && oOS != null) {
                    oIS.close();
                    oOS.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }

    static {
        System.out.println("Noughts and Crosses!");
    }
}