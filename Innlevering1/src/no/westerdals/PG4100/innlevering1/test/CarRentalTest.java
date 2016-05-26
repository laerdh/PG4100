package no.westerdals.PG4100.innlevering1.test;

import no.westerdals.PG4100.innlevering1.Car;
import no.westerdals.PG4100.innlevering1.CarRental;
import no.westerdals.PG4100.innlevering1.Customer;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


public class CarRentalTest {

    @Test
    public void testFindAvailableCar() {
        CarRental carRental = new CarRental();
        Car car = carRental.findAvailableCar();

        assertNotNull("A car should have been returned", car);
    }

    @Test
    public void testCustomerCanRentACar() throws Exception {
        // ARRANGE
        CarRental carRental = new CarRental();
        CarRental carRentalSpy = spy(carRental);
        Customer customer = new Customer("Tester", new CountDownLatch(0));

        // ACT
        carRentalSpy.rentACar(customer);

        // ASSERT & VERIFY
        verify(carRentalSpy).rentACar(customer);
    }

    @Test
    public void testRentCarButNoCarAvailable() throws Exception {
        // ARRANGE
        CarRental carRental = mock(CarRental.class);
        when(carRental.carsAvailable()).thenReturn(0);

        Customer customer = new Customer("Tester", null);

        // ACT
        boolean actual = carRental.rentACar(customer);

        // ASSERT
        assertFalse(actual);
    }

    @Test
    public void testCarsAvailable() throws Exception {
        // ARRANGE
        CarRental carRental = new CarRental(5);

        assertEquals("Number of cars available don't match", 5, carRental.carsAvailable());
    }

    @Test
    public void testCarsAvailableIncreasedByOneWhenCustomerReturnsACar() throws Exception {
        // ARRANGE
        CarRental carRental = new CarRental(2);
        Customer customer = new Customer("Tester", null);

        // ACT
        carRental.rentACar(customer);

        // ASSERT
        assertEquals(1, carRental.carsAvailable());

        // This act should increment cars available by one
        carRental.returnACar(customer);
        assertEquals("Number of available cars didn't increase after customer returned a car", 2, carRental.carsAvailable());
    }

    @Test
    public void testMoreThanFiveCars() throws Exception {
        // ARRANGE
        CarRental carRental = new CarRental(9);

        // ACT
        int numberAvailable = carRental.carsAvailable();

        assertEquals(numberAvailable, 9);
    }


}