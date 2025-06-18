package projetofinal.model;

import java.util.ArrayList;
import java.util.List;

public class Disciplina {
    private String nome;
    private String codigo;
    private int creditos;
    //private media
    private int faltas;
    private String professor;
    private String PED;
    private List<Aula> aulas;
    private List<MetodoDeAvaliacao> avaliacoes;

    public Disciplina(String codigo, String nome, String PED,  int creditos, int faltas, String professor){
        this.nome=nome;
        this.codigo = codigo;
        this.creditos = creditos;
        this.PED = PED;
        this.faltas = faltas;
        this.professor = professor;
        this.aulas = new ArrayList<>();
        this.avaliacoes = new ArrayList<>();
    }

    public String getNome(){
        return this.nome;
    }

    public String getCodigo(){
        return this.codigo;
    }

    public int getCreditos(){
        return this.creditos;
    }

    public String getProfessor(){
        return this.professor;
    }

    public int getFaltas() {
        return this.faltas;
    }

    public void setFaltas(int faltas) {
        this.faltas = faltas;
    }

    public List<MetodoDeAvaliacao> getAvaliacoes(){
        return this.avaliacoes;
    }

    public List<Aula> getAulas(){
        return this.aulas;
    }

    // ver como quer tratar quando ultrapassar as faltas restantes, se vai mandar mensagem, etc

    public int consultarFaltasRestantes(){
        int semanas = 16;
        int aulasPorSemana = this.creditos/2;
        int aulasTotais = semanas*aulasPorSemana;
        int faltasPermitidas = (int)(aulasTotais*0.75) - 1;
        return faltasPermitidas - this.faltas;
    }

    public void computarFalta(){
        this.faltas++;
    }

    public void adicionarAvaliacao(MetodoDeAvaliacao avaliacao){
        this.avaliacoes.add(avaliacao);
    }




    
}
