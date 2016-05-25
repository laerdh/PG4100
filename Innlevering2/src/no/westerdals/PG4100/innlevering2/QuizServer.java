package no.westerdals.PG4100.innlevering2;

import no.westerdals.PG4100.innlevering2.db.DBHandler;
import no.westerdals.PG4100.innlevering2.model.Book;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class QuizServer {
    private static final int SERVER_PORT = 8001;
    private List<Book> quizData;
    private ExecutorService executorService = Executors.newCachedThreadPool();
    private int clients;
    private boolean started;

    public QuizServer(List<Book> quizData) {
        setQuizData(quizData);
    }

    public QuizServer() {
        this(null);
        setQuizData(getDataFromDB());
    }

    public void setQuizData(List<Book> quizData) {
        this.quizData = quizData;
    }

    public void start() {
        try (ServerSocket server = new ServerSocket(SERVER_PORT)){
            displayMessage("> SERVER STARTED");
            while (server.isBound()) {
                Socket client = server.accept();
                clients++;
                executorService.execute(new ClientHandler(client, quizData));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            stop();
        }
    }

    public boolean isStarted() {
        return started;
    }

    private List<Book> getDataFromDB() {
        try (DBHandler dbh = new DBHandler()){
            return dbh.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void stop() {
        try {
            executorService.shutdownNow();
            displayMessage("> SERVER STOPPED");
            Thread.sleep(2000);
            System.exit(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int getClients() {
        return clients;
    }

    public int quizDataSize() {
        return quizData.size();
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }

    public static void main(String[] args) {
        new QuizServer().start();
    }
}
