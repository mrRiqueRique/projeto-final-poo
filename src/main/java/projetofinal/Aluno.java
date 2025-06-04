package projetofinal;

import java.util.ArrayList;
import java.util.List;

public class Aluno {

    private String nome;
    private String curso;
    private double CR;
    private List<Disciplina> disciplinas;

    public Aluno(String nome, String curso){
        this.nome = nome;
        this.curso = curso;
        this.disciplinas = new ArrayList<>();
    }

    public String getNome(){
        return this.nome;
    }

    public String getCurso(){
        return this.curso;
    }

    public double getCR(){
        return this.CR;
    }

}
