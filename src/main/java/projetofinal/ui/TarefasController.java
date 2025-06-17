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

public class TarefasController {

    @FXML
    private VBox tarefasContainer;

    @FXML
    public void initialize() {
        // Exemplo: adiciona 3 tarefas
        for (int i = 0; i < 3; i++) {
            tarefasContainer.getChildren().add(criarItemTarefa("[Nome da tarefa]", "[Material]", "[Data]"));
        }
    }

    private HBox criarItemTarefa(String nome, String material, String data) {
        HBox box = new HBox();
        box.setStyle("-fx-border-color: #6A7FC1; -fx-border-radius: 8; -fx-background-radius: 8; -fx-background-color: #E8EBF9;");
        box.setSpacing(10);
        box.setPadding(new javafx.geometry.Insets(10));
        box.setMaxWidth(600);

        // RadioButton círculo
        RadioButton rb = new RadioButton();
        rb.setFocusTraversable(false);

        VBox textos = new VBox();
        textos.setSpacing(5);

        Label nomeLabel = new Label(nome);
        nomeLabel.setFont(Font.font("Raleway", 14));
        Label matLabel = new Label(material);
        matLabel.setFont(Font.font("Raleway", 12));
        matLabel.setTextFill(Color.GRAY);

        textos.getChildren().addAll(nomeLabel, matLabel);

        Region spacer = new Region();
        HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);

        VBox direita = new VBox();
        direita.setSpacing(5);
        direita.setAlignment(javafx.geometry.Pos.CENTER_RIGHT);

        Label dataLabel = new Label(data);
        dataLabel.setFont(Font.font("Raleway", 12));
        dataLabel.setTextFill(Color.GRAY);

        box.getChildren().addAll(rb, textos, spacer, direita);

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
