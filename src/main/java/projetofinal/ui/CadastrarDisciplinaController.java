package projetofinal.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import projetofinal.model.Aula;

public class CadastrarDisciplinaController {
    
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
    private Rectangle fundoCalculadora;

    @FXML
    private VBox calculadora;

    private Map<String, Boolean> diasAulas;
    private List<Aula> aulas;

    @FXML
    public void handleBotaoDia(ActionEvent event) {
        Button dia = (Button) event.getSource();
        if(!diasAulas.get(dia.getText())){
            diasAulas.put(dia.getText(), true);
            dia.setStyle("-fx-background-color: #2780bb; -fx-text-fill: white; -fx-border-width: 0;");
        }
        else{
            diasAulas.put(dia.getText(), false);
            dia.setStyle("-fx-background-color: white; -fx-text-fill: gray; -fx-border-width: 2px;");
        }
    }

    @FXML
    public void initialize() {
        System.out.println("Cadastrar Disciplina carregou");
        diasAulas = new HashMap<>();
        diasAulas.put("Segunda", false);
        diasAulas.put("Terça", false);
        diasAulas.put("Quarta", false);
        diasAulas.put("Quinta", false);
        diasAulas.put("Sexta", false);
        diasAulas.put("Sábado", false);
        diasAulas.put("Domingo", false);
    }

    @FXML
    public void handleCadastrarDisciplina(ActionEvent event) {
        try{
            String nome = nomeInput.getText();
            String professor = professorInput.getText();
            String codigo = codigoInput.getText();
            int creditos = Integer.parseInt(creditosInput.getText());
            //typeError.setText("");
            System.out.println(nome + professor + codigo + creditos);
        }
        catch(NumberFormatException e){
            //typeError.setText("Insira um número inteiro");
        }
    }

    @FXML
    public void handleInserirMedia(ActionEvent event){
        fundoCalculadora.setVisible(true);
        calculadora.setVisible(true);
    }
    
    @FXML
    public void handleVoltar(ActionEvent event){
        fundoCalculadora.setVisible(false);
        calculadora.setVisible(false);
    }
}
