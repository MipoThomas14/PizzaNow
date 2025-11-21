package edu.ksu.pizzanow;

import edu.ksu.pizzanow.ui.UIController;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {

    private UIController uiController;

    @Override
    public void start(Stage primaryStage) {
        uiController = new UIController();
        uiController.init(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
