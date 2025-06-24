package projetofinal.model;

/**
 * Classe que representa uma aula no sistema.
 * <p>
 * Contém informações sobre o horário, dia da semana, local e disciplina associada à aula.
 */
public class Aula {
    private String horarioInicio;
    private String horarioFim;
    private String diaSemana;
    private String local;
    private String codigoDisciplina;

    /**
     * Construtor da classe Aula.
     *
     * @param horarioInicio    O horário de início da aula.
     * @param horarioFim       O horário de término da aula.
     * @param diaSemana        O dia da semana em que a aula ocorre.
     * @param codigoDisciplina O código da disciplina associada à aula.
     * @param local            O local onde a aula será realizada.
     */
    public Aula(String horarioInicio, String horarioFim, String diaSemana, String codigoDisciplina, String local) {
        this.horarioInicio = horarioInicio;
        this.horarioFim = horarioFim;
        this.diaSemana = diaSemana;
        this.codigoDisciplina = codigoDisciplina;
        this.local = local;
    }

    /**
     * Retorna o horário de início da aula.
     *
     * @return O horário de início.
     */
    public String getHorarioInicio() {
        return this.horarioInicio;
    }

    /**
     * Retorna o horário de término da aula.
     *
     * @return O horário de término.
     */
    public String getHorarioFim() {
        return this.horarioFim;
    }

    /**
     * Retorna o dia da semana em que a aula ocorre.
     *
     * @return O dia da semana.
     */
    public String getDiaSemana() {
        return this.diaSemana;
    }

    /**
     * Retorna o local onde a aula será realizada.
     *
     * @return O local da aula.
     */
    public String getLocal() {
        return this.local;
    }

    /**
     * Retorna o código da disciplina associada à aula.
     *
     * @return O código da disciplina.
     */
    public String getDisciplina() {
        return this.codigoDisciplina;
    }

    /**
     * Define o código da disciplina associada à aula.
     *
     * @param codigoDisciplina O código da disciplina.
     */
    public void setDisciplina(String codigoDisciplina) {
        this.codigoDisciplina = codigoDisciplina;
    }
}