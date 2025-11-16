package edu.ksu.pizzanow.domain.model;

import edu.ksu.pizzanow.domain.type.*;

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
        return this.toppings;
    }

    @Override
    public String getName() {
        String baseName = pizzaSize.toString() + " " + crustType.toString() + " Pizza";
        if (toppings.length > 0) {
            StringBuilder sb = new StringBuilder();
            for (Topping topping : toppings) {
                sb.append(topping.toString()).append(", ");
            }
            return baseName + " with " + sb.toString();
        }
        return baseName;
    }

    @Override
    public double getPrice() {
        if (toppings.length > 0) {
            double totalPrice = this.basePrice + crustType.getPriceModifier() + pizzaSize.getPriceModifier();
            for (Topping topping : toppings) {
                totalPrice += topping.getPriceModifier();
            }
            return totalPrice;
        }
        return this.basePrice + crustType.getPriceModifier() + pizzaSize.getPriceModifier();
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
