package projetofinal.model;

/**
 * Classe abstrata que representa um método de avaliação.
 * <p>
 * Esta classe serve como base para diferentes tipos de métodos de avaliação,
 * como provas, trabalhos, entre outros. Contém informações sobre a nota,
 * nome e código da disciplina associada ao método de avaliação.
 */
public abstract class MetodoDeAvaliacao {
    private double nota;
    private String nome;
    private String codigoDisciplina;

    /**
     * Construtor que inicializa o método de avaliação com o nome.
     *
     * @param nome O nome do método de avaliação.
     */
    public MetodoDeAvaliacao(String nome) {
        this.nome = nome;
    }

    /**
     * Construtor que inicializa o método de avaliação com o nome e o código da disciplina.
     *
     * @param nome             O nome do método de avaliação.
     * @param codigoDisciplina O código da disciplina associada ao método de avaliação.
     */
    public MetodoDeAvaliacao(String nome, String codigoDisciplina) {
        this.nome = nome;
        this.codigoDisciplina = codigoDisciplina;
    }

    /**
     * Construtor que inicializa o método de avaliação com o nome e a nota.
     *
     * @param nome O nome do método de avaliação.
     * @param nota A nota atribuída ao método de avaliação.
     */
    public MetodoDeAvaliacao(String nome, double nota) {
        this.nome = nome;
        this.nota = nota;
    }

    /**
     * Define o código da disciplina associada ao método de avaliação.
     *
     * @param codigo O código da disciplina.
     */
    public void setCodigo(String codigo) {
        this.codigoDisciplina = codigo;
    }

    /**
     * Define a nota atribuída ao método de avaliação.
     *
     * @param nota A nota a ser atribuída.
     */
    public void setNota(double nota) {
        this.nota = nota;
    }

    /**
     * Retorna a nota atribuída ao método de avaliação.
     *
     * @return A nota do método de avaliação.
     */
    public double getNota() {
        return this.nota;
    }

    /**
     * Retorna o nome do método de avaliação.
     *
     * @return O nome do método de avaliação.
     */
    public String getNome() {
        return this.nome;
    }

    /**
     * Lança uma nova nota para o método de avaliação.
     *
     * @param nota A nova nota a ser lançada.
     */
    public void lancarNota(double nota) {
        this.nota = nota;
    }

    /**
     * Retorna o código da disciplina associada ao método de avaliação.
     *
     * @return O código da disciplina.
     */
    public String getCodigoDisciplina() {
        return this.codigoDisciplina;
    }
}