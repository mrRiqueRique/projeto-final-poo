package projetofinal.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import projetofinal.model.Disciplina;
import projetofinal.model.Prova;
import projetofinal.model.TodoItem;
import projetofinal.model.TodoList;
import projetofinal.model.Trabalho;

public class TarefasController {

    @FXML
    private VBox tarefasContainer;

    @FXML
    public void initialize() {
        carregarTodoList();
    }

    @FXML
    private void carregarTodoList() {
        tarefasContainer.getChildren().clear(); // limpa tarefas anteriores

        TodoList lista = new TodoList();
        Disciplina disciplina = new Disciplina("ME323", "Probabilidade", 4, 2, "Élcio");
        Disciplina disciplina2 = new Disciplina("MC322", "POO", 4, 3, "Marcos Raimundo");
        disciplina.adicionarAvaliacao(new Prova("P2 ME323", "PB", "2 horas", "25/06"));
        disciplina2.adicionarAvaliacao(new Trabalho("Trabalho Final", "09/06/2025", "23/06/2025", true));

        lista.adicionarItem(new TodoItem("Concluir Telas", disciplina2, disciplina2.getAvaliacoes().get(0), "alta", "23/06/2025"));
        lista.adicionarItem(new TodoItem("Fazer Relatório", disciplina2, disciplina2.getAvaliacoes().get(0), "alta", "23/06/2025"));
        lista.adicionarItem(new TodoItem("Finalizar UML", disciplina2, disciplina2.getAvaliacoes().get(0), "média", "23/06/2025"));
        lista.adicionarItem(new TodoItem("Estudar para probabilidade", disciplina, disciplina.getAvaliacoes().get(0), "baixa", "01/06/2025"));
        lista.adicionarItem(new TodoItem("Comprar passagem para casa", null, null, "baixa", "07/07/2025"));
        lista.adicionarItem(new TodoItem("Comemorar aniversário!", null, null, "baixa", "02/07/2025"));
        lista.adicionarItem(new TodoItem("Limpar a casa", null, null, "baixa", "25/06/2025"));

        for (TodoItem item : lista.listarItems()) {
            tarefasContainer.getChildren().add(criarItemTarefa(item));
        }
    }

    private HBox criarItemTarefa(TodoItem item) {
        HBox box = new HBox();
        box.setStyle("-fx-border-color: #6A7FC1; -fx-border-radius: 8; -fx-background-radius: 8; -fx-background-color: #E8EBF9;");
        box.setSpacing(10);
        box.setPadding(new javafx.geometry.Insets(10));
        box.setMaxWidth(600);

        RadioButton rb = new RadioButton();
        rb.setFocusTraversable(false);
        rb.setSelected(item.getConcluido());

        VBox textos = new VBox();
        textos.setSpacing(5);

        Label nomeLabel = new Label(item.getNome());
        nomeLabel.setFont(Font.font("Raleway", 14));

        String mat = item.getDisciplina() != null ? item.getDisciplina().getNome() : "Sem disciplina";
        Label matLabel = new Label(mat);
        matLabel.setFont(Font.font("Raleway", 12));
        matLabel.setTextFill(Color.GRAY);

        Label infoLabel = new Label("Prioridade: " + item.getPrioridade() + " | Data: " + item.getData());
        infoLabel.setFont(Font.font("Raleway", 12));
        infoLabel.setTextFill(Color.GRAY);

        textos.getChildren().addAll(nomeLabel, matLabel, infoLabel);

        Region spacer = new Region();
        HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);

        // Botão excluir
        Button excluirBtn = new Button("✕");
        excluirBtn.setOnAction(e -> tarefasContainer.getChildren().remove(box));
        excluirBtn.setStyle("-fx-text-fill: red; -fx-background-color: transparent;");
        excluirBtn.setFont(Font.font(16));

        box.getChildren().addAll(rb, textos, spacer, excluirBtn);

        return box;
    }

    @FXML
    private void handleNovoItem() {
        //Implementar ainda
    }

    @FXML
    private void handleVoltar() {
        System.out.println("Voltar clicado");
        // implementar lógica de voltar para a tela anterior
    }
}
