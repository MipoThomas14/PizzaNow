package edu.ksu.domain.model;

import org.junit.jupiter.api.*;

import edu.ksu.pizzanow.domain.model.Order;
import edu.ksu.pizzanow.domain.model.Customer;
import edu.ksu.pizzanow.domain.type.PaymentType;

public class OrderTest {
    private Order order;

    @BeforeEach
    public void setUp() {
        order = new Order();
    }
}
