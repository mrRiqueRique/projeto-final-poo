package projetofinal.ui;

import java.io.IOException;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 * Controlador da interface gráfica da tela de perfil.
 * Responsável por gerenciar ações dos botões e exibir informações do usuário.
 */
public class PerfilController {

    @FXML
    private Button voltarButton;

    @FXML
    private Button sairButton;

    @FXML
    private Circle perfilImage;

    @FXML
    private Label nomeText;

    @FXML
    private Label cursoText;

    @FXML
    private Label crText;

    @FXML
    private VBox disciplinasAtuaisVBox;

    @FXML
    private Button editarPerfilButton;

    /**
     * Manipula a ação do botão "Voltar".
     * Exibe uma mensagem no console e contém um comentário indicando implementação futura.
     *
     * @param event o evento de clique do botão
     */
    @FXML
    void handleVoltar(ActionEvent event) {
        System.out.println("Botão Voltar foi clicado!");
        // todo - lógica para voltar para a tela anterior.
    }

    /**
     * Manipula a ação do botão "Sair".
     * Exibe uma caixa de diálogo de confirmação para o usuário. Se confirmado,
     * redireciona para a tela de login. Caso contrário, permanece na tela atual.
     *
     * @param event o evento de clique do botão
     */
    @FXML
    void handleSair(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmação de Saída");
        alert.setHeaderText("Tem certeza que deseja sair?");
        alert.setContentText("Você será redirecionado para a tela de login.");

        alert.setGraphic(null);

        try {
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(
                    getClass().getResource("/style/dialog-style.css").toExternalForm());
            dialogPane.getStyleClass().add("my-dialog");
        } catch (Exception e) {
            System.err.println("Erro ao carregar o CSS do diálogo: " + e.getMessage());
        }

        Optional<ButtonType> resultado = alert.showAndWait();

        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            System.out.println("Usuário confirmou a saída. Voltando para a tela de login...");
            carregarTela(event, "/telas/Login.fxml", "Login - Gerenciador de Estudos");
        } else {
            System.out.println("Usuário cancelou a saída.");
        }
    }

    /**
     * Manipula a ação do botão "Editar Perfil".
     * Exibe uma mensagem no console e contém um comentário indicando implementação futura.
     *
     * @param event o evento de clique do botão
     */
    @FXML
    void handleEditarPerfil(ActionEvent event) {
        System.out.println("Botão Editar Perfil foi clicado!");
        // todo - editar perfil
    }


    @FXML
    private void irParaDashboard(ActionEvent event) {
        carregarTela(event, "/telas/Dashboard.fxml", "Dashboard - Gerenciador de Estudos");
    }

    @FXML
    private void irParaDisciplinas(ActionEvent event) {
        carregarTela(event, "/telas/Disciplinas.fxml", "Disciplinas - Gerenciador de Estudos");
    }

    @FXML
    private void irParaAulas(ActionEvent event) {
        carregarTela(event, "/telas/Aulas.fxml", "Aulas - Gerenciador de Estudos");
    }

    @FXML
    private void irParaTarefas(ActionEvent event) {
        carregarTela(event, "/telas/Tarefas.fxml", "Tarefas - Gerenciador de Estudos");
    }

    @FXML
    private void irParaCadastrarDisciplina(ActionEvent event) {
        carregarTela(event, "/telas/CadastrarDisciplina.fxml", "Cadastrar Disciplina - Gerenciador de Estudos");
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