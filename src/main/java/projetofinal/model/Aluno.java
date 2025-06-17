package projetofinal.model;

import java.util.ArrayList;
import java.util.List;

import projetofinal.exceptions.DisciplinaNaoEncontradaException;

public class Aluno {

    private String nome;
    private String ra;
    private String curso;
    private double CR;
    private List<Disciplina> disciplinas;
    private TodoList todoList;

    public Aluno(String ra, String nome, String curso) {
        this.nome = nome;
        this.ra = ra;
        this.curso = curso;
        this.disciplinas = new ArrayList<>();
    }

    public String getNome() {
        return this.nome;
    }

    public String getRa() {
        return this.ra;
    }

    public String getCurso() {
        return this.curso;
    }

    public double getCR() {
        return this.CR;
    }

    public TodoList getTodoList() {
        return this.todoList;
    }

    public void setCR(double CR) {
        this.CR = CR;
    }

    public void setTodoList(TodoList todoList) {
        this.todoList = todoList;
    }

    public void cadastrarDisciplina(Disciplina disciplina) {
        this.disciplinas.add(disciplina);
    }

    public void finalizarDisciplina(Disciplina disciplina) {
        try {
            if (!disciplinas.contains(disciplina))
                throw new DisciplinaNaoEncontradaException("Disciplina não encontrada");

            this.disciplinas.remove(disciplina);
        } catch (DisciplinaNaoEncontradaException e) {
            System.out.println(e.getMessage());
        }
    }

    public void lancarNota(MetodoDeAvaliacao metodoDeAvaliacao, double nota) {
        metodoDeAvaliacao.lancarNota(nota);
    }

    public List<Disciplina> getDisciplinas() {
        return this.disciplinas;
    }

}
