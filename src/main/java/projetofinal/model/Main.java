package projetofinal.model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Classe principal do aplicativo que inicializa a interface gráfica.
 * <p>
 * Esta classe utiliza o framework JavaFX para carregar e exibir a tela inicial do sistema.
 */
public class Main extends Application {

    /**
     * Método responsável por iniciar a aplicação JavaFX.
     * <p>
     * Este método carrega o arquivo FXML da tela de login, aplica os estilos CSS
     * e exibe a janela principal do aplicativo.
     *
     * @param primaryStage O palco principal onde a cena será exibida.
     * @throws Exception Se ocorrer um erro ao carregar os recursos ou inicializar a aplicação.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/telas/Login.fxml"));
        Scene scene = new Scene(loader.load(), 1440, 810);

        // Adiciona os estilos CSS à cena
        scene.getStylesheets().add(getClass().getResource("/style/botao-personalizado.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("/style/botao-voltar.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("/style/circle-checkbox.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("/style/botao-prioridade.css").toExternalForm());

        // Configura o palco principal
        primaryStage.setScene(scene);
        primaryStage.setTitle("Trabalho Final");
        primaryStage.show();
    }

    /**
     * Método principal que inicia a aplicação.
     * <p>
     * Este método é o ponto de entrada do programa e chama o método `launch`
     * para iniciar o ciclo de vida do JavaFX.
     *
     * @param args Os argumentos da linha de comando.
     */
    public static void main(String[] args) {
        launch(args);
    }
}