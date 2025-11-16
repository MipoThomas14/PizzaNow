package edu.ksu.pizzanow.domain.type;

public enum PizzaSize {
    SMALL(1.00),
    MEDIUM(2.00),
    LARGE(3.00),
    XLARGE(5.00);

    private final double priceModifier;
    
    PizzaSize(double priceModifier) {
        this.priceModifier = priceModifier;
    }

    public double getPriceModifier() {
        return this.priceModifier;
    }

    @Override
    public String toString() {
        return switch (this) {
            case SMALL -> "Small";
            case MEDIUM -> "Medium";
            case LARGE -> "Large";
            case XLARGE -> "Extra Large";
        };
    }
}