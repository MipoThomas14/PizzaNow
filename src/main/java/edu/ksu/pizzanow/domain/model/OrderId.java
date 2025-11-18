package edu.ksu.pizzanow.domain.model;

import java.util.HashSet;
import java.util.Random;


public final class OrderId {
    private static final HashSet<String> existingIds = new HashSet<>();
    private static final Random random = new Random();

    private final long numeric;
    private final String normalized;

    public OrderId(){
        String candidate;
        do{
            candidate = generateRandomId();
        } while(!existingIds.add(candidate));

        existingIds.add(candidate);
        normalized = candidate;
        numeric = Long.parseLong(candidate);
    }

    private String generateRandomId(){
        long number = (long)(random.nextDouble() * 1_000_000_0000L);
        return String.format("%010d", number);
    }

    public String getNormalized(){
        return normalized;
    }

    public long getNumericalValue(){
        return numeric;
    }

    public static boolean isOrderId(String candidate){
        return candidate != null && candidate.matches("\\d{10}");
    }

    @Override
    public String toString(){
        return getNormalized();
    }
}