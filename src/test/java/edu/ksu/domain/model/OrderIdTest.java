package edu.ksu.domain.model;

import java.util.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.ksu.pizzanow.domain.model.OrderId;


public class OrderIdTest {
    @Test
    void generatesTenDigitString(){
        OrderId orderId = new OrderId();
        int length = orderId.toString().length();
        assertEquals(10, length, "Order ID numerical value should be 10 digits long");
    }

    @Test
    void generatesUniqueIds(){
        Set<String> ids = new HashSet<>();
        int numberOfIdsToGenerate = 1000;
        for(int i = 0; i < numberOfIdsToGenerate; i++){
            OrderId orderId = new OrderId();
            String idString = orderId.getNormalized();
            ids.add(idString);
        }
        assertEquals(numberOfIdsToGenerate, ids.size(), "All generated Order IDs should be unique");
    }

    @Test
    void numericValueMatchesNormalizedString(){
        OrderId orderId = new OrderId();
        String normalized = orderId.getNormalized();
        long numericValue = orderId.getNumericalValue();
        assertEquals(Long.parseLong(normalized), numericValue, "Numeric value should match the parsed normalized string");
    }

    @Test
    void toStringReturnsNormalized(){
        OrderId orderId = new OrderId();
        String normalized = orderId.getNormalized();
        assertEquals(normalized, orderId.toString(), "toString() should return the normalized string");
    }

    @Test
    void existingIdsSetPersistsAcrossInstances(){
        OrderId firstOrderId = new OrderId();
        String firstId = firstOrderId.getNormalized();

        boolean foundDuplicate = false;
        for(int i = 0; i < 10000; i++){
            OrderId newOrderId = new OrderId();
            if(newOrderId.getNormalized().equals(firstId)){
                foundDuplicate = true;
                break;
            }
        }
        assertEquals(false, foundDuplicate, "No duplicate IDs should be generated across multiple instances");
    }

    @Test
    void generatesdifferntIdsOnMultipleCalls(){
        OrderId orderId1 = new OrderId();
        OrderId orderId2 = new OrderId();
        assertEquals(false, orderId1.getNormalized().equals(orderId2.getNormalized()), "Multiple calls should generate different Order IDs");
    }

    @Test
    void noInfiniteLoopOnIdGeneration(){
        // This test ensures that the ID generation does not enter an infinite loop
        // by attempting to generate a large number of IDs.
        int numberOfIdsToGenerate = 10000;
        for(int i = 0; i < numberOfIdsToGenerate; i++){
            OrderId orderId = new OrderId();
        }
        // If we reach this point, no infinite loop occurred
        assertEquals(true, true, "ID generation completed without infinite loop");
    }

    @Test
    void idwithinexpectedRange(){
        OrderId orderId = new OrderId();
        long numericValue = orderId.getNumericalValue();
        assertEquals(true, numericValue >= 0 && numericValue < 10_000_000_000L, "Order ID numeric value should be within the expected range");
    }

    @Test
    void leadingZerosPreservedInNormalized(){
        OrderId orderId = new OrderId();
        String normalized = orderId.getNormalized();
        assertEquals(10, normalized.length(), "Normalized ID should always be 10 characters long, preserving leading zeros");
    }
}