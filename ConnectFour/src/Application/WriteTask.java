package Application;


import javafx.concurrent.Task;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * This thread is responsible for reading user's input and send it
 * to the server.
 * It runs in an infinite loop until the user types 'bye' to quit.
 *
 * @author www.codejava.net
 */
public class WriteTask extends Task<PrintWriter> {
    private PrintWriter writer;
    private Socket socket;
    private ChatClient client;

    public WriteTask(Socket socket, ChatClient client) {
        this.socket = socket;
        this.client = client;

        try {
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);
        } catch (IOException ex) {
            System.out.println("Error getting output stream: " + ex.getMessage());
            ex.printStackTrace();
        }
    }


    public void sendMessage(int column) {
        writer.println(column);
    }

    @Override
    protected PrintWriter call() throws Exception {
        return writer;
    }
}