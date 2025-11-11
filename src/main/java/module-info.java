module edu.ksu.pizzanow {
  requires javafx.base;
  requires javafx.graphics;
  requires javafx.controls;
  requires javafx.fxml;

  opens edu.ksu.pizzanow.ui to javafx.fxml;
  exports edu.ksu.pizzanow;
}
