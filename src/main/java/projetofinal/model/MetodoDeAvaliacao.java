package projetofinal.model;

public abstract class MetodoDeAvaliacao {
    private double nota;
    private String nome;

    public MetodoDeAvaliacao(String nome){
        this.nome = nome;
    }

    public MetodoDeAvaliacao(String nome, double nota){
        this.nome = nome;
        this.nota = nota;
    }

    public void setNome() {
        this.nome = nome;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public double getNota(){
        return this.nota;
    }

    public String getNome(){
        return this.nome;
    }

    public void lancarNota(double nota){
        this.nota = nota;
    }
}
