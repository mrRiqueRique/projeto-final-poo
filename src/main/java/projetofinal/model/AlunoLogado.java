package projetofinal.model;

import javafx.concurrent.Task;
import projetofinal.filters.ComparatorPrioridade;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável por gerenciar o estado do aluno atualmente logado no sistema.
 * <p>
 * Esta classe implementa o padrão Singleton para garantir que apenas uma instância
 * do aluno logado seja mantida durante a execução do programa. Ela fornece métodos
 * para realizar operações relacionadas ao aluno logado, como login, logout,
 * gerenciamento de disciplinas, tarefas, aulas e avaliações.
 */
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
            System.err.println("Erro ao inicializar AlunoLogado: " + e.getMessage());
        }
    }

    /**
     * Retorna a instância única de AlunoLogado.
     *
     * @return A instância de AlunoLogado.
     */
    public static AlunoLogado getInstance() {
        if (alunoLogado == null) {
            alunoLogado = new AlunoLogado();
        }
        return alunoLogado;
    }

    /**
     * Retorna o aluno atualmente logado.
     *
     * @return O aluno logado.
     */
    public Aluno getAluno() {
        return this.aluno;
    }

    /**
     * Define o aluno atualmente logado.
     *
     * @param aluno O aluno a ser definido como logado.
     */
    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    /**
     * Realiza o logout do aluno, removendo a instância atual.
     */
    public void logout() {
        alunoLogado = null;
    }

    /**
     * Realiza o login do aluno com base no RA e senha.
     *
     * @param ra    O registro acadêmico do aluno.
     * @param senha A senha do aluno.
     * @return `true` se o login for bem-sucedido, caso contrário `false`.
     */
    public Boolean logarAluno(String ra, String senha) {
        this.aluno = alunoRepository.getAlunoPorRa(ra);
        if (this.aluno == null) return false;
        if (!this.aluno.getSenha().equals(senha.trim())) {
            System.out.println("Senha incorreta.");
            return false;
        }

        this.aluno.setTodoList(todoItemRepository.getTodoListPorAluno(ra));
        this.aluno.setDisciplinas(this.disciplinaRepository.getDisciplinasPorAluno(ra));
        this.avaliacoes = new ArrayList<>();
        this.aulas = new ArrayList<>();

        for (Disciplina disciplina : this.aluno.getDisciplinas()) {
            this.aulas.addAll(aulasRepository.listarAulasPorDisciplina(disciplina.getCodigo()));
            this.avaliacoes.addAll(provaRepository.getProvasPorDisciplina(disciplina.getCodigo()));
            this.avaliacoes.addAll(trabalhoRepository.getTrabalhosPorDisciplina(disciplina.getCodigo()));
        }

        return true;
    }

    /**
     * Retorna a lista de métodos de avaliação do aluno logado.
     *
     * @return A lista de métodos de avaliação.
     */
    public List<MetodoDeAvaliacao> getAvaliacoes() {
        return this.avaliacoes;
    }

    /**
     * Retorna a lista de tarefas (TodoList) do aluno logado.
     *
     * @return A lista de tarefas do aluno.
     */
    public TodoList getTodoList() {
        return this.aluno.getTodoList();
    }

    /**
     * Retorna a lista de disciplinas do aluno logado.
     *
     * @return A lista de disciplinas do aluno.
     */
    public List<Disciplina> getDisciplinas() {
        if (this.aluno != null) {
            return this.aluno.getDisciplinas();
        } else {
            System.out.println("Nenhum aluno logado.");
            return new ArrayList<>();
        }
    }

    /**
     * Marca um item de tarefa como concluído.
     *
     * @param item O item de tarefa a ser concluído.
     */
    public void concluirTodoItem(TodoItem item) {
        if (aluno != null) {
            this.aluno.getTodoList().concluirTarefa(item);
            service.deletarTodoItem(item, this.getAluno().getRa());
        } else {
            System.out.println("Nenhum aluno logado.");
        }
    }

    /**
     * Adiciona uma nota a um método de avaliação.
     *
     * @param metodoDeAvaliacao O método de avaliação.
     * @param nota              A nota a ser adicionada.
     */
    public void adicionarNota(MetodoDeAvaliacao metodoDeAvaliacao, double nota) {
        if (aluno != null) {
            aluno.lancarNota(metodoDeAvaliacao, nota);
        } else {
            System.out.println("Nenhum aluno logado.");
        }
    }

    /**
     * Cadastra uma nova prova no sistema.
     *
     * @param prova A prova a ser cadastrada.
     */
    public void cadastrarProva(Prova prova) {
        if (aluno != null) {
            service.adicionarProva(prova);
        } else {
            System.out.println("Nenhum aluno logado.");
        }
    }

    /**
     * Cadastra aulas para uma disciplina.
     *
     * @param codigoDisciplina O código da disciplina.
     * @param aulas            A lista de aulas a serem cadastradas.
     */
    public void cadastrarAula(String codigoDisciplina, List<Aula> aulas) {
        if (aluno != null) {
            service.adicionarAulasDisciplina(codigoDisciplina, aulas);
        } else {
            System.out.println("Nenhum aluno logado.");
        }
    }

    /**
     * Cadastra um novo trabalho no sistema.
     *
     * @param trabalho O trabalho a ser cadastrado.
     */
    public void cadastrarTrabalho(Trabalho trabalho) {
        if (aluno != null) {
            service.adicionarTrabalho(trabalho);
        } else {
            System.out.println("Nenhum aluno logado.");
        }
    }

    /**
     * Cadastra uma nova disciplina para o aluno logado.
     *
     * @param disciplina A disciplina a ser cadastrada.
     */
    public void cadastrarDisciplina(Disciplina disciplina) {
        if (aluno != null) {
            aluno.cadastrarDisciplina(disciplina);
            service.adicionarDisciplina(disciplina);
        } else {
            System.out.println("Nenhum aluno logado.");
        }
    }

    /**
     * Retorna o serviço associado ao aluno logado.
     *
     * @return O serviço do aluno logado.
     */
    public Service getService() {
        return this.service;
    }

    /**
     * Carrega todas as aulas relacionadas às disciplinas do aluno logado.
     *
     * @return A lista de aulas carregadas.
     */
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

    /**
     * Retorna a lista de aulas do aluno logado.
     *
     * @return A lista de aulas.
     */
    public List<Aula> getAulas() {
        return this.aulas;
    }

    /**
     * Retorna uma tarefa que lista os itens urgentes do TodoList.
     *
     * @return A tarefa que lista os itens urgentes.
     */
    public Task<List<TodoItem>> getTodoItensUrgentes() {
        return new Task<>() {
            @Override
            protected List<TodoItem> call() {
                return alunoLogado.getTodoList().listarItems().stream().sorted(new ComparatorPrioridade()).toList();
            }
        };
    }

    /**
     * Retorna as aulas que ocorrem no dia atual.
     *
     * @return A lista de aulas de hoje.
     */
    public List<Aula> getAulasHoje() {
        DayOfWeek hoje = LocalDate.now().getDayOfWeek();
        List<Aula> aulas = alunoLogado.getAulas();
        if (aulas == null) return new ArrayList<>();

        return aulas.stream().filter(aula -> DiaSemanaRepository.traduzir(aula.getDiaSemana()) == hoje).toList();
    }

    /**
     * Cadastra um novo item de tarefa (TodoItem) no sistema.
     *
     * @param nome             O nome do item.
     * @param codigoDisciplina O código da disciplina associada.
     * @param nomeAvaliacao    O nome da avaliação associada.
     * @param data             A data do item.
     * @param prioridade       A prioridade do item.
     */
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