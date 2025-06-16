package projetofinal.model;

public class AlunoLogado {
    private static AlunoLogado alunoLogado;
    private Aluno aluno;
    private AlunoRepository alunoRepository;

    private AlunoLogado() {
        this.alunoRepository = AlunoRepository.getInstancia(); // Use AlunoRepository
    }

    public static AlunoLogado getInstance() {
        if (alunoLogado == null) {
            alunoLogado = new AlunoLogado();
        }
        return alunoLogado;
    }

    public Aluno getAluno() {
        return this.aluno;
    }

    public void logout() {
        alunoLogado = null;
    }

    public void logarAluno(String ra) {
        this.aluno = alunoRepository.getAlunos()
                                    .stream()
                                    .filter(a -> a.getRa().equals(ra))
                                    .findFirst()
                                    .orElse(null); // Fetch the student from AlunoRepository
        if (this.aluno == null) {
            System.out.println("Aluno não encontrado.");
        }
    }

    public void cadastrarDisciplina(Disciplina disciplina) {
        if (aluno != null) {
            aluno.cadastrarDisciplina(disciplina); // Delegate to Aluno
        } else {
            System.out.println("Nenhum aluno logado.");
        }
    }
}