package edu.ksu.pizzanow.service;

import java.io.IOException;
import java.util.List;

import edu.ksu.pizzanow.data.FileHandler;
import edu.ksu.pizzanow.domain.model.Customer;
import edu.ksu.pizzanow.domain.type.PaymentType;


public class CustomerService {
    private final FileHandler fileHandler;

    public CustomerService(){
        fileHandler = new FileHandler();
    }


    ////// PUBLIC API
    public Customer createSessionCustomer(String name, String phoneNumber, PaymentType paymentType) throws IOException {
        Customer newCustomer = new Customer(name, phoneNumber, paymentType);
        saveToFile(newCustomer);
        return newCustomer;
    }
    
    public Customer findExistingCustomer(String phoneNumber) throws IOException {
        Customer newCustomer = null;
        phoneNumber = Customer.normalizePhoneNumber(phoneNumber);

        List<String[]> customerRows = fileHandler.readCSV("customers.csv", true);
        for(String[] row : customerRows){
            if(row.length < 2){
                continue;
            }

            String foundNumber = row[0];
            if(foundNumber.equals(phoneNumber)){
                newCustomer = new Customer(row[1], row[0], PaymentType.valueOf(row[2]));
            }
        }

        if(newCustomer == null){
            throw new IllegalArgumentException("Phone number not found");
        }

        return newCustomer;
    }

    public static boolean isValidPhoneNumber(String input) {
        if (input == null) return false;
        
        int count = 0;
        for (int i = 0; i < input.length(); i++) {
            if (Character.isDigit(input.charAt(i))) {
                count++;
                if (count > 10) return false;
            }
        }
        
        return count == 10;
    }

    public void saveCustomerData(Customer customer) throws IOException {
        saveToFile(customer);
    }

    public boolean existsByPhone(String identifier) throws IOException{
        boolean exists = false;

        if(isValidPhoneNumber(identifier)){
            identifier = Customer.normalizePhoneNumber(identifier);
            List<String[]> customerRows = fileHandler.readCSV("customers.csv", true);
            for(String[] row : customerRows){
                if(row.length < 2){
                    continue;
                }

                String phoneNumber = row[0];
                if(phoneNumber.equals(identifier)){
                    exists = true;
                    break;
                }
            }
        } else {
            throw new IllegalArgumentException("Identifier input is not a phone number. Cannot search records to see if it exists.");
        }

        return exists;
    }

    public boolean existsByName(String identifier) throws IOException {
        boolean exists = false;
        List<String[]> customerRows = fileHandler.readCSV("customers.csv", true);

        for(String[] row : customerRows){
            if(row.length < 2){
                continue;
            }
            
            String name = row[1];
            if(name.equalsIgnoreCase(identifier)){
                exists = true;
                break;
            }
        }

        return exists;
    }




    ///////// PRIVATE METHODS
    // returns true if phone number, else 
    private void saveToFile(Customer customer) throws IOException{
        String[] toSave = {
            customer.getPhoneNumber(), // phoneNumber
            customer.getName(), // name
            customer.getPaymentType().toString() // paymentType
        };

        try {
            System.out.println("attempting to update row");
            fileHandler.updateRow("customers.csv", customer.getPhoneNumber(), toSave);
        } catch (IllegalArgumentException e) {
            System.out.println("row not found, attempting to append entry");
            fileHandler.appendRow("customers.csv", toSave);
        }
    }
}
