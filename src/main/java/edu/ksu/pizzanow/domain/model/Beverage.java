package edu.ksu.pizzanow.domain.model;

import java.util.Objects;

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
    public int hashCode(){
        return Objects.hash(itemName, itemUnitPrice);
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj){
            return true;
        }

        if(obj == null){
            return false;
        }

        return (this.hashCode() == obj.hashCode());
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
