package edu.ksu.pizzanow.ui.controller;

import edu.ksu.pizzanow.application.AppContext;
import edu.ksu.pizzanow.ui.UIController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class HomeController {

    private UIController uiController;
    private AppContext appContext;

    @FXML
    private TextField phoneInput;

    @FXML
    private Button btnContinue;

    @FXML
    private Button btnNewCustomer;

    public void setUiController(UIController uiController) {
        this.uiController = uiController;
    }

    public void setAppContext(AppContext appContext) {
        this.appContext = appContext;
    }

    @FXML
    private void initialize() {
        // Add focus listener to change border color when user clicks input
        phoneInput.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                // Focused: White bg, Red border
                phoneInput.setStyle("-fx-background-color: #ffffff; -fx-border-color: #E75D48; -fx-border-width: 2; -fx-border-radius: 15; -fx-background-radius: 15; -fx-padding: 15; -fx-font-size: 16px;");
            } else {
                // Unfocused: Grey bg, Light Grey border
                phoneInput.setStyle("-fx-background-color: #FAFAFA; -fx-border-color: #eeeeee; -fx-border-width: 2; -fx-border-radius: 15; -fx-background-radius: 15; -fx-padding: 15; -fx-font-size: 16px; -fx-prompt-text-fill: #aaaaaa;");
            }
        });
    }

    @FXML
    private void onContinueClicked() {
        String phone = phoneInput.getText();
        System.out.println("Continue clicked with phone: " + phone);
        
        // TODO: Implementation logic
        // if (appContext.getCustomerService().exists(phone)) { ... }
    }

    @FXML
    private void onNewCustomerClicked() {
        System.out.println("New Customer clicked");
        
        // TODO: Implementation logic
        // uiController.showRegistrationScreen();
    }
}