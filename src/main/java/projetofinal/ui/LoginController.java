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
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import projetofinal.model.AlunoLogado;
import javafx.scene.image.ImageView;


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
    private ImageView fotoView;

    private AlunoLogado alunoLogado = AlunoLogado.getInstance();
    
    @FXML
    public void initialize() {
        Font minhaFonte = Font.loadFont(getClass().getResourceAsStream("/fonts/Raleway/static/Raleway-ExtraBold.ttf"), 40);
        bemVindoLabel.setFont(minhaFonte);

        Image imagem = new Image(getClass().getResource("/images/unicamp.png").toExternalForm());
        fotoView.setImage(imagem);
    }

    @FXML private void handleEntrar(ActionEvent event) {
        
        try {
            String username = nomeField.getText();
            String password = senhaField.getText(); // Senha é ignorada nesse exemplo

            // Verifica se os campos estão preenchidos
            if (username.isEmpty() || password.isEmpty()) {
                messageLabel.setText("Preencha usuário e senha");
                return;
            }

            Boolean loginStatus = alunoLogado.logarAluno(username, password);
            if (!loginStatus) {
                messageLabel.setText("Usuário ou senha inválidos");
                return;
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/telas/Dashboard.fxml"));
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

    @FXML
    private void handleCadastro(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/telas/Cadastro.fxml"));
            Scene scene = new Scene(loader.load(), 1440, 810);
            scene.getStylesheets().add(getClass().getResource("/style/botao-personalizado.css").toExternalForm());
            scene.getStylesheets().add(getClass().getResource("/style/botao-voltar.css").toExternalForm());

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Trabalho Final");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
