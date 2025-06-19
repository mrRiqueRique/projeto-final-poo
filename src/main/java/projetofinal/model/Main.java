package projetofinal.model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/telas/Perfil.fxml"));
        Scene scene = new Scene(loader.load(), 1440, 810);
        scene.getStylesheets().add(getClass().getResource("/style/botao-personalizado.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("/style/botao-voltar.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("/style/circle-checkbox.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("/style/botao-prioridade.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Trabalho Final");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}