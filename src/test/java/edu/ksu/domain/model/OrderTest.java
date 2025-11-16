package edu.ksu.domain.model;

import java.util.*;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.ksu.pizzanow.domain.model.Item;
import edu.ksu.pizzanow.domain.model.Pizza;
import edu.ksu.pizzanow.domain.model.Order;
import edu.ksu.pizzanow.domain.model.Customer;
import edu.ksu.pizzanow.domain.model.Beverage;
import edu.ksu.pizzanow.domain.type.CrustType;
import edu.ksu.pizzanow.domain.type.OrderStatus;
import edu.ksu.pizzanow.domain.type.OrderType;
import edu.ksu.pizzanow.domain.type.PaymentType;
import edu.ksu.pizzanow.domain.type.PizzaSize;
import edu.ksu.pizzanow.domain.type.Topping;

public class OrderTest {
    private Order order;
    private Item pizzaItem;
    private Customer customer;
    private Item beverageItem;

    @BeforeEach
    public void setUp() {
        Topping[] pizzaToppings = {Topping.PEPPERONI, Topping.MUSHROOMS};
        pizzaItem = new Pizza(CrustType.REGULAR, PizzaSize.LARGE, pizzaToppings);
        beverageItem = new Beverage();
        customer = new Customer("Mipo", "555-321-6789", PaymentType.DEBIT);
    }

    @Test
    void customerOnlyConstructorIntializesCorrectly(){
        order = new Order(customer);
        assertNotNull(order, "Order instance should not be null");
        assertEquals(customer, order.getCustomer(), "Customer should match the initialized value");
    }

    @Test
    void customerAndItemsConstructorInitializesCorrectly(){
        order = new Order(customer, Arrays.asList(pizzaItem, beverageItem));
        assertNotNull(order, "Order instance should not be null");
        assertEquals(customer, order.getCustomer(), "Customer should match the initialized value");
    }

    @Test
    void updateCoreFieldsAfterCreation(){
        order = new Order(customer, Arrays.asList(pizzaItem, beverageItem));
        order.setOrderType(OrderType.DELIVERY);
        order.setPaymentType(PaymentType.CREDIT);
        order.finalizeOrder();

        assertEquals(OrderType.DELIVERY, order.getOrderType(), "Order type should match the updated value");
        assertEquals(PaymentType.CREDIT, order.getPaymentType(), "Payment type should match the updated value");
        assertEquals(OrderStatus.COMPLETE, order.getOrderStatus(), "Order status should be COMPLETE after finalization");
    }
}
