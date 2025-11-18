package edu.ksu.pizzanow.domain.model;

public abstract class Item {
    public abstract double getPrice();
    abstract String toReceipt();
    abstract String toCSV();
}