package edu.ksu.pizzanow;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.Objects;

public class MainApp extends Application {
  // @Override
  // public void start(Stage stage) throws Exception {
  //   var url = Objects.requireNonNull(
  //       getClass().getResource("/fxml/Home.fxml"),
  //       "Missing /fxml/Home.fxml on classpath");
  //   var scene = new Scene(new FXMLLoader(url).load(), 900, 600);
  //   stage.setTitle("PizzaNow");
  //   stage.setScene(scene);
  //   stage.show();
  // }
  @Override public void start(javafx.stage.Stage s){
    s.setScene(new javafx.scene.Scene(new javafx.scene.control.Label("Hello JavaFX"), 400, 300));
    s.show();
  }


  public static void main(String[] args) { launch(args); }
}
