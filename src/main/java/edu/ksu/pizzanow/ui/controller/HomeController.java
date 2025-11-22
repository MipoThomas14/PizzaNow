package edu.ksu.pizzanow.ui.controller;

import java.io.IOException;

import edu.ksu.pizzanow.application.AppContext;
import edu.ksu.pizzanow.service.CustomerService;
import edu.ksu.pizzanow.ui.UIController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

    @FXML
    private Label phoneInputFeedback;

    public void setUiController(UIController uiController) {
        this.uiController = uiController;
        initialize();
    }

    public void setAppContext(AppContext appContext) {
        this.appContext = appContext;
    }

    @FXML
    private void initialize() {
        phoneInput.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                phoneInput.setStyle("-fx-background-color: #ffffff; -fx-border-color: #E75D48; -fx-border-width: 2; -fx-border-radius: 15; -fx-background-radius: 15; -fx-padding: 15; -fx-font-size: 16px;");
            } else {
                phoneInput.setStyle("-fx-background-color: #FAFAFA; -fx-border-color: #eeeeee; -fx-border-width: 2; -fx-border-radius: 15; -fx-background-radius: 15; -fx-padding: 15; -fx-font-size: 16px; -fx-prompt-text-fill: #aaaaaa;");
            }
        });
    }

    @FXML
    private void onContinueClicked() throws IOException {
        String phone = phoneInput.getText();

        if(appContext.isValidPhoneNumber(phone)){
            if(appContext.existsByPhone(phone)){
                appContext.initializeSessionCustomerWithExistingNumber(phone);
                uiController.showMenuScreen();
            } else {
                phoneInputFeedback.setText("Could not find phone number in records!");
            }
        } else {
            phoneInputFeedback.setText("Not a valid phone number!");
        }
    }

    @FXML
    private void onNewCustomerClicked() {
        System.out.println("New Customer clicked");

        uiController.showRegistrationScreen();
    }
}