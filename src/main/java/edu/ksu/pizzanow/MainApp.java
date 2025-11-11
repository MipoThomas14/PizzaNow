package edu.ksu.pizzanow;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class MainApp extends Application {

    @Override
    public void start(Stage stage) {
        Text text = new Text(10, 40, "Hello World!");
        text.setFont(new Font(40));
        Scene scene = new Scene(new Group(text));

        stage.setTitle("Welcome to JavaFX!"); 
        stage.setScene(scene); 
        stage.sizeToScene(); 
        stage.show(); 
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}





// import javafx.application.Application;
// import javafx.fxml.FXMLLoader;
// import javafx.scene.Scene;
// import javafx.stage.Stage;

// public class MainApp extends Application {
//   @Override
//   public void start(Stage stage) throws Exception {
//     var url = Objects.requireNonNull(
//         getClass().getResource("/fxml/Home.fxml"),
//         "Missing /fxml/Home.fxml on classpath");
//     var scene = new Scene(new FXMLLoader(url).load(), 900, 600);
//     stage.setTitle("PizzaNow");
//     stage.setScene(scene);
//     stage.show();
//   }


//   public static void main(String[] args) { 
//     Application.launch(args); 
//   }
// }
