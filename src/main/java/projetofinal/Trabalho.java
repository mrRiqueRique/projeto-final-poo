package projetofinal;

import java.util.ArrayList;
import java.util.List;

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

    // depois tratar a exceção de se não achar o aluno
    public void removerMembro(Aluno alunoRemovido){
        for(Aluno aluno: this.grupo){
            if(aluno.equals(alunoRemovido)) this.grupo.remove(aluno);
        }
    }

    // verificar se tem como checar a data atual, e não permitir mudar a data
    // se a data nova a ser modificada ja passou
    public void alterarDataEntrega(String dataEntrega){
        this.dataEntrega = dataEntrega;
    }
}
