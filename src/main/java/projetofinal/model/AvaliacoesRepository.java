package projetofinal.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável por gerenciar o repositório de avaliações.
 * <p>
 * Esta classe implementa o padrão Singleton para garantir que apenas uma instância
 * do repositório seja criada durante a execução do programa. Ela fornece métodos
 * para realizar operações relacionadas às avaliações, como carregar avaliações
 * e buscar trabalhos por disciplina.
 */
public class AvaliacoesRepository extends Repository<MetodoDeAvaliacao> {
    private static AvaliacoesRepository instancia;

    /**
     * Construtor privado para implementar o padrão Singleton.
     * Inicializa o repositório e tenta carregar as avaliações do serviço externo.
     */
    private AvaliacoesRepository() {
        super();
        try {
            carregarAvaliacoes();
        } catch (Exception e) {
            System.err.println("Erro ao inicializar AvaliacoesRepository: " + e.getMessage());
        }
    }

    /**
     * Retorna a instância única de AvaliacoesRepository.
     *
     * @return A instância de AvaliacoesRepository.
     */
    public static AvaliacoesRepository getInstancia() {
        if (instancia == null) {
            instancia = new AvaliacoesRepository();
        }
        return instancia;
    }

    /**
     * Carrega as avaliações a partir do serviço externo.
     * <p>
     * Este método busca provas e trabalhos do serviço e os adiciona ao repositório.
     */
    private void carregarAvaliacoes() {
        try {
            setItems(new ArrayList<>(service.getProvas()));
            getItems().addAll(service.getTrabalhos());
        } catch (Exception e) {
            System.err.println("Erro ao carregar avaliações: " + e.getMessage());
        }
    }

    /**
     * Busca os trabalhos associados a uma disciplina específica.
     *
     * @param codigoDisciplina O código da disciplina.
     * @return A lista de trabalhos da disciplina.
     */
    public List<Trabalho> buscarTrabalhoPorDisciplina(String codigoDisciplina) {
        return service.getTrabalhosPorDisciplina(codigoDisciplina);
    }
}