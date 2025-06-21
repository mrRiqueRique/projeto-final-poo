package projetofinal.model;

import java.util.List;

public class TrabalhoRepository {
    private static TrabalhoRepository instancia;
    private Service service;
    private List<Trabalho> trabalhos;

    private TrabalhoRepository() {
        service = new Service();
        carregarTrabalhos();
    }

    public static TrabalhoRepository getInstancia() {
        if (instancia == null) {
            instancia = new TrabalhoRepository();
        }
        return instancia;
    }

    private void carregarTrabalhos() {
        this.trabalhos = service.getTrabalhos();
    }

    public List<Trabalho> getTrabalhos() {
        return this.trabalhos;
    }

    public Trabalho getTrabalho(String codigoDisciplina, String nomeTrabalho) {
        return this.trabalhos.stream()
                .filter(trabalho -> trabalho.getCodigoDisciplina().equals(codigoDisciplina) && trabalho.getNome().equals(nomeTrabalho))
                .findFirst()
                .orElse(null);
    }

    public List<Trabalho> getTrabalhosPorDisciplina(String codigoDisciplina) {
        return this.trabalhos.stream()
                .filter(trabalho -> trabalho.getCodigoDisciplina().equals(codigoDisciplina))
                .toList();
    }

    public List<Trabalho> getTrabalhoPorDisciplina(String codigoDisciplina) {
        return service.getTrabalhosPorDisciplina(codigoDisciplina);
    }

//    public void adicionarTrabalhoDisciplina(String codigoDisciplina, Trabalho trabalho) {
//        service.adicionarTrabalhoDisciplina(codigoDisciplina, trabalho);
//    }
//
//    public void lancarNotaTrabalho(String codigoDisciplina, String codigoTrabalho, double nota) {
//        service.lancarNotaTrabalho(codigoDisciplina, codigoTrabalho, nota);
//    }
}
