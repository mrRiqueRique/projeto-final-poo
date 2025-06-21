package projetofinal.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane; 
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import projetofinal.model.Aluno;
import projetofinal.model.AlunoRepository;

import java.io.IOException;

/**
 * Controlador da interface gráfica da tela de editar perfil.
 * Responsável por alterar as informações do usuário.
 */
public class EditarPerfilController {

    @FXML private TextField nomeTextField;
    @FXML private TextField cursoTextField;
    @FXML private TextField crTextField;
    @FXML private Button salvarButton;
    @FXML private Button voltarButton;

    private Aluno alunoAtual;

    /**
     * Inicializa o controller com os dados do aluno a ser editado.
     * @param aluno O aluno logado.
     */
    public void initData(Aluno aluno) {
        this.alunoAtual = aluno;
        nomeTextField.setText(aluno.getNome());
        cursoTextField.setText(aluno.getCurso());
        crTextField.setText(String.valueOf(aluno.getCR()));
    }

    /**
     * Manipula a ação do botão "Salvar".
     * Valida os dados, atualiza o objeto Aluno e o salva no repositório.
     * @param event O evento do clique.
     */
    @FXML
    void handleSalvar(ActionEvent event) {
        if (nomeTextField.getText().isEmpty() || cursoTextField.getText().isEmpty() || crTextField.getText().isEmpty()) {
            showAlert(AlertType.ERROR, "Erro de Validação", "Todos os campos devem ser preenchidos.");
            return;
        }

        try {
            double novoCR = Double.parseDouble(crTextField.getText().replace(",", "."));

            // Cria um novo objeto Aluno com os dados atualizados para passar ao repositório
            Aluno alunoAtualizado = new Aluno(alunoAtual.getRa(), nomeTextField.getText(), cursoTextField.getText());
            alunoAtualizado.setCR(novoCR);
            
            // Aqui mantemos as disciplinas e outras informações do aluno original
            alunoAtualizado.getDisciplinas().addAll(alunoAtual.getDisciplinas());

            // Atualiza no repositório
            AlunoRepository.getInstancia().atualizarAluno(alunoAtual, alunoAtualizado);

            showAlert(AlertType.INFORMATION, "Sucesso", "Perfil atualizado com sucesso!");

            // Volta para a tela de perfil
            carregarTela(event, "/telas/Perfil.fxml", "Perfil - Gerenciador de Estudos");

        } catch (NumberFormatException e) {
            showAlert(AlertType.ERROR, "Erro de Formato", "O valor do CR deve ser um número válido.");
        }
    }

    /**
     * Manipula a ação do botão "Voltar", retornando para a tela de perfil.
     * @param event O evento do clique.
     */
    @FXML
    void handleVoltar(ActionEvent event) {
        carregarTela(event, "/telas/Perfil.fxml", "Perfil - Gerenciador de Estudos");
    }
    
    /**
     * Cria e exibe um alerta modal com um estilo CSS personalizado.
     * @param alertType O tipo de alerta (INFORMATION, WARNING, etc.).
     * @param title O título da janela do alerta.
     * @param message A mensagem principal a ser exibida.
     */
    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null); 
        alert.setContentText(message);
        alert.setGraphic(null);

        try {
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(
                getClass().getResource("/style/dialog-style.css").toExternalForm());
        } catch (Exception e) {
            System.err.println("Erro ao carregar o CSS do diálogo: " + e.getMessage());
        }
        
        alert.showAndWait();
    }

    /**
     * Método genérico para carregar uma nova tela FXML na mesma janela.
     * @param event O evento que disparou a ação, usado para obter o Stage atual.
     * @param fxmlPath O caminho para o arquivo FXML a ser carregado.
     * @param title O título da nova janela.
     */
    private void carregarTela(ActionEvent event, String fxmlPath, String title) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Scene scene = new Scene(loader.load(), 1440, 810);
            scene.getStylesheets().add(getClass().getResource("/style/botao-personalizado.css").toExternalForm());
            stage.setScene(scene);
            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            System.err.println("Falha ao carregar a tela: " + fxmlPath);
            e.printStackTrace();
        }
    }
}