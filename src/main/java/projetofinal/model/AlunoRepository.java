package projetofinal.model;

import java.util.ArrayList;
import java.util.List;

public class AlunoRepository {
    private static AlunoRepository instancia;
    private List<Aluno> alunos = new ArrayList<>();
    private Service service;

    private AlunoRepository() {
        try {
            this.service = new Service();
            carregarAlunosDoSheets();
        } catch (Exception e) {
            System.err.println("Erro ao inicializar AlunoRepository: " + e.getMessage());
        }
    }

    public static AlunoRepository getInstancia() {
        if (instancia == null) {
            instancia = new AlunoRepository();
        }
        return instancia;
    }

    public List<Aluno> getAlunos() {
        return this.alunos;
    }

    public Aluno getAlunoPorRa(String ra) {
        return alunos.stream()
                .filter(a -> a.getRa().equals(ra))
                .findFirst()
                .orElse(null);
    }

    private void carregarAlunosDoSheets() {
        try {
            alunos = service.getAlunos(); // Use the Service method to load students
        } catch (Exception e) {
            System.err.println("Erro ao carregar alunos do Sheets: " + e.getMessage());
        }
    }

    public void adicionarAluno(Aluno aluno) {
        try {
            alunos.add(aluno);
            service.adicionarAluno(aluno); // Use a Service method to add a student
        } catch (Exception e) {
            System.err.println("Erro ao adicionar aluno: " + e.getMessage());
        }
    }


    public Aluno buscarAlunoPorRa(String ra) {
        return alunos.stream()
                .filter(a -> a.getRa().equals(ra))
                .findFirst()
                .orElse(null);
    }

    public void removerAluno(String ra) {
        Aluno aluno = buscarAlunoPorRa(ra);
        if (aluno != null) {
            alunos.remove(aluno);
            service.deletarAluno(aluno); // Use a Service method to remove a student
        } else {
            System.out.println("Aluno não encontrado.");
        }
    }

    public void atualizarAluno(Aluno alunoVelho, Aluno alunoNovo) {
        if (alunoVelho == null)
            System.out.println("Aluno não encontrado.");

        service.atualizarAluno(alunoVelho, alunoNovo);

    }
}