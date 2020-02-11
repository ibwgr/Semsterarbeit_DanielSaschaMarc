package Application;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * This is the chat client program.
 * Type 'bye' to terminte the program.
 *
 * @author www.codejava.net
 */
public class ChatClient {
    private String hostname;
    private int port;
    private String userName;
    private SimpleStringProperty ready = new SimpleStringProperty("not Ready");
    private ConnectFour game;
    private WriteTask writer;
    private ReadTask reader;
    private SimpleIntegerProperty lastCol = new SimpleIntegerProperty(-1);
    private GUI gui;

    public SimpleIntegerProperty lastColProperty() {
        return lastCol;
    }

    public void setLastCol(int lastCol) {
        this.lastCol.set(lastCol);
    }

    public ReadTask getReader() {
        return reader;
    }

    public WriteTask getWriter() {
        return writer;
    }

    public void setReady(String ready) {
        this.ready.set(ready);
    }

    public String getReady() {
        return ready.getValue();
    }

    public SimpleStringProperty readyProperty() {
        return ready;
    }

    public ChatClient(String hostname, int port, GUI gui) {
        this.hostname = hostname;
        this.port = port;
        this.gui = gui;
    }

    public void execute() {
        try {
            Socket socket = new Socket(hostname, port);
            System.out.println("Connected to the chat server");
            ChatClient client = this;

            Service readerS = new Service() {
                @Override
                protected Task createTask() {
                    reader = new ReadTask(socket, client, gui);
                    return reader;
                }
            };
            readerS.start();

            Service writerS = new Service() {
                @Override
                protected Task createTask() {
                    writer = new WriteTask(socket, client);
                    return writer;
                }
            };
            writerS.start();


        } catch (UnknownHostException ex) {
            System.out.println("Server not found: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("I/O Error: " + ex.getMessage());
        }

    }

    void setUserName(String userName) {
        this.userName = userName;
    }

    String getUserName() {
        return this.userName;
    }

}