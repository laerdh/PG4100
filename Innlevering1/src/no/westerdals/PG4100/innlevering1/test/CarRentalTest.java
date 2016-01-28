package no.westerdals.PG4100.innlevering1.test;

import no.westerdals.PG4100.innlevering1.Car;
import no.westerdals.PG4100.innlevering1.CarRental;
import no.westerdals.PG4100.innlevering1.Customer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class CarRentalTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testFindAvailableCar() {
        CarRental carRental = new CarRental();
        Car car = carRental.findAvailableCar();

        assertNotNull(car);
    }

    @Test
    public void testCustomerCanRentACar() throws Exception {
        // ARRANGE
        CarRental carRental = new CarRental();
        Customer customer = new Customer("Tester");

        // ACT
        boolean car = carRental.rentACar(customer);

        // ASSERT
        assertTrue(car);
    }

    @Test
    public void testRentCarButNoCarAvailable() throws Exception {
        // ARRANGE
        CarRental carRental = new CarRental(1);
        Customer customer1 = new Customer("Tester 1");
        Customer customer2 = new Customer("Tester 2");

        // ACT
        carRental.rentACar(customer1);

        // ASSERT
        assertFalse(carRental.rentACar(customer2));
    }

    @Test
    public void testCarsAvailableIncreasedByOneWhenCustomerReturnsACar() throws Exception {
        // ARRANGE
        CarRental carRental = new CarRental(2);
        Customer customer = new Customer("Tester");

        // ACT
        carRental.rentACar(customer);

        // ASSERT
        assertEquals(1, carRental.carsAvailable());
        // This act should increment cars available by one
        carRental.returnACar(customer);
        assertEquals(2, carRental.carsAvailable());
    }
}