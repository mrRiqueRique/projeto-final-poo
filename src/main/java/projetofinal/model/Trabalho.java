package projetofinal.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import projetofinal.exceptions.DataInvalidaException;

public class Trabalho extends MetodoDeAvaliacao{
    private String dataInicio;
    private String dataEntrega;
    private boolean emGrupo;
    private String grupo;

    public Trabalho(String nome, String codigoDisciplina, String dataInicio, String dataEntrega, boolean emGrupo, String grupo){
        super(nome, codigoDisciplina);
        this.dataInicio = dataInicio;
        this.dataEntrega = dataEntrega;
        this.emGrupo = emGrupo;
        this.grupo = grupo;
    }

    public String getDataInicio() {
        return this.dataInicio;
    }

    public String getDataEntrega() {
        return this.dataEntrega;
    }


    public boolean getEmGrupo(){
        return this.emGrupo;
    }

    public String getGrupo(){
        return this.grupo;
    }

    public void setEmGrupo(boolean emGrupo){
        this.emGrupo = emGrupo;
    }

    public void alterarDataInicio(String data){
        LocalDate dataAtual = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataNova = LocalDate.parse(data, formatter);

        try{
            // se a nova data já ocorreu não é possível modificar
            if(dataNova.isBefore(dataAtual))
                throw new DataInvalidaException("Não é possível inserir uma data passada");
            
            this.dataInicio=data;
            
            } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void alterarDataEntrega(String data){
        LocalDate dataAtual = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataNova = LocalDate.parse(data, formatter);

        try{
            // se a nova data já ocorreu não é possível modificar
            if(dataNova.isBefore(dataAtual))
                throw new DataInvalidaException("Não é possível inserir uma data passada");
            
            this.dataEntrega=data;
            
            } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
