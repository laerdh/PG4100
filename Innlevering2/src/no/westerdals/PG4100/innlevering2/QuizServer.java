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
            showStatus(Status.SERVER_STARTED);
            while (server.isBound()) {
                Socket client = server.accept();
                clients++;
                showStatus(Status.CLIENT_CONNECTED);
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
            showStatus(Status.SERVER_STOPPED);
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

    public void showStatus(Status status) {
        switch (status) {
            case SERVER_STARTED:
                started = true;
                System.out.println("-> SERVER STARTED");
                break;
            case SERVER_STOPPED:
                System.out.println("-> SERVER STOPPED");
                started = false;
                break;
            case CLIENT_CONNECTED:
                System.out.println("-> CLIENT CONNECTED");
                break;
            case CLIENT_DISCONNECTED:
                System.out.println("-> CLIENT DISCONNECTED");
                break;
        }
    }

    enum Status {
        SERVER_STARTED, SERVER_STOPPED, CLIENT_CONNECTED, CLIENT_DISCONNECTED
    }

    public static void main(String[] args) {
        new QuizServer().start();
    }
}
