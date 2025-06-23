package projetofinal.ui;

import java.io.File;
import java.io.IOException;

import java.util.List;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

import projetofinal.model.*;

import javafx.scene.shape.Circle;
import javafx.scene.paint.ImagePattern;

import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

public class DashboardController {

    @FXML
    private Label bemVindoLabel;
    @FXML
    private VBox tarefasUrgentesContainer;
    @FXML
    private VBox aulasHojeContainer;

    @FXML private StackPane profileButton;
    @FXML private Circle profileImageCircle;

    private AlunoLogado alunoLogado = AlunoLogado.getInstance();

    @FXML
    public void initialize() {
        bemVindoLabel.setText("Bem‑vindo, " + alunoLogado.getAluno().getNome() + "!");
        carregarFotoPerfil(); // Carrega a foto do aluno no círculo

        // Executa o carregamento dos dados após renderizar o cabeçalho
        Platform.runLater(() -> {
            carregarTarefasUrgentes();
            carregarAulasHoje();
        });
    }


    private void carregarFotoPerfil() {
        Aluno aluno = alunoLogado.getAluno();
        Image imagem;
        Image imagemPadrao = new Image(getClass().getResourceAsStream("/images/unicamp.png"));

        if (aluno != null && aluno.getCaminhoFoto() != null && !aluno.getCaminhoFoto().isEmpty()) {
            try {
                File fotoFile = new File(aluno.getCaminhoFoto());
                if (fotoFile.exists()) {
                    imagem = new Image(fotoFile.toURI().toString());
                } else {
                    System.err.println("DashboardController: Arquivo de foto não encontrado em: " + aluno.getCaminhoFoto());
                    imagem = imagemPadrao;
                }
            } catch (Exception e) {
                System.err.println("DashboardController: Falha ao carregar imagem de perfil do arquivo. Erro: " + e.getMessage());
                imagem = imagemPadrao;
            }
        } else {
            imagem = imagemPadrao;
        }
        
        profileImageCircle.setFill(new ImagePattern(imagem));

    }

    @FXML
    private void handleProfileClick(MouseEvent event) {
        try {
            Stage stage = (Stage) profileButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/telas/Perfil.fxml"));
            Scene novaCena = new Scene(loader.load(), 1440, 810);

            novaCena.getStylesheets().add(getClass().getResource("/style/botao-personalizado.css").toExternalForm());
            novaCena.getStylesheets().add(getClass().getResource("/style/botao-voltar.css").toExternalForm());
            
            stage.setScene(novaCena);
            stage.setTitle("Perfil do Aluno");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void irParaPerfil(ActionEvent event) {
        try {
            // Usa o profileButton para obter o Stage, garantindo que nunca seja nulo neste contexto
            Stage stage = (Stage) profileButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/telas/Perfil.fxml"));
            Scene novaCena = new Scene(loader.load(), 1440, 810);

            // Adicione os CSS que a tela de Perfil precisa
            novaCena.getStylesheets().add(getClass().getResource("/style/botao-personalizado.css").toExternalForm());
            novaCena.getStylesheets().add(getClass().getResource("/style/botao-voltar.css").toExternalForm());
            // Adicione outros CSS se necessário

            stage.setScene(novaCena);
            stage.setTitle("Perfil do Aluno");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void carregarTarefasUrgentes() {
        tarefasUrgentesContainer.getChildren().clear();

        Task<List<TodoItem>> task = alunoLogado.getTodoItensUrgentes();
        task.setOnSucceeded(event -> {
            for (TodoItem item : task.getValue()) {
                tarefasUrgentesContainer.getChildren().add(criarItemTarefa(item));
            }
        });

        task.setOnFailed(e -> task.getException().printStackTrace());

        new Thread(task).start();
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

    private void carregarAulasHoje() {
        aulasHojeContainer.getChildren().clear();
        List<Aula> aulasHoje = alunoLogado.getAulasHoje();

        aulasHoje.stream().map(this::criarItemAula).forEach(aulasHojeContainer.getChildren()::add);
    }


    private HBox criarItemAula(Aula aula) {
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

        // Nome da disciplina
        Label materia = new Label(aula.getDisciplina());
        materia.setFont(Font.font("Raleway", FontWeight.BOLD, 14));
        textos.getChildren().add(materia);

        // Função auxiliar para criar título cinza
        java.util.function.Function<String, Label> criarTituloLabel = titulo -> {
            Label l = new Label(titulo);
            l.setFont(Font.font("Raleway", FontWeight.BOLD, 12));
            l.setTextFill(Color.web("#9E9E9E"));  // cinza grifado
            return l;
        };

        // ----- HORÁRIO -----
        String horarioTexto = aula.getHorarioInicio() + " - " + aula.getHorarioFim();
        Label horarioTitulo = criarTituloLabel.apply("Horário:");
        Label horarioValor = criarTag(horarioTexto, "#BBDEFB", "#1565C0");
        textos.getChildren().add(new HBox(5, horarioTitulo, horarioValor));

        // ----- LOCAL -----
        Label localTitulo = criarTituloLabel.apply("Local:");
        Label localValor = criarTag(aula.getLocal(), "#C8E6C9", "#2E7D32");
        textos.getChildren().add(new HBox(5, localTitulo, localValor));

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

    private HBox linhaTag(String titulo, Label valorTag) {
        Label tituloLbl = new Label(titulo);
        tituloLbl.setFont(Font.font("Raleway", FontWeight.BOLD, 12));
        HBox linha = new HBox(5, tituloLbl, valorTag);
        return linha;
    }

    /* ---------- Navegação ---------- */
    @FXML
    private void handleAbrirTarefas(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/telas/Tarefas.fxml"));
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

    @FXML
    private void handleAbrirHorario(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/telas/Aulas.fxml"));
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

    @FXML
    private void handleAbrirDisciplinas(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/telas/Disciplinas.fxml"));
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

    @FXML
    private void handleSair(ActionEvent event) {
        try {
            alunoLogado.logout();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/telas/Login.fxml"));
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
