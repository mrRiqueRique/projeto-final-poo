package projetofinal.ui;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import projetofinal.model.AlunoLogado;
import projetofinal.model.Aula;
import projetofinal.model.Disciplina;
import projetofinal.model.MetodoDeAvaliacao;
import projetofinal.model.Prova;
import projetofinal.model.Trabalho;

public class CadastrarDisciplinaController {

    private AlunoLogado alunoLogado = AlunoLogado.getInstance();

    @FXML
    private TextField nomeInput;

    @FXML
    private TextField professorInput;

    @FXML
    private TextField PEDInput;

    @FXML
    private TextField codigoInput;

    @FXML
    private TextField creditosInput;

    @FXML
    private Label typeError;

    @FXML
    private HBox botoesAulas;

    @FXML
    private Rectangle fundoCalculadora;

    @FXML
    private VBox calculadora;

    private Map<String, Boolean> diasAulas;

    @FXML
    private void handleVoltar(ActionEvent event) {
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


    private String formula = "";
    private int traco = 0;

    @FXML
    private TextField resultado;

    private <T> T findNodeById(Parent parent, String id, Class<T> type) {
        for (Node node : parent.getChildrenUnmodifiable()) {
            if (id.equals(node.getId()) && type.isInstance(node)) {
                return type.cast(node);
            }
            if (node instanceof Parent) {
                T childResult = findNodeById((Parent) node, id, type);
                if (childResult != null) return childResult;
            }
        }
        return null;
    }

    @FXML
    public void handleBotaoDia(MouseEvent event) {
        StackPane dia = (StackPane) event.getSource();

        Label label = findNodeById(dia, "label", Label.class);
        TextField inicio = findNodeById(dia, "inicio", TextField.class);
        TextField fim = findNodeById(dia, "fim", TextField.class);
        TextField local = findNodeById(dia, "local", TextField.class);

        if (!diasAulas.get(label.getText())) {
            diasAulas.put(label.getText(), true);
            label.setStyle("-fx-text-fill: white;");
            dia.setStyle("-fx-background-color: #395BC7; -fx-border-width: 0;");
            inicio.setVisible(true);
            fim.setVisible(true);
            local.setVisible(true);
        } else {
            diasAulas.put(label.getText(), false);
            label.setStyle("-fx-text-fill: gray;");
            dia.setStyle("-fx-background-color: white; -fx-border-width: 2;");
            inicio.setVisible(false);
            fim.setVisible(false);
            local.setVisible(false);
        }
    }

    @FXML
    public void initialize() {
        diasAulas = new HashMap<>();
        diasAulas.put("Segunda", false);
        diasAulas.put("Terça", false);
        diasAulas.put("Quarta", false);
        diasAulas.put("Quinta", false);
        diasAulas.put("Sexta", false);
        diasAulas.put("Sábado", false);
        diasAulas.put("Domingo", false);

        avaliacoes = new ArrayList<>();
    }


    @FXML
    public void handleInserirMedia(ActionEvent event) {
        fundoCalculadora.setVisible(true);
        calculadora.setVisible(true);
    }

    @FXML
    public void handleVoltarBtn(ActionEvent event) {
        fundoCalculadora.setVisible(false);
        calculadora.setVisible(false);
    }

    @FXML
    public void handleVoltarRect(MouseEvent event) {
        fundoCalculadora.setVisible(false);
        calculadora.setVisible(false);
    }

    private void addTraco() {
        String visualFormula = formula.substring(0, traco) + "|" + formula.substring(traco);
        resultado.setText(visualFormula);
    }

    @FXML
    public void handleCalculadora(ActionEvent event) {
        Button btn = (Button) event.getSource();

        formula = formula.substring(0, traco) + btn.getText() + formula.substring(traco);
        traco += 1;
        addTraco();
    }

    @FXML
    public void handleBack(ActionEvent event) {
        if (formula.charAt(traco - 1) == '}') {
            while (true) {
                traco -= 1;
                if (formula.charAt(traco) == '{') {
                    break;
                }
            }
        } else if (traco > 0) {
            traco -= 1;
        }
        addTraco();
    }

    @FXML
    public void handleForward(ActionEvent event) {
        if (formula.charAt(traco) == '{') {
            while (formula.charAt(traco) != '}') {
                traco += 1;
            }
        }
        if (traco < formula.length()) {
            traco += 1;
            addTraco();
        }
    }

    @FXML
    public void handleBackspace(ActionEvent event) {
        if (traco == 0) return;  // nothing to delete

        if (formula.charAt(traco - 1) == '}') {
            do {
                formula = formula.substring(0, traco - 1) + formula.substring(traco);
                traco -= 1;

            } while (formula.charAt(traco - 1) != '{');

            if (traco > 0) {
                formula = formula.substring(0, traco - 1) + formula.substring(traco);
                traco -= 1;
            }
        } else {
            formula = formula.substring(0, traco - 1) + formula.substring(traco);
            traco -= 1;
        }

        addTraco();
    }

    private void handleVariableToFormula(String text) {
        formula = formula.substring(0, traco) + "{" + formula.substring(traco);
        traco += 1;
        formula = formula.substring(0, traco) + text + formula.substring(traco);
        traco += text.length();
        formula = formula.substring(0, traco) + "}" + formula.substring(traco);
        traco += 1;
        addTraco();
    }

    @FXML
    private VBox inputsProva;

    @FXML
    private VBox inputsTrabalho;

    @FXML
    private HBox addMetodoAvaliacao;

    @FXML
    private TextField integrantes;

    @FXML
    public void handleAddProva(ActionEvent event) {
        addMetodoAvaliacao.setVisible(false);
        inputsProva.setVisible(true);
    }

    @FXML
    public void handleAddTrabalho(ActionEvent event) {
        addMetodoAvaliacao.setVisible(false);
        inputsTrabalho.setVisible(true);
    }

    @FXML
    public void handleProvaToMetodo(ActionEvent event) {
        inputsProva.setVisible(false);
        addMetodoAvaliacao.setVisible(true);
    }

    @FXML
    public void handleTrabalhoToMetodo(ActionEvent event) {
        inputsTrabalho.setVisible(false);
        addMetodoAvaliacao.setVisible(true);
    }

    @FXML
    public void handleGroup(ActionEvent event) {
        Button btn = (Button) event.getSource();
        btn.setVisible(false);
        integrantes.setVisible(true);
    }

    @FXML
    private FlowPane avaliacoesCadastradas;


    @FXML
    public void handleSaveProva(ActionEvent event) {
        TextField nome = findNodeById(inputsProva, "nome", TextField.class);
        TextField local = findNodeById(inputsProva, "local", TextField.class);
        DatePicker data = findNodeById(inputsProva, "data", DatePicker.class);
        TextField horarioInicio = findNodeById(inputsProva, "horarioInicio", TextField.class);
        TextField duracao = findNodeById(inputsProva, "duracao", TextField.class);

        Prova prova = new Prova(nome.getText(),"TODO - DISCIPLINA", local.getText(), horarioInicio.getText(), duracao.getText(), data.getValue().toString());
        avaliacoes.add(prova);
        Button btn = new Button(nome.getText());

        btn.setStyle("-fx-background-color: white; -fx-border-width: 1px; -fx-border-color: black; -fx-cursor: hand");

        btn.setOnMousePressed(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                handleVariableToFormula(btn.getText());
            } else if (e.getButton() == MouseButton.SECONDARY) {
                avaliacoesCadastradas.getChildren().remove(btn);
                avaliacoes.remove(prova);
            }
        });

        avaliacoesCadastradas.getChildren().add(btn);

        nome.setText("");
        local.setText("");
        data.setValue(null);
        horarioInicio.setText("");
        duracao.setText("");

        handleProvaToMetodo(event);
    }

    @FXML
    public void handleSaveTrabalho(ActionEvent event) {
        TextField nome = findNodeById(inputsTrabalho, "nome", TextField.class);
        DatePicker dataInicio = findNodeById(inputsTrabalho, "inicio", DatePicker.class);
        DatePicker dataEntrega = findNodeById(inputsTrabalho, "entrega", DatePicker.class);
        Button grupo = findNodeById(inputsTrabalho, "grupo", Button.class);
        TextField integrantes = findNodeById(inputsTrabalho, "integrantes", TextField.class);

        Trabalho trabalho = new Trabalho(nome.getText(), "TODO - COLOCAR DISCIPLINA", dataInicio.getValue().toString(), dataEntrega.getValue().toString(), !grupo.isVisible(), integrantes.toString());
        avaliacoes.add(trabalho);
        Button btn = new Button(nome.getText());
        btn.setStyle("-fx-background-color: white; -fx-border-width: 1px; -fx-border-color: black; -fx-cursor: hand");


        btn.setOnMousePressed(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                handleVariableToFormula(btn.getText());
            } else if (e.getButton() == MouseButton.SECONDARY) {
                avaliacoesCadastradas.getChildren().remove(btn);
                avaliacoes.remove(trabalho);
            }
        });

        avaliacoesCadastradas.getChildren().add(btn);

        nome.setText("");
        dataInicio.setValue(null);
        dataEntrega.setValue(null);
        grupo.setVisible(true);
        integrantes.setText("");
        integrantes.setVisible(false);

        handleTrabalhoToMetodo(event);
    }

    private List<MetodoDeAvaliacao> avaliacoes;

    @FXML
    public void handleCadastrarDisciplina(ActionEvent event) {
        try {
            String nome = nomeInput.getText();
            String professor = professorInput.getText();
            String PED = PEDInput.getText();
            String codigo = codigoInput.getText();
            int creditos = Integer.parseInt(creditosInput.getText());

            Disciplina disciplina = new Disciplina(codigo, nome, PED, creditos, formula, professor);

            List<Aula> aulas = new ArrayList<>();
            for (var item : diasAulas.entrySet()) {
                if (item.getValue()) {
                    StackPane botaoAula = (StackPane) findNodeById(botoesAulas, item.getKey(), StackPane.class);
                    TextField inicio = findNodeById(botaoAula, "inicio", TextField.class);
                    TextField fim = findNodeById(botaoAula, "fim", TextField.class);
                    TextField local = findNodeById(botaoAula, "local", TextField.class);


                    aulas.add(new Aula(inicio.getText(), fim.getText(), item.getKey(), codigo, local.getText()));
                }
            }
            disciplina.setAulas(aulas);

            for (var avaliacao : avaliacoes) {
                avaliacao.setCodigo(codigo);
            }

            disciplina.setAvaliacoes(avaliacoes);

            alunoLogado.getAluno().cadastrarDisciplina(disciplina);

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

        } catch (NumberFormatException e) {
            String styleOriginal = creditosInput.getStyle();
            creditosInput.setStyle(styleOriginal + "; -fx-border-color: red; -fx-border-width: 3px;");
        }
    }

}
