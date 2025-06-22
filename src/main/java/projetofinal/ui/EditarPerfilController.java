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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
 * Controlador da interface gráfica da tela de editar perfil.
 * Responsável por gerenciar ações dos botões e editar informações do usuário.
 */
public class EditarPerfilController {


    @FXML private Label editarPerfilLabel;
    @FXML private Label messageLabel;
    @FXML private TextField nomeTextField;
    @FXML private TextField cursoTextField;
    @FXML private TextField crTextField;
    @FXML private ImageView fotoPreview;
    @FXML private Button alterarFotoButton;
    @FXML private Button salvarButton;
    @FXML private Button voltarButton;

    private Aluno alunoAtual;
    private File fotoSelecionada;


    /**
     * Método de inicialização do JavaFX.
     * Busca os dados do aluno logado e preenche todos os campos da tela,
     */
    @FXML
    public void initialize() {

        try {
            Font minhaFonte = Font.loadFont(getClass().getResourceAsStream("/fonts/Raleway/static/Raleway-ExtraBold.ttf"), 40);
            if (minhaFonte != null) {
                editarPerfilLabel.setFont(minhaFonte);
            }
        } catch (Exception e) {
            System.err.println("Erro ao carregar a fonte Raleway: " + e.getMessage());
        }
    }
    
    /**
     * Inicializa os dados do aluno atual na tela, preenchendo os campos com os valores atuais.
     * @param aluno Objeto do tipo Aluno que representa o aluno logado.
     */
    public void initData(Aluno aluno) {
        this.alunoAtual = aluno;
        nomeTextField.setText(aluno.getNome());
        cursoTextField.setText(aluno.getCurso());
        crTextField.setText(String.valueOf(aluno.getCR()));
        carregarImagemPerfil();
    }

    /**
     * Carrega a imagem de perfil do aluno, ou uma imagem padrão se nenhuma estiver definida.
     */
    private void carregarImagemPerfil() {
        Image imagem;
        if (alunoAtual.getCaminhoFoto() != null && !alunoAtual.getCaminhoFoto().isEmpty()) {
            imagem = new Image(getClass().getResourceAsStream("/" + alunoAtual.getCaminhoFoto()));
        } else {
            imagem = new Image(getClass().getResourceAsStream("/images/unicamp.png"));
        }
        fotoPreview.setImage(imagem);
    }
    
    /**
     * Manipula o evento de clique no botão "Alterar Foto", permitindo que o usuário selecione uma nova imagem.
     */
    @FXML
    void handleAlterarFoto() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecione uma nova foto de perfil");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imagens", "*.png", "*.jpg", "*.jpeg"));
        
        Stage stage = (Stage) alterarFotoButton.getScene().getWindow();
        File novaFoto = fileChooser.showOpenDialog(stage);

        if (novaFoto != null) {
            this.fotoSelecionada = novaFoto;
            fotoPreview.setImage(new Image(fotoSelecionada.toURI().toString()));
        }
    }

    /**
     * Manipula o evento de clique no botão "Salvar".
     * Valida os campos, atualiza os dados do aluno e salva a nova imagem de perfil (caso alterada).
     * @param event Evento de clique no botão.
     */
    @FXML
    void handleSalvar(ActionEvent event) {
        // Validação de campos básicos
        if (nomeTextField.getText().isEmpty() || cursoTextField.getText().isEmpty() || crTextField.getText().isEmpty()) {
            messageLabel.setText("Todos os campos devem ser preenchidos.");
            messageLabel.setTextFill(Color.RED);
            return;
        }

        try {
            double novoCR = Double.parseDouble(crTextField.getText().replace(",", "."));

            Aluno alunoAtualizado = new Aluno(alunoAtual.getRa(), nomeTextField.getText(), cursoTextField.getText());
            alunoAtualizado.setCR(novoCR);
            alunoAtualizado.getDisciplinas().addAll(alunoAtual.getDisciplinas());
            
            // Lógica para salvar a nova foto
            if (fotoSelecionada != null) {
                try {
                    String novoCaminho = salvarArquivoFoto(fotoSelecionada, alunoAtual.getRa());
                    alunoAtualizado.setCaminhoFoto(novoCaminho);
                } catch (IOException e) {
                    e.printStackTrace();
                    messageLabel.setText("Erro ao salvar a foto de perfil.");
                    messageLabel.setTextFill(Color.RED);
                    return;
                }
            } else {
                alunoAtualizado.setCaminhoFoto(alunoAtual.getCaminhoFoto());
            }

            // Atualiza no repositório
            AlunoRepository.getInstancia().atualizarAluno(alunoAtual, alunoAtualizado);

            messageLabel.setText("Perfil atualizado com sucesso!");
            messageLabel.setTextFill(Color.GREEN);

        } catch (NumberFormatException e) {
            messageLabel.setText("O valor do CR deve ser um número válido.");
            messageLabel.setTextFill(Color.RED);
        }
    }
    
    /**
     * Salva a imagem de perfil selecionada no diretório do projeto.
     *
     * @param foto Arquivo da nova foto selecionada.
     * @param ra RA do aluno (usado como nome do arquivo).
     * @return Caminho relativo onde a imagem foi salva.
     * @throws IOException Se ocorrer erro ao copiar o arquivo.
     */
    private String salvarArquivoFoto(File foto, String ra) throws IOException {
        Path destFolder = Paths.get("src/main/resources/images/profiles");
        if (!Files.exists(destFolder)) {
            Files.createDirectories(destFolder);
        }
        String extensao = foto.getName().substring(foto.getName().lastIndexOf("."));
        String nomeArquivo = ra + extensao;
        Path destPath = destFolder.resolve(nomeArquivo);

        Files.copy(foto.toPath(), destPath, StandardCopyOption.REPLACE_EXISTING);
        return "images/profiles/" + nomeArquivo;
    }

    /**
     * Manipula o evento de clique do botão "Voltar", retornando para a tela de login.
     * @param event O evento do clique.
     */
    @FXML
    void handleVoltar(ActionEvent event) {
        carregarTela(event, "/telas/Perfil.fxml", "Perfil - Gerenciador de Estudos");
    }
    
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