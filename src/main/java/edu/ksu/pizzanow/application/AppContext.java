package edu.ksu.pizzanow.application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.ksu.pizzanow.domain.model.Customer;
import edu.ksu.pizzanow.domain.model.Item;
import edu.ksu.pizzanow.domain.model.Order;
import edu.ksu.pizzanow.domain.type.CrustType;
import edu.ksu.pizzanow.domain.type.OrderType;
import edu.ksu.pizzanow.domain.type.PaymentType;
import edu.ksu.pizzanow.domain.type.PizzaSize;
import edu.ksu.pizzanow.domain.type.Topping;
import edu.ksu.pizzanow.service.CatalogService;
import edu.ksu.pizzanow.service.CustomerService;
import edu.ksu.pizzanow.service.OrderService;
import edu.ksu.pizzanow.service.ReportService;


public class AppContext {
    private Order sessionOrder;
    private Customer sessionCustomer;
    
    private final OrderService orderService;
    private final ReportService reportService;
    private final CatalogService catalogService;
    private final CustomerService customerService;


    public AppContext() {
        orderService = new OrderService();
        reportService = new ReportService();
        catalogService = new CatalogService();
        customerService = new CustomerService();
    }

    // Customer API
    public Customer getSessionCustomer() {
        if(sessionCustomer == null){
            throw new IllegalStateException("Session customer has not been registered to Application Context.");
        }
        return sessionCustomer;
    }

    // updates existing customer attributes, creates a whole new customer if necessary
    public void updateCustomer(String name, String phoneNumber, PaymentType paymentType) throws IOException {
        if (sessionCustomer == null) { // creates a new customer
            sessionCustomer = customerService.createCustomer(name, phoneNumber, paymentType);
        } else {
            sessionCustomer.setName(name);
            sessionCustomer.setPhoneNumber(phoneNumber);
            sessionCustomer.setPaymentType(paymentType);
        }
    }


    public void clearSessionCustomer() {
        sessionCustomer = null;
    }


    // Order API
    public Order getSessionOrder() {
        if(sessionOrder == null){
            throw new IllegalStateException("Session order has not been registered to Application Context.");
        }

        return sessionOrder;
    }

    public void newSessionOrder(OrderType orderType) {
        if(sessionCustomer == null){
            throw new IllegalStateException("Cannot start new session order while session customer has not been registered.");
        }

        sessionOrder = orderService.createOrder(sessionCustomer, new ArrayList<>(), orderType);

    }

    public void addPizzaToOrder(CrustType crust, PizzaSize size, Topping[] toppings){
        if(sessionOrder == null){
            throw new IllegalStateException("Empty session Order.");
        }
        
        List<Topping> toppingList = (toppings == null) ? List.of() : Arrays.asList(toppings);
        sessionOrder.addItem(catalogService.buildPizza(crust, size, toppingList));
    }

    public void addPizzaToOrder_NoToppings(CrustType crust, PizzaSize size){
        if(sessionOrder == null){
            throw new IllegalStateException("Empty session Order.");
        }

        sessionOrder.addItem(catalogService.buildPizza(crust, size, List.of()));
    }

    public List<Item> getSessionOrderItems(){
        if(sessionOrder == null){
            throw new IllegalStateException("Empty session Order.");
        }

        return sessionOrder.getOrderItems();
    }

    public void clearSessionOrder(){
        sessionOrder = null;
    }

    public double getCurrentSubtotal(){
        if(sessionOrder == null){
            throw new IllegalStateException("Empty session Order.");
        }

        return sessionOrder.getSubTotal();
    }

    public double getCurrentTax(){
        if(sessionOrder == null){
            throw new IllegalStateException("Empty session Order.");
        }

        return sessionOrder.getTax();
    }

    public double getTotal(){
        if(sessionOrder == null){
            throw new IllegalStateException("Empty session Order.");
        }
        
        return sessionOrder.getTotal();
    }


    // Catalog API
    public List<PizzaSize> getAvailableSizes(){
        return catalogService.getAvailableSizes();
    }

    public List<CrustType> getAvailableCrustTypes(){
        return catalogService.getAvailableCrustTypes();
    }

    public List<Topping> getAvailableToppings(){
        return catalogService.getAvailableToppings();
    }


    // Report API 
    public String finalizeSessionOrder() throws IOException {
        if(sessionCustomer == null){
            throw new IllegalStateException("Cannot finalize session order with unregistered session customer.");
        }

        if(sessionOrder == null){
            throw new IllegalStateException("Cannot finalize session order with non-initialized session order.");
        }

        orderService.finalizeOrder(sessionOrder); // automatically saves to db
        customerService.saveCustomerData(sessionCustomer);

        String orderReceipt = reportService.generateOrderReceipt(sessionOrder);

        sessionOrder = null;
        return orderReceipt;
    }
}