package test;

import no.westerdals.PG4100.innlevering2.QuizClient;
import no.westerdals.PG4100.innlevering2.QuizServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.stubbing.Answer;

import javax.net.SocketFactory;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.Assert.*;

public class QuizServerTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testIfServerGotQuizData() throws Exception {
        // ARRANGE & ACT
        QuizServer server = new QuizServer(TestDataProvider.getTestData());
        int expected = TestDataProvider.getTestDataSize();

        // ASSERT
        assertEquals(expected, server.quizDataSize());
    }

    @Test
    public void testStartAndStopServer() throws Exception {
        // ARRANGE & ACT
        QuizServer server = new QuizServer(TestDataProvider.getTestData());

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                server.start();
                // ASSERT
                assertTrue(server.isStarted());
            }
        });
        t1.start();

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                server.stop();
                //ASSERT
                assertFalse(server.isStarted());
            }
        });
        t2.start();
    }

    @Test
    public void testServerAcceptIncomingClientRequest() throws Exception {
        // ARRANGE & ACT
        QuizServer server = new QuizServer(TestDataProvider.getTestData());
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                server.start();
            }
        });
        t1.start();

        Socket client = new Socket("localhost", 8001);

        // ASSERT
        assertTrue(client.isBound());
        Thread.sleep(500);
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                server.stop();
            }
        });
        t2.start();
    }
}