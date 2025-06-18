// package projetofinal.ui;

// import java.time.DayOfWeek;
// import java.time.LocalDate;
// import java.util.Comparator;
// import java.util.List;
// import java.util.stream.Collectors;

// import javafx.animation.FadeTransition;
// import javafx.fxml.FXML;
// import javafx.geometry.Insets;
// import javafx.scene.control.Button;
// import javafx.scene.control.CheckBox;
// import javafx.scene.control.ContentDisplay;
// import javafx.scene.control.Label;
// import javafx.scene.layout.HBox;
// import javafx.scene.layout.Priority;
// import javafx.scene.layout.Region;
// import javafx.scene.layout.VBox;
// import javafx.scene.paint.Color;
// import javafx.scene.text.Font;
// import javafx.scene.text.FontWeight;
// import javafx.util.Duration;
// import projetofinal.filters.FiltroPorPrioridade;
// import projetofinal.model.*;

// public class DashboardController {

//     @FXML private Label bemVindoLabel;
//     @FXML private VBox tarefasUrgentesContainer;
//     @FXML private VBox aulasHojeContainer;

//     private final AlunoLogado aluno = AlunoLogado.getInstance();

    
//     @FXML
//     public void initialize() {
//         // garante que temos aluno logado (caso ainda não tenha sido feito)
//         aluno.logarAluno("281773");

//         bemVindoLabel.setText("Bem‑vindo, " + aluno.getAluno().getNome() + "!");

//         carregarTarefasUrgentes();
//         carregarAulasHoje();
//     }

//     /* ---------- Tarefas ---------- */
//     private void carregarTarefasUrgentes() {
//         tarefasUrgentesContainer.getChildren().clear();

//         TodoList todo = aluno.getTodoList();
//         List<TodoItem> todos = todo.listarItems();

//         FiltroPorPrioridade filtroAlta = new FiltroPorPrioridade("alta");
//         List<TodoItem> urgentes = filtroAlta.meetCriteria(todos);

//         urgentes.forEach(item ->
//             tarefasUrgentesContainer.getChildren().add(criarItemTarefa(item))
//         );
//     }

//     private HBox criarItemTarefa(TodoItem item) {
//         HBox box = new HBox(10);
//         box.setPadding(new Insets(10));
//         box.setMaxWidth(400);
//         box.setStyle("""
//             -fx-border-color: #6A7FC1;
//             -fx-border-radius: 8;
//             -fx-background-radius: 8;
//             -fx-background-color: #E8EBF9;
//         """);

//         /* ✓ checkbox redondo */
//         CheckBox chk = new CheckBox();
//         chk.setSelected(item.getConcluido());
//         chk.getStyleClass().add("circle-checkbox");
//         Label tick = new Label("✔");
//         tick.setTextFill(Color.WHITE);
//         tick.visibleProperty().bind(chk.selectedProperty());
//         tick.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");
//         chk.setGraphic(tick);
//         chk.setContentDisplay(ContentDisplay.CENTER);

//         /* textos */
//         VBox textos = new VBox(5);
//         Label nome = new Label(item.getNome());
//         nome.setFont(Font.font("Raleway", FontWeight.BOLD, 14));
//         textos.getChildren().add(nome);

//         if (item.getDisciplina() != null) {
//             textos.getChildren().add(linhaTag("Disciplina:",
//                     criarTag(item.getDisciplina().getCodigo(),
//                              "#E1BEE7", "#6A1B9A")));
//         }

//         if (item.getData() != null) {
//             textos.getChildren().add(linhaTag("Data:",
//                     criarTag(item.getData().toString(),
//                              "#BBDEFB", "#1565C0")));
//         }

//         Region spacer = new Region();
//         HBox.setHgrow(spacer, Priority.ALWAYS);
//         box.getChildren().addAll(chk, textos, spacer);

//         /* animação quando concluir */
//         chk.selectedProperty().addListener((obs, o, marcado) -> {
//             item.setConcluido(marcado);
//             if (marcado) {
//                 FadeTransition ft = new FadeTransition(Duration.millis(250), box);
//                 ft.setFromValue(1); ft.setToValue(0);
//                 ft.setOnFinished(e -> tarefasUrgentesContainer.getChildren()
//                                                               .remove(box));
//                 ft.play();
//             }
//         });

//         return box;
//     }

//     /* ---------- Aulas ---------- */
//     private void carregarAulasHoje() {
//         aulasHojeContainer.getChildren().clear();

//         DayOfWeek hoje = LocalDate.now().getDayOfWeek();
//         List<Disciplina> disciplinas = aluno.getDisciplinas();
//         if (disciplinas == null) return;

//         disciplinas.stream()
//             .flatMap(d -> d.getAulas().stream()
//                         .filter(a -> DayOfWeek.valueOf(a.getDiaSemana().toUpperCase()) == hoje)
//                         .map(a -> new AulaDTO(d, a)))
//             .sorted(Comparator.comparing(dto -> dto.aula().getHorarioInicio()))
//             .limit(3)
//             .forEach(dto -> aulasHojeContainer.getChildren()
//                                       .add(criarItemAula(dto)));
//     }

//     private HBox criarItemAula(AulaDTO dto) {
//         HBox box = new HBox(10);
//         box.setPadding(new Insets(10));
//         box.setMaxWidth(400);
//         box.setStyle("""
//             -fx-border-color: #6A7FC1;
//             -fx-border-radius: 8;
//             -fx-background-radius: 8;
//             -fx-background-color: #E8EBF9;
//         """);

//         VBox textos = new VBox(5);

//         Label materia = new Label(dto.disciplina().getNome());
//         materia.setFont(Font.font("Raleway", FontWeight.BOLD, 14));
//         textos.getChildren().add(materia);

//         textos.getChildren().add(linhaTag("Horário:",
//                 criarTag(dto.aula().getHorarioInicio().toString(),
//                          "#BBDEFB", "#1565C0")));

//         // textos.getChildren().add(linhaTag("Local:",
//         //         criarTag(dto.aula().getLocal(),
//         //                  "#C8E6C9", "#2E7D32")));

//         box.getChildren().add(textos);
//         return box;
//     }

//     /* ---------- Utilidades ---------- */
//     private Label criarTag(String texto, String corFundo, String corTexto) {
//         Label tag = new Label(texto);
//         tag.setFont(Font.font("Raleway", FontWeight.BOLD, 11));
//         tag.setStyle(String.format("""
//             -fx-background-color: %s;
//             -fx-background-radius: 12;
//             -fx-padding: 4 10 4 10;
//             -fx-text-fill: %s;
//         """, corFundo, corTexto));
//         return tag;
//     }

//     private HBox linhaTag(String titulo, Label valorTag) {
//         Label tituloLbl = new Label(titulo);
//         tituloLbl.setFont(Font.font("Raleway", FontWeight.BOLD, 12));
//         HBox linha = new HBox(5, tituloLbl, valorTag);
//         return linha;
//     }

//     /* ---------- Navegação ---------- */
//     @FXML private void handleAbrirTarefas()      { /* trocar de cena */ }
//     @FXML private void handleAbrirHorario()      { /* trocar de cena */ }
//     @FXML private void handleAbrirDisciplinas()  { /* trocar de cena */ }

//     /* DTO bem simples para facilitar o flatMap */
//     private record AulaDTO(Disciplina disciplina, Aula aula) {}
// }
