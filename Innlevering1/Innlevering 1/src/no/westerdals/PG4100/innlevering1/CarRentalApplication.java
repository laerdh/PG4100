package no.westerdals.PG4100.innlevering1;

import javax.swing.*;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class CarRentalApplication {
    private static final int CUSTOMERS = 10;

    private static CarRentalService carRental = new CarRental();
    private static ArrayList<String> names = new ArrayList<>();

    public static void main(String[] args) {
        for (int i = 1; i <= CUSTOMERS; i++) {
            String name = JOptionPane.showInputDialog("Skriv inn kundenavn", null);
            names.add(name);

            if (i == CUSTOMERS / 2)
                startThreadPool(0, (CUSTOMERS / 2));
        }
        // Start the rest of customer threads
        startThreadPool((CUSTOMERS / 2), CUSTOMERS);
    }

    public static void startThreadPool(int start, int end) {
        ExecutorService executor = Executors.newFixedThreadPool(end - start);
        for (int i = start; i < end; i++) {
            Customer customer = new Customer(names.get(i));
            customer.setCarRental(carRental);
            executor.execute(customer);
        }
        executor.shutdown();
    }
}
