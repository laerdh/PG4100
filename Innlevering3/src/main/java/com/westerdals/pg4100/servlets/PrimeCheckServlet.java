package com.westerdals.pg4100.servlets;

import org.apache.commons.math3.primes.Primes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PrimeCheckServlet extends HttpServlet {
    // Log4j2 Loggers
    static Logger requestLogger = LogManager.getLogger("com.westerdals.pg4100.servlets.RequestLogger");
    static Logger errorLogger = LogManager.getLogger("com.westerdals.pg4100.servlets.ErrorLogger");

    // Responses to client
    private static final String IS_PRIME = " is a prime!";
    private static final String NOT_A_PRIME = " is NOT a prime!";
    private static final String INVALID_INPUT = "Please provide a valid input. Example: 1337";

    /**
     * Takes HTTP POST parameter and returns a new view with result.
     * @param request an HttpServletRequest object that contains the request the client has made of the servlet
     * @param response an HttpServletResponse object that contains the response the servlet sends to the client
     * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest, HttpServletResponse)
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            String number = request.getParameter("number");

            // Log event
            requestLogger.trace("A request was sent with number " + number);

            request.setAttribute("result", getResult(number));

            RequestDispatcher view = request.getRequestDispatcher("index.jsp");
            view.forward(request, response);
        } catch (ServletException | IOException e) {
            // Log Servlet or IO failures
            errorLogger.error("An exception were thrown: " + org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace(e));
        }
    }

    /**
     * Returns true or false depending on whether the number is a prime or not.
     * @param number number to check
     * @return a boolean value, true or false
     * @see org.apache.commons.math3.primes.Primes#isPrime(int)
     */
    private boolean checkPrime(int number) {
        return Primes.isPrime(number);
    }

    /**
     * Returns a textual representation of the result of checkprime() method.
     * @param number number to check
     * @return a textual representation of the result (String)
     */
    public String getResult(String number) {
        String result;
        try {
            int n = Integer.parseInt(number);
            if (checkPrime(n)) {
                result = n + IS_PRIME;
            } else {
                result = n + NOT_A_PRIME;
            }
        } catch (NumberFormatException e) {
            // Log error
            errorLogger.error("Exception thrown: " + org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace(e));
            result = INVALID_INPUT;
        }
        return result;
    }
}
