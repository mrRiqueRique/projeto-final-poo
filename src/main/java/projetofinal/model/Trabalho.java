package projetofinal.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import projetofinal.exceptions.DataInvalidaException;

/**
 * Represents a type of evaluation method called "Trabalho" (Work).
 * A Trabalho includes information about its start date, delivery date,
 * whether it is a group work, and the group name.
 */
public class Trabalho extends MetodoDeAvaliacao {
    private String dataInicio;
    private String dataEntrega;
    private boolean emGrupo;
    private String grupo;

    /**
     * Constructs a new Trabalho.
     *
     * @param nome             The name of the work.
     * @param codigoDisciplina The code of the associated discipline.
     * @param dataInicio       The start date of the work in the format "dd/MM/yyyy".
     * @param dataEntrega      The delivery date of the work in the format "dd/MM/yyyy".
     * @param emGrupo          Indicates if the work is done in a group.
     * @param grupo            The name of the group (if applicable).
     */
    public Trabalho(String nome, String codigoDisciplina, String dataInicio, String dataEntrega, boolean emGrupo, String grupo) {
        super(nome, codigoDisciplina);
        this.dataInicio = dataInicio;
        this.dataEntrega = dataEntrega;
        this.emGrupo = emGrupo;
        this.grupo = grupo;
    }

    /**
     * Gets the start date of the work.
     *
     * @return The start date in the format "dd/MM/yyyy".
     */
    public String getDataInicio() {
        return this.dataInicio;
    }

    /**
     * Gets the delivery date of the work.
     *
     * @return The delivery date in the format "dd/MM/yyyy".
     */
    public String getDataEntrega() {
        return this.dataEntrega;
    }

    /**
     * Checks if the work is done in a group.
     *
     * @return True if the work is done in a group, false otherwise.
     */
    public boolean getEmGrupo() {
        return this.emGrupo;
    }

    /**
     * Gets the name of the group associated with the work.
     *
     * @return The name of the group.
     */
    public String getGrupo() {
        return this.grupo;
    }

    /**
     * Sets whether the work is done in a group.
     *
     * @param emGrupo True if the work is done in a group, false otherwise.
     */
    public void setEmGrupo(boolean emGrupo) {
        this.emGrupo = emGrupo;
    }

    /**
     * Updates the start date of the work.
     * Throws an exception if the new date is in the past.
     *
     * @param data The new start date in the format "dd/MM/yyyy".
     */
    public void alterarDataInicio(String data) {
        LocalDate dataAtual = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataNova = LocalDate.parse(data, formatter);

        try {
            // If the new date has already passed, it cannot be modified
            if (dataNova.isBefore(dataAtual))
                throw new DataInvalidaException("Não é possível inserir uma data passada");

            this.dataInicio = data;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Updates the delivery date of the work.
     * Throws an exception if the new date is in the past.
     *
     * @param data The new delivery date in the format "dd/MM/yyyy".
     */
    public void alterarDataEntrega(String data) {
        LocalDate dataAtual = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataNova = LocalDate.parse(data, formatter);

        try {
            // If the new date has already passed, it cannot be modified
            if (dataNova.isBefore(dataAtual))
                throw new DataInvalidaException("Não é possível inserir uma data passada");

            this.dataEntrega = data;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}