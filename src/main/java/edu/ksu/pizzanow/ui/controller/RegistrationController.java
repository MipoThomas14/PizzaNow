package edu.ksu.pizzanow.ui.controller;

import java.io.IOException;

import edu.ksu.pizzanow.application.AppContext;
import edu.ksu.pizzanow.domain.type.PaymentType;
import edu.ksu.pizzanow.ui.UIController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class RegistrationController {

    private UIController uiController;
    private AppContext appContext;

    @FXML
    private TextField nameInput;

    @FXML
    private TextField phoneInput;

    @FXML
    private ComboBox<String> paymentInput;

    @FXML
    private Label nameInputFeedback;

    @FXML
    private Label phoneInputFeedback;

    @FXML
    private Button btnRegister;

    public void setUiController(UIController uiController) {
        this.uiController = uiController;
    }

    public void setAppContext(AppContext appContext) {
        this.appContext = appContext;
    }

    @FXML
    private void initialize() {
        nameInput.focusedProperty().addListener((obs, oldVal, newVal) -> {
            updateFieldStyle(nameInput, newVal);
        });

        phoneInput.focusedProperty().addListener((obs, oldVal, newVal) -> {
            updateFieldStyle(phoneInput, newVal);
        });
        
        if (nameInputFeedback != null) nameInputFeedback.setVisible(false);
        if (phoneInputFeedback != null) phoneInputFeedback.setVisible(false);
    }

    private void updateFieldStyle(TextField field, boolean isFocused) {
        if (isFocused) {
            field.setStyle("-fx-background-color: #ffffff; -fx-border-color: #E75D48; -fx-border-width: 2; -fx-border-radius: 12; -fx-background-radius: 12; -fx-padding: 12; -fx-font-size: 14px;");
        } else {
            field.setStyle("-fx-background-color: #FAFAFA; -fx-border-color: #eeeeee; -fx-border-width: 2; -fx-border-radius: 12; -fx-background-radius: 12; -fx-padding: 12; -fx-font-size: 14px;");
        }
    }

    @FXML
    private void onRegisterClicked() throws IOException {
        boolean isValid = true;

        String name = nameInput.getText();
        if (name == null || name.isBlank()) {
            nameInputFeedback.setText("Name is required");
            nameInputFeedback.setVisible(true);
            nameInputFeedback.setStyle("-fx-text-fill: #E75D48;"); // Red color
            isValid = false;
        } else {
            nameInputFeedback.setVisible(false);
        }

        String phone = phoneInput.getText();
        if (phone == null || phone.isBlank() || phone.replaceAll("[^0-9]", "").length() != 10) {
            phoneInputFeedback.setText("Invalid phone (10 digits required)");
            phoneInputFeedback.setVisible(true);
            phoneInputFeedback.setStyle("-fx-text-fill: #E75D48;"); // Red color
            isValid = false;
        } else {
            phoneInputFeedback.setVisible(false);
        }

        String payment = paymentInput.getValue();
        if (payment == null) {
            isValid = false;
            System.out.println("Payment type not selected");
        }

        // Final Processing
        if (isValid) {
            System.out.println("Registration Valid!");
            System.out.println("Name: " + name);
            System.out.println("Phone: " + phone);
            System.out.println("Payment: " + payment);

            if(appContext.existsByPhone(phone)){
                // here, the user already exists in the entry. just update fields
                appContext.updateCustomer(phone, name, PaymentType.valueOf(payment.toUpperCase()));
            } else { // create new customer entry as normal
                appContext.initializeSessionCustomerWithoutExistingNumber(phone, name, PaymentType.valueOf(payment.toUpperCase()));
            }
            uiController.showMenuScreen();
        }
    }
}