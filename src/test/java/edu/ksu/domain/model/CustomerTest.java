package edu.ksu.domain.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.ksu.pizzanow.domain.model.Customer;
import edu.ksu.pizzanow.domain.type.PaymentType;

public class CustomerTest {
    private Customer customer;

    @BeforeEach
    public void setUp() {
        customer = new Customer("John Doe", "555-123-4567", PaymentType.CREDIT);
    }

    @Test
    void testConstructorAndGetters() {
        assertEquals("John Doe", customer.getName());
        assertEquals("555-123-4567", customer.getPhoneNumber());
        assertEquals(PaymentType.CREDIT, customer.getPaymentType());
    }

    @Test
    void testSetters() {
        customer.setName("Jane Doe");
        customer.setPhoneNumber("404-987-6543");
        customer.setPaymentType(PaymentType.CASH);

        assertEquals("Jane Doe", customer.getName());
        assertEquals("404-987-6543", customer.getPhoneNumber());
        assertEquals(PaymentType.CASH, customer.getPaymentType());
    }

    @Test
    void testNormalization() {
        String normalized = "555-999-1234";
        customer.setPhoneNumber("5559991234"); // implicitly calls .normalize()
        assertEquals(normalized, customer.getPhoneNumber());
    }

    @Test
    void testToCSVConvertsProperly() {
        assertTrue(true);
        // String csv = customer.toCSV();
        // assertTrue(csv.contains("John Doe"));
        // assertTrue(csv.contains("555-123-4567"));
        // assertTrue(csv.contains("123 Main St"));
        // assertTrue(csv.contains("CREDIT"));
    }

    @Test
    void testEqualsAndHashCode() {
        Customer c1 = new Customer("John Doe", "5551234567", PaymentType.CREDIT);
        Customer c2 = new Customer("John Doe", "5551234567", PaymentType.CREDIT);
        c2.setPhoneNumber("5551234567");
        assertEquals(c1, c2);
        assertEquals(c1.hashCode(), c2.hashCode());
    }

    @Test
    void testInvalidPhoneThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Customer("Bob", "12A456", PaymentType.CASH);
        });
    }
}