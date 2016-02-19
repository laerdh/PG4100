package no.westerdals.PG4100.innlevering2;

import no.westerdals.PG4100.innlevering2.model.Book;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientHandler implements Runnable {
    private Socket client;
    private DataOutputStream output;
    private DataInputStream input;
    private List<Book> quizData;
    private Book current;
    private boolean clientAnswer = false;

    public ClientHandler(Socket client, List<Book> quizData) {
        setClientSocket(client);
        setQuizDatabase(quizData);
    }

    public void setClientSocket(Socket client) {
        this.client = client;
    }

    public void setQuizDatabase(List<Book> quizData) {
        this.quizData = quizData;
    }

    public Book getCurrentQuestion() {
        return current;
    }

    public void setCurrentQuestion(Book book) {
        current = book;
    }

    @Override
    public void run() {
        try {
            initiateContact();
            String message = read();
            if (!message.equalsIgnoreCase("nei")) {
                do {
                    if (clientAnswer) {
                        String response = checkAnswer(message) ? "Riktig!" : "Feil!";
                        send(response + "\nVil du fortsette? (j/n)");
                        clientAnswer = false;
                    } else {
                        send(getRandomQuestion());
                    }
                    message = read();
                    if (message.equalsIgnoreCase("ja")) {
                        continue;
                    }
                    clientAnswer = true;
                } while (!message.equalsIgnoreCase("nei"));
                send("Takk for at du deltok!");
            }
        } catch (IOException e) {
            System.out.println("-> CLIENT DISCONNECTED");
        } finally {
            disconnect();
        }
    }

    public void initiateContact() throws IOException {
        output = new DataOutputStream(client.getOutputStream());
        output.flush();
        input = new DataInputStream(client.getInputStream());
        String message = "Vil du delta i forfatter-QUIZ? (ja/nei)";
        send(message);
    }

    public void send(String message) throws IOException {
        output.writeUTF(message);
        output.flush();
    }

    public String read() throws IOException {
        return input.readUTF();
    }

    public void disconnect() {
        try {
            client.close();
            input.close();
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getRandomQuestion() {
        int index = (int) (Math.random() * quizData.size());
        if (getCurrentQuestion() != null) {
            do {
                index = (int) (Math.random() * quizData.size());
            } while (getCurrentQuestion().equals(quizData.get(index)));
        }
        setCurrentQuestion(quizData.get(index));
        return "Hvem har skrevet " + current.getTitle() + "?";
    }

    public boolean checkAnswer(String answer) {
        String[] data = getCurrentQuestion().getAuthor().toLowerCase().split(", ");
        String lastname = data[0].trim();
        String firstname = data[1].trim();

        String regex = "^\\s*(("+ lastname + "\\s+" + firstname + ")|(" + lastname + ",\\s+" + firstname + ")|("
                + firstname + "\\s+"  + lastname + ")|(" + firstname + ",\\s+" + lastname + "))$";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(answer);

        return matcher.matches();
    }
}
