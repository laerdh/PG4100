package no.westerdals.PG4100.innlevering1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


public class CarRental implements CarRentalService {
    private static final String REGISTRATION_PREFIX = "RF";
    private static final int DEFAULT_CAR_POOL = 5;
    private int numberOfCars;

    private ReentrantLock lock = new ReentrantLock();
    private Condition newCondition = lock.newCondition();

    private ArrayList<Car> cars = new ArrayList<>();
    private HashMap<Customer, Car> reservations = new HashMap<>();

    public CarRental(int numberOfCars) {
        setNumberOfCars(numberOfCars);
        init();
    }

    public CarRental() {
        this(DEFAULT_CAR_POOL);
    }

    private void setNumberOfCars(int numberOfCars) {
        this.numberOfCars = numberOfCars;
    }

    private void init() {
        for (int i = 1; i <= numberOfCars; i++) {
            String reg = REGISTRATION_PREFIX;
            for (int j = 1; j <= 5; j++) {
                reg += i;
            }
            cars.add(new Car(reg));
        }
        printReservationStatus();
    }

    @Override
    public boolean rentACar(Customer customer) {
        lock.lock();
        try {
            // Make thread wait for available car
            while (carsAvailable() == 0) {
                printNotAvailable(customer.getName());
                newCondition.await();
            }

            Car car = findAvailableCar();
            reservations.put(customer, car);
            printReservation(customer.getName(), car.getRegNumber());
            return true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            printReservationStatus();
            lock.unlock();
        }
        return false;
    }

    @Override
    public void returnACar(Customer customer) {
        lock.lock();
        try {
            Car car = reservations.get(customer);
            car.setAvailable(true);
            reservations.remove(customer);
            printCarReturned(customer.getName(), car.getRegNumber());

            // Signal waiting thread
            newCondition.signalAll();
        } finally {
            printReservationStatus();
            lock.unlock();
        }
    }

    public Car findAvailableCar() {
        Optional<Car> car = cars.stream()
                .filter(Car::isAvailable)
                .findFirst();
        Car c = car.orElse(null);
        if (c != null) c.setAvailable(false);
        return c;
    }

    public int carsAvailable() {
        int count = 0;
        for (Car car : cars) {
            if (car.isAvailable()) {
                count++;
            }
        }
        return count;
    }

    private Customer getCustomerByCar(Car car) {
        Optional<Customer> customer = reservations.keySet()
                .stream()
                .filter(c -> reservations.get(c).equals(car))
                .findFirst();
        return customer.orElse(null);
    }

    private void printReservation(String name, String car) {
        System.out.println(name + " har leid " + car + ".");
    }

    private void printCarReturned(String name, String car) {
        System.out.println(name + " har levert inn " + car + ".");
    }

    private void printNotAvailable(String name) {
        System.out.println(name + " forsøkte å leie en bil, men alle bilene var opptatt.");
    }

    private void printReservationStatus() {
        System.out.printf("%s\n", "*********** Status for utleiebilene ***********");
        for (Car car : cars) {
            String status = !car.isAvailable() ? "utleid til " + getCustomerByCar(car).getName() : "ledig";
            System.out.print(car.getRegNumber() + " - " + status + ", ");
        }
        System.out.printf("\n%s\n\n", "*********** Status slutt ***********");
    }
}
