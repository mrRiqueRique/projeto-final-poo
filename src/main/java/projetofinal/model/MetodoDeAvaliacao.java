package projetofinal.model;

public abstract class MetodoDeAvaliacao {
    private double nota;
    private String nome;

    public MetodoDeAvaliacao(String nome){
        this.nome = nome;
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
