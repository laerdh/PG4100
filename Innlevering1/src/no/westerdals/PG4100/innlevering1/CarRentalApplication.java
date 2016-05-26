package no.westerdals.PG4100.innlevering1;

import javax.swing.*;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class CarRentalApplication {
    private static final int CUSTOMERS = 10;
    private static CountDownLatch countDownLatch = new CountDownLatch(CUSTOMERS / 2);
    private static CarRentalService carRental = new CarRental();
    private static ArrayList<String> names = new ArrayList<>();

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(CUSTOMERS);
        for (int i = 0; i < CUSTOMERS; i++) {
            String name = JOptionPane.showInputDialog("Skriv inn kundenavn", null);
            names.add(name);

            countDownLatch.countDown();
            Customer customer = new Customer(names.get(i), countDownLatch);
            customer.setCarRental(carRental);
            executor.execute(customer);
        }

        // Shutdown when finished
        executor.shutdown();
    }
}
