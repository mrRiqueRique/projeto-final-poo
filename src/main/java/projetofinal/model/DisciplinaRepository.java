package projetofinal.model;

import java.util.List;

public class DisciplinaRepository {
    private static DisciplinaRepository instancia;
    private List<Disciplina> disciplinas;
    private Service service;

    private DisciplinaRepository() {
        service = new Service();
        carregarDisciplinas();
    }

    public static DisciplinaRepository getInstancia() {
        if (instancia == null) {
            instancia = new DisciplinaRepository();
        }
        return instancia;
    }

    public List<Disciplina> getDisciplinas() {
        return this.disciplinas;
    }

    private void carregarDisciplinas() {
        try {
            disciplinas = service.getDisciplinas();
        } catch (Exception e) {
            System.err.println("Erro ao carregar disciplinas: " + e.getMessage());
        }
    }

    public void adicionarDisciplina(Disciplina disciplina) {
        try {
            service.adicionarDisciplina(disciplina);
            disciplinas.add(disciplina);
        } catch (Exception e) {
            System.err.println("Erro ao adicionar disciplina: " + e.getMessage());
        }
    }

}