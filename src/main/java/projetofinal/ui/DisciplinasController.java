
package projetofinal.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import projetofinal.model.AlunoLogado;
import projetofinal.model.Disciplina;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.util.List;

public class DisciplinasController {

    @FXML
    private VBox disciplinasContainer;

    @FXML
    private ListView<Disciplina> listaDisciplinas;

    private AlunoLogado alunoLogado = AlunoLogado.getInstance();

    @FXML
    public void initialize() {
        carregarDisciplinas();
    }

    // ATUALIZAR PARA ALUNO LOGADO DEPOIS
    private void carregarDisciplinas() {

        List<Disciplina> disciplinas = alunoLogado.getDisciplinas();
        for (Disciplina d : disciplinas) {
            disciplinasContainer.getChildren().add(criarItemDisciplina(d));
        }
    }

   private HBox criarItemDisciplina(Disciplina d) {
        HBox box = new HBox(10);
        box.setPadding(new Insets(10));
        box.setMaxWidth(600);
        box.setStyle("""
            -fx-border-color: #6A7FC1;
            -fx-border-radius: 8;
            -fx-background-radius: 8;
            -fx-background-color: #E8EBF9;
        """);

        VBox infoBox = new VBox(5);

        Label nomeLabel = new Label(d.getNome());
        nomeLabel.setFont(Font.font("Raleway", FontWeight.BOLD, 14));
        nomeLabel.setStyle("-fx-text-fill:rgb(47, 74, 165);");  
        infoBox.getChildren().add(nomeLabel);

        infoBox.getChildren().add(criarLinha("Código:", d.getCodigo(), "#BBDEFB", "#1A237E"));
        infoBox.getChildren().add(criarLinha("Professor:", d.getProfessor(), "#D1C4E9", "#512DA8"));
        infoBox.getChildren().add(criarLinha("Créditos:", String.valueOf(d.getCreditos()), "#B2EBF2", "#00796B"));
        infoBox.getChildren().add(criarLinha("Faltas:", d.getFaltas() + " faltas", "#FFCDD2", "#C62828"));

        box.getChildren().add(infoBox);

        // Evento de clique para seleção visual e ação
        box.setOnMouseClicked(e -> {
            // Limpar seleção anterior
            limparSelecao();

            // Marcar esta caixa como selecionada (estilo)
            box.setStyle("""
                -fx-border-color: #395BC7;
                -fx-border-radius: 8;
                -fx-background-radius: 8;
                -fx-background-color: #C7D1FF;
            """);

            // Você pode chamar método para abrir detalhes da disciplina
            System.out.println("Selecionada disciplina: " + d.getNome());
            // abrirDetalhesDisciplina(d); // ex
        });

        return box;
    }

    private void limparSelecao() {
        for (javafx.scene.Node node : disciplinasContainer.getChildren()) {
            if (node instanceof HBox) {
                node.setStyle("""
                    -fx-border-color: #6A7FC1;
                    -fx-border-radius: 8;
                    -fx-background-radius: 8;
                    -fx-background-color: #E8EBF9;
                """);
            }
        }
    }
    private HBox criarLinha(String titulo, String valor, String corFundo, String corTexto) {
        HBox linha = new HBox(5);
        Label lblTitulo = new Label(titulo);
        lblTitulo.setFont(Font.font("Raleway", FontWeight.BOLD, 12));

        Label tag = new Label(valor);
        tag.setFont(Font.font("Raleway", FontWeight.BOLD, 11));
        tag.setStyle(String.format("""
            -fx-background-color: %s;
            -fx-background-radius: 12;
            -fx-padding: 4 10 4 10;
            -fx-text-fill: %s;
        """, corFundo, corTexto));

        linha.getChildren().addAll(lblTitulo, tag);
        return linha;
    }

    @FXML
    public void handleCadastrarDisciplina(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/telas/CadastrarDisciplina.fxml"));
            Scene scene = new Scene(loader.load(), 1440, 810);
            CadastrarDisciplinaController controller = loader.getController();
            scene.getStylesheets().add(getClass().getResource("/style/botao-personalizado.css").toExternalForm());
            scene.getStylesheets().add(getClass().getResource("/style/botao-aula.css").toExternalForm());
            scene.getStylesheets().add(getClass().getResource("/style/botao-calc.css").toExternalForm());

            Stage stage = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
        }
        catch(IOException e){
            e.printStackTrace();
            System.out.println("Tela de cadastrar disciplina não carregou");
        }
                     

        System.out.println("Botão 'Cadastrar Disciplina' clicado.");
            // todo - implementar abertura de tela para cadastrar nova disciplina
    }


    @FXML
    public void handleLancarNota() {
        System.out.println("Lançar nota");
        // todo - implementar lançar nota
    }

    @FXML private void handleVoltar(ActionEvent event) {
        try {
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

}