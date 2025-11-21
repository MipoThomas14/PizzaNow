package edu.ksu.pizzanow.domain.model;

import java.util.Objects;

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
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = normalizePhoneNumber(phoneNumber);
    }

    public PaymentType getPaymentType() {
        return this.paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    @Override
    public int hashCode(){
        return Objects.hash(name, phoneNumber, paymentType);
    }

    @Override
    public boolean equals(Object obj){
        if(this.hashCode() == obj.hashCode()){
            return true;
        } 
        
        return false;
    }



    // static methods
    public static String denormalizePhoneNumber(String phoneNumber) throws IllegalArgumentException {
        if(phoneNumber.isBlank()){
            throw new IllegalArgumentException("Phone number cannot be null or empty.");
        }

        String digits = phoneNumber.replaceAll("[^0-9]", "");

        if(digits.length() != 10){
            throw new IllegalArgumentException("Phone number must be 10 digits");
        }

        return digits;
    }
    
    public static String normalizePhoneNumber(String phoneNumber) {
        if (phoneNumber == null) {
            throw new IllegalArgumentException("Phone number cannot be null");
        }

        String digits = phoneNumber.replaceAll("[^0-9]", "");

        if (digits.length() != 10) {
            throw new IllegalArgumentException("Phone number must be 10 digits");
        }

        return String.format("%s-%s-%s", digits.substring(0, 3), digits.substring(3, 6), digits.substring(6));
    }

}
