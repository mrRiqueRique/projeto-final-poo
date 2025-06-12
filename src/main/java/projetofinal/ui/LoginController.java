package projetofinal.ui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private Label bemVindoLabel;

    @FXML
    private TextField nomeField;

    @FXML
    private PasswordField senhaField;

    @FXML
    private Label messageLabel;

    
    @FXML
    public void initialize() {
        Font minhaFonte = Font.loadFont(getClass().getResourceAsStream("/fonts/ComicRelief-Bold.ttf"), 35);
        bemVindoLabel.setFont(minhaFonte);
    }
    
    @FXML
    private void handleEntrar() {
        // inserir logica de ir para a tela principal
        System.out.println("Entrou\n");
    }

    @FXML
    private void handleCadastro(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Cadastro.fxml"));
            Scene scene = new Scene(loader.load(), 1440, 810);
            scene.getStylesheets().add(getClass().getResource("/styleButton.css").toExternalForm());

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Trabalho Final");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
