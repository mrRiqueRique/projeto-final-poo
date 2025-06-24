package projetofinal.model;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import projetofinal.exceptions.DataInvalidaException;

public class Prova extends MetodoDeAvaliacao{
    private String local;
    private String duracao;
    private String data;
    private String horarioInicio;

    public Prova(String nome,String codigoDisciplina, String local, String duracao, String data, String horarioInicio){
        super(nome, codigoDisciplina);
        this.local = local;
        this.duracao = duracao;
        this.data = data;
        this.horarioInicio = horarioInicio;
    }

    public String getLocal() {
        return local;
    }

    public String getDuracao() {
        return duracao;
    }

    public String getData() {
        return data;
    }

    public String getHorarioInicio(){
        return horarioInicio;
    }

    public void alterarData(String data){
        LocalDate dataAtual = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataNova = LocalDate.parse(data, formatter);

        try{
            // se a nova data já ocorreu não é possível modificar
            if(dataNova.isBefore(dataAtual))
                throw new DataInvalidaException("Não é possível inserir uma data passada");
            
            this.data=data;
            
            } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void alterarLocal(String local){
        this.local = local;
    }
}
