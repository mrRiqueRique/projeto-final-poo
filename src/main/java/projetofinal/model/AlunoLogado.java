package projetofinal.model;

public class AlunoLogado {
    private static AlunoLogado alunoLogado;
    private Aluno aluno;
    private Service service;

    private AlunoLogado() {
        this.service = new Service(); // Inicializa o Service
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
        this.aluno = service.getAluno(ra); // Obtém o aluno via Service
        if (this.aluno == null) {
            System.out.println("Aluno não encontrado.");
        }
    }

    public void cadastrarDisciplina(Disciplina disciplina) {
        if (aluno != null) {
            aluno.cadastrarDisciplina(disciplina); // Delegação para a classe Aluno
        } else {
            System.out.println("Nenhum aluno logado.");
        }
    }
}