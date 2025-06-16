package projetofinal.model;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

public class AlunoLogado {
    public static AlunoLogado alunoLogado;
    private Aluno aluno;
    private GoogleSheetsFacade sheetsFacade;

    private AlunoLogado() {
        try {
            sheetsFacade = new GoogleSheetsFacade();
        } catch (IOException | GeneralSecurityException e) {
            System.err.println(e.getMessage());
        }
    }

    public static AlunoLogado getInstance() {
        if (alunoLogado == null) {
            alunoLogado = new AlunoLogado();
        }
        return alunoLogado;
    }

    public Aluno getAluno() {
        return this.aluno;
    }

    public void logout() {
        alunoLogado = null;
    }

    public void logarAluno(String ra) {
        try {
            List<Object> dataAluno = sheetsFacade.lerDadosLinhaPorId("Aluno", "A", "D", ra);
            System.out.println(dataAluno);
            aluno = new Aluno(dataAluno.get(0).toString(), dataAluno.get(1).toString(), dataAluno.get(2).toString());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void cadastrarDisciplina(Disciplina disciplina) {
        if (aluno != null) {
            // todo verificar se a disciplina já existe
            aluno.cadastrarDisciplina(disciplina);

        } else {
            System.out.println("Nenhum aluno logado.");
        }
    }
}
