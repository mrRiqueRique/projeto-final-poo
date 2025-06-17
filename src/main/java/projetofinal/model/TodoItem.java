package projetofinal.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import projetofinal.exceptions.DataInvalidaException;

public class TodoItem {
    private String id;
    private String nome;
    private Disciplina disciplina;
    private MetodoDeAvaliacao avaliacao;
    private String prioridade;
    private boolean concluido;
    private String data;

    public TodoItem(String nome, Disciplina disciplina, MetodoDeAvaliacao avaliacao, String prioridade, String data){
        this.nome = nome;
        this.disciplina = disciplina;
        this.avaliacao = avaliacao;
        this.prioridade = prioridade;
        this.data = data;
        this.concluido = false;
    }

    public String getNome(){
        return this.nome;
    }

    public Disciplina getDisciplina(){
        return this.disciplina;
    }

    public MetodoDeAvaliacao getAvaliacao(){
        return this.avaliacao;
    }

    public String getPrioridade(){
        return this.prioridade;
    }

    public boolean getConcluido(){
        return this.concluido;
    }

    public String getData(){
        return this.data;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public void setDisciplina(Disciplina disciplina){
        this.disciplina = disciplina;
    }

    public void setAvaliacao(MetodoDeAvaliacao avaliacao){
        this.avaliacao = avaliacao;
    }

    public void setPrioridade(String prioridade){
        this.prioridade = prioridade;
    }

    public void alterarData(String data){
        LocalDate dataAtual = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataNova = LocalDate.parse(data, formatter);

        try{
            // se a nova data já ocorreu não é possível modificar
            if(dataNova.isBefore(dataAtual))
                throw new DataInvalidaException("Não é possível inserir uma data passada");
            
            this.data=data;
            
            } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void setConcluido(Boolean concluido){
        this.concluido = concluido;
    }

    public void setData(String data){
        this.data = data;
    }

    public void concluir(boolean concluir) {
        this.concluido = concluir;
    }
}
