package projetofinal.ui;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import projetofinal.model.Aluno;
import projetofinal.model.AlunoRepository;

/**
 * Controlador da interface gráfica da tela de cadastro do usuário.
 * Responsável por coletar as informações do usuário necessárias para o cadastro.
 */
public class CadastroController {

    @FXML private Label cadastroLabel;
    @FXML private Label messageLabel;

    @FXML private TextField raField;
    @FXML private PasswordField senhaField;

    @FXML private TextField nomeCompletoField;
    @FXML private TextField cursoField;
    @FXML private TextField crField;
    @FXML private ImageView fotoPreview;

    private File fotoSelecionada;
    private AlunoRepository alunoRepository;

    /**
     * Método de inicialização do JavaFX.
     * Configura a fonte personalizada para o título, obtém a instância do repositório
     * de alunos e define uma imagem padrão para a pré-visualização da foto.
     */
    @FXML
    public void initialize() {
        Font minhaFonte = Font.loadFont(getClass().getResourceAsStream("/fonts/Raleway/static/Raleway-ExtraBold.ttf"), 40);
        cadastroLabel.setFont(minhaFonte);
        alunoRepository = AlunoRepository.getInstancia();

        // Define uma imagem padrão para o preview
        Image placeholder = new Image(getClass().getResourceAsStream("/images/unicamp.png")); // Use uma imagem sua se preferir
        fotoPreview.setImage(placeholder);
    }
    
    /**
     * Manipula o evento do botão "Subir Foto".
     * Abre um seletor de arquivos (FileChooser) para que o usuário possa escolher uma imagem
     * de perfil (PNG, JPG, JPEG). A imagem selecionada é então exibida na pré-visualização.
     */
    @FXML
    private void handleSubirFoto() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecione uma foto de perfil");
        // Filtra para aceitar apenas arquivos de imagem comuns
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Imagens", "*.png", "*.jpg", "*.jpeg")
        );
        
        // Abre o seletor de arquivos
        Stage stage = (Stage) fotoPreview.getScene().getWindow();
        fotoSelecionada = fileChooser.showOpenDialog(stage);

        if (fotoSelecionada != null) {
            // Exibe a imagem selecionada no ImageView
            Image imagemPerfil = new Image(fotoSelecionada.toURI().toString());
            fotoPreview.setImage(imagemPerfil);
            messageLabel.setText("Foto selecionada: " + fotoSelecionada.getName());
            messageLabel.setTextFill(Color.GREEN);
        }
    }

    /**
     * Manipula o evento de clique do botão "Criar Conta".
     * Realiza a validação dos campos de entrada (verifica se estão preenchidos,
     * se o RA já existe e se o CR é um número válido). Se a validação for bem-sucedida,
     * cria um novo objeto Aluno, salva a foto de perfil no sistema de arquivos,
     * persiste o novo aluno no repositório e, por fim, retorna à tela de login.
     * @param event O evento do clique.
     */
    @FXML
    private void handleCriarConta(ActionEvent event) {
        String ra = raField.getText();
        String nome = nomeCompletoField.getText();
        String curso = cursoField.getText();
        String crTexto = crField.getText();
        String password = senhaField.getText();

        // 1. Validação dos campos
        if (ra.isEmpty() || nome.isEmpty() || curso.isEmpty() || crTexto.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Todos os campos são obrigatórios!");
            messageLabel.setTextFill(Color.RED);
            return;
        }

        if (alunoRepository.buscarAlunoPorRa(ra) != null) {
            messageLabel.setText("O RA '" + ra + "' já está cadastrado.");
            messageLabel.setTextFill(Color.RED);
            return;
        }

        double crValor;
        try {
            crValor = Double.parseDouble(crTexto);
        } catch (NumberFormatException e) {
            messageLabel.setText("O valor do CR deve ser um número válido (ex: 251018).");
            messageLabel.setTextFill(Color.RED);
            return;
        }

        // 2. Criação do Aluno
        Aluno novoAluno = new Aluno(ra, nome, curso);
        novoAluno.setCR(crValor);
        
        // 3. Lógica para salvar a foto e armazenar seu caminho
        if (fotoSelecionada != null) {
            try {
                // Define a pasta de destino fora do projeto, na pasta do usuário
                String userHome = System.getProperty("user.home");
                Path destFolder = Paths.get(userHome, ".gerenciador-de-estudos", "profiles");

                // Cria as pastas se elas não existirem
                if (!Files.exists(destFolder)) {
                    Files.createDirectories(destFolder);
                }

                // Define o nome do arquivo e o caminho de destino final
                String extensao = fotoSelecionada.getName().substring(fotoSelecionada.getName().lastIndexOf("."));
                String nomeArquivo = ra + extensao;
                Path destPath = destFolder.resolve(nomeArquivo);

                // Copia o arquivo selecionado para o destino
                 Files.copy(fotoSelecionada.toPath(), destPath, StandardCopyOption.REPLACE_EXISTING);
 
                // Salva o caminho ABSOLUTO do arquivo no objeto Aluno
                novoAluno.setCaminhoFoto(destPath.toAbsolutePath().toString()); 

            } catch (IOException e) {
                e.printStackTrace();
                messageLabel.setText("Erro ao salvar a foto.");
                messageLabel.setTextFill(Color.RED);
                return; // Impede o cadastro se a foto não puder ser salva  
            }
        }
        
        // 4. Persistência do Aluno (agora com o caminho da foto)
        alunoRepository.adicionarAluno(novoAluno);
        
        // 5. Sucesso e Navegação
        messageLabel.setText("Conta criada com sucesso!");
        messageLabel.setTextFill(Color.GREEN);

        handleVoltar(event);
    }

    /**
     * Manipula o evento de clique do botão "Voltar", retornando para a tela de login.
     * @param event O evento do clique.
     */
     @FXML 
     private void handleVoltar(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/telas/Login.fxml"));
            Scene novaCena = new Scene(loader.load(), 1440, 810);

            novaCena.getStylesheets().add(getClass().getResource("/style/botao-personalizado.css").toExternalForm());
            novaCena.getStylesheets().add(getClass().getResource("/style/botao-voltar.css").toExternalForm());
            
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(novaCena);
            stage.setTitle("Trabalho Final");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}