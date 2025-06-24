package projetofinal.model;

import java.util.List;

/**
 * Repository class for managing "Trabalho" entities.
 * Provides methods to retrieve, filter, and manage "Trabalho" objects.
 */
public class TrabalhoRepository extends Repository<Trabalho> {
    private static TrabalhoRepository instancia;

    /**
     * Private constructor to initialize the repository and load "Trabalho" items.
     */
    private TrabalhoRepository() {
        super();
        carregarTrabalhos();
    }

    /**
     * Retrieves the singleton instance of the repository.
     *
     * @return The singleton instance of TrabalhoRepository.
     */
    public static TrabalhoRepository getInstancia() {
        if (instancia == null) {
            instancia = new TrabalhoRepository();
        }
        return instancia;
    }

    /**
     * Loads "Trabalho" items into the repository.
     * Fetches the items from the service and sets them in the repository.
     * Logs an error message if an exception occurs during loading.
     */
    private void carregarTrabalhos() {
        try {
            setItems(service.getTrabalhos());
        } catch (Exception e) {
            System.err.println("Erro ao carregar trabalhos: " + e.getMessage());
        }
    }

    /**
     * Retrieves all "Trabalho" items in the repository.
     *
     * @return A list of "Trabalho" items.
     */
    public List<Trabalho> getTrabalhos() {
        return getItems();
    }

    /**
     * Retrieves a specific "Trabalho" item based on the discipline code and work name.
     *
     * @param codigoDisciplina The code of the discipline associated with the work.
     * @param nomeTrabalho     The name of the work.
     * @return The "Trabalho" item matching the criteria, or null if not found.
     */
    public Trabalho getTrabalho(String codigoDisciplina, String nomeTrabalho) {
        return getTrabalhos().stream()
                .filter(trabalho -> trabalho.getCodigoDisciplina().equals(codigoDisciplina) && trabalho.getNome().equals(nomeTrabalho))
                .findFirst()
                .orElse(null);
    }

    /**
     * Retrieves all "Trabalho" items associated with a specific discipline.
     *
     * @param codigoDisciplina The code of the discipline.
     * @return A list of "Trabalho" items associated with the discipline.
     */
    public List<Trabalho> getTrabalhosPorDisciplina(String codigoDisciplina) {
        return getItems().stream()
                .filter(trabalho -> trabalho.getCodigoDisciplina().equals(codigoDisciplina))
                .toList();
    }

    /**
     * Fetches "Trabalho" items for a specific discipline directly from the service.
     *
     * @param codigoDisciplina The code of the discipline.
     * @return A list of "Trabalho" items fetched from the service.
     */
    public List<Trabalho> getTrabalhoPorDisciplina(String codigoDisciplina) {
        return service.getTrabalhosPorDisciplina(codigoDisciplina);
    }
}