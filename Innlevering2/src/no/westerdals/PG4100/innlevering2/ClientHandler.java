package no.westerdals.PG4100.innlevering2;

import no.westerdals.PG4100.innlevering2.model.Book;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientHandler implements Runnable {
    private Socket client;
    private DataOutputStream output;
    private DataInputStream input;
    private List<Book> quizData;
    private Set<Book> askedQuestions = new HashSet<>();
    private Book currentQuestion;

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
        return currentQuestion;
    }

    public void setCurrentQuestion(Book book) {
        currentQuestion = book;
    }

    @Override
    public void run() {
        try {
            initiateContact();
            disconnect();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void runGame() throws IOException {
        String message;
        String response;
        do {
            String question = getQuestion();
            if (question == null)
                initiateGame("Alle spørsmålene har blitt stilt. Vil du spille på nytt? (ja/nei)");
            send(question);
            message = read();

            response = checkAnswer(message) ? "Riktig!" : "Feil, det er " + currentQuestion.getAuthor();
            response += "\nVil du fortsette? (j/n)";
            send(response);

            message = read();
        } while (shouldContinue(message));

        // End game
        send("Takk for at du deltok!");
    }

    private boolean shouldContinue(String message) {
        return message.equalsIgnoreCase("j") || message.equalsIgnoreCase("ja")
                || message.equalsIgnoreCase("y") || message.equalsIgnoreCase("yes");
    }

    public void initiateContact() throws IOException {
        System.out.println("> CLIENT CONNECTED: " + client.getInetAddress());
        output = new DataOutputStream(client.getOutputStream());
        output.flush();
        input = new DataInputStream(client.getInputStream());

        // Confirm start
        initiateGame("Vil du delta i forfatter-QUIZ? (ja/nei)");
    }

    private void initiateGame(String message) throws IOException {
        send(message);
        String answer = read();

        if (shouldContinue(answer)) {
            askedQuestions.clear();
            runGame();
        } else {
            send("Takk for at du deltok!");
        }
    }

    public void send(String message) throws IOException {
        output.writeUTF(message);
        output.flush();
    }

    public String read() throws IOException {
        return input.readUTF();
    }

    public void disconnect() throws IOException {
        System.out.println("> CLIENT DISCONNECTED: " + client.getInetAddress());
        output.close();
        input.close();
        client.close();
    }

    public String getQuestion() throws IOException {
        Optional<Book> books = quizData.stream()
                .filter(t -> !askedQuestions.contains(t))
                .findFirst();
        Book b = books.orElse(null);
        setCurrentQuestion(b);
        askedQuestions.add(b);

        return getCurrentQuestion() == null ? null : "Hvem har skrevet "
                + getCurrentQuestion().getTitle() + "?";
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
