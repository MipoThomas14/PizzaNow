package edu.ksu.pizzanow.domain.model;

public class Beverage extends Item {
    private final String itemName;
    private final double itemUnitPrice;

    public Beverage() {
        this.itemName = "2l Soda";
        this.itemUnitPrice = 2.00;
    }

    @Override
    public double getPrice() {
        return this.itemUnitPrice;
    }

    @Override
    public String toString() {
        return this.itemName;
    }

    @Override
    public String toReceipt() {
        // Implementation for receipt format
        return "null";
    }

    @Override
    public String toCSV() {
        // Implementation for CSV format
        return "null";
    }
}
