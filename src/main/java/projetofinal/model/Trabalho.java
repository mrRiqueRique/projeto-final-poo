package projetofinal.model;

import java.util.ArrayList;
import java.util.List;

import projetofinal.exceptions.AlunoNaoEncontradoException;

public class Trabalho extends MetodoDeAvaliacao{

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
                throw new AlunoNaoEncontradoException("Aluno não pertencee ao grupo");
            
            this.grupo.remove(alunoRemovido);
            

        }catch(AlunoNaoEncontradoException e){
            System.out.println(e.getMessage());
        }
    }

    // verificar se tem como checar a data atual, e não permitir mudar a data
    // se a data nova a ser modificada ja passou
    public void alterarDataEntrega(String dataEntrega){
        this.dataEntrega = dataEntrega;
    }
}
