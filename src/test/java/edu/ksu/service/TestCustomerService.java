package edu.ksu.service;

import java.io.IOException;
import java.util.List;

import edu.ksu.pizzanow.data.FileHandler;
import edu.ksu.pizzanow.domain.model.Customer;
import edu.ksu.pizzanow.domain.type.PaymentType;
import edu.ksu.pizzanow.service.CustomerService;


public class TestCustomerService extends CustomerService {
    private final FileHandler fileHandler;

    public TestCustomerService(){
        fileHandler = new FileHandler();
    }


    ////// PUBLIC API
    // @Override
    // public Customer createCustomer(String name, String phoneNumber, PaymentType paymentType) throws IOException {
    //     Customer newCustomer = new Customer(name, phoneNumber, paymentType);
    //     saveToFile(newCustomer);
    //     return newCustomer;
    // }

    @Override
    public void saveCustomerData(Customer customer) throws IOException {
        saveToFile(customer);
    }

    @Override
    public boolean existsByPhone(String identifier) throws IOException{
        boolean exists = false;
        boolean isPhoneNumber = determineIdentifierType(identifier);

        if(isPhoneNumber){
            List<String[]> customerRows = fileHandler.readCSV("test-customers.csv", true);
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
            throw new IllegalArgumentException("Identifier input is not a phone number.");
        }

        return exists;
    }

    @Override
    public boolean existsByName(String identifier) throws IOException {
        boolean exists = false;
        boolean isString = !(determineIdentifierType(identifier));

        if(isString){
            List<String[]> customerRows = fileHandler.readCSV("test-customers.csv", true);
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
        } else {
            throw new IllegalArgumentException("Identifier input is not a name.");
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
            fileHandler.updateRow("test-customers.csv", customer.getPhoneNumber(), toSave);
        } catch (IllegalArgumentException e) {
            fileHandler.appendRow("test-customers.csv", toSave);
        }
    }

    private boolean determineIdentifierType(String identifier){
        boolean isPhoneNumber = false;

        if(identifier == null || identifier.isBlank()){
            return false;
        }

        try {
            Integer.valueOf(identifier);
            isPhoneNumber = true;
        } catch (NumberFormatException e) {}

        return isPhoneNumber;
    }
}
