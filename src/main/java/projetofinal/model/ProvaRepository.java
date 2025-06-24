package projetofinal.model;

import java.util.List;

public class ProvaRepository extends Repository<Prova> {
    private static ProvaRepository instancia;

    private ProvaRepository() {
        super();
        carregarProvas();
    }

    public static ProvaRepository getInstancia() {
        if (instancia == null) {
            instancia = new ProvaRepository();
        }
        return instancia;
    }

    public Prova getProva(String codigoDisciplina, String nomeAvaliacao) {
        return getItems().stream()
                .filter(prova -> prova.getCodigoDisciplina().equals(codigoDisciplina) && prova.getNome().equals(nomeAvaliacao))
                .findFirst()
                .orElse(null);
    }

    public List<Prova> getProvasPorDisciplina(String codigoDisciplina) {
        return getItems().stream()
                .filter(prova -> prova.getCodigoDisciplina().equals(codigoDisciplina))
                .toList();
    }

    private void carregarProvas() {
        try {
            setItems(service.getProvas());
        } catch (Exception e) {
            System.err.println("Erro ao carregar provas: " + e.getMessage());
        }
    }
}