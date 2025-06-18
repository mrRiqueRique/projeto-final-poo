package projetofinal.ui;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import projetofinal.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AulasController {

    @FXML
    private VBox aulasContainer;

    @FXML
    private GridPane calendarioGrid;

    AlunoLogado alunoLogado;

    @FXML
    public void initialize() {
        alunoLogado = AlunoLogado.getInstance();
        carregarAulas();
    }

    @FXML
    private void carregarAulas() {
        calendarioGrid.getChildren().clear();
        alunoLogado.logarAluno("281773");
        Disciplina disciplina = new Disciplina("MA311", "Cálculo 3", "Prof. João", 4, 2, "Prof. Maria");
        Disciplina disciplina2 = new Disciplina("MC322", "Programação Orientada a Objetos", "Prof. Caio", 4, 3, "Prof. Marcos");
        alunoLogado.cadastrarDisciplina(disciplina);
        alunoLogado.cadastrarDisciplina(disciplina2);
        List<Aula> aulas = alunoLogado.getAulas();

        // Array de cores para disciplinas
        String[] cores = {"#87A5EF", "#F4A261", "#E76F51", "#2A9D8F", "#E9C46A", "#264653", "#6A7FC1", "#C0C3E5", "#F4B400", "#34A853"};

        // Mapa de cor por disciplina
        Map<String, String> mapaCores = new HashMap<>();
        int corIndex = 0;

        for (Disciplina d : alunoLogado.getDisciplinas()) {
            if (!mapaCores.containsKey(d.getCodigo())) {
                String cor = cores[corIndex % cores.length];
                mapaCores.put(d.getCodigo(), cor);
                corIndex++;
            }
        }

        // Adicionar cabeçalhos dos dias da semana
        List<String> diasSemana = new ArrayList<>(List.of("Segunda", "Terça", "Quarta", "Quinta", "Sexta"));

        for (int coluna = 1; coluna <= 5; coluna++) {
            StackPane diaCelula = new StackPane();
            diaCelula.setPrefSize(100, 40);
            Label diaLabel = new Label(diasSemana.get(coluna - 1));
            diaLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #395BC7; -fx-font-size: 14px;");
            diaCelula.getChildren().add(diaLabel);
            StackPane.setAlignment(diaLabel, Pos.CENTER);
            calendarioGrid.add(diaCelula, coluna, 0);
        }

        // Determinar o intervalo de horários com base nas aulas
        int horarioMaisCedo = aulas.stream().mapToInt(aula -> Integer.parseInt(aula.getHorarioInicio().split(":")[0])).min().orElse(7);
        int horarioMaisTarde = aulas.stream().mapToInt(aula -> Integer.parseInt(aula.getHorarioInicio().split(":")[0])).max().orElse(23);

        for (int linha = horarioMaisCedo - 6; linha <= horarioMaisTarde - 6; linha++) {
            int hora = 6 + linha;
            String textoHora = String.format("%02d:00", hora);

            StackPane horarioCelula = new StackPane();
            horarioCelula.setPrefSize(100, 40);
            Label horarioLabel = new Label(textoHora);
            horarioLabel.setStyle("-fx-font-weight: bold;");
            horarioCelula.getChildren().add(horarioLabel);
            StackPane.setAlignment(horarioLabel, Pos.CENTER);
            calendarioGrid.add(horarioCelula, 0, linha + 1);

            for (int coluna = 1; coluna <= 5; coluna++) {
                StackPane celula = new StackPane();
                celula.setPrefSize(100, 40);

                int diaSemana = coluna;
                boolean aulaNoHorarioEDia = aulas.stream().anyMatch(aula -> Integer.parseInt(aula.getHorarioInicio().split(":")[0]) == hora && aula.getDiaSemana().equals(diasSemana.get(diaSemana - 1)));

                if (aulaNoHorarioEDia) {
                    Aula aulaCorrespondente = aulas.stream().filter(aula -> Integer.parseInt(aula.getHorarioInicio().split(":")[0]) == hora && aula.getDiaSemana().equals(diasSemana.get(diaSemana - 1))).findFirst().orElse(null);

                    if (aulaCorrespondente != null) {
                        // Obtem o código da disciplina
                        String codigoDisciplina = aulaCorrespondente.getDisciplina();

                        // Pega a cor associada no mapa
                        String corDisciplina = mapaCores.getOrDefault(codigoDisciplina, "#CCCCCC");

                        celula.setStyle("-fx-border-color: lightgray; -fx-background-color: " + corDisciplina + ";");

                        Label disciplinaLabel = new Label(codigoDisciplina); // ou o nome completo, se preferir
                        disciplinaLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
                        celula.getChildren().add(disciplinaLabel);
                        StackPane.setAlignment(disciplinaLabel, Pos.CENTER);
                    }
                } else {
                    celula.setStyle("-fx-border-color: lightgray; -fx-background-color: white;");
                }

                calendarioGrid.add(celula, coluna, linha + 1);
            }
        }

        for (int i = 0; i <= 5; i++) {
            ColumnConstraints cc = new ColumnConstraints();
            if (i == 0) {
                cc.setPercentWidth(10);
            } else {
                cc.setPercentWidth(18);
            }
            calendarioGrid.getColumnConstraints().add(cc);
        }
    }

    private HBox criarItemTarefa(Aula item) {
        HBox box = new HBox();
        box.setStyle("-fx-border-color: #6A7FC1; -fx-border-radius: 8; -fx-background-radius: 8; -fx-background-color: #E8EBF9;");
        box.setSpacing(10);
        box.setPadding(new javafx.geometry.Insets(10));
        box.setMaxWidth(600);

//        RadioButton rb = new RadioButton();
//        rb.setFocusTraversable(false);
//        rb.setSelected(item.getConcluido());

        VBox textos = new VBox();
        textos.setSpacing(5);

//        Label nomeLabel = new Label(item.getNome());
//        nomeLabel.setFont(Font.font("Raleway", 14));

//        String mat = item.getDisciplina() != null ? item.getDisciplina().getNome() : "Sem disciplina";
//        Label matLabel = new Label(mat);
//        matLabel.setFont(Font.font("Raleway", 12));
//        matLabel.setTextFill(Color.GRAY);

//        Label infoLabel = new Label("Prioridade: " + item.getPrioridade() + " | Data: " + item.getData());
//        infoLabel.setFont(Font.font("Raleway", 12));
//        infoLabel.setTextFill(Color.GRAY);

//        textos.getChildren().addAll(nomeLabel, matLabel, infoLabel);

        Region spacer = new Region();
        HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);


        box.getChildren().addAll(textos, spacer);

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
