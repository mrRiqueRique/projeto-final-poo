package projetofinal.model;

import javafx.concurrent.Task;
import projetofinal.filters.ComparatorPrioridade;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AlunoLogado {
    private static AlunoLogado alunoLogado;
    private AlunoRepository alunoRepository;
    private DisciplinaRepository disciplinaRepository;
    private AulasRepository aulasRepository;
    private ProvaRepository provaRepository;
    private TodoItemRepository todoItemRepository;
    private TrabalhoRepository trabalhoRepository;

    private Aluno aluno;
    private List<MetodoDeAvaliacao> avaliacoes;
    private Service service;
    private TodoList todoList;
    private List<Aula> aulas;

    private AlunoLogado() {
        try {
            this.aulasRepository = AulasRepository.getInstancia();
            this.alunoRepository = AlunoRepository.getInstancia();
            this.disciplinaRepository = DisciplinaRepository.getInstancia();
            this.provaRepository = ProvaRepository.getInstancia();
            this.trabalhoRepository = TrabalhoRepository.getInstancia();
            this.todoItemRepository = TodoItemRepository.getInstancia();
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

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public void logout() {
        alunoLogado = null;
    }

    public Boolean logarAluno(String ra, String senha) {
        this.aluno = alunoRepository.getAlunoPorRa(ra);
        if (this.aluno == null) return false;
        if (!this.aluno.getSenha().equals(senha.trim())) {
            System.out.println("Senha incorreta. " + this.aluno.getSenha() + " " + senha);
            return false;
        }


        this.aluno.setTodoList(todoItemRepository.getTodoListPorAluno(ra));
        this.aluno.setDisciplinas(this.disciplinaRepository.getDisciplinasPorAluno(ra));
        this.avaliacoes = new ArrayList<>();
        this.aulas = new ArrayList<>();
        for (Disciplina disciplina : this.aluno.getDisciplinas()) {
            System.out.println(disciplina.getCodigo());
            this.aulas.addAll(aulasRepository.listarAulasPorDisciplina(disciplina.getCodigo()));
            this.avaliacoes.addAll(provaRepository.getProvasPorDisciplina(disciplina.getCodigo()));
            this.avaliacoes.addAll(trabalhoRepository.getTrabalhosPorDisciplina(disciplina.getCodigo()));
        }

        return true;
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

    public Service getService(){
        return this.service;
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
        return this.aulas;
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

    public void cadastrarTodoItem(String nome, String codigoDisciplina, String nomeAvaliacao, String data, String prioridade) {
        Disciplina disciplina = disciplinaRepository.getDisciplina(codigoDisciplina);
        Prova prova = provaRepository.getProva(codigoDisciplina, nomeAvaliacao);
        Trabalho trabalho = trabalhoRepository.getTrabalho(codigoDisciplina, nomeAvaliacao);
        MetodoDeAvaliacao metodoDeAvaliacao = (prova != null) ? prova : trabalho;
        TodoItem todoItem = new TodoItem(this.aluno.getRa(), nome, disciplina, metodoDeAvaliacao, prioridade, data);
        this.aluno.getTodoList().adicionarItem(todoItem);
        service.adicionarTodoItem(this.getAluno().getRa(), todoItem);
    }
}