package projetofinal.model;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.*;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Classe responsável por interagir com a API do Google Sheets.
 * <p>
 * Esta classe fornece métodos para ler, escrever, atualizar e deletar dados em planilhas do Google Sheets.
 */
public class GoogleSheetsFacade {
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS);

    private Sheets sheetsService;
    private final String spreadsheetId;

    private static final String EXTERNAL_CREDENTIALS_PATH = "/usr/local/gerenciador-academico/credentials.json";
    private static final String RESOURCE_CREDENTIALS_PATH = "/credentials.json";

    /**
     * Construtor da classe GoogleSheetsFacade.
     * <p>
     * Inicializa o serviço do Google Sheets e define o ID da planilha.
     *
     * @throws GeneralSecurityException Se ocorrer um erro de segurança ao inicializar o serviço.
     * @throws IOException              Se ocorrer um erro de entrada/saída ao carregar as credenciais.
     */
    public GoogleSheetsFacade() throws GeneralSecurityException, IOException {
        String spreadsheetId = "1Ie0FsmkwFZHv4AO-4pdGRV4lLMaRH1mLMQq-BSKB0BA";
        this.spreadsheetId = spreadsheetId;

        // Verifica se o arquivo externo existe
        Path externalPath = Paths.get(EXTERNAL_CREDENTIALS_PATH);
        if (Files.exists(externalPath)) {
            // Usa arquivo externo
            this.sheetsService = criarSheetsService(Files.newInputStream(externalPath));
        } else {
            // Usa arquivo dentro do JAR (resource)
            InputStream resourceStream = Main.class.getResourceAsStream(RESOURCE_CREDENTIALS_PATH);
            if (resourceStream == null) {
                throw new IOException("Arquivo de credenciais não encontrado no recurso interno nem no caminho externo");
            }
            this.sheetsService = criarSheetsService(resourceStream);
        }
    }

    /**
     * Cria o serviço do Google Sheets com base nas credenciais fornecidas.
     *
     * @param credentialsPath O caminho para o arquivo de credenciais.
     * @return Uma instância do serviço Sheets.
     * @throws GeneralSecurityException Se ocorrer um erro de segurança ao criar o serviço.
     * @throws IOException              Se ocorrer um erro de entrada/saída ao carregar as credenciais.
     */
    private Sheets criarSheetsService(InputStream credentialsStream) throws GeneralSecurityException, IOException {
        GoogleCredentials credenciais = GoogleCredentials.fromStream(credentialsStream).createScoped(SCOPES);

        return new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, new HttpCredentialsAdapter(credenciais)).setApplicationName("Google Sheets API - Facade Example").build();
    }

    /**
     * Obtém a última linha com dados em um intervalo específico da planilha.
     *
     * @param nomePlanilha O nome da aba da planilha.
     * @param colunaInicio A coluna inicial do intervalo.
     * @param colunaFim    A coluna final do intervalo.
     * @return O número da última linha com dados.
     * @throws IOException Se ocorrer um erro ao ler os dados da planilha.
     */
    private int obterUltimaLinhaComDados(String nomePlanilha, String colunaInicio, String colunaFim) throws IOException {
        List<List<Object>> valores = lerDados(nomePlanilha, colunaInicio, colunaFim);

        int ultimaLinha = 0;

        if (valores != null) {
            for (int i = 0; i < valores.size(); i++) {
                List<Object> linha = valores.get(i);
                boolean temDado = linha.stream().anyMatch(celula -> !celula.toString().trim().isEmpty());

                if (temDado) {
                    ultimaLinha = i + 1;
                }
            }
        }

        return ultimaLinha;
    }

    /**
     * Lê dados de um intervalo específico da planilha.
     *
     * @param nomePlanilha O nome da aba da planilha.
     * @param celulaInicio A célula inicial do intervalo.
     * @param celulaFim    A célula final do intervalo.
     * @return Uma lista de listas representando os dados lidos.
     * @throws IOException Se ocorrer um erro ao ler os dados da planilha.
     */
    public List<List<Object>> lerDados(String nomePlanilha, String celulaInicio, String celulaFim) throws IOException {
        String intervalo = nomePlanilha + "!" + celulaInicio + ":" + celulaFim;
        ValueRange response = sheetsService.spreadsheets().values().get(spreadsheetId, intervalo).execute();
        List<List<Object>> valores = response.getValues();
        if (valores != null && !valores.isEmpty()) valores.remove(0);

        return valores;
    }

    /**
     * Lê uma linha específica da planilha com base em um ID.
     *
     * @param nomePlanilha O nome da aba da planilha.
     * @param celulaInicio A célula inicial do intervalo.
     * @param celulaFim    A célula final do intervalo.
     * @param id           O ID da linha a ser buscada.
     * @return Uma lista representando a linha correspondente ao ID.
     * @throws IOException Se ocorrer um erro ao ler os dados da planilha.
     */
    public List<Object> lerDadosLinhaPorId(String nomePlanilha, String celulaInicio, String celulaFim, String id) throws IOException {
        List<List<Object>> dados = lerDados(nomePlanilha, celulaInicio, celulaFim);

        for (List<Object> linha : dados) {
            if (!linha.isEmpty() && linha.get(0).equals(id)) {
                return linha;
            }
        }

        return Collections.emptyList();
    }

    /**
     * Escreve dados em um intervalo específico da planilha.
     *
     * @param nomePlanilha  O nome da aba da planilha.
     * @param colunaInicial A coluna inicial do intervalo.
     * @param colunaFinal   A coluna final do intervalo.
     * @param valores       Os valores a serem escritos.
     * @throws IOException Se ocorrer um erro ao escrever os dados na planilha.
     */
    public void escreverDados(String nomePlanilha, String colunaInicial, String colunaFinal, List<List<Object>> valores) throws IOException {
        int ultimaLinhaComDados = obterUltimaLinhaComDados(nomePlanilha, colunaInicial, colunaFinal);
        int linhaDestino = ultimaLinhaComDados + 2;

        Spreadsheet spreadsheet = sheetsService.spreadsheets().get(spreadsheetId).execute();

        Sheet sheet = spreadsheet.getSheets().stream().filter(s -> s.getProperties().getTitle().equals(nomePlanilha)).findFirst().orElseThrow(() -> new IOException("Aba '" + nomePlanilha + "' não encontrada"));

        int totalLinhas = sheet.getProperties().getGridProperties().getRowCount();

        if (linhaDestino + valores.size() - 1 > totalLinhas) {
            int linhasParaAdicionar = (linhaDestino + valores.size() - 1) - totalLinhas;

            Request request = new Request().setAppendDimension(new AppendDimensionRequest().setSheetId(sheet.getProperties().getSheetId()).setDimension("ROWS").setLength(linhasParaAdicionar));

            BatchUpdateSpreadsheetRequest bodyUpdate = new BatchUpdateSpreadsheetRequest().setRequests(Collections.singletonList(request));

            sheetsService.spreadsheets().batchUpdate(spreadsheetId, bodyUpdate).execute();
        }

        String intervalo = nomePlanilha + "!" + colunaInicial + linhaDestino + ":" + colunaFinal;

        ValueRange body = new ValueRange().setRange(intervalo).setValues(valores);

        sheetsService.spreadsheets().values().update(spreadsheetId, intervalo, body).setValueInputOption("RAW").execute();
    }

    /**
     * Atualiza dados em um intervalo específico da planilha.
     *
     * @param nomePlanilha   O nome da aba da planilha.
     * @param colunaInicial  A coluna inicial do intervalo.
     * @param colunaFinal    A coluna final do intervalo.
     * @param valoresAntigos Os valores antigos a serem substituídos.
     * @param valoresNovos   Os novos valores a serem escritos.
     * @throws IOException Se ocorrer um erro ao atualizar os dados na planilha.
     */
    public void atualizarDados(String nomePlanilha, String colunaInicial, String colunaFinal, List<List<Object>> valoresAntigos, List<List<Object>> valoresNovos) throws IOException {
        if (valoresAntigos.isEmpty() || valoresNovos.isEmpty()) {
            throw new IllegalArgumentException("Listas de valores antigos ou novos estão vazias.");
        }

        List<List<Object>> todasAsLinhas = lerDados(nomePlanilha, colunaInicial, colunaFinal);

        int linhaAlvo = -1;
        for (int i = 0; i < todasAsLinhas.size(); i++) {
            List<Object> linha = todasAsLinhas.get(i);

            if (linha.equals(valoresAntigos.get(0))) {
                linhaAlvo = i + 2;
                break;
            }
        }

        if (linhaAlvo == -1) {
            throw new IOException("Linha a ser atualizada não encontrada.");
        }

        String intervalo = nomePlanilha + "!" + colunaInicial + linhaAlvo + ":" + colunaFinal + linhaAlvo;

        ValueRange corpo = new ValueRange().setRange(intervalo).setValues(valoresNovos);

        sheetsService.spreadsheets().values().update(spreadsheetId, intervalo, corpo).setValueInputOption("RAW").execute();
    }

    /**
     * Deleta uma linha específica da planilha.
     *
     * @param nomePlanilha  O nome da aba da planilha.
     * @param colunaInicial A coluna inicial do intervalo.
     * @param colunaFinal   A coluna final do intervalo.
     * @param numeroLinha   O número da linha a ser deletada.
     */
    public void deletarDados(String nomePlanilha, String colunaInicial, String colunaFinal, int numeroLinha) {
        try {
            String intervalo = nomePlanilha + "!" + colunaInicial + numeroLinha + ":" + colunaFinal + numeroLinha;

            Request request = new Request().setDeleteRange(new DeleteRangeRequest().setRange(new GridRange().setSheetId(sheetsService.spreadsheets().get(spreadsheetId).execute().getSheets().stream().filter(s -> s.getProperties().getTitle().equals(nomePlanilha)).findFirst().orElseThrow(() -> new IOException("Aba '" + nomePlanilha + "' não encontrada")).getProperties().getSheetId()).setStartRowIndex(numeroLinha - 1).setEndRowIndex(numeroLinha)).setShiftDimension("ROWS"));

            BatchUpdateSpreadsheetRequest bodyUpdate = new BatchUpdateSpreadsheetRequest().setRequests(Collections.singletonList(request));

            sheetsService.spreadsheets().batchUpdate(spreadsheetId, bodyUpdate).execute();
        } catch (IOException e) {
            System.err.println("Erro ao deletar linha: " + e.getMessage());
        }
    }
}