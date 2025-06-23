package projetofinal.ui;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.text.FontWeight;

import projetofinal.filters.*;
import projetofinal.model.*;

public class TarefasController {

    private AlunoLogado alunoLogado;

    @FXML
    private VBox tarefasContainer;
    @FXML
    private Region fundoModal;
    @FXML
    private VBox modalContainer;

    @FXML
    private TextField campoNome, campoDisciplina, campoAvaliacao;
    @FXML
    private DatePicker campoData;
    @FXML
    private ToggleGroup prioridadeGroup;

    @FXML
    private TextField campoFiltro;
    @FXML
    private ComboBox<String> filtroDisciplinas;
    @FXML
    private ComboBox<String> filtroPrioridades;
    @FXML
    private ComboBox<String> filtroOrdem;
    @FXML
    private DatePicker filtroData;

    @FXML
    public void initialize() {
        alunoLogado = AlunoLogado.getInstance();
        carregarTodoList();

        // Inicializa ComboBoxes
        List<String> codigosDisciplinas = alunoLogado.getDisciplinas().stream().map(Disciplina::getCodigo).toList();
        filtroDisciplinas.getItems().addAll(codigosDisciplinas);
    }

    @FXML
    private void carregarTodoList() {
        tarefasContainer.getChildren().clear();
        TodoList listaToDo = alunoLogado.getTodoList();
        for (TodoItem item : listaToDo.listarItems()) {
            tarefasContainer.getChildren().add(criarItemTarefa(item));
        }
    }

    @FXML
    private void carregarTodoList(TodoList lista) {
        tarefasContainer.getChildren().clear();
        for (TodoItem item : lista.listarItems()) {
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

        if (item.getDisciplina() != null) {
            HBox linha = new HBox(5);
            Label titulo = new Label("Disciplina:");
            titulo.setFont(Font.font("Raleway", FontWeight.BOLD, 12));
            titulo.setTextFill(Color.web("#A9A9A9"));
            Label valor = criarTag(item.getDisciplina().getCodigo(), "#E1BEE7", "#6A1B9A");
            linha.getChildren().addAll(titulo, valor);
            textos.getChildren().add(linha);
        }

        if (item.getAvaliacao() instanceof Prova prova) {
            HBox linha = new HBox(5);
            Label titulo = new Label("Prova:");
            titulo.setFont(Font.font("Raleway", FontWeight.BOLD, 12));
            titulo.setTextFill(Color.web("#A9A9A9"));
            Label valor = criarTag(prova.getNome(), "rgb(169, 233, 219)", "#006064");
            linha.getChildren().addAll(titulo, valor);
            textos.getChildren().add(linha);
        }

        if (item.getAvaliacao() instanceof Trabalho trab) {
            HBox linha = new HBox(5);
            Label titulo = new Label("Trabalho:");
            titulo.setFont(Font.font("Raleway", FontWeight.BOLD, 12));
            titulo.setTextFill(Color.web("#A9A9A9"));
            Label valor = criarTag(trab.getNome(), "#B2EBF2", "#0097A7");
            linha.getChildren().addAll(titulo, valor);
            textos.getChildren().add(linha);
        }

        if (item.getPrioridade() != null) {
            HBox linha = new HBox(5);
            Label titulo = new Label("Prioridade:");
            titulo.setFont(Font.font("Raleway", FontWeight.BOLD, 12));
            titulo.setTextFill(Color.web("#A9A9A9"));
            String prioridade = item.getPrioridade().toLowerCase();
            String corFundo = "#FFCDD2";
            String corTexto = "#C62828";
            if (prioridade.contains("média") || prioridade.contains("media")) {
                corFundo = "#ffec82";
                corTexto = "#d48f28";
            } else if (prioridade.contains("baixa")) {
                corFundo = "#C8E6C9";
                corTexto = "#2E7D32";
            }
            Label valor = criarTag(item.getPrioridade(), corFundo, corTexto);
            linha.getChildren().addAll(titulo, valor);
            textos.getChildren().add(linha);
        }

        if (item.getData() != null) {
            HBox linha = new HBox(5);
            Label titulo = new Label("Data:");
            titulo.setFont(Font.font("Raleway", FontWeight.BOLD, 12));
            titulo.setTextFill(Color.web("#A9A9A9"));
            Label valor = criarTag(item.getData().toString(), "#BBDEFB", "#1565C0");
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
    private void aplicarFiltro() {
        TodoList tarefasFiltradas = new TodoList();
        List<TodoItem> lista = alunoLogado.getTodoList().listarItems();

        String textoBusca = campoFiltro.getText().toLowerCase();
        String disciplinaSelecionada = filtroDisciplinas.getValue();
        String prioridadeSelecionada = filtroPrioridades.getValue();
        String ordemSelecionada = filtroOrdem.getValue();
        LocalDate dataSelecionada = filtroData.getValue();

        List<TodoItem> filtrada = new ArrayList<>(lista);

        if (textoBusca != null && !textoBusca.isBlank()) {
            filtrada = new FiltroPorNome(textoBusca).meetCriteria(filtrada);
        }
        if (disciplinaSelecionada != null) {
            filtrada = new FiltroPorDisciplina(disciplinaSelecionada).meetCriteria(filtrada);
        }
        if (prioridadeSelecionada != null) {
            filtrada = new FiltroPorPrioridade(prioridadeSelecionada).meetCriteria(filtrada);
        }
        if (dataSelecionada != null) {
            String dataFormatada = dataSelecionada.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            filtrada = new FiltroPorData(dataFormatada).meetCriteria(filtrada);
        }
        if (ordemSelecionada != null) {
            if (ordemSelecionada.equals("Ordem Alfabética")) {
                filtrada = new FiltroPorOrdemAlfabetica().meetCriteria(filtrada);
            } else if (ordemSelecionada.equals("Prioridade Crescente")) {
                filtrada = new FiltroPorOrdemPrioridadeCrescente().meetCriteria(filtrada);
            } else if (ordemSelecionada.equals("Prioridade Decrescente")) {
                filtrada = new FiltroPorOrdemPrioridadeDecrescente().meetCriteria(filtrada);
            }
        }

        tarefasFiltradas.adicionarTodoItens(filtrada);
        carregarTodoList(tarefasFiltradas);
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
            String disciplinaCodigo = campoDisciplina.getText();
            String avaliacaoNome = campoAvaliacao.getText();
            LocalDate data = campoData.getValue();

            ToggleButton selecionado = (ToggleButton) prioridadeGroup.getSelectedToggle();
            String prioridade = selecionado != null ? selecionado.getText() : null;

            // Validação de campos obrigatórios
            if (nome.isEmpty() || disciplinaCodigo.isEmpty() || data == null || prioridade == null) {
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setTitle("Campos obrigatórios");
                alerta.setHeaderText(null);
                alerta.setContentText("Todos os campos devem ser preenchidos.");
                alerta.showAndWait();
                return;
            }

            // Validação de data passada
            if (data.isBefore(LocalDate.now())) {
                Alert alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setTitle("Data inválida");
                alerta.setHeaderText(null);
                alerta.setContentText("A data selecionada já passou. Escolha uma data futura.");
                alerta.showAndWait();
                return;
            }

            // Cadastro
            String dataFormatada = data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            alunoLogado.cadastrarTodoItem(nome, disciplinaCodigo, avaliacaoNome, dataFormatada, prioridade);
            carregarTodoList();

            // Fecha o modal
            modalContainer.setVisible(false);
            fundoModal.setVisible(false);
    }

    @FXML
    private void handleVoltar(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/telas/Dashboard.fxml"));
            Scene novaCena = new Scene(loader.load(), 1440, 810);
            novaCena.getStylesheets().add(getClass().getResource("/style/botao-personalizado.css").toExternalForm());
            novaCena.getStylesheets().add(getClass().getResource("/style/botao-voltar.css").toExternalForm());
            novaCena.getStylesheets().add(getClass().getResource("/style/circle-checkbox.css").toExternalForm());
            novaCena.getStylesheets().add(getClass().getResource("/style/botao-prioridade.css").toExternalForm());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(novaCena);
            stage.setTitle("Trabalho Final");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleResetFiltro() {
        campoFiltro.clear();
        filtroDisciplinas.getSelectionModel().clearSelection();
        filtroPrioridades.getSelectionModel().clearSelection();
        filtroOrdem.getSelectionModel().clearSelection();
        filtroData.setValue(null);
        carregarTodoList();
    }
}
