package projetofinal.model;

import java.util.List;

public class DisciplinaRepository extends Repository<Disciplina> {
    private static DisciplinaRepository instancia;

    private DisciplinaRepository() {
        super();
        carregarDisciplinas();
    }

    public static DisciplinaRepository getInstancia() {
        if (instancia == null) {
            instancia = new DisciplinaRepository();
        }
        return instancia;
    }

    public List<Disciplina> getDisciplinasPorAluno(String raAluno) {
        return service.getDiciplinasDoAluno(raAluno);
    }

    public Disciplina getDisciplina(String codigoDisciplina) {
        return getItems().stream().filter(s -> s.getCodigo().equals(codigoDisciplina)).findFirst().orElse(null);
    }

    private void carregarDisciplinas() {
        try {
            setItems(service.getDisciplinas());
        } catch (Exception e) {
            System.err.println("Erro ao carregar disciplinas: " + e.getMessage());
        }
    }

    public void adicionarDisciplina(Disciplina disciplina) {
        try {
            addItem(disciplina);
            service.adicionarDisciplina(disciplina);
        } catch (Exception e) {
            System.err.println("Erro ao adicionar disciplina: " + e.getMessage());
        }
    }
}