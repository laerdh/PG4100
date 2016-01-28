package no.westerdals.PG4100.innlevering1;

import java.util.concurrent.CountDownLatch;


public class Customer implements Runnable {
    private static final int DEFAULT_SLEEP_TIME_HIRE = 10000;
    private static final int DEFAULT_SLEEPTIME_RETURN = 3000;

    private ICarRental carRental;
    private CountDownLatch latch;
    private String name;

    public Customer(String name) {
        setName(name);
    }

    public void setCountDownLatch(CountDownLatch latch) {
        this.latch = latch;
    }

    public void setCarRental(ICarRental carRental) {
        this.carRental = carRental;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public void run() {
        try {
            while (true) {
                // Counts down before starting first threadpool.
                latch.countDown();

                // Default: 1-10 seconds before customer rent a car.
                Thread.sleep((int) (Math.random() * (DEFAULT_SLEEP_TIME_HIRE - 1000) + 1000));

                if (carRental.rentACar(this)) {
                    // Default: 1-3 seconds before customer returns car.
                    Thread.sleep((int) (Math.random() * (DEFAULT_SLEEPTIME_RETURN - 1000) + 1000));
                    carRental.returnACar(this);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
