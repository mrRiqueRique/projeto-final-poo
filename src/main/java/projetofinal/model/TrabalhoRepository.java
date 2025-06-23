package projetofinal.model;

import java.util.List;

public class TrabalhoRepository extends Repository<Trabalho> {
    private static TrabalhoRepository instancia;

    private TrabalhoRepository() {
        super();
        carregarTrabalhos();
    }

    public static TrabalhoRepository getInstancia() {
        if (instancia == null) {
            instancia = new TrabalhoRepository();
        }
        return instancia;
    }

    private void carregarTrabalhos() {
        try {
            setItems(service.getTrabalhos());
        } catch (Exception e) {
            System.err.println("Erro ao carregar trabalhos: " + e.getMessage());
        }
    }

    public List<Trabalho> getTrabalhos() {
        return getItems();
    }

    public Trabalho getTrabalho(String codigoDisciplina, String nomeTrabalho) {
        return getTrabalhos().stream()
                .filter(trabalho -> trabalho.getCodigoDisciplina().equals(codigoDisciplina) && trabalho.getNome().equals(nomeTrabalho))
                .findFirst()
                .orElse(null);
    }

    public List<Trabalho> getTrabalhosPorDisciplina(String codigoDisciplina) {
        return getItems().stream()
                .filter(trabalho -> trabalho.getCodigoDisciplina().equals(codigoDisciplina))
                .toList();
    }

    public List<Trabalho> getTrabalhoPorDisciplina(String codigoDisciplina) {
        return service.getTrabalhosPorDisciplina(codigoDisciplina);
    }
}
