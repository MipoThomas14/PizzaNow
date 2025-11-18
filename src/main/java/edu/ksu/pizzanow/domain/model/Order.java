package edu.ksu.pizzanow.domain.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import edu.ksu.pizzanow.domain.type.OrderStatus;
import edu.ksu.pizzanow.domain.type.OrderType;
import edu.ksu.pizzanow.domain.type.PaymentType;

public class Order {
    private final String orderId;
    private final Customer customer;

    private OrderType orderType;
    private List<Item> orderItems;
    private PaymentType paymentType;

    private double tax;
    private double total;
    private double subtotal;

    private OrderStatus orderStatus;
    private LocalDateTime timeCreated; // not initialized until checkout



    public Order(Customer customer){
        this.customer = customer;
        this.orderId = new OrderId().getNormalized();

        this.orderItems = new ArrayList<>();

        this.orderType = OrderType.None;
        this.paymentType = customer.getPaymentType();

        this.tax = 0.0;
        this.subtotal = 0.0;
        this.total = 0.0;
        this.orderStatus = OrderStatus.INCOMPLETE;
    }

    public Order(Customer customer, List<Item> items){
        this.customer = customer;
        this.orderId = new OrderId().getNormalized();

        this.orderItems = items;

        this.orderType = OrderType.None;
        this.paymentType = customer.getPaymentType();

        this.tax = 0.0;
        this.subtotal = 0.0;
        this.total = 0.0;
        this.orderStatus = OrderStatus.INCOMPLETE;
    }

    public String getOrderId(){
        return orderId;
    }

    public Customer getCustomer(){
        return customer;
    }

    public List<Item> getOrderItems(){
        return orderItems;
    }

    public void setOrderItems(List<Item> items){
        this.orderItems = items;
    }

    public OrderType getOrderType(){
        return orderType;
    }

    public void addItem(Item toAdd){
        orderItems.add(toAdd);
    }

    public void removeItem(Item toRemove) {
        orderItems.remove(toRemove);
    }

    public void setOrderType(OrderType orderType){
        this.orderType = orderType;
    }

    public PaymentType getPaymentType(){
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType){
        this.paymentType = paymentType;
    }

    public double getSubTotal(){
        subtotal = 0.0;
        for(Item item : orderItems){
            subtotal += item.getPrice();
        }

        return subtotal;
    }

    public double getTax(){
        tax = getSubTotal() * 0.06; // 6% tax
        return tax;
    }

    public double getTotal(){
        total = getSubTotal() + getTax();
        return total;
    }

    public final void setTimeCreated(){
        timeCreated = LocalDateTime.now();
    }

    public LocalDateTime getTimeCreated() throws IllegalStateException {
        if(orderStatus == null || orderStatus == OrderStatus.INCOMPLETE){
            throw new IllegalStateException("Order status is not set. Order has not been officially created yet");
        }
        return timeCreated;
    }

    public OrderStatus getOrderStatus(){
        return orderStatus;
    }

    public final void setOrderStatus(){
        orderStatus = OrderStatus.COMPLETE;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Order ID: ").append(orderId).append("\n");
        sb.append("Customer: ").append(customer.getName()).append("\n");
        sb.append("Order Type: ").append(orderType.toString()).append("\n");
        sb.append("Payment Type: ").append(paymentType.toString()).append("\n");
        sb.append("Items:\n");
        for(Item item : orderItems){
            sb.append(" - ").append(item.toString()).append("\n");
        }
        sb.append(String.format("Subtotal: $%.2f\n", getSubTotal()));
        sb.append(String.format("Tax: $%.2f\n", getTax()));
        sb.append(String.format("Total: $%.2f\n", getTotal()));
        sb.append("Order Status: ").append(orderStatus.toString()).append("\n");
        if(timeCreated != null){
            sb.append("Time Created: ").append(timeCreated.toString()).append("\n");
        }
        return sb.toString();
    }

    public String toCSV() throws UnsupportedOperationException{
        throw new UnsupportedOperationException(".toCSV() not implemented yet");
    }

    public String toReceipt() throws UnsupportedOperationException{
        throw new UnsupportedOperationException(".toReceipt() not implemented yet");
    }
}
