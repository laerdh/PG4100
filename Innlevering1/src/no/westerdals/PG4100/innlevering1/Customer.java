package no.westerdals.PG4100.innlevering1;


import java.util.concurrent.CountDownLatch;

public class Customer implements Runnable {
    private static final int DEFAULT_SLEEP_TIME_HIRE = 10000;
    private static final int DEFAULT_SLEEPTIME_RETURN = 3000;

    private CarRentalService carRental;
    private CountDownLatch countDownLatch;
    private String name;
    private int sleepTimeHire;
    private int sleepTimeReturn;
    private boolean stop = false;

    public Customer(String name, CountDownLatch countDownLatch) {
        setName(name);
        setSleepTimeHire(DEFAULT_SLEEP_TIME_HIRE);
        setSleepTimeReturn(DEFAULT_SLEEPTIME_RETURN);
        this.countDownLatch = countDownLatch;
    }

    public void setCarRental(CarRentalService carRental) {
        this.carRental = carRental;
    }

    public void setSleepTimeHire(int sleepTimeHire) { this.sleepTimeHire = sleepTimeHire; }

    public void setSleepTimeReturn(int sleepTimeReturn) { this.sleepTimeReturn = sleepTimeReturn; }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void stop() { stop = true; }

    public boolean isRunning() { return !stop; }

    @Override
    public void run() {
        try {
            while (!stop) {
                countDownLatch.await();
                // Default: 1-10 seconds before customer rent a car.
                Thread.sleep((int) (Math.random() * (sleepTimeHire - 1000) + 1000));

                carRental.rentACar(this);
                // Default: 1-3 seconds before customer returns car.
                Thread.sleep((int) (Math.random() * (sleepTimeReturn - 1000) + 1000));
                carRental.returnACar(this);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
