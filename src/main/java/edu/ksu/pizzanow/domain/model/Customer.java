package edu.ksu.pizzanow.domain.model;

import edu.ksu.pizzanow.domain.type.PaymentType;


public class Customer {
    private String name;
    private String phoneNumber;
    private PaymentType paymentType;


    // constructors
    public Customer(String name, String phoneNumber, PaymentType paymentType) {
        this.name = name;
        this.phoneNumber = normalizePhoneNumber(phoneNumber);
        this.paymentType = paymentType;
    }

    public Customer(String name, String phoneNumber){
        this.name = name;
        this.phoneNumber = normalizePhoneNumber(phoneNumber);
        this.paymentType = PaymentType.None;
    }



    // getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = normalizePhoneNumber(phoneNumber);
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }





    // static methods
    public static String normalizePhoneNumber(String phoneNumber) throws IllegalArgumentException{
        return " ";
    }
}
