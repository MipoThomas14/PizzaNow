module edu.ksu.pizzanow {
  requires javafx.base;
  requires javafx.controls;
  requires javafx.fxml;
  requires transitive javafx.graphics;


  exports edu.ksu.pizzanow;
  exports edu.ksu.pizzanow.ui;

  opens edu.ksu.pizzanow.ui.controller to javafx.fxml;
}