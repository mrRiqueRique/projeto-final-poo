package projetofinal.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import projetofinal.exceptions.AlunoNaoEncontradoException;
import projetofinal.exceptions.DataInvalidaException;

public class Trabalho extends MetodoDeAvaliacao{
    private String id;
    private String dataInicio;
    private String dataEntrega;
    private boolean emGrupo;
    private List<Aluno> grupo;

    public Trabalho(String nome, String dataInicio, String dataEntrega, boolean emGrupo){
        super(nome);
        this.dataInicio = dataInicio;
        this.dataEntrega = dataEntrega;
        this.emGrupo = emGrupo;
        grupo = new ArrayList<>();
    }

    public String getDataInicio() {
        return this.dataInicio;
    }

    public String getDataEntrega() {
        return this.dataEntrega;
    }

    public boolean getEmGrupo(){
        return this.emGrupo;
    }

    public List<Aluno> getGrupo(){
        return this.grupo;
    }

    public void setEmGrupo(boolean emGrupo){
        this.emGrupo = emGrupo;
    }

    public void adicionaGrupo(List<Aluno> grupo){
        this.grupo.addAll(grupo);
    }

    public void adicionarMembro(Aluno aluno){
        this.grupo.add(aluno);
    }

    public void removerMembro(Aluno alunoRemovido){
        try{
            if(!this.grupo.contains(alunoRemovido))
                throw new AlunoNaoEncontradoException("Aluno não pertence ao grupo");
            
            this.grupo.remove(alunoRemovido);
            

        }catch(AlunoNaoEncontradoException e){
            System.out.println(e.getMessage());
        }
    }

    public void alterarDataInicio(String data){
        LocalDate dataAtual = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataNova = LocalDate.parse(data, formatter);

        try{
            // se a nova data já ocorreu não é possível modificar
            if(dataNova.isBefore(dataAtual))
                throw new DataInvalidaException("Não é possível inserir uma data passada");
            
            this.dataInicio=data;
            
            } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void alterarDataEntrega(String data){
        LocalDate dataAtual = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataNova = LocalDate.parse(data, formatter);

        try{
            // se a nova data já ocorreu não é possível modificar
            if(dataNova.isBefore(dataAtual))
                throw new DataInvalidaException("Não é possível inserir uma data passada");
            
            this.dataEntrega=data;
            
            } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
