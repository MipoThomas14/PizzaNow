package edu.ksu.pizzanow.domain.model;

import java.util.Arrays;
import java.util.Objects;

import edu.ksu.pizzanow.domain.type.CrustType;
import edu.ksu.pizzanow.domain.type.PizzaSize;
import edu.ksu.pizzanow.domain.type.Topping;

public class Pizza extends Item{
    private final CrustType crustType;
    private final PizzaSize pizzaSize;
    private final double basePrice;
    private double itemUnitPrice;
    private Topping[] toppings = new Topping[2];


    public Pizza(CrustType crustType, PizzaSize pizzaSize) {
        this.crustType = crustType;
        this.pizzaSize = pizzaSize;
        this.basePrice = 8.99;
        this.itemUnitPrice = basePrice + crustType.getPriceModifier() + pizzaSize.getPriceModifier();
    }

    public Pizza(CrustType crustType, PizzaSize pizzaSize, Topping[] toppings) {
        this.crustType = crustType;
        this.pizzaSize = pizzaSize;
        this.basePrice = 8.99;

        this.toppings = toppings;
        this.itemUnitPrice = basePrice + crustType.getPriceModifier() + pizzaSize.getPriceModifier();
        for (Topping topping : toppings) {
            this.itemUnitPrice += topping.getPriceModifier();
        }
    }

    public Topping[] getToppings() {
        return toppings;
    }

    public CrustType getCrustType() {
        return crustType;
    }

    public PizzaSize getSize() {
        return pizzaSize;
    }
    

    @Override
    public int hashCode(){
        return Objects.hash(crustType.toString(), pizzaSize.toString(), String.valueOf(basePrice), String.valueOf(itemUnitPrice), toppings);
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
    public String toString() {
        String baseName = pizzaSize.toString() + " " + crustType.toString() + " Pizza";
        if (Arrays.stream(toppings).anyMatch(t -> t != null)) {
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < toppings.length; i++) {
                sb.append(toppings[i].toString());
                if (i < toppings.length - 1) {
                    sb.append(", ");
                }
            }

            return baseName + " with " + sb.toString();
        }
        return baseName;
    }

    @Override
    public double getPrice() {
        itemUnitPrice = basePrice + crustType.getPriceModifier() + pizzaSize.getPriceModifier();
        for (Topping topping : toppings) {
            if(topping == null){
                continue;
            }
            itemUnitPrice += topping.getPriceModifier();
        }

        System.out.println("\n\n\n ITEM UNIT PRICE FOR " + this.toString() + ": " + Double.toString(itemUnitPrice));
        return itemUnitPrice;
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
