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
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GoogleSheetsFacade {
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS);

    private Sheets sheetsService;
    private final String spreadsheetId;

    public GoogleSheetsFacade() throws GeneralSecurityException, IOException {
        String credenciais = Main.class.getResource("/credentials.json").getPath();
        String spreadsheetId = "1Ie0FsmkwFZHv4AO-4pdGRV4lLMaRH1mLMQq-BSKB0BA";

        this.spreadsheetId = spreadsheetId;
        this.sheetsService = criarSheetsService(credenciais);
    }

    private Sheets criarSheetsService(String credentialsPath) throws GeneralSecurityException, IOException {
        // Carrega as credenciais do arquivo JSON
        GoogleCredentials credenciais = GoogleCredentials
                .fromStream(new FileInputStream(credentialsPath))
                .createScoped(SCOPES);

        return new Sheets.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY,
                new HttpCredentialsAdapter(credenciais)
        ).setApplicationName("Google Sheets API - Facade Example").build();
    }

    private int obterUltimaLinhaComDados(String nomePlanilha, String colunaInicio, String colunaFim) throws IOException {
        List<List<Object>> valores = lerDados(nomePlanilha, colunaInicio, colunaFim);

        int ultimaLinha = 0;

        if (valores != null) {
            for (int i = 0; i < valores.size(); i++) {
                List<Object> linha = valores.get(i);

                // Verifica se a linha tem pelo menos uma célula com conteúdo
                boolean temDado = linha.stream().anyMatch(celula -> !celula.toString().trim().isEmpty());

                if (temDado) {
                    ultimaLinha = i + 1; // +1 pois linhas no Sheets começam em 1 (não 0)
                }
            }
        }

        return ultimaLinha;
    }


    public List<List<Object>> lerDados(String nomePlanilha, String celulaInicio, String celulaFim) throws IOException {
        String intervaldo = nomePlanilha + "!" + celulaInicio + ":" + celulaFim;
        ValueRange response = sheetsService.spreadsheets().values()
                .get(spreadsheetId, intervaldo)
                .execute();
        List<List<Object>> valores = response.getValues();
        if (valores != null && !valores.isEmpty())
            valores.remove(0); // Remove the header (first row)

        return valores;
    }


    public List<Object> lerDadosLinhaPorId(String nomePlanilha, String celulaInicio, String celulaFim, String id) throws IOException {
        List<List<Object>> dados = lerDados(nomePlanilha, celulaInicio, celulaFim);

        for (List<Object> linha : dados) {
            if (!linha.isEmpty() && linha.get(0).equals(id)) {
                return linha; // Retorna a linha correspondente ao ID
            }
        }

        return Collections.emptyList(); // Retorna uma lista vazia se o ID não for encontrado
    }

    public void escreverDados(String nomePlanilha, String colunaInicial, String colunaFinal, List<List<Object>> valores) throws IOException {
        int ultimaLinhaComDados = obterUltimaLinhaComDados(nomePlanilha, colunaInicial, colunaFinal);
        int linhasNecessarias = valores.size();
        int linhaDestino = ultimaLinhaComDados + 2;

        // 1. Verifica quantas linhas existem atualmente na planilha
        Spreadsheet spreadsheet = sheetsService.spreadsheets().get(spreadsheetId).execute();

        Sheet sheet = spreadsheet.getSheets().stream()
                .filter(s -> s.getProperties().getTitle().equals(nomePlanilha))
                .findFirst()
                .orElseThrow(() -> new IOException("Aba '" + nomePlanilha + "' não encontrada"));

        int totalLinhas = sheet.getProperties().getGridProperties().getRowCount();

        // 2. Se não houver linhas suficientes, adiciona mais
        if (linhaDestino + linhasNecessarias - 1 > totalLinhas) {
            int linhasParaAdicionar = (linhaDestino + linhasNecessarias - 1) - totalLinhas;

            Request request = new Request().setAppendDimension(new AppendDimensionRequest()
                    .setSheetId(sheet.getProperties().getSheetId())
                    .setDimension("ROWS")
                    .setLength(linhasParaAdicionar));

            BatchUpdateSpreadsheetRequest bodyUpdate = new BatchUpdateSpreadsheetRequest()
                    .setRequests(Collections.singletonList(request));

            sheetsService.spreadsheets().batchUpdate(spreadsheetId, bodyUpdate).execute();

            System.out.printf("Adicionadas %d novas linhas à aba '%s'.%n", linhasParaAdicionar, nomePlanilha);
        }

        // 3. Escreve os dados no intervalo certo
        String intervalo = nomePlanilha + "!" + colunaInicial + linhaDestino + ":" + colunaFinal;

        ValueRange body = new ValueRange()
                .setRange(intervalo)
                .setValues(valores);

        UpdateValuesResponse result = sheetsService.spreadsheets().values()
                .update(spreadsheetId, intervalo, body)
                .setValueInputOption("RAW")
                .execute();

        System.out.printf("%d células atualizadas na aba '%s'.%n", result.getUpdatedCells(), nomePlanilha);
    }

    public void atualizarDados(String nomePlanilha, String colunaInicial, String colunaFinal,
                               List<List<Object>> valoresAntigos, List<List<Object>> valoresNovos) throws IOException {
        if (valoresAntigos.isEmpty() || valoresNovos.isEmpty()) {
            throw new IllegalArgumentException("Listas de valores antigos ou novos estão vazias.");
        }

        // Lê todos os dados da planilha no intervalo desejado
        List<List<Object>> todasAsLinhas = lerDados(nomePlanilha, colunaInicial, colunaFinal);

        // Encontra o índice da linha a ser atualizada
        int linhaAlvo = -1;
        for (int i = 0; i < todasAsLinhas.size(); i++) {
            List<Object> linha = todasAsLinhas.get(i);

            // Compara o conteúdo da linha com valoresAntigos
            if (linha.equals(valoresAntigos.get(0))) {
                linhaAlvo = i + 2; // +2: uma para pular o cabeçalho, outra porque índice começa em 0
                break;
            }
        }

        if (linhaAlvo == -1) {
            throw new IOException("Linha a ser atualizada não encontrada.");
        }

        // Monta o intervalo com base na linhaAlvo
        String intervalo = nomePlanilha + "!" + colunaInicial + linhaAlvo + ":" + colunaFinal + linhaAlvo;

        ValueRange corpo = new ValueRange()
                .setRange(intervalo)
                .setValues(valoresNovos);

        UpdateValuesResponse resultado = sheetsService.spreadsheets().values()
                .update(spreadsheetId, intervalo, corpo)
                .setValueInputOption("RAW")
                .execute();

        System.out.printf("Linha %d atualizada (%d células) na aba '%s'.%n",
                linhaAlvo, resultado.getUpdatedCells(), nomePlanilha);
    }

    public void deletarDados(String nomePlanilha, String colunaInicial, String colunaFinal, int numeroLinha){
        try {
            // Define o intervalo a ser deletado
            String intervalo = nomePlanilha + "!" + colunaInicial + numeroLinha + ":" + colunaFinal + numeroLinha;

            // Cria a requisição de exclusão
            Request request = new Request().setDeleteRange(new DeleteRangeRequest()
                    .setRange(new GridRange()
                            .setSheetId(sheetsService.spreadsheets().get(spreadsheetId).execute()
                                    .getSheets().stream()
                                    .filter(s -> s.getProperties().getTitle().equals(nomePlanilha))
                                    .findFirst()
                                    .orElseThrow(() -> new IOException("Aba '" + nomePlanilha + "' não encontrada"))
                                    .getProperties().getSheetId())
                            .setStartRowIndex(numeroLinha - 1)
                            .setEndRowIndex(numeroLinha))
                    .setShiftDimension("ROWS"));

            // Executa a atualização da planilha
            BatchUpdateSpreadsheetRequest bodyUpdate = new BatchUpdateSpreadsheetRequest()
                    .setRequests(Collections.singletonList(request));

            sheetsService.spreadsheets().batchUpdate(spreadsheetId, bodyUpdate).execute();

            System.out.printf("Linha %d deletada com sucesso na aba '%s'.%n", numeroLinha, nomePlanilha);
        } catch (IOException e) {
            System.err.println("Erro ao deletar linha: " + e.getMessage());
        }
    }

}
