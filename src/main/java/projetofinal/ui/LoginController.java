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

/**
 * Controlador responsável pela interface de login do sistema.
 * 
 * Essa classe gerencia os campos de entrada para nome de usuário e senha,
 * validação de login, exibição de mensagens de erro e navegação para telas
 * posteriores após autenticação.
 * 
 * Também inicializa elementos visuais como fonte customizada e imagem.
 */
public class LoginController {

    /** 
     * Label que exibe uma mensagem de boas-vindas ao usuário.
     * Sua fonte é configurada para uma fonte personalizada no método initialize.
     */
    @FXML
    private Label bemVindoLabel;

    /**
     * Campo de texto para o usuário digitar seu nome/login.
     */
    @FXML
    private TextField nomeField;

    /**
     * Campo de senha para o usuário digitar sua senha.
     * A senha é um campo protegido que oculta os caracteres digitados.
     */
    @FXML
    private PasswordField senhaField;

    /**
     * Label utilizado para exibir mensagens de erro ou informação ao usuário,
     * como "Usuário ou senha inválidos" ou "Preencha usuário e senha".
     */
    @FXML
    private Label messageLabel;

    /**
     * Componente gráfico ImageView que exibe uma imagem, neste caso,
     * um logo (ex: da Unicamp) carregado no método initialize.
     */
    @FXML
    private ImageView fotoView;

    /**
     * Instância singleton que representa o aluno logado.
     * Permite realizar ações como autenticar o usuário.
     */
    private AlunoLogado alunoLogado = AlunoLogado.getInstance();
    
    /**
     * Método chamado automaticamente após o carregamento do FXML e criação do controlador.
     * Aqui são inicializados elementos visuais:
     * - Carrega uma fonte customizada do arquivo e aplica ao label de boas-vindas.
     * - Carrega uma imagem da pasta resources e exibe no ImageView.
     */
    @FXML
    public void initialize() {
        // Carrega a fonte Raleway ExtraBold com tamanho 40
        Font minhaFonte = Font.loadFont(getClass().getResourceAsStream("/fonts/Raleway/static/Raleway-ExtraBold.ttf"), 40);
        bemVindoLabel.setFont(minhaFonte);

        // Carrega a imagem do logo e define na ImageView
        Image imagem = new Image(getClass().getResource("/images/unicamp.png").toExternalForm());
        fotoView.setImage(imagem);
    }

    /**
     * Método acionado quando o usuário clica no botão "Entrar" para realizar o login.
     * Realiza validações básicas, chama o método de autenticação e, se bem-sucedido,
     * troca a cena para o dashboard principal da aplicação.
     * 
     * @param event Evento de ação gerado pelo clique do usuário.
     */
    @FXML 
    private void handleEntrar(ActionEvent event) {
        try {
            // Obtém os textos digitados nos campos de usuário e senha
            String username = nomeField.getText();
            String password = senhaField.getText(); // Senha atualmente ignorada no exemplo

            // Verifica se os campos não estão vazios; se estiverem, exibe mensagem e retorna
            if (username.isEmpty() || password.isEmpty()) {
                messageLabel.setText("Preencha usuário e senha");
                return;
            }

            // Chama método para validar login; retorna true se sucesso, false caso contrário
            Boolean loginStatus = alunoLogado.logarAluno(username, password);
            if (!loginStatus) {
                // Se falhou login, exibe mensagem de erro
                messageLabel.setText("Usuário ou senha inválidos");
                return;
            }

            // Carrega a tela do Dashboard após login com sucesso
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/telas/Dashboard.fxml"));
            Scene novaCena = new Scene(loader.load(), 1440, 810);

            // Adiciona as folhas de estilo CSS necessárias para a nova cena
            novaCena.getStylesheets().add(getClass().getResource("/style/botao-personalizado.css").toExternalForm());
            novaCena.getStylesheets().add(getClass().getResource("/style/botao-voltar.css").toExternalForm());
            novaCena.getStylesheets().add(getClass().getResource("/style/circle-checkbox.css").toExternalForm());
            novaCena.getStylesheets().add(getClass().getResource("/style/botao-prioridade.css").toExternalForm());

            // Obtém a janela atual (Stage) a partir do evento e troca para a nova cena do Dashboard
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(novaCena);
            stage.setTitle("Trabalho Final");
            stage.show();

        } catch (IOException e) {
            // Caso haja erro no carregamento do FXML ou da cena, imprime a exceção para debug
            e.printStackTrace();
        }
    }

    /**
     * Método acionado quando o usuário clica no botão para ir para a tela de cadastro.
     * Realiza a troca da cena atual para a tela de cadastro de novo usuário.
     * 
     * @param event Evento de ação gerado pelo clique do usuário.
     */
    @FXML
    private void handleCadastro(ActionEvent event) {
        try {
            // Carrega o arquivo FXML da tela de cadastro
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/telas/Cadastro.fxml"));
            Scene scene = new Scene(loader.load(), 1440, 810);

            // Adiciona folhas de estilo CSS para manter padrão visual
            scene.getStylesheets().add(getClass().getResource("/style/botao-personalizado.css").toExternalForm());
            scene.getStylesheets().add(getClass().getResource("/style/botao-voltar.css").toExternalForm());

            // Obtém o stage atual e troca a cena para a tela de cadastro
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Trabalho Final");
            stage.show();

        } catch (IOException e) {
            // Caso erro no carregamento, imprime a stack trace para ajudar no debug
            e.printStackTrace();
        }
    }
}

