package test;

import no.westerdals.PG4100.innlevering2.ClientHandler;
import no.westerdals.PG4100.innlevering2.QuizServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import javax.net.SocketFactory;
import java.net.Socket;

import static org.junit.Assert.*;

public class ClientHandlerTest {

    @Test
    public void testGetRandomQuestion() throws Exception {
        ClientHandler ch = new ClientHandler(null, TestDataProvider.getTestData());
        String q1 = ch.getRandomQuestion();

        assertNotNull(q1);
    }

    @Test
    public void testGetRandomQuestionUpdatesCurrentQuestion() throws Exception {
        ClientHandler ch = new ClientHandler(null, TestDataProvider.getTestData());
        String actual = ch.getRandomQuestion();
        String expected = "Hvem har skrevet " + ch.getCurrentQuestion().getTitle() + "?";

        assertEquals(expected, actual);
    }

    @Test
    public void testGetRandomQuestionDoesNotReturnSameQuestionTwice() throws Exception {
        ClientHandler ch = new ClientHandler(null, TestDataProvider.getTestData());

        for (int i = 0; i < 10; i++) {
            String q1 = ch.getRandomQuestion();
            String q2 = ch.getRandomQuestion();
            assertNotEquals("Same question appeared two times in a row", q1, q2);
        }
    }

    @Test
    public void testCheckAnswer() throws Exception {
        ClientHandler ch = new ClientHandler(null, TestDataProvider.getTestData());
        ch.setCurrentQuestion(TestDataProvider.getTestBook());

        String answer1 = "Pierre Lemaitre";
        String answer2 = "Lemaitre Pierre";

        assertTrue(ch.checkAnswer(answer1));
        assertTrue(ch.checkAnswer(answer2));
    }

    @Test
    public void testCheckAnswerUppercase() throws Exception {
        ClientHandler ch = new ClientHandler(null, TestDataProvider.getTestData());
        ch.setCurrentQuestion(TestDataProvider.getTestBook());

        String answer1 = "PIERRE LEMAITRE";
        String answer2 = "LEMAITRE PIERRE";

        assertTrue(ch.checkAnswer(answer1));
        assertTrue(ch.checkAnswer(answer2));
    }

    @Test
    public void testCheckAnswerWithCommaBetweenFirstAndLastname() throws Exception {
        ClientHandler ch = new ClientHandler(null, TestDataProvider.getTestData());
        ch.setCurrentQuestion(TestDataProvider.getTestBook());

        String answer1 = "LEMAITRE, PIERRE";
        String answer2 = "PIERRE, LEMAITRE";

        assertTrue(ch.checkAnswer(answer1));
        assertTrue(ch.checkAnswer(answer2));
    }
}