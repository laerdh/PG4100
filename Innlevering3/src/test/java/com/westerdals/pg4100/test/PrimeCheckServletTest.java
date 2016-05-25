package com.westerdals.pg4100.test;

import com.westerdals.pg4100.servlets.PrimeCheckServlet;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

public class PrimeCheckServletTest extends TestCase {
    // Responses
    private final String NOT_VALID_INPUT = "Please provide a valid input. Example: 1337";
    private final String NOT_A_PRIME = " is NOT a prime!";
    private final String IS_A_PRIME = " is a prime!";

    // Servlet
    private PrimeCheckServlet servlet;

    @Before
    public void setUp() {
        servlet = new PrimeCheckServlet();
    }

    @Test
    public void testResultNotAPrimeNumber() throws Exception {
        // ARRANGE
        String num = "100";

        String expected = num + NOT_A_PRIME;

        // ACT
        String actual = servlet.getResult(num);

        // ASSERT
        assertEquals("Number should not be a prime number: ", expected, actual);
    }

    @Test
    public void testNumberIsPrimeNumber() throws Exception {
        // ARRANGE
        String num = "5";

        String expected = num + IS_A_PRIME;

        // ACT
        String actual = servlet.getResult(num);

        // ASSERT
        assertEquals("Number should be a prime number: ", expected, actual);
    }

    @Test
    public void testNotLegalNumber() throws Exception {
        // ARRANGE
        String num = "48j4h3hbbb";

        // ACT
        String actual = servlet.getResult(num);

        // ASSERT
        assertEquals("Number should not be legal: ", NOT_VALID_INPUT, actual);
    }

    @Test
    public void testNegativeNumber() throws Exception {
        // ARRANGE
        String num = "-291951";
        String expected = num + NOT_A_PRIME;

        // ACT
        String actual = servlet.getResult(num);

        // ASSERT
        assertEquals("Number should not be legal: ", expected, actual);
    }

    @Test
    public void testNotANumber() throws Exception {
        // ARRANGE
        String num = "abcdefghijklmnop";

        // ACT
        String actual = servlet.getResult(num);

        // ASSERT
        assertEquals("Number should not be legal: ", NOT_VALID_INPUT, actual);
    }

    @Test
    public void testEmptyInput() throws Exception {
        // ARRANGE
        String num = "";

        // ACT
        String actual = servlet.getResult(num);

        // ASSERT
        assertEquals("Number should not be legal: ", NOT_VALID_INPUT, actual);
    }

    @Test
    public void testNumberExceedIntMaxValue() throws Exception {
        // ARRANGE
        String num = "192858472839584839";

        // ACT
        String actual = servlet.getResult(num);

        // ASSERT
        assertEquals("Number should not be legal: ", NOT_VALID_INPUT, actual);
    }
}