package projetofinal.model;

import javafx.concurrent.Task;
import projetofinal.filters.ComparatorPrioridade;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AlunoLogado {
    private static AlunoLogado alunoLogado;
    private Aluno aluno;
    private List<MetodoDeAvaliacao> avaliacoes;
    private AlunoRepository alunoRepository;
    private Service service;
    private AulasRepository aulasRepository;
    private List<Aula> aulasALuno;

    private AlunoLogado() {
        try {
            this.aulasRepository = AulasRepository.getInstancia();
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
        this.aulasALuno = carregarAulas();

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

    public List<Aula> carregarAulas() {
        if (aluno != null) {
            List<Aula> aulas = new ArrayList<>();
            for (Disciplina disciplina : aluno.getDisciplinas()) {
                List<Aula> aulasDaDisciplina = this.aulasRepository.listarAulasPorDisciplina(disciplina.getCodigo());
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

    public List<Aula> getAulas() {
        return this.aulasALuno;
    }

    public Task<List<TodoItem>> getTodoItensUrgentes() {
        return new Task<>() {
            @Override
            protected List<TodoItem> call() {
                return alunoLogado.getTodoList().listarItems().stream().sorted(new ComparatorPrioridade()).toList();
            }
        };
    }

    public List<Aula> getAulasHoje() {
        DayOfWeek hoje = LocalDate.now().getDayOfWeek();
        List<Aula> aulas = alunoLogado.getAulas();
        if (aulas == null) return new ArrayList<>();

        return aulas.stream().filter(aula -> DiaSemanaRepository.traduzir(aula.getDiaSemana()) == hoje).toList();
    }
}