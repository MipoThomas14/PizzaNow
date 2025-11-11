module edu.ksu.pizzanow {
  requires javafx.base;
  requires javafx.controls;
  requires javafx.fxml;
  requires transitive javafx.graphics;

  opens edu.ksu.pizzanow.ui to javafx.fxml;
  exports edu.ksu.pizzanow;
}
