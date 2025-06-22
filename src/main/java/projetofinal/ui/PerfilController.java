package projetofinal.ui;

import java.io.IOException;
import java.util.List;
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
import javafx.scene.control.MenuButton; 

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import projetofinal.model.Aluno;
import projetofinal.model.AlunoLogado;
import projetofinal.model.Disciplina;

/**
 * Controlador da interface gráfica da tela de perfil.
 * Responsável por gerenciar ações dos botões e exibir informações do usuário.
 */
public class PerfilController {

    @FXML private Button voltarButton;
    @FXML private Button sairButton;
    @FXML private Circle perfilImage;
    @FXML private Label nomeText;
    @FXML private Label cursoText;
    @FXML private Label crText;
    @FXML private VBox disciplinasAtuaisVBox;
    @FXML private Button editarPerfilButton;
    @FXML private MenuButton menuButton; 

    private Aluno alunoLogado;

    /**
     * Método de inicialização do JavaFX.
     * Busca os dados do aluno logado e preenche todos os campos da tela,
     * incluindo a lista de disciplinas.
     */
    @FXML
    public void initialize() {
    this.alunoLogado = AlunoLogado.getInstance().getAluno();

    if (alunoLogado != null) {
        
        // 1. Preenche as informações de texto do aluno
        nomeText.setText(alunoLogado.getNome());
        cursoText.setText(alunoLogado.getCurso());
        crText.setText("CR: " + String.format("%.2f", alunoLogado.getCR()));

        // 2. Inicia a variável 'imagem' com a imagem PADRÃO.
        // Garantimos que ela nunca será nula.
        Image imagem = new Image(getClass().getResourceAsStream("/images/unicamp.png"));

        // 3. Tenta carregar a foto específica do aluno para sobrescrever a padrão
        String caminhoFoto = alunoLogado.getCaminhoFoto();
        if (caminhoFoto != null && !caminhoFoto.isEmpty()) {
            try {
                Image fotoAluno = new Image(getClass().getResourceAsStream("/" + caminhoFoto));
                // Se a imagem do aluno foi carregada sem erros, ela se torna a imagem principal
                if (!fotoAluno.isError()) {
                    imagem = fotoAluno;
                }
            } catch (Exception e) {
                // Se der erro ao carregar a foto do aluno, não fazemos nada,
                // pois a 'imagem' padrão já está carregada. Apenas registramos o erro.
                System.err.println("Falha ao carregar imagem de perfil do aluno. Usando imagem padrão. Erro: " + e.getMessage());
            }
        }
        
        // 4. Aplica a imagem final.
        perfilImage.setFill(new ImagePattern(imagem, 0, 0, 1, 1, true));

        // Limpa o VBox para garantir que não haja itens de uma execução anterior.
        disciplinasAtuaisVBox.getChildren().clear();

        List<Disciplina> disciplinas = alunoLogado.getDisciplinas();

        if (disciplinas != null && !disciplinas.isEmpty()) {
            for (Disciplina disciplina : disciplinas) {
                Label disciplinaLabel = new Label("• " + disciplina.getNome());
                disciplinaLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #333333;");
                disciplinaLabel.setWrapText(true);
                disciplinasAtuaisVBox.getChildren().add(disciplinaLabel);
            }
        } else {
            Label nenhumaDisciplinaLabel = new Label("Nenhuma disciplina cadastrada.");
            nenhumaDisciplinaLabel.setStyle("-fx-text-fill: #888888; -fx-font-style: italic;");
            disciplinasAtuaisVBox.getChildren().add(nenhumaDisciplinaLabel);
        }

    } else {
        // Caso nenhum aluno seja encontrado (ou ninguém tenha feito login)
        nomeText.setText("[Aluno não logado]");
        cursoText.setText("[N/A]");
        crText.setText("[N/A]");
        editarPerfilButton.setDisable(true);
        System.err.println("Nenhum aluno logado no sistema. A tela de perfil não pode ser carregada corretamente.");

        // Carrega e exibe a imagem padrão quando não há ninguém logado.
        try {
            Image imagemPadrao = new Image(getClass().getResourceAsStream("/images/unicamp.png"));
            if (!imagemPadrao.isError()) {
                perfilImage.setFill(new ImagePattern(imagemPadrao, 0, 0, 1, 1, true));
            }
        } catch (Exception e) {
            System.err.println("Falha ao carregar a imagem padrão: " + e.getMessage());
        }
    }
}

    /**
     * Manipula a ação do botão "Voltar", retornando para a tela de perfil.
     * @param event O evento do clique.
     */
    @FXML
    void handleVoltar(ActionEvent event) {
        System.out.println("Botão Voltar foi clicado!");
        irParaDashboard(event);
    }

    /**
     * Manipula o evento de clique do botão "Sair".
     * @param event O evento de ação que acionou este método.
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
            dialogPane.getStylesheets().add(getClass().getResource("/style/dialog-style.css").toExternalForm());
        } catch (Exception e) {
            System.err.println("Erro ao carregar o CSS do diálogo: " + e.getMessage());
        }

        Optional<ButtonType> resultado = alert.showAndWait();

        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            AlunoLogado.getInstance().logout();
            carregarTela(event, "/telas/Login.fxml", "Login - Gerenciador de Estudos");
        }
    }

    /**
     * Manipula o evento de clique do botão "Editar Perfil".
     * @param event O evento de ação que acionou este método.
     */
    @FXML
    void handleEditarPerfil(ActionEvent event) {
        if (alunoLogado == null) {
            showAlert(AlertType.ERROR, "Erro", "Não foi possível editar o perfil.", "Nenhum aluno está carregado no sistema.");
            return;
        }

        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/telas/EditarPerfil.fxml"));

            Scene scene = new Scene(loader.load(), 1440, 810);
            scene.getStylesheets().add(getClass().getResource("/style/botao-personalizado.css").toExternalForm());

            EditarPerfilController editarController = loader.getController();
            editarController.initData(alunoLogado);

            stage.setScene(scene);
            stage.setTitle("Editar Perfil - Gerenciador de Estudos");
            stage.show();

        } catch (IOException e) {
            System.err.println("Falha ao carregar a tela de edição de perfil: " + e.getMessage());
            e.printStackTrace();
        }
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
            Stage stage = (Stage) menuButton.getScene().getWindow();
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

    /**
     * Cria e exibe um alerta modal com um estilo CSS personalizado.
     * @param alertType O tipo de alerta (INFORMATION, WARNING, etc.).
     * @param title O título da janela do alerta.
     * @param message A mensagem principal a ser exibida.
     */
    private void showAlert(AlertType alertType, String title, String header, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null); // Remove o cabeçalho secundário
        alert.setContentText(message);
        alert.setGraphic(null); // Remove o ícone padrão

         try {
             DialogPane dialogPane = alert.getDialogPane();
             dialogPane.getStylesheets().add(
                 getClass().getResource("/style/dialog-style.css").toExternalForm());
         } catch (Exception e) {
             System.err.println("Erro ao carregar o CSS do diálogo: " + e.getMessage());
         }

        alert.showAndWait();
    }
}