package test;

import no.westerdals.PG4100.innlevering2.QuizClient;
import no.westerdals.PG4100.innlevering2.QuizServer;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class QuizClientTest {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testClientCanConnect() throws Exception {
        // ARRANGE
        QuizServer server = new QuizServer(TestDataProvider.getTestData());
        QuizClient client = new QuizClient();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                server.start();
            }
        });

        // ACT
        t1.start();
        Thread.sleep(500);
        client.connect();

        // ASSERT
        assertTrue(client.isConnected());
        server.stop();
    }
}