package projetofinal.model;

import java.util.ArrayList;
import java.util.List;

public class Disciplina {
    private String nome;
    private String codigo;
    private int creditos;
    private String media;
    private int faltas;
    private String professor;
    private String PED;
    private List<Aula> aulas;
    private List<MetodoDeAvaliacao> avaliacoes;

    public Disciplina(String codigo, String nome, String PED,  int creditos, String media, String professor){
        this.nome=nome;
        this.codigo = codigo;
        this.creditos = creditos;
        this.PED = PED;
        this.faltas = 0;
        this.media = media;
        this.professor = professor;
        this.aulas = new ArrayList<>();
        this.avaliacoes = new ArrayList<>();
    }

    public String getNome(){
        return this.nome;
    }

    public void setNome(String novoNome){
        this.nome = novoNome;
    }

    public String getCodigo(){
        return this.codigo;
    }

    public void setCodigo(String novoCodigo){
        this.codigo = novoCodigo;
    }


    public int getCreditos(){
        return this.creditos;
    }

    public void setCreditos(int novoCreditos){
        this.creditos = novoCreditos;
    }

    public String getProfessor(){
        return this.professor;
    }

    public void setProfessor(String novoProfessor){
        this.professor = novoProfessor;
    }

    public int getFaltas() {
        return this.faltas;
    }

    public String getPED(){
        return this.PED;
    }

    public void setPED(String novoPED){
        this.PED = novoPED;
    }

    public void setFaltas(int faltas) {
        this.faltas = faltas;
    }

    public List<MetodoDeAvaliacao> getAvaliacoes(){
        return this.avaliacoes;
    }
    
    public void setAvaliacoes(List<MetodoDeAvaliacao> avaliacoes){
        this.avaliacoes = avaliacoes;
    }

    public List<Aula> getAulas(){
        return this.aulas;
    }

    public void setAulas(List<Aula> aulas){
        this.aulas = aulas;
    }

    public String getMedia(){
        return this.media;
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
