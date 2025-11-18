package edu.ksu.application;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.ksu.pizzanow.application.AppContext;
import edu.ksu.pizzanow.domain.model.Customer;
import edu.ksu.pizzanow.domain.model.Item;
import edu.ksu.pizzanow.domain.type.CrustType;
import edu.ksu.pizzanow.domain.type.OrderType;
import edu.ksu.pizzanow.domain.type.PaymentType;
import edu.ksu.pizzanow.domain.type.PizzaSize;
import edu.ksu.pizzanow.domain.type.Topping;

public class AppContextTest {

    private AppContext appContext;

    @BeforeEach
    void setUp() {
        appContext = new AppContext();
    }

    @Test
    void createAndUpdateCustomer_updatesSessionCustomer() throws IOException {
        // use first enum value to avoid relying on specific names
        PaymentType initialPaymentType = PaymentType.values()[0];

        // create new session customer
        appContext.updateCustomer("Alice", "5551234567", initialPaymentType);
        Customer customer = appContext.getSessionCustomer();

        assertNotNull(customer);
        assertEquals("Alice", customer.getName());
        assertEquals("5551234567", customer.getPhoneNumber());
        assertEquals(initialPaymentType, customer.getPaymentType());

        // update existing session customer
        PaymentType updatedPaymentType = PaymentType.values().length > 1
                ? PaymentType.values()[1]
                : initialPaymentType;

        appContext.updateCustomer("Alice Smith", "5559876543", updatedPaymentType);
        Customer updated = appContext.getSessionCustomer();

        assertEquals("Alice Smith", updated.getName());
        assertEquals("5559876543", updated.getPhoneNumber());
        assertEquals(updatedPaymentType, updated.getPaymentType());
    }

    @Test
    void fullOrderFlow_generatesReceiptAndClearsSessionOrder() throws IOException {
        // 1. create session customer
        PaymentType paymentType = PaymentType.values()[0];
        appContext.updateCustomer("Bob", "5550000000", paymentType);

        // 2. start new session order
        OrderType orderType = OrderType.values()[0];
        appContext.newSessionOrder(orderType);

        // 3. pick valid menu options from Catalog via AppContext
        List<CrustType> crustTypes = appContext.getAvailableCrustTypes();
        List<PizzaSize> sizes = appContext.getAvailableSizes();
        List<Topping> toppings = appContext.getAvailableToppings();

        assertFalse(crustTypes.isEmpty(), "No crust types available");
        assertFalse(sizes.isEmpty(), "No pizza sizes available");

        CrustType crust = crustTypes.get(0);
        PizzaSize size = sizes.get(0);
        Topping[] selectedToppings = toppings.isEmpty()
                ? new Topping[0]
                : new Topping[] { toppings.get(0) };

        // 4. add pizza to current order
        appContext.addPizzaToOrder(crust, size, selectedToppings);

        List<Item> items = appContext.getSessionOrderItems();
        assertFalse(items.isEmpty(), "Order should contain at least one item");

        double subtotal = appContext.getCurrentSubtotal();
        double total = appContext.getTotal();

        assertTrue(subtotal > 0.0, "Subtotal should be greater than zero");
        assertTrue(total >= subtotal, "Total should be at least subtotal (tax included)");

        // 5. finalize order & get receipt
        String receipt = appContext.finalizeSessionOrder();

        assertNotNull(receipt);
        assertFalse(receipt.isBlank(), "Receipt should not be blank");
        assertTrue(receipt.contains("Order"), "Receipt should contain order label or header");
        assertTrue(receipt.contains("Total") || receipt.contains("TOTAL"),
                   "Receipt should contain total section");

        // 6. session order should be cleared after finalization
        assertThrows(IllegalStateException.class, () -> appContext.getSessionOrder());
    }
}
