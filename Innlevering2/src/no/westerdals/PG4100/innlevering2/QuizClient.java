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


    public void connect() {
        try (Socket connection = new Socket(SERVER_ADDRESS, SERVER_PORT);
             Scanner in = new Scanner(System.in)){
            connected = true;

            // Setup and run game
            initiateContact(connection);
            runGame(in);

        } catch (IOException | NoSuchElementException e) {
            System.out.println("Disconnected");
        }
    }

    private void runGame(Scanner in) throws IOException, NoSuchElementException {
        // Gameloop
        String message;
        do {
            displayMessage();
            message = in.nextLine();
            send(message);
        } while (!shouldQuit(message));

        //Disconnect
        disconnect();
    }

    private boolean shouldQuit(String message) {
        return message.equalsIgnoreCase("n") || message.equalsIgnoreCase("nei")
                || message.equalsIgnoreCase("no") || message.equalsIgnoreCase("q") || message.equalsIgnoreCase("quit");
    }

    private void initiateContact(Socket connection) throws IOException {
        output = new DataOutputStream(connection.getOutputStream());
        input = new DataInputStream(connection.getInputStream());
    }

    public void send(String message) throws IOException {
        output.writeUTF(message);
        output.flush();
    }

    public String read() throws IOException {
        return input.readUTF();
    }

    private void displayMessage() throws IOException {
        String message = read();
        System.out.println(message);
    }

    public boolean isConnected() {
        return connected;
    }

    public void disconnect() throws IOException {
        displayMessage();
        input.close();
        output.close();
        connected = false;
        System.out.println("Closing...");
    }

    public static void main(String[] args) {
        new QuizClient().connect();
    }
}
