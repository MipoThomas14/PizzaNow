package edu.ksu.pizzanow.ui.controller;

import edu.ksu.pizzanow.application.AppContext;
import edu.ksu.pizzanow.domain.model.Item;
import edu.ksu.pizzanow.ui.UIController;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartController {

    private UIController uiController;
    private AppContext appContext;

    @FXML
    private VBox itemContainer;

    @FXML
    private Label lblSubtotal;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnCheckout;

    // Formatter for currency
    private static final DecimalFormat df = new DecimalFormat("$0.00");

    public void setUiController(UIController uiController) {
        this.uiController = uiController;
    }

    public void setAppContext(AppContext appContext) {
        this.appContext = appContext;
        renderCart();
    }

    @FXML
    private void onBackClicked() {
        if (uiController != null) {
            uiController.showMenuScreen();
        }
    }

    @FXML
    private void onPaymentClicked() {
        System.out.println("Proceeding to Payment...");
        if (uiController != null) {
            uiController.showPaymentScreen();
        }    
    }

    private void renderCart() {
        itemContainer.getChildren().clear();

        if (appContext == null || appContext.getSessionOrder() == null) {
            lblSubtotal.setText("$0.00");
            return;
        }

        List<Item> rawItems = appContext.getSessionOrder().getOrderItems();

        // Group Identical Items
        Map<String, List<Item>> groupedItems = new HashMap<>();
        for (Item item : rawItems) {
            String signature = item.toString();
            groupedItems.computeIfAbsent(signature, k -> new ArrayList<>()).add(item);
        }

        // Build Rows
        for (String signature : groupedItems.keySet()) {
            List<Item> group = groupedItems.get(signature);
            Item representative = group.get(0);

            int quantity = group.size();
            double unitPrice = representative.getPrice();
            double lineTotal = unitPrice * quantity;

            HBox row = createCartRow(representative, quantity, unitPrice, lineTotal, group);
            itemContainer.getChildren().add(row);
        }

        // Update Footer
        lblSubtotal.setText(df.format(appContext.getSessionOrder().getSubTotal()));
    }

    private HBox createCartRow(Item item, int quantity, double unitPrice, double lineTotal, List<Item> allItemsInGroup) {
        HBox row = new HBox();
        row.setAlignment(Pos.CENTER_LEFT);
        row.setSpacing(10);
        row.setPadding(new javafx.geometry.Insets(20, 30, 20, 0));
        row.setStyle("-fx-border-color: #000000; -fx-border-width: 0 0 1 0;");

        // --- 1. Item Details ---
        VBox detailsBox = new VBox(5);
        detailsBox.setAlignment(Pos.CENTER_LEFT);
        detailsBox.setPrefWidth(350);
        HBox.setHgrow(detailsBox, Priority.ALWAYS);

        Label nameLabel = new Label(item.toString());
        nameLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
        nameLabel.setTextFill(Color.BLACK);
        nameLabel.setWrapText(true);
        detailsBox.getChildren().add(nameLabel);

        // --- 2. Unit Price (CENTERED) ---
        Label priceLabel = new Label(df.format(unitPrice));
        priceLabel.setFont(Font.font("Segoe UI", 16));
        priceLabel.setTextFill(Color.BLACK);
        priceLabel.setPrefWidth(100);
        priceLabel.setAlignment(Pos.CENTER); // Fixed Alignment

        // --- 3. Quantity (CENTERED) ---
        HBox qtyBox = new HBox(10);
        qtyBox.setAlignment(Pos.CENTER); // Fixed Alignment
        qtyBox.setPrefWidth(120);

        Label qtyNum = new Label(String.valueOf(quantity));
        qtyNum.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        qtyNum.setTextFill(Color.BLACK);
        qtyNum.setStyle("-fx-border-color: #000000; -fx-border-radius: 5; -fx-padding: 5 15;");
        qtyBox.getChildren().add(qtyNum);

        // --- 4. Line Total (CENTERED) ---
        Label totalLabel = new Label(df.format(lineTotal));
        totalLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        totalLabel.setTextFill(Color.BLACK);
        totalLabel.setPrefWidth(100);
        totalLabel.setAlignment(Pos.CENTER); // Fixed Alignment

        // --- 5. Remove Button ---
        Region spacer = new Region();
        spacer.setPrefWidth(20);

        Button removeBtn = new Button("Remove");
        removeBtn.setStyle("-fx-background-color: #E75D48; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5; -fx-cursor: hand;");
        removeBtn.setPrefWidth(80);

        removeBtn.setOnAction(e -> {
            for (Item i : allItemsInGroup) {
                appContext.getSessionOrder().removeItem(i);
            }
            renderCart();
        });

        row.getChildren().addAll(detailsBox, priceLabel, qtyBox, totalLabel, spacer, removeBtn);
        return row;
    }
}