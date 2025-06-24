package projetofinal.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import projetofinal.exceptions.DataInvalidaException;

/**
 * Represents a TodoItem associated with a student.
 * A TodoItem contains information about the student, the task, its priority, and its completion status.
 */
public class TodoItem {
    private String raAluno;
    private String nome;
    private Disciplina disciplina;
    private MetodoDeAvaliacao avaliacao;
    private String prioridade;
    private boolean concluido;
    private String data;

    /**
     * Constructs a new TodoItem.
     *
     * @param raAluno    The RA (Registro Acadêmico) of the student.
     * @param nome       The name of the task.
     * @param disciplina The discipline associated with the task.
     * @param avaliacao  The evaluation method for the task.
     * @param prioridade The priority of the task.
     * @param data       The due date of the task in the format "dd/MM/yyyy".
     */
    public TodoItem(String raAluno, String nome, Disciplina disciplina, MetodoDeAvaliacao avaliacao, String prioridade, String data) {
        this.raAluno = raAluno;
        this.nome = nome;
        this.disciplina = disciplina;
        this.avaliacao = avaliacao;
        this.prioridade = prioridade;
        this.data = data;
        this.concluido = false;
    }

    /**
     * Gets the RA of the student.
     *
     * @return The RA of the student.
     */
    public String getRaAluno() {
        return this.raAluno;
    }

    /**
     * Gets the name of the task.
     *
     * @return The name of the task.
     */
    public String getNome() {
        return this.nome;
    }

    /**
     * Gets the discipline associated with the task.
     *
     * @return The discipline associated with the task.
     */
    public Disciplina getDisciplina() {
        return this.disciplina;
    }

    /**
     * Gets the evaluation method for the task.
     *
     * @return The evaluation method for the task.
     */
    public MetodoDeAvaliacao getAvaliacao() {
        return this.avaliacao;
    }

    /**
     * Gets the priority of the task.
     *
     * @return The priority of the task.
     */
    public String getPrioridade() {
        return this.prioridade;
    }

    /**
     * Gets the completion status of the task.
     *
     * @return True if the task is completed, false otherwise.
     */
    public boolean getConcluido() {
        return this.concluido;
    }

    /**
     * Gets the due date of the task.
     *
     * @return The due date of the task in the format "dd/MM/yyyy".
     */
    public String getData() {
        return this.data;
    }

    /**
     * Sets the name of the task.
     *
     * @param nome The new name of the task.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Sets the discipline associated with the task.
     *
     * @param disciplina The new discipline associated with the task.
     */
    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    /**
     * Sets the evaluation method for the task.
     *
     * @param avaliacao The new evaluation method for the task.
     */
    public void setAvaliacao(MetodoDeAvaliacao avaliacao) {
        this.avaliacao = avaliacao;
    }

    /**
     * Sets the priority of the task.
     *
     * @param prioridade The new priority of the task.
     */
    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade;
    }

    /**
     * Updates the due date of the task.
     * Throws an exception if the new date is in the past.
     *
     * @param data The new due date in the format "dd/MM/yyyy".
     */
    public void alterarData(String data) {
        LocalDate dataAtual = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataNova = LocalDate.parse(data, formatter);

        try {
            // If the new date has already passed, it cannot be modified
            if (dataNova.isBefore(dataAtual))
                throw new DataInvalidaException("Não é possível inserir uma data passada");

            this.data = data;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Sets the completion status of the task.
     *
     * @param concluido True if the task is completed, false otherwise.
     */
    public void setConcluido(Boolean concluido) {
        this.concluido = concluido;
    }

    /**
     * Sets the due date of the task.
     *
     * @param data The new due date in the format "dd/MM/yyyy".
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * Marks the task as completed or not completed.
     *
     * @param concluir True to mark the task as completed, false otherwise.
     */
    public void concluir(boolean concluir) {
        this.concluido = concluir;
    }
}