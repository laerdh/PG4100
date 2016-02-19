package no.westerdals.PG4100.innlevering2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class QuizClient {
    private final String SERVER_ADDRESS = "localhost";
    private final int SERVER_PORT = 8001;
    private DataOutputStream output;
    private DataInputStream input;
    private boolean connected;

    public QuizClient() {
        connect();
    }

    public void connect() {
        try (Socket connection = new Socket(SERVER_ADDRESS, SERVER_PORT);
             Scanner in = new Scanner(System.in)){
            connected = true;
            initiateContact(connection);
            String message;
            do {
                displayMessage();
                message = in.nextLine();
                send(message);
            } while(!message.equals("quit"));
        } catch (IOException | NoSuchElementException e) {
            System.out.println("Disconnected");
        } finally {
            connected = false;
            disconnect();
        }
    }

    private void initiateContact(Socket connection) throws IOException {
        output = new DataOutputStream(connection.getOutputStream());
        input = new DataInputStream(connection.getInputStream());
    }

    public void send(String message) throws IOException {
        output.writeUTF(message);
        output.flush();
    }

    private void displayMessage() throws IOException {
        System.out.println(input.readUTF());
    }

    public boolean isConnected() {
        return connected;
    }

    public void disconnect() {
        try {
            if (input != null)
                input.close();
            if (output != null)
                output.close();
            System.out.println("Closing...");
            Thread.sleep(2000);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    public static void main(String[] args) {
        new QuizClient().connect();
    }
}
