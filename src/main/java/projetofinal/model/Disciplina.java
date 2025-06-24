package projetofinal.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa uma disciplina no sistema.
 * <p>
 * Contém informações sobre o nome, código, créditos, professor, PED, aulas, avaliações e faltas.
 */
public class Disciplina {
    private String nome;
    private String codigo;
    private int creditos;
    private String media;
    private int faltas;
    private String professor;
    private String PED;
    private List<Aula> aulas;
    private List<MetodoDeAvaliacao> avaliacoes;

    /**
     * Construtor da classe Disciplina.
     *
     * @param codigo    O código da disciplina.
     * @param nome      O nome da disciplina.
     * @param PED       O Plano de Ensino Detalhado (PED) da disciplina.
     * @param creditos  A quantidade de créditos da disciplina.
     * @param media     A média da disciplina.
     * @param professor O nome do professor responsável pela disciplina.
     */
    public Disciplina(String codigo, String nome, String PED, int creditos, String media, String professor) {
        this.nome = nome;
        this.codigo = codigo;
        this.creditos = creditos;
        this.PED = PED;
        this.faltas = 0;
        this.media = media;
        this.professor = professor;
        this.aulas = new ArrayList<>();
        this.avaliacoes = new ArrayList<>();
    }

    /**
     * Retorna o nome da disciplina.
     *
     * @return O nome da disciplina.
     */
    public String getNome() {
        return this.nome;
    }

    /**
     * Define o nome da disciplina.
     *
     * @param novoNome O novo nome da disciplina.
     */
    public void setNome(String novoNome) {
        this.nome = novoNome;
    }

    /**
     * Retorna o código da disciplina.
     *
     * @return O código da disciplina.
     */
    public String getCodigo() {
        return this.codigo;
    }

    /**
     * Define o código da disciplina.
     *
     * @param novoCodigo O novo código da disciplina.
     */
    public void setCodigo(String novoCodigo) {
        this.codigo = novoCodigo;
    }

    /**
     * Retorna a quantidade de créditos da disciplina.
     *
     * @return A quantidade de créditos.
     */
    public int getCreditos() {
        return this.creditos;
    }

    /**
     * Define a quantidade de créditos da disciplina.
     *
     * @param novoCreditos A nova quantidade de créditos.
     */
    public void setCreditos(int novoCreditos) {
        this.creditos = novoCreditos;
    }

    /**
     * Retorna o nome do professor responsável pela disciplina.
     *
     * @return O nome do professor.
     */
    public String getProfessor() {
        return this.professor;
    }

    /**
     * Define o nome do professor responsável pela disciplina.
     *
     * @param novoProfessor O novo nome do professor.
     */
    public void setProfessor(String novoProfessor) {
        this.professor = novoProfessor;
    }

    /**
     * Retorna a quantidade de faltas acumuladas na disciplina.
     *
     * @return A quantidade de faltas.
     */
    public int getFaltas() {
        return this.faltas;
    }

    /**
     * Define a quantidade de faltas acumuladas na disciplina.
     *
     * @param faltas A nova quantidade de faltas.
     */
    public void setFaltas(int faltas) {
        this.faltas = faltas;
    }

    /**
     * Retorna o Plano de Ensino Detalhado (PED) da disciplina.
     *
     * @return O PED da disciplina.
     */
    public String getPED() {
        return this.PED;
    }

    /**
     * Define o Plano de Ensino Detalhado (PED) da disciplina.
     *
     * @param novoPED O novo PED da disciplina.
     */
    public void setPED(String novoPED) {
        this.PED = novoPED;
    }

    /**
     * Retorna a lista de avaliações da disciplina.
     *
     * @return A lista de avaliações.
     */
    public List<MetodoDeAvaliacao> getAvaliacoes() {
        return this.avaliacoes;
    }

    /**
     * Define a lista de avaliações da disciplina.
     *
     * @param avaliacoes A nova lista de avaliações.
     */
    public void setAvaliacoes(List<MetodoDeAvaliacao> avaliacoes) {
        this.avaliacoes = avaliacoes;
    }

    /**
     * Retorna a lista de aulas da disciplina.
     *
     * @return A lista de aulas.
     */
    public List<Aula> getAulas() {
        return this.aulas;
    }

    /**
     * Define a lista de aulas da disciplina.
     *
     * @param aulas A nova lista de aulas.
     */
    public void setAulas(List<Aula> aulas) {
        this.aulas = aulas;
    }

    /**
     * Retorna a média da disciplina.
     *
     * @return A média da disciplina.
     */
    public String getMedia() {
        return this.media;
    }

    /**
     * Consulta a quantidade de faltas restantes antes de ultrapassar o limite permitido.
     *
     * @return A quantidade de faltas restantes.
     */
    public int consultarFaltasRestantes() {
        int semanas = 16;
        int aulasPorSemana = this.creditos / 2;
        int aulasTotais = semanas * aulasPorSemana;
        int faltasPermitidas = (int) (aulasTotais * 0.75) - 1;
        return faltasPermitidas - this.faltas;
    }

    /**
     * Computa uma falta na disciplina.
     */
    public void computarFalta() {
        this.faltas++;
    }

    /**
     * Adiciona uma avaliação à lista de avaliações da disciplina.
     *
     * @param avaliacao A avaliação a ser adicionada.
     */
    public void adicionarAvaliacao(MetodoDeAvaliacao avaliacao) {
        this.avaliacoes.add(avaliacao);
    }
}