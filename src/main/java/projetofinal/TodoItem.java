package projetofinal;

public class TodoItem {

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

    public void concluir(){
        this.concluido = true;
    }

    public void setData(String data){
        this.data = data;
    }
    
}
