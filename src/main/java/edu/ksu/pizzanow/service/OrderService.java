package edu.ksu.pizzanow.service;

import java.io.IOException;
import java.util.List;

import edu.ksu.pizzanow.data.FileHandler;
import edu.ksu.pizzanow.domain.model.Customer;
import edu.ksu.pizzanow.domain.model.Item;
import edu.ksu.pizzanow.domain.model.Order;
import edu.ksu.pizzanow.domain.type.OrderType;


public class OrderService {
    private final FileHandler fileHandler;

    public OrderService(){
        fileHandler = new FileHandler();
    }
    
    public Order createOrder(Customer customer, List<Item> orderItems, OrderType orderType) {
        Order newOrder = new Order(customer, orderItems);
        newOrder.setOrderType(orderType);

        return newOrder;
    }

    public void finalizeOrder(Order order) throws IOException{
        order.setOrderStatus();
        order.setTimeCreated();
        saveToFile(order);
    }

    private void saveToFile(Order toSave) throws IOException{
        Customer customer = toSave.getCustomer();

        String[] orderLine = {
            toSave.getOrderId(), // orderId
            customer.getPhoneNumber(), // customerPhone
            toSave.getOrderType().toString(), // orderType
            toSave.getPaymentType().toString(), // paymentType
            String.valueOf(toSave.getSubTotal()), // subtotal
            String.valueOf(toSave.getTax()), // tax
            String.valueOf(toSave.getTotal()), // total
            toSave.getTimeCreated().toString(), // timeCreated
            toSave.getOrderStatus().toString() // orderStatus
        };

        try {
            fileHandler.updateRow("orders.csv", toSave.getOrderId(), orderLine);
        } catch (IllegalArgumentException e) {
            fileHandler.appendRow("orders.csv", orderLine);
        }
    }

}
