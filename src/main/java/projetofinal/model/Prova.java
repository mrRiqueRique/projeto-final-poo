package projetofinal.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import projetofinal.exceptions.DataInvalidaException;

/**
 * Classe que representa uma prova como método de avaliação.
 * <p>
 * Contém informações sobre o local, duração, data e horário de início da prova.
 */
public class Prova extends MetodoDeAvaliacao {
    private String local;
    private String duracao;
    private String data;
    private String horarioInicio;

    /**
     * Construtor da classe Prova.
     *
     * @param nome             O nome da prova.
     * @param codigoDisciplina O código da disciplina associada à prova.
     * @param local            O local onde a prova será realizada.
     * @param duracao          A duração da prova.
     * @param data             A data da prova no formato dd/MM/yyyy.
     * @param horarioInicio    O horário de início da prova.
     */
    public Prova(String nome, String codigoDisciplina, String local, String duracao, String data, String horarioInicio) {
        super(nome, codigoDisciplina);
        this.local = local;
        this.duracao = duracao;
        this.data = data;
        this.horarioInicio = horarioInicio;
    }

    /**
     * Retorna o local onde a prova será realizada.
     *
     * @return O local da prova.
     */
    public String getLocal() {
        return local;
    }

    /**
     * Retorna a duração da prova.
     *
     * @return A duração da prova.
     */
    public String getDuracao() {
        return duracao;
    }

    /**
     * Retorna a data da prova.
     *
     * @return A data da prova no formato dd/MM/yyyy.
     */
    public String getData() {
        return data;
    }

    /**
     * Retorna o horário de início da prova.
     *
     * @return O horário de início da prova.
     */
    public String getHorarioInicio() {
        return horarioInicio;
    }

    /**
     * Altera a data da prova.
     * <p>
     * Este método verifica se a nova data é válida (não pode ser uma data passada).
     *
     * @param data A nova data da prova no formato dd/MM/yyyy.
     */
    public void alterarData(String data) {
        LocalDate dataAtual = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataNova = LocalDate.parse(data, formatter);

        try {
            if (dataNova.isBefore(dataAtual)) {
                throw new DataInvalidaException("Não é possível inserir uma data passada");
            }
            this.data = data;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Altera o local onde a prova será realizada.
     *
     * @param local O novo local da prova.
     */
    public void alterarLocal(String local) {
        this.local = local;
    }
}