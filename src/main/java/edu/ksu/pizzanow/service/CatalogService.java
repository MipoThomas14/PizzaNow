package edu.ksu.pizzanow.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import edu.ksu.pizzanow.domain.model.Pizza;
import edu.ksu.pizzanow.domain.type.CrustType;
import edu.ksu.pizzanow.domain.type.PizzaSize;
import edu.ksu.pizzanow.domain.type.Topping;

public class CatalogService {

    public CatalogService(){}

    public List<CrustType> getAvailableCrustTypes() {
        return Arrays.asList(CrustType.values());
    }

    public List<PizzaSize> getAvailableSizes() {
        return Arrays.asList(PizzaSize.values());
    }

    public List<Topping> getAvailableToppings() {
        return Arrays.asList(Topping.values());
    }

    public List<String> getAvailableSizeNames() {
        return Arrays.stream(PizzaSize.values()).map(Enum::name).collect(Collectors.toList());
    }

    public List<String> getAvailableToppingNames() {
        return Arrays.stream(Topping.values()).map(Enum::name).collect(Collectors.toList());
    }

    public List<String> getAvailableCrustNames() {
        return Arrays.stream(CrustType.values()).map(Enum::name).collect(Collectors.toList());
    }

    public Pizza buildPizza(CrustType crustType, PizzaSize pizzaSize, List<Topping> selectedToppings) {
        if (selectedToppings == null || selectedToppings.isEmpty()) {
            return new Pizza(crustType, pizzaSize);
        }

        List<Topping> trimmed = selectedToppings.size() > 2 ? selectedToppings.subList(0, 2) : selectedToppings;
        Topping[] toppingArray = trimmed.toArray(new Topping[0]);
        return new Pizza(crustType, pizzaSize, toppingArray);
    }
}
