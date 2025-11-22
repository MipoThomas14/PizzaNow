package edu.ksu.pizzanow.ui;

import edu.ksu.pizzanow.application.AppContext;
import edu.ksu.pizzanow.domain.type.OrderType;
import edu.ksu.pizzanow.ui.controller.HomeController;
import edu.ksu.pizzanow.ui.controller.MenuController;
import edu.ksu.pizzanow.ui.controller.RegistrationController;
// import edu.ksu.pizzanow.ui.controller.MenuController;
// import edu.ksu.pizzanow.ui.controller.CartController;
// import edu.ksu.pizzanow.ui.controller.CheckoutController;
// import edu.ksu.pizzanow.ui.controller.ConfirmationController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class UIController {
    private final AppContext appContext;
    private Stage primaryStage;


    // constructors
    public UIController() {
        this(new AppContext());
    }

    public UIController(AppContext appContext) {
        this.appContext = appContext;
    }

    public void init(Stage primaryStage) {
        if (this.primaryStage != null) {
            throw new IllegalStateException("UIController has already been initialized.");
        }
        this.primaryStage = Objects.requireNonNull(primaryStage, "primaryStage must not be null");
        this.primaryStage.setTitle("PizzaNow");
        showHomeScreen();
    }

    public AppContext getAppContext() {
        return appContext;
    }


    // navigation
    public void showHomeScreen() {
        HomeController controller = loadScene("/fxml/Home.fxml");
        controller.setUiController(this);
        controller.setAppContext(appContext);
    }

    public void showRegistrationScreen() {
        RegistrationController controller = loadScene("/fxml/Registration.fxml");
        controller.setUiController(this);
        controller.setAppContext(appContext);
    }

    public void showMenuScreen() {
        if(appContext.getSessionCustomer() != null){
            appContext.newSessionOrder(OrderType.None);

            MenuController controller = loadScene("/fxml/Menu.fxml");
            controller.setUiController(this);
            controller.setAppContext(appContext);
        }

    }

    // public void showCartScreen() {
    //     CartController controller = loadScene("/edu/ksu/pizzanow/ui/Cart.fxml");
    //     controller.setUiController(this);
    //     controller.setAppContext(appContext);
    // }

    // public void showCheckoutScreen() {
    //     CheckoutController controller = loadScene("/edu/ksu/pizzanow/ui/Checkout.fxml");
    //     controller.setUiController(this);
    //     controller.setAppContext(appContext);
    // }

    // public void showConfirmationScreen() {
    //     ConfirmationController controller = loadScene("/edu/ksu/pizzanow/ui/Confirmation.fxml");
    //     controller.setUiController(this);
    //     controller.setAppContext(appContext);
    // }


    public void resetSessionAndGoHome() {
        appContext.clearSessionOrder();
        appContext.clearSessionCustomer();
        showHomeScreen();
    }


    // internal helpers
    private <T> T loadScene(String fxmlPath) {
        if (primaryStage == null) {
            throw new IllegalStateException("UIController has not been initialized with a primary stage.");
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
            return loader.getController();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load FXML: " + fxmlPath, e);
        }
    }
}
