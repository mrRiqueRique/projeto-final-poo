package projetofinal.model;

import java.util.ArrayList;
import java.util.List;

public class AvaliacoesRepository {
    private static AvaliacoesRepository instancia;
    private List<MetodoDeAvaliacao> avaliacoes = new ArrayList<>();
    private Service service;

    private AvaliacoesRepository() {
        try {
            service = new Service();
            carregarAvaliacoes();
        } catch (Exception e) {
            System.err.println("Erro ao inicializar AlunoRepository: " + e.getMessage());
        }
    }

    public static AvaliacoesRepository getInstancia() {
        if (instancia == null) {
            instancia = new AvaliacoesRepository();
        }
        return instancia;
    }

    List<MetodoDeAvaliacao> getAvaliacoes() {
        return this.avaliacoes;
    }

    private void carregarAvaliacoes() {
        try {
            avaliacoes = new ArrayList<>(service.getProvas());
            avaliacoes.addAll(service.getTrabalhos());
        } catch (Exception e) {
            System.err.println("Erro ao carregar avaliações: " + e.getMessage());
        }
    }

    public List<Trabalho> buscarTrabalhoPorDisciplina(String codigoDisciplina) {
        return service.getTrabalhosPorDisciplina(codigoDisciplina);
    }


    // Métodos para manipular avaliações, como adicionar, remover, listar, etc.
}
