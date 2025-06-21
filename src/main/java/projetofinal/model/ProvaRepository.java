package projetofinal.model;

import java.util.List;

public class ProvaRepository {
    private static ProvaRepository instancia;
    private List<Prova> provas;
    private Service service;

    private ProvaRepository() {
        service = new Service();
        carregarProvas();
    }

    public static ProvaRepository getInstancia() {
        if (instancia == null) {
            instancia = new ProvaRepository();
        }
        return instancia;
    }

    public List<Prova> getProvas() {
        return this.provas;
    }

    public Prova getProva(String codigoDisciplina, String nomeAvaliacao) {
        return this.provas.stream()
                .filter(prova -> prova.getCodigoDisciplina().equals(codigoDisciplina) && prova.getNome().equals(nomeAvaliacao))
                .findFirst()
                .orElse(null);
    }

    public List<Prova> getProvasPorDisciplina(String codigoDisciplina) {
        return this.provas.stream()
                .filter(prova -> prova.getCodigoDisciplina().equals(codigoDisciplina))
                .toList();
    }

    private void carregarProvas() {
        try {
            provas = service.getProvas();
        } catch (Exception e) {
            System.err.println("Erro ao carregar provas: " + e.getMessage());
        }
    }

    public void adicionarProva(Prova prova, String codigoDisciplina) {
        try {
            service.adicionarProva(prova, codigoDisciplina);
            provas.add(prova);
        } catch (Exception e) {
            System.err.println("Erro ao adicionar prova: " + e.getMessage());
        }
    }
}