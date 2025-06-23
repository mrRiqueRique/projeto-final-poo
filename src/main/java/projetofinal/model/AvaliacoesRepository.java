package projetofinal.model;

import java.util.ArrayList;
import java.util.List;

public class AvaliacoesRepository extends Repository<MetodoDeAvaliacao> {
    private static AvaliacoesRepository instancia;

    private AvaliacoesRepository() {
        super();
        try {
            carregarAvaliacoes();
        } catch (Exception e) {
            System.err.println("Erro ao inicializar AvaliacoesRepository: " + e.getMessage());
        }
    }

    public static AvaliacoesRepository getInstancia() {
        if (instancia == null) {
            instancia = new AvaliacoesRepository();
        }
        return instancia;
    }

    private void carregarAvaliacoes() {
        try {
            setItems(new ArrayList<>(service.getProvas()));
            getItems().addAll(service.getTrabalhos());
        } catch (Exception e) {
            System.err.println("Erro ao carregar avaliações: " + e.getMessage());
        }
    }

    public List<Trabalho> buscarTrabalhoPorDisciplina(String codigoDisciplina) {
        return service.getTrabalhosPorDisciplina(codigoDisciplina);
    }

}