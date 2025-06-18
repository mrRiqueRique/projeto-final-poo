package projetofinal.ui;

import java.time.LocalDate;
import java.util.List;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import projetofinal.model.AlunoLogado;
import projetofinal.model.Disciplina;
import projetofinal.model.Prova;
import javafx.scene.text.FontWeight;
import projetofinal.model.TodoItem;
import projetofinal.model.TodoList;
import projetofinal.model.Trabalho;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.DatePicker;
import javafx.util.Duration;

public class TarefasController {

    @FXML
    private AlunoLogado alunoLogado;

    @FXML
    private VBox tarefasContainer;

    @FXML private Region fundoModal;

    @FXML private VBox modalContainer;
    @FXML private TextField campoNome, campoDisciplina, campoAvaliacao;
    @FXML private DatePicker campoData;
    @FXML private ToggleGroup prioridadeGroup;

    @FXML
    public void initialize() {
        alunoLogado = AlunoLogado.getInstance();
        alunoLogado.logarAluno("281773");
        carregarTodoList();
    }

    @FXML
    private void carregarTodoList() {
        tarefasContainer.getChildren().clear(); // limpa tarefas anteriores
        TodoList listaToDo = alunoLogado.getTodoList();

        for (TodoItem item : listaToDo.listarItems()) {
            tarefasContainer.getChildren().add(criarItemTarefa(item));
        }
    }

    private Label criarTag(String texto, String corFundo, String corTexto) {
        Label tag = new Label(texto);
        tag.setFont(Font.font("Raleway", FontWeight.BOLD, 11));
        tag.setStyle(String.format("""
            -fx-background-color: %s;
            -fx-background-radius: 12;
            -fx-padding: 4 10 4 10;
            -fx-text-fill: %s;
        """, corFundo, corTexto));
        return tag;
    }

    private HBox criarItemTarefa(TodoItem item) {
        HBox box = new HBox(10);
        box.setPadding(new Insets(10));
        box.setMaxWidth(600);
        box.setStyle("""
            -fx-border-color: #6A7FC1;
            -fx-border-radius: 8;
            -fx-background-radius: 8;
            -fx-background-color: #E8EBF9;
        """);

        CheckBox chk = new CheckBox();
        chk.setFocusTraversable(false);
        chk.setSelected(item.getConcluido());
        chk.getStyleClass().add("circle-checkbox");

        Label tick = new Label("✔");
        tick.setTextFill(Color.WHITE);
        tick.visibleProperty().bind(chk.selectedProperty());
        tick.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");
        chk.setGraphic(tick);
        chk.setContentDisplay(ContentDisplay.CENTER);

        VBox textos = new VBox(5);

        Label nomeLabel = new Label(item.getNome());
        nomeLabel.setFont(Font.font("Raleway", FontWeight.BOLD, 14));
        textos.getChildren().add(nomeLabel);

        // ----- DISCIPLINA -----
        if (item.getDisciplina() != null) {
            HBox linha = new HBox(5);
            Label titulo = new Label("Disciplina:");
            titulo.setFont(Font.font("Raleway", FontWeight.BOLD, 12));
            Label valor = criarTag(item.getDisciplina().getCodigo(), "#E1BEE7", "#6A1B9A"); // roxo
            linha.getChildren().addAll(titulo, valor);
            textos.getChildren().add(linha);
        }

        // ----- PROVA -----
        if (item.getAvaliacao() instanceof Prova prova) {
            HBox linha = new HBox(5);
            Label titulo = new Label("Prova:");
            titulo.setFont(Font.font("Raleway", FontWeight.BOLD, 12));
            Label valor = criarTag(prova.getNome(), "rgb(169, 233, 219)", "#006064"); // rosa
            linha.getChildren().addAll(titulo, valor);
            textos.getChildren().add(linha);
        }

        // ----- TRABALHO -----
        if (item.getAvaliacao() instanceof Trabalho trab) {
            HBox linha = new HBox(5);
            Label titulo = new Label("Trabalho:");
            titulo.setFont(Font.font("Raleway", FontWeight.BOLD, 12));
            Label valor = criarTag(trab.getNome(), "#B2EBF2", "#0097A7"); // ciano
            linha.getChildren().addAll(titulo, valor);
            textos.getChildren().add(linha);
        }

        // ----- PRIORIDADE -----
        if (item.getPrioridade() != null) {
            HBox linha = new HBox(5);
            Label titulo = new Label("Prioridade:");
            titulo.setFont(Font.font("Raleway", FontWeight.BOLD, 12));

            String prioridade = item.getPrioridade().toLowerCase();
            String corFundo = "#FFCDD2"; // default red
            String corTexto = "#C62828";

            if (prioridade.contains("média") || prioridade.contains("media")) {
                corFundo = "#ffec82"; corTexto = "#d48f28"; // amarelo
            } else if (prioridade.contains("baixa")) {
                corFundo = "#C8E6C9"; corTexto = "#2E7D32"; // verde
            }

            Label valor = criarTag(item.getPrioridade(), corFundo, corTexto);
            linha.getChildren().addAll(titulo, valor);
            textos.getChildren().add(linha);
        }

        // ----- DATA -----
        if (item.getData() != null) {
            HBox linha = new HBox(5);
            Label titulo = new Label("Data:");
            titulo.setFont(Font.font("Raleway", FontWeight.BOLD, 12));
            Label valor = criarTag(item.getData().toString(), "#BBDEFB", "#1565C0"); // azul
            linha.getChildren().addAll(titulo, valor);
            textos.getChildren().add(linha);
        }

        chk.selectedProperty().addListener((obs, oldVal, marcado) -> {
            item.setConcluido(marcado);
            if (marcado) {
                alunoLogado.concluirTodoItem(item);
                FadeTransition ft = new FadeTransition(Duration.millis(250), box);
                ft.setFromValue(1.0);
                ft.setToValue(0.0);
                ft.setOnFinished(e -> tarefasContainer.getChildren().remove(box));
                ft.play();
            }
        });

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        box.getChildren().addAll(chk, textos, spacer);
        return box;
    }

    @FXML
    private void handleNovoItem() {
        modalContainer.setVisible(true);
        fundoModal.setVisible(true);
    }

    @FXML
    private void handleFecharModal() {
        modalContainer.setVisible(false);
        fundoModal.setVisible(false);
    }

    @FXML
    private void handleSalvarTarefa() {
        String nome = campoNome.getText();
        String disciplina = campoDisciplina.getText();
        String avaliacao = campoAvaliacao.getText();
        LocalDate data = campoData.getValue();

        ToggleButton selecionado = (ToggleButton) prioridadeGroup.getSelectedToggle();
        if (selecionado != null) {
            String prioridade = selecionado.getText(); // "Baixa", "Média" ou "Alta"
        }
        // Aqui você cria a tarefa (ajuste conforme suas classes)
        // Exemplo:
        // TodoItem item = new TodoItem(nome, ..., data, prioridade);
        // alunoLogado.getTodoList().adicionarItem(item);
        // carregarTodoList();

        modalContainer.setVisible(false);
    }


    @FXML
    private void handleVoltar() {
        System.out.println("Voltar clicado");
        // implementar lógica de voltar para a tela anterior
    }
}
