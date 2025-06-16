package projetofinal.model;

import java.util.ArrayList;
import java.util.List;

public class Service {
    private GoogleSheetsFacade sheetsFacade;

    public Service() {
        try {
            sheetsFacade = new GoogleSheetsFacade();
        } catch (Exception e) {
            System.err.println("Erro ao inicializar GoogleSheetsFacade: " + e.getMessage());
        }
    }

    // CRUD

    public List<Aluno> getAlunos() {
        try {
            List<List<Object>> dataAluno = sheetsFacade.lerDados("Aluno", "A", "D");
            List<Aluno> alunos = new ArrayList<>();
            for (List<Object> linha : dataAluno) {
                alunos.add(new Aluno(linha.get(0).toString(), linha.get(1).toString(), linha.get(2).toString()));
            }
            return alunos;
        } catch (Exception e) {
            System.err.println("Erro ao obter alunos: " + e.getMessage());
            return List.of(); // Retorna uma lista vazia em caso de erro
        }
    }

    public Aluno getAluno(String ra) {
        try {
            List<Object> dataAluno = sheetsFacade.lerDadosLinhaPorId("Aluno", "A", "D", ra);
            if (dataAluno.isEmpty()) return null;
            Aluno aluno = new Aluno(dataAluno.get(0).toString(), dataAluno.get(1).toString(), dataAluno.get(2).toString());
            return aluno;
        } catch (Exception e) {
            System.err.println("Erro ao obter aluno: " + e.getMessage());
            return null;
        }
    }

    public List<Disciplina> getDisciplinas() {
        try {
            List<List<Object>> dataDisciplina = sheetsFacade.lerDados("Disciplina", "A", "D");

            List<Disciplina> disciplinas = new ArrayList<>();
            for (List<Object> linha : dataDisciplina) {
                // todo - arrumar tratamento do ultimo parametro de [avaliações]
                disciplinas.add(new Disciplina(linha.get(0).toString(), linha.get(1).toString(), Integer.parseInt(linha.get(2).toString()), 0, linha.get(3).toString()));
            }

            return disciplinas;
        } catch (Exception e) {
            System.err.println("Erro ao obter disciplinas: " + e.getMessage());
            return List.of(); // Retorna uma lista vazia em caso de erro
        }
    }

    public Disciplina getDisciplina(String codigo) {
        try {
            List<Object> dataDisciplina = sheetsFacade.lerDadosLinhaPorId("Disciplina", "A", "D", codigo);
            if (dataDisciplina.isEmpty()) return null;
            Disciplina disciplina = new Disciplina(dataDisciplina.get(0).toString(), dataDisciplina.get(1).toString(), Integer.parseInt(dataDisciplina.get(2).toString()), 0, dataDisciplina.get(3).toString());
            return disciplina;
        } catch (Exception e) {
            System.err.println("Erro ao obter disciplina: " + e.getMessage());
            return null;
        }
    }

    public List<Disciplina> getDiciplinasDoAluno(Aluno aluno) {
        List<Disciplina> disciplinasDoAluno = new ArrayList<>();
        try {
            List<List<Object>> dataDisciplinaAluno = sheetsFacade.lerDados("AlunoDisciplina", "A", "C");
            for (List<Object> linha : dataDisciplinaAluno) {
                if (linha.get(0).toString().equals(aluno.getRa())) {
                    Disciplina disciplina = getDisciplina(linha.get(1).toString());
                    if (disciplina != null) {
                        disciplina.setFaltas(Integer.parseInt(linha.get(2).toString()));
                        disciplinasDoAluno.add(disciplina);
                    }
                }
            }

        } catch (Exception e) {
            System.err.println("Erro ao obter disciplinas do aluno: " + e.getMessage());
        }
        return disciplinasDoAluno;
    }

    public List<Prova> getProvas() {
        try {
            List<List<Object>> dataProva = sheetsFacade.lerDados("Prova", "A", "E");
            List<Prova> provas = new ArrayList<>();
            for (List<Object> linha : dataProva) {

                provas.add(new Prova(linha.get(0).toString(), linha.get(1).toString(), linha.get(2).toString(), linha.get(3).toString()));
            }
            return provas;
        } catch (Exception e) {
            System.err.println("Erro ao obter provas: " + e.getMessage());
            return List.of(); // Retorna uma lista vazia em caso de erro
        }
    }

    public Prova getProva(String nomeProva, String codigoDisciplina) {
        try {
            List<List<Object>> dataProva = sheetsFacade.lerDados("Prova", "A", "E");
            for (List<Object> linha : dataProva) {
                if (linha.get(0).toString().equals(nomeProva) && linha.get(4).toString().equals(codigoDisciplina)) {
                    return new Prova(linha.get(0).toString(), linha.get(1).toString(), linha.get(2).toString(), linha.get(3).toString());
                }
            }
            return null; // Retorna null se não encontrar a prova
        } catch (Exception e) {
            System.err.println("Erro ao obter prova: " + e.getMessage());
            return null;
        }
    }

    public List<Prova> getProvasPorDisciplina(String codigoDisciplina) {
        try {
            List<List<Object>> dataProva = sheetsFacade.lerDados("Prova", "A", "E");
            List<Prova> provas = new ArrayList<>();
            for (List<Object> linha : dataProva) {
                if (linha.get(4).toString().equals(codigoDisciplina)) { // Filtra pelo código da disciplina
                    provas.add(new Prova(linha.get(0).toString(), linha.get(1).toString(), linha.get(2).toString(), linha.get(3).toString()));
                }
            }
            return provas;
        } catch (Exception e) {
            System.err.println("Erro ao obter provas por disciplina: " + e.getMessage());
            return List.of(); // Retorna uma lista vazia em caso de erro
        }
    }

    public int getNotaProva(String nomeProva, String codigoDisciplina, String raAluno) {
        try {
            List<List<Object>> dataNotaProva = sheetsFacade.lerDados("AlunoProva", "A", "D");
            for (List<Object> linha : dataNotaProva) {
                if (linha.get(0).toString().equals(raAluno) && linha.get(1).toString().equals(nomeProva) && linha.get(2).toString().equals(codigoDisciplina)) {
                    return Integer.parseInt(linha.get(3).toString()); // Retorna a nota da prova
                }
            }
            return -1; // Retorna -1 se não encontrar a nota
        } catch (Exception e) {
            System.err.println("Erro ao obter nota da prova: " + e.getMessage());
            return -1; // Retorna -1 em caso de erro
        }
    }

    public List<Trabalho> getTrabalhos() {
        try {
            List<List<Object>> dataTrabalho = sheetsFacade.lerDados("Trabalho", "A", "F");
            List<Trabalho> trabalhos = new ArrayList<>();
            for (List<Object> linha : dataTrabalho) {
                trabalhos.add(new Trabalho(linha.get(0).toString(), linha.get(1).toString(), linha.get(2).toString(), Boolean.parseBoolean(linha.get(3).toString())));
            }
            return trabalhos;
        } catch (Exception e) {
            System.err.println("Erro ao obter trabalhos: " + e.getMessage());
            return List.of(); // Retorna uma lista vazia em caso de erro
        }
    }

    public Trabalho getTrabalho(String nomeTrabalho, String codigoDisciplina) {
        try {
            List<List<Object>> dataTrabalho = sheetsFacade.lerDados("Trabalho", "A", "F");
            for (List<Object> linha : dataTrabalho) {
                if (linha.get(0).toString().equals(nomeTrabalho) && linha.get(4).toString().equals(codigoDisciplina)) {
                    return new Trabalho(linha.get(0).toString(), linha.get(1).toString(), linha.get(2).toString(), Boolean.parseBoolean(linha.get(3).toString()));
                }
            }
            return null; // Retorna null se não encontrar o trabalho
        } catch (Exception e) {
            System.err.println("Erro ao obter trabalho: " + e.getMessage());
            return null;
        }
    }

    public List<Trabalho> getTrabalhosPorDisciplina(String codigoDisciplina) {
        try {
            List<List<Object>> dataTrabalho = sheetsFacade.lerDados("Trabalho", "A", "E");
            List<Trabalho> trabalhos = new ArrayList<>();
            for (List<Object> linha : dataTrabalho) {
                if (linha.get(5).toString().equals(codigoDisciplina)) { // Filtra pelo código da disciplina
                    trabalhos.add(new Trabalho(linha.get(0).toString(), linha.get(1).toString(), linha.get(2).toString(), Boolean.parseBoolean(linha.get(3).toString())));
                }
            }
            return trabalhos;
        } catch (Exception e) {
            System.err.println("Erro ao obter trabalhos por disciplina: " + e.getMessage());
            return List.of(); // Retorna uma lista vazia em caso de erro
        }
    }

    public int getNotaTrabalho(String nomeTrabalho, String codigoDisciplina, String raAluno) {
        try {
            List<List<Object>> dataNotaTrabalho = sheetsFacade.lerDados("AlunoTrabalho", "A", "D");
            for (List<Object> linha : dataNotaTrabalho) {
                if (linha.get(0).toString().equals(raAluno) && linha.get(1).toString().equals(nomeTrabalho) && linha.get(2).toString().equals(codigoDisciplina)) {
                    return Integer.parseInt(linha.get(3).toString()); // Retorna a nota do trabalho
                }
            }
            return -1; // Retorna -1 se não encontrar a nota
        } catch (Exception e) {
            System.err.println("Erro ao obter nota do trabalho: " + e.getMessage());
            return -1; // Retorna -1 em caso de erro
        }
    }

    public TodoList getTodoListAluno(Aluno aluno) {
        TodoList todoList = new TodoList();
        try {
            List<List<Object>> dataTodoList = sheetsFacade.lerDados("TodoItem", "A", "h");
            for (List<Object> linha : dataTodoList) {
                if (linha.get(0).toString().equals(aluno.getRa()))
                {
                    Disciplina disciplina = getDisciplina(linha.get(2).toString());
                    MetodoDeAvaliacao metodoDeAvaliacao;
                    Prova prova = getProva(linha.get(3).toString(), linha.get(2).toString());

                    if (prova == null)
                        metodoDeAvaliacao = getTrabalho(linha.get(3).toString(), linha.get(2).toString());
                    else metodoDeAvaliacao = prova;

                    TodoItem todoItem = new TodoItem(linha.get(1).toString(), disciplina, metodoDeAvaliacao, linha.get(4).toString(), linha.get(5).toString());
                    if (Boolean.parseBoolean(linha.get(6).toString())) todoItem.concluir();

                    todoList.adicionarItem(todoItem);
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao obter TodoLists do aluno: " + e.getMessage());
        } return todoList;
    }

    // add s

    public void adicionarAluno(Aluno aluno) {
        try {
            List<List<Object>> novoAluno = List.of(List.of(aluno.getRa(), aluno.getNome(), aluno.getCurso(), aluno.getCR()));
            sheetsFacade.escreverDados("Aluno", "A", "D", novoAluno);
        } catch (Exception e) {
            System.err.println("Erro ao adicionar aluno: " + e.getMessage());
        }
    }

    public void adicionarDisciplina(Disciplina disciplina) {
        try {
            List<List<Object>> novaDisciplina = List.of(List.of(disciplina.getCodigo(), disciplina.getNome(), disciplina.getCreditos(), disciplina.getProfessor()));
            sheetsFacade.escreverDados("Disciplina", "A", "D", novaDisciplina);
        } catch (Exception e) {
            System.err.println("Erro ao adicionar disciplina: " + e.getMessage());
        }
    }

    public void adicionarDisciplinaAluno(Aluno aluno, Disciplina disciplina) {
        try {
            List<List<Object>> novaDisciplinaAluno = List.of(List.of(aluno.getRa(), disciplina.getCodigo(), 0));
            sheetsFacade.escreverDados("AlunoDisciplina", "A", "C", novaDisciplinaAluno);
        } catch (Exception e) {
            System.err.println("Erro ao adicionar disciplina ao aluno: " + e.getMessage());
        }
    }

    public void adicionarProva(Prova prova, String codigoDisciplina) {
        try {
            List<List<Object>> novaProva = List.of(List.of(prova.getNome(), prova.getLocal(), prova.getDuracao(), prova.getData(), codigoDisciplina));
            sheetsFacade.escreverDados("Prova", "A", "E", novaProva);
        } catch (Exception e) {
            System.err.println("Erro ao adicionar prova: " + e.getMessage());
        }
    }

    public void adicionarNotaProva(String raAluno, String nomeProva, String codigoDisciplina, int nota) {
        try {
            List<List<Object>> novaNotaProva = List.of(List.of(raAluno, nomeProva, codigoDisciplina, nota));
            sheetsFacade.escreverDados("AlunoProva", "A", "D", novaNotaProva);
        } catch (Exception e) {
            System.err.println("Erro ao adicionar nota da prova: " + e.getMessage());
        }
    }

    public void adicionarTrabalho(Trabalho trabalho, String codigoDisciplina) {
        try {
            List<List<Object>> novoTrabalho = List.of(List.of(trabalho.getNome(), trabalho.getDataInicio(), trabalho.getDataEntrega(), trabalho.getEmGrupo(), trabalho.getGrupo(), codigoDisciplina));
            sheetsFacade.escreverDados("Trabalho", "A", "F", novoTrabalho);
        } catch (Exception e) {
            System.err.println("Erro ao adicionar trabalho: " + e.getMessage());
        }
    }

    public void adicionarNotaTrabalho(String raAluno, String nomeTrabalho, String codigoDisciplina, int nota) {
        try {
            List<List<Object>> novaNotaTrabalho = List.of(List.of(raAluno, nomeTrabalho, codigoDisciplina, nota));
            sheetsFacade.escreverDados("AlunoTrabalho", "A", "D", novaNotaTrabalho);
        } catch (Exception e) {
            System.err.println("Erro ao adicionar nota do trabalho: " + e.getMessage());
        }
    }

    public void adicionarTodoItem(String raAluno, TodoItem todoItem) {
        try {
            List<List<Object>> novoTodoItem = List.of(List.of(raAluno, todoItem.getNome(), todoItem.getDisciplina().getCodigo(), todoItem.getAvaliacao().getNome(), todoItem.getPrioridade(), todoItem.getData(), String.valueOf(todoItem.getConcluido())));
            sheetsFacade.escreverDados("TodoItem", "A", "G", novoTodoItem);
        } catch (Exception e) {
            System.err.println("Erro ao adicionar TodoItem: " + e.getMessage());
        }
    }

    // put

    public void atualizarFalta(Disciplina disciplina, Aluno aluno) {
        try {
            // todo - DEVE DAR UM UPDATE N CRIAR OUTRO!!!
            // sheetsFacade.escreverDados("AlunoDisciplina", "A", "C", novaFalta);
        } catch (Exception e) {
            System.err.println("Erro ao adicionar falta: " + e.getMessage());
        }
    }

    public void atualizarStatusTodoItem(TodoItem todoItem) {
        try {
            // todo
            // sheetsFacade.atualizarDados("TodoItem", "A", "G", novoTodoItem, todoItem.getNome());
        } catch (Exception e) {
            System.err.println("Erro ao atualizar TodoItem: " + e.getMessage());
        }
    }
}
