package edu.ksu.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.*;
import org.junit.jupiter.api.*;

import edu.ksu.pizzanow.domain.model.Pizza;
import edu.ksu.pizzanow.domain.type.CrustType;
import edu.ksu.pizzanow.domain.type.PizzaSize;
import edu.ksu.pizzanow.domain.type.Topping;
public class PizzaTest{
    private CrustType crust;
    private PizzaSize size;
    private Pizza pizza;


    @BeforeEach
    public void setUp() {
        crust = CrustType.THIN;
        size = PizzaSize.MEDIUM;
        pizza = new Pizza(crust, size);
    }

    @Test
    void createWithoutToppings(){
        assertNotNull(pizza, "Pizza instance should not be null");
        assertEquals(crust, pizza.getCrustType(), "Crust type should match the initialized value");
        assertEquals(size, pizza.getSize(), "Pizza size should match the initialized value");
        assertEquals(Arrays.toString(new Topping[2]), Arrays.toString(pizza.getToppings()), "Pizza should have no toppings");
    }

    @Test
    void createWithToppings(){
        Topping[] toppings = {Topping.PEPPERONI, Topping.MUSHROOMS};
        Pizza pizzaWithToppings = new Pizza(crust, size, toppings);

        assertNotNull(pizzaWithToppings, "Pizza instance should not be null");
        assertEquals(crust, pizzaWithToppings.getCrustType(), "Crust type should match the initialized value");
        assertEquals(size, pizzaWithToppings.getSize(), "Pizza size should match the initialized value");
        assertEquals(Arrays.toString(toppings), Arrays.toString(pizzaWithToppings.getToppings()), "Pizza toppings should match the initialized values");
    }

    @Test
    void testToStringWithoutToppings(){
        String expectedString = "Medium Thin Crust Pizza";
        assertEquals(expectedString, pizza.toString(), "Pizza toString should match the expected format");
    }

    @Test
    void testToStringWithToppings(){
        Topping[] toppings = {Topping.PEPPERONI, Topping.MUSHROOMS};
        Pizza pizzaWithToppings = new Pizza(crust, size, toppings);
        String expectedString = "Medium Thin Crust Pizza with Pepperoni, Mushrooms";
        assertEquals(expectedString, pizzaWithToppings.toString(), "Pizza toString should match the expected format");
    }
 
    @Test
    void getPrice(){
        Topping[] toppings = {Topping.PEPPERONI, Topping.MUSHROOMS};
        Pizza pizzaWithToppings = new Pizza(crust, size, toppings);
        double expectedPrice = 8.99; // base price
        expectedPrice += crust.getPriceModifier() + size.getPriceModifier(); // crust and size modifiers
        expectedPrice += Topping.PEPPERONI.getPriceModifier() + Topping.MUSHROOMS.getPriceModifier(); // toppings modifiers
        assertEquals(expectedPrice, pizzaWithToppings.getPrice(), 0.01, "Pizza price should be calculated correctly");
    }
}
