package com.westerdals.pg4100.test;

import com.westerdals.pg4100.servlets.PrimeCheckServlet;
import junit.framework.TestCase;

public class PrimeCheckServletTest extends TestCase {

    public void testResultNotAPrimeNumber() throws Exception {
        // ARRANGE
        String num = "100";
        PrimeCheckServlet p = new PrimeCheckServlet();

        String expected = num + " is NOT a prime!";

        // ACT
        String actual = p.getResult(num);

        // ASSERT
        assertEquals("Number should not be a prime number: ", expected, actual);
    }

    public void testNumberIsPrimeNumber() throws Exception {
        // ARRANGE
        String num = "5";
        PrimeCheckServlet p = new PrimeCheckServlet();

        String expected = num + " is a prime!";

        // ACT
        String actual = p.getResult(num);

        // ASSERT
        assertEquals("Number should be a prime number: ", expected, actual);
    }

    public void testNotLegalNumber() throws Exception {
        // ARRANGE
        String num = "48j4h3hbbb";
        PrimeCheckServlet p = new PrimeCheckServlet();
        String expected = "Please provide a valid input. Example: 1337";

        // ACT
        String actual = p.getResult(num);

        // ASSERT
        assertEquals("Number should not be legal: ", expected, actual);
    }

    public void testNegativeNumber() throws Exception {
        // ARRANGE
        String num = "-291951";
        PrimeCheckServlet p = new PrimeCheckServlet();
        String expected = num + " is NOT a prime!";

        // ACT
        String actual = p.getResult(num);

        // ASSERT
        assertEquals("Number should not be legal: ", expected, actual);
    }

    public void testNotANumber() throws Exception {
        // ARRANGE
        String num = "abcdefghijklmnop";
        PrimeCheckServlet p = new PrimeCheckServlet();
        String expected = "Please provide a valid input. Example: 1337";

        // ACT
        String actual = p.getResult(num);

        // ASSERT
        assertEquals("Number should not be legal: ", expected, actual);
    }

    public void testEmptyInput() throws Exception {
        // ARRANGE
        String num = "";
        PrimeCheckServlet p = new PrimeCheckServlet();
        String expected = "Please provide a valid input. Example: 1337";

        // ACT
        String actual = p.getResult(num);

        // ASSERT
        assertEquals("Number should not be legal: ", expected, actual);
    }

    public void testNumberExceedIntMaxValue() throws Exception {
        // ARRANGE
        String num = "192858472839584839";
        PrimeCheckServlet p = new PrimeCheckServlet();
        String expected = "Please provide a valid input. Example: 1337";

        // ACT
        String actual = p.getResult(num);

        // ASSERT
        assertEquals("Number should not be legal: ", expected, actual);
    }
}