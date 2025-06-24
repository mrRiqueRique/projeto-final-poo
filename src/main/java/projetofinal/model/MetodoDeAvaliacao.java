package projetofinal.model;

public abstract class MetodoDeAvaliacao {
    private double nota;
    private String nome;
    private String codigoDisciplina;

    public MetodoDeAvaliacao(String nome) {
        this.nome = nome;
    }

    public MetodoDeAvaliacao(String nome, String codigoDisciplina) {
        this.nome = nome;
        this.codigoDisciplina = codigoDisciplina;
    }

    public MetodoDeAvaliacao(String nome, double nota) {
        this.nome = nome;
        this.nota = nota;
    }

    public void setCodigo(String codigo) {
        this.codigoDisciplina = codigo;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public double getNota() {
        return this.nota;
    }

    public String getNome() {
        return this.nome;
    }

    public void lancarNota(double nota) {
        this.nota = nota;
    }

    public String getCodigoDisciplina() {
        return this.codigoDisciplina;
    }

}
