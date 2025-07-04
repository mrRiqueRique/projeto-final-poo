package projetofinal.model;

import java.util.List;

/**
 * Classe responsável por gerenciar o repositório de provas.
 * <p>
 * Esta classe implementa o padrão Singleton para garantir que apenas uma instância
 * do repositório seja criada durante a execução do programa. Ela fornece métodos
 * para realizar operações relacionadas às provas, como carregar, buscar e listar provas.
 */
public class ProvaRepository extends Repository<Prova> {
    private static ProvaRepository instancia;

    /**
     * Construtor privado para implementar o padrão Singleton.
     * Inicializa o repositório e tenta carregar as provas do serviço externo.
     */
    private ProvaRepository() {
        super();
        carregarProvas();
    }

    /**
     * Retorna a instância única de ProvaRepository.
     *
     * @return A instância de ProvaRepository.
     */
    public static ProvaRepository getInstancia() {
        if (instancia == null) {
            instancia = new ProvaRepository();
        }
        return instancia;
    }

    /**
     * Retorna uma prova específica com base no código da disciplina e no nome da avaliação.
     *
     * @param codigoDisciplina O código da disciplina associada à prova.
     * @param nomeAvaliacao    O nome da avaliação.
     * @return A prova correspondente aos critérios, ou `null` se não encontrada.
     */
    public Prova getProva(String codigoDisciplina, String nomeAvaliacao) {
        System.out.println(getItems());
        return getItems().stream()
                .filter(prova -> prova.getCodigoDisciplina().equals(codigoDisciplina.trim()) && prova.getNome().equals(nomeAvaliacao.trim()))
                .findFirst()
                .orElse(null);
    }

    /**
     * Retorna a lista de provas associadas a uma disciplina específica.
     *
     * @param codigoDisciplina O código da disciplina.
     * @return A lista de provas da disciplina.
     */
    public List<Prova> getProvasPorDisciplina(String codigoDisciplina) {
        return getItems().stream()
                .filter(prova -> prova.getCodigoDisciplina().equals(codigoDisciplina))
                .toList();
    }

    /**
     * Carrega as provas a partir do serviço externo.
     * <p>
     * Este método busca as provas do serviço e as adiciona ao repositório.
     */
    private void carregarProvas() {
        try {
            setItems(service.getProvas());
        } catch (Exception e) {
            System.err.println("Erro ao carregar provas: " + e.getMessage());
        }
    }
}