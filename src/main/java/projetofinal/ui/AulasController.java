package projetofinal.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import projetofinal.model.*;

import java.io.IOException;
import java.util.*;

/**
 * Controller responsável por exibir o calendário de aulas do aluno.
 * <p>
 * Esta classe gerencia a tela de visualização do cronograma de aulas,
 * organizando os horários em um grid e exibindo uma legenda com as disciplinas.
 * Permite também a navegação de volta ao dashboard principal.
 */
public class AulasController {

    /** Container que exibe a legenda com as cores e nomes das disciplinas. */
    @FXML
    private VBox legendaDisciplinas;

    /** Grade que representa visualmente o calendário de aulas, com dias e horários. */
    @FXML
    private GridPane calendarioGrid;

    /** Instância singleton representando o aluno logado. */
    AlunoLogado alunoLogado;

    /**
     * Método de inicialização automática do controller.
     * <p>
     * Invocado automaticamente pelo JavaFX após o carregamento do FXML.
     * Obtém a instância do aluno logado e carrega as aulas na interface.
     */
    @FXML
    public void initialize() {
        alunoLogado = AlunoLogado.getInstance();
        carregarAulas();
    }

    /**
     * Carrega e organiza visualmente as aulas do aluno no calendário.
     * <p>
     * Este método popula o {@link #calendarioGrid} com os horários das aulas
     * e gera uma legenda visual em {@link #legendaDisciplinas}, associando cores
     * diferentes para cada disciplina.
     * <p>
     * Ele calcula o intervalo de horários com base nas aulas cadastradas e distribui
     * os elementos visuais para cada dia da semana.
     */
    @FXML
    private void carregarAulas() {
        calendarioGrid.getChildren().clear();
        List<Aula> aulas = alunoLogado.getAulas();

        // Array de cores para disciplinas
        String[] cores = {"#87A5EF", "#F4A261", "#E76F51", "#2A9D8F", "#E9C46A", "#264653", "#6A7FC1", "#C0C3E5", "#F4B400", "#34A853"};

        // Mapeia cores únicas para cada disciplina
        Map<String, String> mapaCores = new HashMap<>();
        int corIndex = 0;

        for (Disciplina d : alunoLogado.getDisciplinas()) {
            if (!mapaCores.containsKey(d.getCodigo())) {
                String cor = cores[corIndex % cores.length];
                mapaCores.put(d.getCodigo(), cor);
                corIndex++;
            }
        }

        // Cabeçalho com dias da semana
        List<String> diasSemana = new ArrayList<>(List.of("Segunda", "Terça", "Quarta", "Quinta", "Sexta"));

        for (int coluna = 1; coluna <= 5; coluna++) {
            StackPane diaCelula = new StackPane();
            Label diaLabel = new Label(diasSemana.get(coluna - 1));
            diaLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: black; -fx-font-size: 14px;");
            diaCelula.getChildren().add(diaLabel);
            StackPane.setAlignment(diaLabel, Pos.CENTER);
            calendarioGrid.add(diaCelula, coluna, 0);
        }

        // Determina o horário mais cedo e mais tarde das aulas
        int horarioMaisCedo = aulas.stream().mapToInt(aula -> Integer.parseInt(aula.getHorarioInicio().split(":")[0])).min().orElse(7);
        int horarioMaisTarde = aulas.stream().mapToInt(aula -> Integer.parseInt(aula.getHorarioFim().split(":")[0])).max().orElse(23) - 1;

        Set<String> celulasPreenchidas = new HashSet<>();

        // Preenche a grade de horários e dias com as aulas
        for (int linha = horarioMaisCedo - 6; linha <= horarioMaisTarde - 6; linha++) {
            int hora = 6 + linha;
            String textoHora = String.format("%02d:00", hora);

            // Coluna com o horário (lado esquerdo)
            StackPane horarioCelula = new StackPane();
            Label horarioLabel = new Label(textoHora);
            horarioLabel.setStyle("-fx-font-weight: bold;");
            horarioCelula.getChildren().add(horarioLabel);
            StackPane.setAlignment(horarioLabel, Pos.CENTER);
            calendarioGrid.add(horarioCelula, 0, linha + 1);

            // Preenche as colunas de dias da semana
            for (int coluna = 1; coluna <= 5; coluna++) {
                String diaSemana = diasSemana.get(coluna - 1);
                boolean celulaPreenchida = false;

                for (Aula aula : aulas) {
                    if (!aula.getDiaSemana().equals(diaSemana)) continue;

                    int horaInicio = Integer.parseInt(aula.getHorarioInicio().split(":")[0]);
                    int horaFim = Integer.parseInt(aula.getHorarioFim().split(":")[0]);

                    // Verifica se a aula ocorre nesse horário
                    if (hora >= horaInicio && hora < horaFim) {
                        String idCelula = coluna + "-" + (linha + 1);
                        if (celulasPreenchidas.contains(idCelula)) {
                            celulaPreenchida = true;
                            break;
                        }

                        StackPane celula = new StackPane();
                        celula.setPrefSize(100, 35);

                        String codigo = aula.getDisciplina();
                        String cor = mapaCores.getOrDefault(codigo, "#CCCCCC");
                        celula.setStyle("-fx-border-color: lightgray; -fx-background-color: " + cor + ";");

                        VBox vbox = new VBox();
                        vbox.setAlignment(Pos.CENTER);

                        Label label = new Label(codigo);
                        label.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 12px;");
                        Label localLabel = new Label(aula.getLocal());
                        localLabel.setStyle("-fx-text-fill: white; -fx-font-size: 10px;");

                        vbox.getChildren().addAll(label, localLabel);
                        celula.getChildren().add(vbox);

                        calendarioGrid.add(celula, coluna, linha + 1);
                        celulasPreenchidas.add(idCelula);
                        celulaPreenchida = true;
                        break;
                    }
                }

                // Se não houver aula nesse horário, insere célula em branco
                if (!celulaPreenchida) {
                    StackPane celula = new StackPane();
                    celula.setPrefSize(100, 35);
                    celula.setStyle("-fx-border-color: lightgray; -fx-background-color: white;");
                    calendarioGrid.add(celula, coluna, linha + 1);
                }
            }

            // LEGENDAS (refaz a cada ciclo de linha)
            legendaDisciplinas.getChildren().clear();

            for (Disciplina d : alunoLogado.getDisciplinas()) {
                HBox item = new HBox();
                item.setSpacing(10);
                item.setAlignment(Pos.TOP_LEFT);

                // Quadradinho de cor da disciplina
                Region corBox = new Region();
                corBox.setPrefSize(15, 15);
                corBox.setStyle("-fx-background-color: " + mapaCores.get(d.getCodigo()) + "; -fx-border-color: lightgray; -fx-margin-right: 10px;");

                // Texto com nome e professor
                VBox info = new VBox(2);
                Label titleLabel = new Label(d.getCodigo() + " - " + d.getNome());
                titleLabel.setStyle("-fx-font-weight: bold;");
                Label infoLabel = new Label("Professor: " + d.getProfessor());
                info.getChildren().addAll(titleLabel, infoLabel);

                item.getChildren().addAll(corBox, info);
                legendaDisciplinas.getChildren().add(item);
            }
        }

        // Define as proporções das colunas
        for (int i = 0; i <= 5; i++) {
            ColumnConstraints cc = new ColumnConstraints();
            if (i == 0) {
                cc.setPercentWidth(10);  // coluna de horários
            } else {
                cc.setPercentWidth(18);  // colunas de dias da semana
            }
            calendarioGrid.getColumnConstraints().add(cc);
        }
    }

    /**
     * Manipula o clique no botão "Voltar".
     * <p>
     * Retorna o usuário à tela de dashboard principal, recarregando a interface.
     *
     * @param event Evento de clique no botão.
     */
    @FXML
    private void handleVoltar(ActionEvent event) {
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
