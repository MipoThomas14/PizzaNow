package edu.ksu.pizzanow.domain.model;

public abstract class Item {
    abstract String getName();
    abstract double getPrice();
    abstract String toReceipt();
    abstract String toCSV();
}