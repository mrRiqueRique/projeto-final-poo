package projetofinal.model;

public class Prova extends MetodoDeAvaliacao{

    private String local;
    private String duracao;
    private String data;

    public Prova(String nome, String local, String duracao, String data){
        super(nome);
        this.local = local;
        this.duracao = duracao;
        this.data = data;
    }

    public String getLocal() {
        return local;
    }

    public String getDuracao() {
        return duracao;
    }

    public String getData() {
        return data;
    }

    public void alterarData(String data){
        this.data = data;
    }

    public void alterarLocal(String local){
        this.local = local;
    }
}
