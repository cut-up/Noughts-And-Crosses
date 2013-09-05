import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

class Server {
    void activate() {
        Board board = new Board();
        board.printBoard();
        BufferedOutputStream bOS;
        ObjectOutputStream oOS = null;
        BufferedInputStream bIS;
        ObjectInputStream oIS = null;
        try {
            ServerSocket serverSocket = new ServerSocket(65535);
            System.out.println(InetAddress.getLocalHost() + " ready.");
            Socket socket = serverSocket.accept();
            System.out.println("Client is connected!");
            while (true) {
                bOS = new BufferedOutputStream(socket.getOutputStream());
                oOS = new ObjectOutputStream(bOS);
                oOS.writeObject(board);
                oOS.flush();
                if (board.getAct()) {
                    System.out.println("Wait...");
                } else {
                    break;
                }
                bIS = new BufferedInputStream(socket.getInputStream());
                oIS = new ObjectInputStream(bIS);
                board = (Board) oIS.readObject();
                if (board.getAct()) {
                    board.step(2);
                } else {
                    board.printWins(1);
                    break;
                }
            }
        } catch (EOFException exception) {
            System.out.println("Client is unavailable.");
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            try {
                if (oOS != null && oIS != null) {
                    oOS.close();
                    oIS.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }
}