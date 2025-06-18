package projetofinal.model;

import java.util.ArrayList;
import java.util.List;

public class AlunoLogado {
    private static AlunoLogado alunoLogado;
    private Aluno aluno;
    private List<MetodoDeAvaliacao> avaliacoes;
    private AlunoRepository alunoRepository;
    private Service service;

    private AlunoLogado() {
        try {
            this.alunoRepository = AlunoRepository.getInstancia(); // Use AlunoRepository
            this.service = new Service();
        } catch (Exception e) {
            System.err.println("Erro ao inicializar AlunoRepository: " + e.getMessage());
        }
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
        this.aluno = alunoRepository.getAlunos().stream().filter(a -> a.getRa().equals(ra)).findFirst().orElse(null); // Fetch the student from AlunoRepository
        this.aluno.setTodoList(service.getTodoListAluno(ra));
        List<Disciplina> disciplinas = service.getDiciplinasDoAluno(ra);
        for (Disciplina disciplina : disciplinas)
            this.aluno.cadastrarDisciplina(disciplina);

        this.avaliacoes = new ArrayList<>(service.getAvaliacoesAluno(ra));
        this.avaliacoes.addAll(service.getTrabalhos());

        if (this.aluno == null) {
            System.out.println("Aluno não encontrado.");
        }
    }

    public List<MetodoDeAvaliacao> getAvaliacoes() {
        return this.avaliacoes;
    }

    public TodoList getTodoList() {
        return this.aluno.getTodoList();
    }

    public List<Disciplina> getDisciplinas() {
        if (aluno != null) {
            return aluno.getDisciplinas(); // Delegate to Aluno
        } else {
            System.out.println("Nenhum aluno logado.");
            return new ArrayList<>();
        }
    }

    public void concluirTodoItem(TodoItem item) {
        if (aluno != null) {
            this.aluno.getTodoList().concluirTarefa(item); // Delegate to Aluno's TodoList
            service.deletarTodoItem(item, this.getAluno().getRa());
        } else {
            System.out.println("Nenhum aluno logado.");
        }
    }

    public void adicionarNota(MetodoDeAvaliacao metodoDeAvaliacao, double nota) {
        if (aluno != null) {
            aluno.lancarNota(metodoDeAvaliacao, nota); // Delegate to Aluno
        } else {
            System.out.println("Nenhum aluno logado.");
        }
    }


    public void cadastrarDisciplina(Disciplina disciplina) {
        if (aluno != null) {
            aluno.cadastrarDisciplina(disciplina); // Delegate to Aluno
        } else {
            System.out.println("Nenhum aluno logado.");
        }
    }

    public List<Aula> getAulas() {
        if (aluno != null) {
            List<Aula> aulas = new ArrayList<>();
            for (Disciplina disciplina : aluno.getDisciplinas()) {
                List<Aula> aulasDaDisciplina = AulasRepository.getInstancia().listarAulasPorDisciplina(disciplina.getCodigo());
                if (aulasDaDisciplina != null) {
                    aulas.addAll(aulasDaDisciplina);
                }
            }
            return aulas;
        } else {
            System.out.println("Nenhum aluno logado.");
            return new ArrayList<>();
        }
    }
}