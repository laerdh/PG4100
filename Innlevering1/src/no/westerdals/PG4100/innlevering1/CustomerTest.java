package no.westerdals.PG4100.innlevering1;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class CustomerTest {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testCheckCustomerThreadIsRunning() throws Exception {
        // ARRANGE
        CarRental carRental = new CarRental();
        Customer customer = new Customer("Tester", new CountDownLatch(0));
        customer.setCarRental(carRental);

        // ACT
        new Thread(customer).start();

        // ASSERT
        assertTrue("Customer thread should be running", customer.isRunning());

        customer.stop();
    }

    @Test
    public void testStopCustomerThread() throws Exception {
        // ARRANGE
        CarRental carRental = new CarRental();
        Customer customer = new Customer("Tester", new CountDownLatch(0));

        // ACT & ASSERT
        new Thread(customer).start();

        assertTrue("Customer thread should be running", customer.isRunning());

        customer.stop();

        assertFalse("Customer thread should have been stopped", customer.isRunning());
    }

    @Test
    public void testCustomerRentCar() throws Exception {
        // ARRANGE
        CarRental carRental = new CarRental();
        CarRental carRentalSpy = spy(carRental);
        Customer customer = new Customer("Tester", null);

        // ACT
        carRentalSpy.rentACar(customer);

        // ASSERT
        verify(carRentalSpy).rentACar(customer);

        // Stop customer thread
        customer.stop();
    }

    @Test
    public void testCustomerReturnCar() throws Exception {
        // ARRANGE
        CarRental carRental = new CarRental();
        CarRental carRentalSpy = spy(carRental);
        Customer customer = new Customer("Tester", null);

        // ACT
        carRentalSpy.rentACar(customer);
        carRentalSpy.returnACar(customer);

        // ASSERT & VERIFY
        verify(carRentalSpy).rentACar(customer);
        verify(carRentalSpy).returnACar(customer);

        // Stop customer thread
        customer.stop();
    }
}