package edu.ksu.pizzanow.ui.controller;

import edu.ksu.pizzanow.application.AppContext;
import edu.ksu.pizzanow.domain.model.Item;
import edu.ksu.pizzanow.domain.type.OrderType;
import edu.ksu.pizzanow.domain.type.PaymentType;
import edu.ksu.pizzanow.ui.UIController;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.text.DecimalFormat;
import java.util.List;
import java.io.IOException;

public class PaymentController {

    private UIController uiController;
    private AppContext appContext;
    private static final DecimalFormat df = new DecimalFormat("$0.00");

    // Left Section
    @FXML private VBox summaryContainer;
    @FXML private Label lblSubtotal;
    @FXML private Label lblTax;
    @FXML private Label lblTotal;

    // Right Section
    @FXML private Button btnPickup;
    @FXML private Button btnDelivery;
    @FXML private Button btnCash;
    @FXML private Button btnDebit;
    @FXML private Button btnCredit;
    @FXML private Button btnConfirm;

    // Selection State
    private OrderType selectedOrderType = null;
    private PaymentType selectedPaymentType = null;

    // Style Constants
    private static final String STYLE_UNSELECTED = "-fx-background-color: #f9f9f9; -fx-text-fill: #333; -fx-border-color: #ddd; -fx-border-radius: 10; -fx-background-radius: 10; -fx-font-size: 15px; -fx-cursor: hand;";
    private static final String STYLE_SELECTED = "-fx-background-color: #FFF5F4; -fx-text-fill: #E75D48; -fx-border-color: #E75D48; -fx-border-width: 2; -fx-border-radius: 10; -fx-background-radius: 10; -fx-font-size: 15px; -fx-cursor: hand; -fx-font-weight: bold;";

    public void setUiController(UIController uiController) {
        this.uiController = uiController;
    }

    public void setAppContext(AppContext appContext) {
        this.appContext = appContext;
        
        // Initialize Data
        renderOrderSummary();
        updateFinancials();
        
        // Reset selections on load
        selectedOrderType = null;
        selectedPaymentType = null;
        updateButtonStyles();
    }

    private void renderOrderSummary() {
        summaryContainer.getChildren().clear();
        if (appContext == null || appContext.getSessionOrder() == null) return;

        List<Item> items = appContext.getSessionOrder().getOrderItems();
        for (Item item : items) {
            HBox row = new HBox();
            row.setAlignment(Pos.CENTER_LEFT);
            
            Label name = new Label(item.toString());
            name.setTextFill(Color.BLACK);
            name.setFont(Font.font("Segoe UI", 14));
            name.setWrapText(true);
            name.setPrefWidth(250);
            HBox.setHgrow(name, Priority.ALWAYS);

            Label price = new Label(df.format(item.getPrice()));
            price.setTextFill(Color.BLACK);
            price.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
            price.setMinWidth(60);
            price.setAlignment(Pos.CENTER_RIGHT);

            row.getChildren().addAll(name, price);
            summaryContainer.getChildren().add(row);
        }
    }

    private void updateFinancials() {
        if (appContext == null || appContext.getSessionOrder() == null) {
            lblSubtotal.setText("$0.00");
            lblTax.setText("$0.00");
            lblTotal.setText("$0.00");
            return;
        }
        
        double sub = appContext.getSessionOrder().getSubTotal();
        double tax = appContext.getSessionOrder().getTax();
        double total = appContext.getSessionOrder().getTotal();

        lblSubtotal.setText(df.format(sub));
        lblTax.setText(df.format(tax));
        lblTotal.setText(df.format(total));
    }


    @FXML
    private void onPickupClicked() {
        selectedOrderType = OrderType.PICKUP;
        updateButtonStyles();
    }

    @FXML
    private void onDeliveryClicked() {
        selectedOrderType = OrderType.DELIVERY;
        updateButtonStyles();
    }

    // ===== ACTIONS: PAYMENT TYPE =====

    @FXML
    private void onCashClicked() {
        selectedPaymentType = PaymentType.CASH;
        updateButtonStyles();
    }

    @FXML
    private void onDebitClicked() {
        selectedPaymentType = PaymentType.DEBIT;
        updateButtonStyles();
    }

    @FXML
    private void onCreditClicked() {
        selectedPaymentType = PaymentType.CREDIT;
        updateButtonStyles();
    }


    private void updateButtonStyles() {
        btnPickup.setStyle(selectedOrderType == OrderType.PICKUP ? STYLE_SELECTED : STYLE_UNSELECTED);
        btnDelivery.setStyle(selectedOrderType == OrderType.DELIVERY ? STYLE_SELECTED : STYLE_UNSELECTED);

        btnCash.setStyle(selectedPaymentType == PaymentType.CASH ? STYLE_SELECTED : STYLE_UNSELECTED);
        btnDebit.setStyle(selectedPaymentType == PaymentType.DEBIT ? STYLE_SELECTED : STYLE_UNSELECTED);
        btnCredit.setStyle(selectedPaymentType == PaymentType.CREDIT ? STYLE_SELECTED : STYLE_UNSELECTED);
    }

    @FXML
    private void onBackToCartClicked() {
        if (uiController != null) {
            uiController.showCartScreen();
        }
    }

    @FXML
    private void onConfirmClicked() throws IOException {
        // Validation
        if (selectedOrderType == null) {
            showAlert("Missing Information", "Please select an Order Type (Pickup or Delivery).");
            return;
        }
        if (selectedPaymentType == null) {
            showAlert("Missing Information", "Please select a Payment Method.");
            return;
        }

        appContext.getSessionOrder().setOrderType(selectedOrderType);
        appContext.getSessionOrder().setPaymentType(selectedPaymentType);

        String receipt = appContext.finalizeSessionOrder();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Order Confirmed");
        alert.setHeaderText("Thank you for your order!");
        alert.setContentText(receipt);
        alert.showAndWait();

        if (uiController != null) {
            uiController.showHomeScreen(); 
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}