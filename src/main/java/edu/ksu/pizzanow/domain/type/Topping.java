package edu.ksu.pizzanow.domain.type;

public enum Topping {
    PEPPERONI,
    SAUSAGE,
    MUSHROOMS,
    EXTRA_CHEESE,
    BACON,
    GREEN_PEPPERS,
    PINEAPPLE,
    SPINACH;

    public double getPriceModifier(){
        return 1.50;
    }

    @Override
    public String toString() {
        return switch (this) {
            case PEPPERONI -> "Pepperoni";
            case SAUSAGE -> "Sausage";
            case MUSHROOMS -> "Mushrooms";
            case EXTRA_CHEESE -> "Extra Cheese";
            case BACON -> "Bacon";
            case GREEN_PEPPERS -> "Green Peppers";
            case PINEAPPLE -> "Pineapple";
            case SPINACH -> "Spinach";
        };
    }
}
