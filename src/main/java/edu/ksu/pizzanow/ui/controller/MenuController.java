package edu.ksu.pizzanow.ui.controller;

import edu.ksu.pizzanow.application.AppContext;
import edu.ksu.pizzanow.domain.type.CrustType;
import edu.ksu.pizzanow.domain.type.OrderType;
import edu.ksu.pizzanow.domain.type.PizzaSize;
import edu.ksu.pizzanow.domain.type.Topping;
import edu.ksu.pizzanow.ui.UIController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;

public class MenuController {

    private UIController uiController;
    private AppContext appContext;

    // ===== FXML WIRED CONTROLS =====
    @FXML
    private Button btnCheese;

    @FXML
    private Button btnPepperoni;

    @FXML
    private ComboBox<String> comboCrust;

    @FXML
    private ComboBox<String> comboSize;

    @FXML
    private ComboBox<String> comboTopping1;

    @FXML
    private ComboBox<String> comboTopping2;

    @FXML
    private Button btnAddCustom;

    @FXML
    private Button btnSoda;

    @FXML
    private Button btnContinueToCart;

    @FXML
    private Label lblWelcome;

    public MenuController() {
    }

    // ===== SETTERS FROM UIController =====
    public void setUiController(UIController uiController) {
        this.uiController = uiController;
    }

    public void setAppContext(AppContext appContext) {
        this.appContext = appContext;
        System.out.println("starting session order");
        
        // Initialize new order
        appContext.newSessionOrder(OrderType.None);

        // Set Welcome Message
        if (appContext != null && appContext.getSessionCustomer() != null) {
            lblWelcome.setText("Welcome, " + appContext.getSessionCustomer().getName() + "!");
        }
        
        // Initialize button text (Checkout (0))
        updateCartCount();
    }

    // ===== LIFECYCLE =====
    @FXML
    private void initialize() {
        // Initial setup if needed
    }

    // ===== HELPER: Update Checkout Button =====
    private void updateCartCount() {
        if (appContext != null && appContext.getSessionOrder() != null) {
            int count = appContext.getSessionOrder().getOrderItems().size();
            btnContinueToCart.setText("Checkout (" + count + ")");
        } else {
            btnContinueToCart.setText("Checkout (0)");
        }
    }

    // ===== BUTTON HANDLERS =====

    @FXML
    private void onCheeseClicked() {
        try {
            CrustType crust = CrustType.REGULAR;
            PizzaSize size = PizzaSize.MEDIUM;

            Topping[] toppings = new Topping[] { Topping.EXTRA_CHEESE };
            appContext.addPizzaToOrder(crust, size, toppings);
            updateCartCount();

        } catch (IllegalStateException ex) {
            showError("Unable to add pizza: " + ex.getMessage());
        }
    }

    @FXML
    private void onPepperoniClicked() {
        try {
            CrustType crust = CrustType.REGULAR;
            PizzaSize size = PizzaSize.MEDIUM;

            Topping[] toppings = new Topping[] { Topping.PEPPERONI };
             appContext.addPizzaToOrder(crust, size, toppings);
            
            updateCartCount();
        } catch (IllegalStateException ex) {
            showError("Unable to add pizza: " + ex.getMessage());
        }
    }

    @FXML
    private void onAddCustomClicked() {
        String crustStr = comboCrust.getValue();
        String sizeStr = comboSize.getValue();
        String topping1Str = comboTopping1.getValue();
        String topping2Str = comboTopping2.getValue();

        if (crustStr == null || sizeStr == null) {
            showError("Please select both a crust type and size.");
            return;
        }

        try {
            CrustType crust = mapCrust(crustStr);
            PizzaSize size = mapSize(sizeStr);

            List<Topping> toppings = new ArrayList<>();

            if (topping1Str != null && !"None".equalsIgnoreCase(topping1Str)) {
                toppings.add(mapTopping(topping1Str));
            }
            if (topping2Str != null && !"None".equalsIgnoreCase(topping2Str)) {
                toppings.add(mapTopping(topping2Str));
            }

            if (toppings.isEmpty()) {
                appContext.addPizzaToOrder_NoToppings(crust, size);
            } else {
                appContext.addPizzaToOrder(crust, size, toppings.toArray(new Topping[0]));
            }

            updateCartCount();
            // showInfo("Custom pizza added to order.");
        } catch (IllegalArgumentException ex) {
            showError("Invalid menu selection: " + ex.getMessage());
        } catch (IllegalStateException ex) {
            showError("Unable to add pizza: " + ex.getMessage());
        }
    }

    @FXML
    private void onSodaClicked() {
        appContext.addBeverageToOrder();
        updateCartCount();
        // showInfo("Added ");
    }

    @FXML
    private void onCheckoutClicked() {
        System.out.println("checkout clicked");
        uiController.showCartScreen();
    }

    // ===== MAPPING HELPERS =====

    private CrustType mapCrust(String label) {
        return switch (label) {
            case "Thin" -> CrustType.THIN;
            case "Regular" -> CrustType.REGULAR;
            case "Cheesy" -> CrustType.CHEESY;
            default -> throw new IllegalArgumentException("Unknown crust type: " + label);
        };
    }

    private PizzaSize mapSize(String label) {
        return switch (label) {
            case "Small" -> PizzaSize.SMALL;
            case "Medium" -> PizzaSize.MEDIUM;
            case "Large" -> PizzaSize.LARGE;
            case "Extra Large" -> PizzaSize.XLARGE;
            default -> throw new IllegalArgumentException("Unknown size: " + label);
        };
    }

    private Topping mapTopping(String label) {
        return switch (label) {
            case "Pepperoni" -> Topping.PEPPERONI;
            case "Sausage" -> Topping.SAUSAGE;
            case "Mushrooms" -> Topping.MUSHROOMS;
            case "Extra Cheese" -> Topping.EXTRA_CHEESE;
            case "Bacon" -> Topping.BACON;
            case "Green Peppers" -> Topping.GREEN_PEPPERS;
            case "Pineapple" -> Topping.PINEAPPLE;
            case "Spinach" -> Topping.SPINACH;
            default -> throw new IllegalArgumentException("Unknown topping: " + label);
        };
    }

    // ===== UI HELPERS =====
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Menu Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Menu");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}