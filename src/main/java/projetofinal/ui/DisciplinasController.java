
package projetofinal.ui;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import projetofinal.model.Disciplina;
import projetofinal.model.DisciplinaRepository;

import java.util.List;

public class DisciplinasController {

    @FXML
    private ListView<Disciplina> listaDisciplinas;

    @FXML
    private Label disciplinasLabel;

    private DisciplinaRepository disciplinaRepository;

    @FXML
    public void initialize() {
        disciplinaRepository = DisciplinaRepository.getInstancia();

        Font minhaFonte = Font.loadFont(getClass().getResourceAsStream("/fonts/Raleway/static/Raleway-ExtraBold.ttf"), 40);
        disciplinasLabel.setFont(minhaFonte);

        listaDisciplinas.setPrefWidth(450); // define largura preferida da ListView

        listaDisciplinas.setCellFactory(param -> new ListCell<>() {

            {
                // Faz a célula ocupar toda a largura disponível da ListView, com margem de 20px
                prefWidthProperty().bind(param.widthProperty().subtract(20));
            }

            @Override
            protected void updateItem(Disciplina disciplina, boolean empty) {
                super.updateItem(disciplina, empty);
                if (empty || disciplina == null) {
                    setText(null);
                    setGraphic(null);
                    setStyle("");
                } else {
                    Label titulo = new Label(disciplina.getNome());
                    titulo.setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-text-fill: black;");

                    HBox mediaBox = new HBox(5);
                    Label mediaLabel = new Label("Média:");
                    mediaLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: black;");
                    Label mediaValor = new Label("[cálculo]");
                    mediaValor.setStyle("-fx-text-fill: black;");
                    mediaBox.getChildren().addAll(mediaLabel, mediaValor);

                    HBox professorBox = new HBox(5);
                    Label professorLabel = new Label("Professor:");
                    professorLabel.setStyle("-fx-font-weight: bold;-fx-text-fill: black;");
                    Label professorValor = new Label(disciplina.getProfessor());
                    professorValor.setStyle("-fx-text-fill: black;");
                    professorBox.getChildren().addAll(professorLabel, professorValor);

                    HBox codigoBox = new HBox(5);
                    Label codigoLabel = new Label("Código:");
                    codigoLabel.setStyle("-fx-font-weight: bold;-fx-text-fill: black;");
                    Label codigoValor = new Label(disciplina.getCodigo());
                    codigoValor.setStyle("-fx-text-fill: black;");
                    codigoBox.getChildren().addAll(codigoLabel, codigoValor);

                    HBox creditosBox = new HBox(5);
                    Label creditosLabel = new Label("Créditos:");
                    creditosLabel.setStyle("-fx-font-weight: bold;-fx-text-fill: black;");
                    Label creditosValor = new Label(String.valueOf(disciplina.getCreditos()));
                    creditosValor.setStyle("-fx-text-fill: black;");
                    creditosBox.getChildren().addAll(creditosLabel, creditosValor);

                    HBox faltasBox = new HBox(5);
                    Label faltasLabel = new Label("Faltas:");
                    faltasLabel.setStyle("-fx-font-weight: bold;-fx-text-fill: red;");
                    Label faltasValor = new Label(String.valueOf(disciplina.consultarFaltasRestantes()));
                    faltasValor.setStyle("-fx-text-fill: red;");
                    faltasBox.getChildren().addAll(faltasLabel, faltasValor);

                    VBox container = new VBox(5);
                    container.getChildren().addAll(titulo, mediaBox, professorBox, codigoBox, creditosBox, faltasBox);
                    container.setPadding(new Insets(15));
                    container.setPrefWidth(400);

                    String baseStyle = "-fx-border-color:rgba(147, 193, 214, 0.9); -fx-border-radius: 10; -fx-border-width: 2; -fx-background-radius: 5;";
                    String background = isSelected() ? "-fx-background-color: lightblue;" : "-fx-background-color:rgba(230, 230, 230, 0.9);";
                    container.setStyle(baseStyle + background);

                    setText(null);
                    setGraphic(container);
                }
            }

            @Override
            public void updateSelected(boolean selected) {
                super.updateSelected(selected);
                if (getGraphic() != null) {
                    VBox container = (VBox) getGraphic();
                    String baseStyle = "-fx-border-color: black; -fx-border-radius: 10; -fx-border-width: 2; -fx-background-radius: 5;";
                    String background = selected ? "-fx-background-color: lightblue;" : "-fx-background-color: #f9f9f9;";
                    container.setStyle(baseStyle + background);
                }
            }
        });

        carregarDisciplinas();
    }

    private void carregarDisciplinas() {
        List<Disciplina> disciplinas = disciplinaRepository.getDisciplinas();
        System.out.println("Disciplinas carregadas: " + disciplinas);
        listaDisciplinas.getItems().setAll(disciplinas);
    }

    @FXML
    public void handleCadastrarDisciplina() {
        System.out.println("Botão 'Cadastrar Disciplina' clicado.");
        // todo - implementar abertura de tela para cadastrar nova disciplina
    }

    @FXML
    public void handleLancarNota() {
        System.out.println("Lançar nota");
        // todo - implementar lançar nota
    }

    @FXML
    public void handleVoltar() {
        System.out.println("Voltar");
        // todo - implementar voltar
    }
}