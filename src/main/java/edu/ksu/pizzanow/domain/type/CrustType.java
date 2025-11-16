package edu.ksu.pizzanow.domain.type;

public enum CrustType {
    THIN(1.00),
    REGULAR(0.00),
    CHEESY(2.00);

    private final double priceModifier;

    CrustType(double priceModifier) {
        this.priceModifier = priceModifier;
    }

    public double getPriceModifier() {
        return this.priceModifier;
    }

    @Override
    public String toString() {
        return switch (this) {
            case THIN -> "Thin Crust";
            case REGULAR -> "Regular Crust";
            case CHEESY -> "Cheesy Crust";
        };
    }
}
