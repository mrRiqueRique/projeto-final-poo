package projetofinal.ui;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;
import projetofinal.filters.ComparatorPrioridade;
import projetofinal.model.*;


public class DashboardController {

    @FXML private Label bemVindoLabel;
    @FXML private VBox tarefasUrgentesContainer;
    @FXML private VBox aulasHojeContainer;

    private AlunoLogado aluno = AlunoLogado.getInstance();
   

    
    @FXML
    public void initialize() {
        // garante que temos aluno logado (caso ainda não tenha sido feito)
        aluno.logarAluno("281773");

        bemVindoLabel.setText("Bem‑vindo, " + aluno.getAluno().getNome() + "!");

        carregarTarefasUrgentes();
        carregarAulasHoje();
    }

    /* ---------- Tarefas ---------- */
    private void carregarTarefasUrgentes() {
        tarefasUrgentesContainer.getChildren().clear();

        TodoList todo = aluno.getTodoList();
        List<TodoItem> todos = todo.listarItems();
        todos.sort(new ComparatorPrioridade());

        todos.forEach(item -> tarefasUrgentesContainer.getChildren().add(criarItemTarefa(item)));
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

        // Método auxiliar para criar label título com cor cinza grifado
        java.util.function.Function<String, Label> criarTituloLabel = titulo -> {
            Label l = new Label(titulo);
            l.setFont(Font.font("Raleway", FontWeight.BOLD, 12));
            l.setTextFill(Color.web("#9E9E9E"));  // cinza grifado
            return l;
        };

        // ----- DISCIPLINA -----
        if (item.getDisciplina() != null) {
            HBox linha = new HBox(5);
            Label titulo = criarTituloLabel.apply("Disciplina:");
            Label valor = criarTag(item.getDisciplina().getCodigo(), "#E1BEE7", "#6A1B9A"); // roxo
            linha.getChildren().addAll(titulo, valor);
            textos.getChildren().add(linha);
        }

        // ----- PROVA -----
        if (item.getAvaliacao() instanceof Prova prova) {
            HBox linha = new HBox(5);
            Label titulo = criarTituloLabel.apply("Prova:");
            Label valor = criarTag(prova.getNome(), "rgb(169, 233, 219)", "#006064"); // rosa
            linha.getChildren().addAll(titulo, valor);
            textos.getChildren().add(linha);
        }

        // ----- TRABALHO -----
        if (item.getAvaliacao() instanceof Trabalho trab) {
            HBox linha = new HBox(5);
            Label titulo = criarTituloLabel.apply("Trabalho:");
            Label valor = criarTag(trab.getNome(), "#B2EBF2", "#0097A7"); // ciano
            linha.getChildren().addAll(titulo, valor);
            textos.getChildren().add(linha);
        }

        // ----- PRIORIDADE -----
        if (item.getPrioridade() != null) {
            HBox linha = new HBox(5);
            Label titulo = criarTituloLabel.apply("Prioridade:");

            String prioridade = item.getPrioridade().toLowerCase().trim();
            String corFundo;
            String corTexto;
            switch (prioridade) {
                case "alta" -> {
                    corFundo = "#FFCDD2"; // vermelho claro
                    corTexto = "#B71C1C"; // vermelho escuro
                }
                case "média", "media" -> {
                    corFundo = "#FFF9C4"; // amarelo claro
                    corTexto = "#F57F17"; // amarelo escuro
                }
                case "baixa" -> {
                    corFundo = "#C8E6C9"; // verde claro
                    corTexto = "#2E7D32"; // verde escuro
                }
                default -> {
                    corFundo = "#E0E0E0"; // cinza claro padrão
                    corTexto = "#424242";  // cinza escuro padrão
                }
            }

            Label valor = criarTag(item.getPrioridade(), corFundo, corTexto);
            linha.getChildren().addAll(titulo, valor);
            textos.getChildren().add(linha);
        }

        // ----- DATA -----
        if (item.getData() != null) {
            HBox linha = new HBox(5);
            Label titulo = criarTituloLabel.apply("Data:");
            Label valor = criarTag(item.getData().toString(), "#BBDEFB", "#1565C0"); // azul claro
            linha.getChildren().addAll(titulo, valor);
            textos.getChildren().add(linha);
        }

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        box.getChildren().addAll(chk, textos, spacer);

        /* animação quando concluir */
        chk.selectedProperty().addListener((obs, o, marcado) -> {
            item.setConcluido(marcado);
            if (marcado) {
                FadeTransition ft = new FadeTransition(Duration.millis(250), box);
                ft.setFromValue(1);
                ft.setToValue(0);
                ft.setOnFinished(e -> tarefasUrgentesContainer.getChildren().remove(box));
                ft.play();
            }
        });

        return box;
    }


    /* ---------- Aulas ---------- */
    private void carregarAulasHoje() {
        aulasHojeContainer.getChildren().clear();

        DayOfWeek hoje = LocalDate.now().getDayOfWeek();
        List<Disciplina> disciplinas = aluno.getDisciplinas();
        if (disciplinas == null) return;

        disciplinas.stream()
            .flatMap(d -> d.getAulas().stream()
                        .filter(a -> DiaSemanaRepository.traduzir(a.getDiaSemana()) == hoje)
                        .map(a -> new AulaDTO(d, a)))
            .sorted(Comparator.comparing(dto ->LocalTime.parse(dto.aula().getHorarioInicio())))
            .limit(3)
            .forEach(dto -> aulasHojeContainer.getChildren().add(criarItemAula(dto)));
    }

    private HBox criarItemAula(AulaDTO dto) {
        HBox box = new HBox(10);
        box.setPadding(new Insets(10));
        box.setMaxWidth(400);
        box.setStyle("""
            -fx-border-color: #6A7FC1;
            -fx-border-radius: 8;
            -fx-background-radius: 8;
            -fx-background-color: #E8EBF9;
        """);

        VBox textos = new VBox(5);

        Label materia = new Label(dto.disciplina().getNome());
        materia.setFont(Font.font("Raleway", FontWeight.BOLD, 14));
        textos.getChildren().add(materia);

        textos.getChildren().add(linhaTag("Horário:",
                criarTag(dto.aula().getHorarioInicio().toString(),
                         "#BBDEFB", "#1565C0")));

        // textos.getChildren().add(linhaTag("Local:",
        //         criarTag(dto.aula().getLocal(),
        //                  "#C8E6C9", "#2E7D32")));

        box.getChildren().add(textos);
        return box;
    }

    /* ---------- Utilidades ---------- */
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

    private Label criarTituloTag(String titulo) {
        Label tag = new Label(titulo);
        tag.setFont(Font.font("Raleway", FontWeight.BOLD, 12));
        tag.setStyle("""
            -fx-background-color: #E0E0E0;  /* cinza claro */
            -fx-background-radius: 12;
            -fx-padding: 4 10 4 10;
            -fx-text-fill: #757575;  /* cinza médio */
        """);
        return tag;
    }

    private HBox linhaTag(String titulo, Label valorTag) {
        Label tituloLbl = new Label(titulo);
        tituloLbl.setFont(Font.font("Raleway", FontWeight.BOLD, 12));
        HBox linha = new HBox(5, tituloLbl, valorTag);
        return linha;
    }

    /* ---------- Navegação ---------- */
    @FXML private void handleAbrirTarefas()      { /* trocar de cena */ }
    @FXML private void handleAbrirHorario()      { /* trocar de cena */ }
    @FXML private void handleAbrirDisciplinas()  { /* trocar de cena */ }
     @FXML private void handleVoltar()  { /* trocar de cena */ }


    /* DTO bem simples para facilitar o flatMap */
    private record AulaDTO(Disciplina disciplina, Aula aula) {}
}
