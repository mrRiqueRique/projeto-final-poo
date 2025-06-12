package projetofinal.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;

public class CadastroController {

    @FXML
    private Label bemVindoLabel;

    @FXML
    private TextField nomeField;

    @FXML
    private PasswordField senhaField;

    @FXML
    private Label messageLabel;

    @FXML
    private void handleSubirFoto() {
        System.out.println("Arrumar depois\n");
    }

    @FXML
    public void initialize() {
        Font minhaFonte = Font.loadFont(getClass().getResourceAsStream("/fonts/ComicRelief-Bold.ttf"), 35);
        bemVindoLabel.setFont(minhaFonte);
    }

    @FXML
    private void handleCriarConta(ActionEvent event) {
        String username = nomeField.getText();
        String password = senhaField.getText(); // Senha é ignorada nesse exemplo

        // Verifica se os campos estão preenchidos
        if (username.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Preencha usuário e senha");
            System.out.println("Deu\n");
            return;
        }
    }


}
