package projetofinal.model;

public class Aula {
    private String horarioInicio;
    private String horarioFim;
    private String diaSemana;
    private String local;
    private String codigoDisciplina;

    public Aula(String horarioInicio, String horarioFim, String diaSemana, String codigoDisciplina, String local) {
        this.horarioInicio = horarioInicio;
        this.horarioFim = horarioFim;
        this.diaSemana = diaSemana;
        this.codigoDisciplina = codigoDisciplina;
        this.local = local;
    }

    public String getHorarioInicio(){
        return this.horarioInicio;
    }

    public String getHorarioFim(){
        return this.horarioFim;
    }

    public String getDiaSemana(){
        return this.diaSemana;
    }

    public String getLocal(){
        return this.local;
    }
    
    public String getDisciplina() {
        return this.codigoDisciplina;
    }

    public void setDisciplina(String codigoDisciplina) {
        this.codigoDisciplina = codigoDisciplina;
    }
}
