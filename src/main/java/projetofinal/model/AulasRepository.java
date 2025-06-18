package projetofinal.model;

import java.util.List;

public class AulasRepository {
    private static AulasRepository instancia;
    private Service service;

    private AulasRepository() {
        service = new Service();
    }

    public static AulasRepository getInstancia() {
        if (instancia == null) {
            instancia = new AulasRepository();
        }
        return instancia;
    }

    public void adicionarAulasDisciplina(String codigoDisciplina, List<Aula> aulas) {
        service.adicionarAulasDisciplina(codigoDisciplina, aulas);
    }

    public List<Aula> listarAulasPorDisciplina(String codigoDisciplina) {
        return service.getAulasDiscipla(codigoDisciplina);
    }
}