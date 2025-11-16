package edu.ksu.domain.model;

import java.util.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


import edu.ksu.pizzanow.domain.model.Beverage;


public class BeverageTest {
    Beverage bev;

    @BeforeEach
    public void setUp() {
        bev = new Beverage();
    }

    @Test
    void createBeverage(){
        assertNotNull(bev, "Beverage instance should not be null");
    }

    @Test
    void getPrice(){
        double expectedPrice = 2.00; // base price for all bevs
        assertEquals(expectedPrice, bev.getPrice(), "Beverage price should match the expected value");
    }

    @Test
    void testToString(){
        String expectedString = "2l Soda";
        assertEquals(expectedString, bev.toString(), "Beverage toString should match the expected format");
    }
}