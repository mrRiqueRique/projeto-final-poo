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
        Font minhaFonte = Font.loadFont(getClass().getResourceAsStream("/fonts/Raleway/static/Raleway-ExtraBold.ttf"), 40);
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

     @FXML private void handleVoltar(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/telas/Login.fxml"));
            Scene novaCena = new Scene(loader.load(), 1440, 810);

            novaCena.getStylesheets().add(getClass().getResource("/style/botao-personalizado.css").toExternalForm());
            novaCena.getStylesheets().add(getClass().getResource("/style/botao-voltar.css").toExternalForm());
            novaCena.getStylesheets().add(getClass().getResource("/style/circle-checkbox.css").toExternalForm());
            novaCena.getStylesheets().add(getClass().getResource("/style/botao-prioridade.css").toExternalForm());

            // Obtém o Stage atual a partir do botão que disparou o evento
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(novaCena);
            stage.setTitle("Trabalho Final");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
