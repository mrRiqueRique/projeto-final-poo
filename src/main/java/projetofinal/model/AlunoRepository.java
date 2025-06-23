package projetofinal.model;

public class AlunoRepository extends Repository<Aluno> {
    private static AlunoRepository instancia;

    private AlunoRepository() {
        super();
        try {
            carregarAlunosDoSheets();
        } catch (Exception e) {
            System.err.println("Erro ao inicializar AlunoRepository: " + e.getMessage());
        }
    }

    public static AlunoRepository getInstancia() {
        if (instancia == null) {
            instancia = new AlunoRepository();
        }
        return instancia;
    }

    public Aluno getAlunoPorRa(String ra) {
        return getItems().stream()
                .filter(a -> a.getRa().equals(ra))
                .findFirst()
                .orElse(null);
    }

    private void carregarAlunosDoSheets() {
        try {
            setItems(service.getAlunos()); // Use the Service method to load students
        } catch (Exception e) {
            System.err.println("Erro ao carregar alunos do Sheets: " + e.getMessage());
        }
    }

    public void adicionarAluno(Aluno aluno) {
        try {
            addItem(aluno);
            service.adicionarAluno(aluno); // Use a Service method to add a student
        } catch (Exception e) {
            System.err.println("Erro ao adicionar aluno: " + e.getMessage());
        }
    }

    public void removerAluno(String ra) {
        Aluno aluno = getAlunoPorRa(ra);
        if (aluno != null) {
            removeItem(aluno);
            service.deletarAluno(aluno); // Use a Service method to remove a student
        } else {
            System.out.println("Aluno não encontrado.");
        }
    }

    public void atualizarAluno(Aluno alunoVelho, Aluno alunoNovo) {
        if (alunoVelho == null) {
            System.out.println("Aluno não encontrado.");
        } else {
            service.atualizarAluno(alunoVelho, alunoNovo);
        }
    }
}