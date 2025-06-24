/**
 * Classe que representa um aluno, contendo informações como nome, RA, curso, CR, disciplinas, lista de tarefas e senha.
 * <p>
 * A classe fornece métodos para manipular e acessar os dados do aluno, como cadastrar disciplinas, lançar notas,
 * gerenciar a lista de tarefas e finalizar disciplinas.
 */
package projetofinal.model;

import java.util.ArrayList;
import java.util.List;

import projetofinal.exceptions.DisciplinaNaoEncontradaException;

public class Aluno {

    private String nome;
    private String ra;
    private String curso;
    private double CR;
    private String caminhoFoto;
    private List<Disciplina> disciplinas;
    private TodoList todoList;
    private String senha;

    /**
     * Construtor da classe Aluno.
     *
     * @param ra    Registro Acadêmico do aluno.
     * @param nome  Nome do aluno.
     * @param curso Curso do aluno.
     * @param senha Senha do aluno.
     */
    public Aluno(String ra, String nome, String curso, String senha) {
        this.nome = nome;
        this.ra = ra;
        this.curso = curso;
        this.disciplinas = new ArrayList<>();
        this.senha = senha;
    }

    /**
     * Retorna a senha do aluno.
     *
     * @return A senha do aluno.
     */
    public String getSenha() {
        return this.senha;
    }

    /**
     * Retorna o nome do aluno.
     *
     * @return O nome do aluno.
     */
    public String getNome() {
        return this.nome;
    }

    /**
     * Retorna o RA (Registro Acadêmico) do aluno.
     *
     * @return O RA do aluno.
     */
    public String getRa() {
        return this.ra;
    }

    /**
     * Retorna o curso do aluno.
     *
     * @return O curso do aluno.
     */
    public String getCurso() {
        return this.curso;
    }

    /**
     * Retorna o Coeficiente de Rendimento (CR) do aluno.
     *
     * @return O CR do aluno.
     */
    public double getCR() {
        return this.CR;
    }

    /**
     * Retorna a lista de tarefas (TodoList) do aluno.
     *
     * @return A lista de tarefas do aluno.
     */
    public TodoList getTodoList() {
        return this.todoList;
    }

    /**
     * Retorna o caminho da foto do aluno.
     *
     * @return O caminho da foto do aluno.
     */
    public String getCaminhoFoto() {
        return this.caminhoFoto;
    }

    /**
     * Define o nome do aluno.
     *
     * @param nome O nome do aluno.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Define o curso do aluno.
     *
     * @param curso O curso do aluno.
     */
    public void setCurso(String curso) {
        this.curso = curso;
    }

    /**
     * Define o Coeficiente de Rendimento (CR) do aluno.
     *
     * @param CR O CR do aluno.
     */
    public void setCR(double CR) {
        this.CR = CR;
    }

    /**
     * Define as disciplinas do aluno.
     *
     * @param disciplinas A lista de disciplinas do aluno.
     */
    public void setDisciplinas(List<Disciplina> disciplinas) {
        this.disciplinas = disciplinas;
    }

    /**
     * Define a lista de tarefas (TodoList) do aluno.
     *
     * @param todoList A lista de tarefas do aluno.
     */
    public void setTodoList(TodoList todoList) {
        this.todoList = todoList;
    }

    /**
     * Define o caminho da foto do aluno.
     *
     * @param caminhoFoto O caminho da foto do aluno.
     */
    public void setCaminhoFoto(String caminhoFoto) {
        this.caminhoFoto = caminhoFoto;
    }

    /**
     * Adiciona uma disciplina à lista de disciplinas do aluno.
     *
     * @param disciplina A disciplina a ser adicionada.
     */
    public void cadastrarDisciplina(Disciplina disciplina) {
        this.disciplinas.add(disciplina);
    }

    /**
     * Remove uma disciplina da lista de disciplinas do aluno.
     *
     * @param disciplina A disciplina a ser removida.
     * @throws DisciplinaNaoEncontradaException Se a disciplina não for encontrada na lista.
     */
    public void finalizarDisciplina(Disciplina disciplina) {
        try {
            if (!disciplinas.contains(disciplina))
                throw new DisciplinaNaoEncontradaException("Disciplina não encontrada");

            this.disciplinas.remove(disciplina);
        } catch (DisciplinaNaoEncontradaException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Lança uma nota para um método de avaliação.
     *
     * @param metodoDeAvaliacao O método de avaliação.
     * @param nota              A nota a ser lançada.
     */
    public void lancarNota(MetodoDeAvaliacao metodoDeAvaliacao, double nota) {
        metodoDeAvaliacao.lancarNota(nota);
    }

    /**
     * Retorna a lista de disciplinas do aluno.
     *
     * @return A lista de disciplinas do aluno.
     */
    public List<Disciplina> getDisciplinas() {
        return this.disciplinas;
    }

}