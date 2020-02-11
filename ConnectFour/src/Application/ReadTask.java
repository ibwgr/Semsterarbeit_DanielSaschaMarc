package Application;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.concurrent.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * This thread is responsible for reading server's input and printing it
 * to the console.
 * It runs in an infinite loop until the client disconnects from the server.
 *
 * @author www.codejava.net
 */
public class ReadTask extends Task<BufferedReader> {
    private BufferedReader reader;
    private Socket socket;
    private ChatClient client;
    private GUI gui;

    public ReadTask(Socket socket, ChatClient client, GUI gui) {
        this.socket = socket;
        this.client = client;
        this.gui = gui;

        try {
            InputStream input = socket.getInputStream();
            reader = new BufferedReader(new InputStreamReader(input));
        } catch (IOException ex) {
            System.out.println("Error getting input stream: " + ex.getMessage());
            ex.printStackTrace();
        }
    }


    @Override
    protected BufferedReader call() throws Exception {
        //get Username from Server
        try {
            client.setUserName(reader.readLine());
            System.out.println("username " + client.getUserName() + " set");
            if (reader.readLine().contains("ready")) {
                client.setReady("ready");
                System.out.println("ready set");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // get Messages from Server

        while (true) {
            try {
                int response = Integer.parseInt(reader.readLine());
                gui.setLastCol(response);
                gui.setLastCol(-1);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}

