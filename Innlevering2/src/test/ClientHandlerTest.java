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
    private ClientHandler ch;

    @Before
    public void setUp() {
        ch = new ClientHandler(null, TestDataProvider.getTestData());
    }

    @Test
    public void testGetRandomQuestion() throws Exception {
        String q1 = ch.getQuestion();

        assertNotNull(q1);
    }

    @Test
    public void testGetRandomQuestionUpdatesCurrentQuestion() throws Exception {
        String actual = ch.getQuestion();
        String expected = "Hvem har skrevet " + ch.getCurrentQuestion().getTitle() + "?";

        assertEquals(expected, actual);
    }

    @Test
    public void testGetRandomQuestionDoesNotReturnSameQuestionTwice() throws Exception {

        for (int i = 0; i < 10; i++) {
            String q1 = ch.getQuestion();
            String q2 = ch.getQuestion();
            assertNotEquals("Same question appeared two times in a row", q1, q2);
        }
    }

    @Test
    public void testCheckAnswer() throws Exception {
        ch.setCurrentQuestion(TestDataProvider.getTestBook());

        String answer1 = "Pierre Lemaitre";
        String answer2 = "Lemaitre Pierre";

        assertTrue(ch.checkAnswer(answer1));
        assertTrue(ch.checkAnswer(answer2));
    }

    @Test
    public void testCheckAnswerUppercase() throws Exception {
        ch.setCurrentQuestion(TestDataProvider.getTestBook());

        String answer1 = "PIERRE LEMAITRE";
        String answer2 = "LEMAITRE PIERRE";

        assertTrue(ch.checkAnswer(answer1));
        assertTrue(ch.checkAnswer(answer2));
    }

    @Test
    public void testCheckAnswerWithCommaBetweenFirstAndLastname() throws Exception {
        ch.setCurrentQuestion(TestDataProvider.getTestBook());

        String answer1 = "LEMAITRE, PIERRE";
        String answer2 = "PIERRE, LEMAITRE";

        assertTrue(ch.checkAnswer(answer1));
        assertTrue(ch.checkAnswer(answer2));
    }
}