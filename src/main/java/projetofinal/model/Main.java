package projetofinal.model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/LoginView.fxml"));
        primaryStage.setTitle("Marketplace");
        primaryStage.setScene(new Scene(root, 1440, 810));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}