package projetofinal.model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
        Scene scene = new Scene(loader.load(), 1440, 810);
        scene.getStylesheets().add(getClass().getResource("/styleButton.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.setTitle("Trabalho Final");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}