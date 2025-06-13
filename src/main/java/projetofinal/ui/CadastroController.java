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

public class CadastroController {

    @FXML
    private Label cadastroLabel;

    @FXML
    private TextField nomeField;

    @FXML
    private PasswordField senhaField;

    @FXML
    private Label messageLabel;
    
    @FXML
    public void initialize() {
        Font minhaFonte = Font.loadFont(getClass().getResourceAsStream("/fonts/ComicRelief-Bold.ttf"), 35);
        cadastroLabel.setFont(minhaFonte);
    }
    
    @FXML
    private void handleSubirFoto() {
        // inserir logica de captar a foto
        System.out.println("Foto\n");
    }
    @FXML
    private void handleCriarConta(ActionEvent event) {
        String username = nomeField.getText();
        String password = senhaField.getText(); // Senha é ignorada nesse exemplo

        // Verifica se os campos estão preenchidos
        if (username.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Preencha usuário e senha");
            return;
        }
    }

    @FXML
    private void handleVoltar(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
            Scene scene = new Scene(loader.load(), 1440, 810);
            scene.getStylesheets().add(getClass().getResource("/botao-personalizado.css").toExternalForm());
            scene.getStylesheets().add(getClass().getResource("/botao-padrao.css").toExternalForm());

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Trabalho Final");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
