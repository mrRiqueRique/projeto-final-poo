package projetofinal.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class responsible for interacting with Google Sheets data.
 * Provides CRUD operations for various entities such as Aluno, Disciplina, Prova, Trabalho, etc.
 */
public class Service {
    private GoogleSheetsFacade sheetsFacade;

    /**
     * Initializes the Service class and sets up the GoogleSheetsFacade.
     * Logs an error message if initialization fails.
     */
    public Service() {
        try {
            sheetsFacade = new GoogleSheetsFacade();
        } catch (Exception e) {
            System.err.println("Erro ao inicializar GoogleSheetsFacade: " + e.getMessage());
        }
    }

    /**
     * Retrieves all students from the Google Sheets.
     *
     * @return A list of Aluno objects representing the students.
     * Returns an empty list if an error occurs.
     */
    public List<Aluno> getAlunos() {
        try {
            List<List<Object>> dataAluno = sheetsFacade.lerDados("Aluno", "A", "F");
            List<Aluno> alunos = new ArrayList<>();
            for (List<Object> linha : dataAluno) {
                if (linha.isEmpty()) continue; // Pula linhas vazias

                // Lógica de criação do aluno para incluir CR e Foto.
                Aluno aluno = new Aluno(linha.get(0).toString(), linha.get(1).toString(), linha.get(2).toString(), linha.get(4).toString());

                // Adiciona o CR se a coluna existir
                if (linha.size() > 3 && linha.get(3) != null && !linha.get(3).toString().isEmpty()) {
                    aluno.setCR(Double.parseDouble(linha.get(3).toString()));
                }

                // Adiciona o caminho da foto se a coluna existir
                if (linha.size() > 5 && linha.get(5) != null) {
                    aluno.setCaminhoFoto(linha.get(5).toString());
                }

                alunos.add(aluno);
            }
            return alunos;
        } catch (Exception e) {
            System.err.println("Erro ao obter alunos: " + e.getMessage());
            return List.of(); // Retorna uma lista vazia em caso de erro
        }
    }

    /**
     * Retrieves a specific student based on their RA (Registro Acadêmico).
     *
     * @param ra The RA of the student to retrieve.
     * @return An Aluno object representing the student, or null if not found.
     */
    public Aluno getAluno(String ra) {
        try {
            List<Object> dataAluno = sheetsFacade.lerDadosLinhaPorId("Aluno", "A", "F", ra);
            if (dataAluno.isEmpty()) return null;

            Aluno aluno = new Aluno(dataAluno.get(0).toString(), dataAluno.get(1).toString(), dataAluno.get(2).toString(), dataAluno.get(4).toString());

            if (dataAluno.size() > 3 && dataAluno.get(3) != null && !dataAluno.get(3).toString().isEmpty()) {
                aluno.setCR(Double.parseDouble(dataAluno.get(3).toString()));
            }

            if (dataAluno.size() > 4 && dataAluno.get(5) != null) {
                aluno.setCaminhoFoto(dataAluno.get(5).toString());
            }

            return aluno;
        } catch (Exception e) {
            System.err.println("Erro ao obter aluno: " + e.getMessage());
            return null;
        }
    }

    /**
     * Retrieves all disciplines from the Google Sheets.
     *
     * @return A list of Disciplina objects representing the disciplines.
     * Returns an empty list if an error occurs.
     */
    public List<Disciplina> getDisciplinas() {
        try {
            List<List<Object>> dataDisciplina = sheetsFacade.lerDados("Disciplina", "A", "F");

            List<Disciplina> disciplinas = new ArrayList<>();
            for (List<Object> linha : dataDisciplina) {
                // todo - arrumar tratamento do ultimo parametro de [avaliações]
                disciplinas.add(new Disciplina(linha.get(0).toString(), linha.get(1).toString(), linha.get(2).toString(), Integer.parseInt(linha.get(3).toString()), linha.get(5).toString(), linha.get(4).toString()));
            }

            return disciplinas;
        } catch (Exception e) {
            System.err.println("Erro ao obter disciplinas: " + e.getMessage());
            return List.of(); // Retorna uma lista vazia em caso de erro
        }
    }

    /**
     * Retrieves a specific discipline based on its code.
     *
     * @param codigo The code of the discipline to retrieve.
     * @return A Disciplina object representing the discipline, or null if not found.
     */
    public Disciplina getDisciplina(String codigo) {
        try {
            List<Object> dataDisciplina = sheetsFacade.lerDadosLinhaPorId("Disciplina", "A", "F", codigo);
            if (dataDisciplina.isEmpty()) return null;
            Disciplina disciplina = new Disciplina(dataDisciplina.get(0).toString(), dataDisciplina.get(1).toString(), dataDisciplina.get(2).toString(), Integer.parseInt(dataDisciplina.get(3).toString()), dataDisciplina.get(5).toString(), dataDisciplina.get(4).toString());
            return disciplina;
        } catch (Exception e) {
            System.err.println("Erro ao obter disciplina: " + e.getMessage());
            return null;
        }
    }

    /**
     * Retrieves all disciplines associated with a specific student.
     *
     * @param raAluno The RA of the student.
     * @return A list of Disciplina objects representing the student's disciplines.
     */
    public List<Disciplina> getDiciplinasDoAluno(String raAluno) {
        List<Disciplina> disciplinasDoAluno = new ArrayList<>();
        try {
            List<List<Object>> dataDisciplinaAluno = sheetsFacade.lerDados("AlunoDisciplina", "A", "C");
            System.out.println("AAAAAA" + dataDisciplinaAluno);
            for (List<Object> linha : dataDisciplinaAluno) {
                if (linha.get(0).toString().equals(raAluno)) {
                    Disciplina disciplina = getDisciplina(linha.get(1).toString());
                    if (disciplina != null) {
                        System.out.println("2. AAAAAA" + disciplina);

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

    /**
     * Retrieves all exams (Prova) from the Google Sheets.
     *
     * @return A list of Prova objects representing the exams.
     * Returns an empty list if an error occurs.
     */
    public List<Prova> getProvas() {
        try {
            List<List<Object>> dataProva = sheetsFacade.lerDados("Prova", "A", "F");
            List<Prova> provas = new ArrayList<>();
            for (List<Object> linha : dataProva) {
                Prova novaProva = new Prova(linha.get(0).toString(), linha.get(4).toString(), linha.get(1).toString(), linha.get(2).toString(), linha.get(3).toString(), linha.get(5).toString());
                novaProva.setCodigo(linha.get(3).toString());
                provas.add(novaProva);
            }
            return provas;
        } catch (Exception e) {
            System.err.println("Erro ao obter provas: " + e.getMessage());
            return List.of(); // Retorna uma lista vazia em caso de erro
        }
    }

    /**
     * Retrieves a specific exam (Prova) based on its name and associated discipline code.
     *
     * @param nomeProva        The name of the exam.
     * @param codigoDisciplina The code of the associated discipline.
     * @return A Prova object representing the exam, or null if not found.
     */
    public Prova getProva(String nomeProva, String codigoDisciplina) {
        try {
            List<List<Object>> dataProva = sheetsFacade.lerDados("Prova", "A", "F");
            for (List<Object> linha : dataProva) {
                if (linha.get(0).toString().equals(nomeProva) && linha.get(4).toString().equals(codigoDisciplina)) {
                    Prova novaProva = new Prova(linha.get(0).toString(), linha.get(4).toString(), linha.get(1).toString(), linha.get(2).toString(), linha.get(3).toString(), linha.get(5).toString());
                    novaProva.setCodigo(linha.get(3).toString());
                    return novaProva;
                }
            }
            return null; // Retorna null se não encontrar a prova
        } catch (Exception e) {
            System.err.println("Erro ao obter prova: " + e.getMessage());
            return null;
        }
    }

    /**
     * Retrieves all exams (Prova) associated with a specific discipline.
     *
     * @param codigoDisciplina The code of the discipline.
     * @return A list of Prova objects representing the exams for the discipline.
     * Returns an empty list if an error occurs.
     */
    public List<Prova> getProvasPorDisciplina(String codigoDisciplina) {
        try {
            List<List<Object>> dataProva = sheetsFacade.lerDados("Prova", "A", "F");
            List<Prova> provas = new ArrayList<>();
            for (List<Object> linha : dataProva) {
                if (linha.get(4).toString().equals(codigoDisciplina)) { // Filtra pelo código da disciplina
                    Prova novaProva = new Prova(linha.get(0).toString(), linha.get(4).toString(), linha.get(1).toString(), linha.get(2).toString(), linha.get(3).toString(), linha.get(5).toString());
                    novaProva.setCodigo(linha.get(3).toString());
                    provas.add(novaProva);
                }
            }
            return provas;
        } catch (Exception e) {
            System.err.println("Erro ao obter provas por disciplina: " + e.getMessage());
            return List.of(); // Retorna uma lista vazia em caso de erro
        }
    }

    /**
     * Retrieves the grade of a specific exam (Prova) for a student.
     *
     * @param nomeProva        The name of the exam.
     * @param codigoDisciplina The code of the associated discipline.
     * @param raAluno          The RA of the student.
     * @return The grade of the exam, or -1 if not found or an error occurs.
     */
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

    /**
     * Retrieves all assignments (Trabalho) from the Google Sheets.
     *
     * @return A list of Trabalho objects representing the assignments.
     * Returns an empty list if an error occurs.
     */
    public List<Trabalho> getTrabalhos() {
        try {
            List<List<Object>> dataTrabalho = sheetsFacade.lerDados("Trabalho", "A", "F");
            List<Trabalho> trabalhos = new ArrayList<>();
            System.out.println("TRABALHOOOS" + dataTrabalho);
            for (List<Object> linha : dataTrabalho) {
                trabalhos.add(new Trabalho(linha.get(0).toString(), linha.get(5).toString(), linha.get(1).toString(), linha.get(2).toString(), Boolean.parseBoolean(linha.get(3).toString()), linha.get(4).toString()));
                System.out.println("TRABALHOOOS" + trabalhos + " " + linha.get(5).toString());
            }
            return trabalhos;
        } catch (Exception e) {
            System.err.println("Erro ao obter trabalhos: " + e.getMessage());
            return List.of(); // Retorna uma lista vazia em caso de erro
        }
    }

    /**
     * Retrieves a specific assignment (Trabalho) based on its name and associated discipline code.
     *
     * @param nomeTrabalho     The name of the assignment.
     * @param codigoDisciplina The code of the associated discipline.
     * @return A Trabalho object representing the assignment, or null if not found.
     */
    public Trabalho getTrabalho(String nomeTrabalho, String codigoDisciplina) {
        try {
            List<List<Object>> dataTrabalho = sheetsFacade.lerDados("Trabalho", "A", "F");
            for (List<Object> linha : dataTrabalho) {
                if (linha.get(0).toString().equals(nomeTrabalho) && linha.get(5).toString().equals(codigoDisciplina)) {
                    return new Trabalho(linha.get(0).toString(), linha.get(0).toString(), linha.get(1).toString(), linha.get(2).toString(), false, linha.get(4).toString()); // Cria o objeto Trabalho com os dados da linha
                }
            }
            return null; // Retorna null se não encontrar o trabalho
        } catch (Exception e) {
            System.err.println("Erro ao obter trabalho: " + e.getMessage());
            return null;
        }
    }

    /**
     * Retrieves all assignments (Trabalho) associated with a specific discipline.
     *
     * @param codigoDisciplina The code of the discipline.
     * @return A list of Trabalho objects representing the assignments for the discipline.
     * Returns an empty list if an error occurs.
     */
    public List<Trabalho> getTrabalhosPorDisciplina(String codigoDisciplina) {
        try {
            // Aqui estava lendo até a coluna E (olhar comentário da linha 224)
            // Correção da linha abaixo pdoe ser:
            // List<List<Object>> dataTrabalho = sheetsFacade.lerDados("Trabalho", "A", "F");
            List<List<Object>> dataTrabalho = sheetsFacade.lerDados("Trabalho", "A", "E");
            List<Trabalho> trabalhos = new ArrayList<>();
            for (List<Object> linha : dataTrabalho) {

                // Mas aqui estamos tentando acessar a coluna F (índice 5)
                if (linha.get(5).toString().equals(codigoDisciplina)) { // Filtra pelo código da disciplina
                    trabalhos.add(new Trabalho(linha.get(0).toString(), linha.get(0).toString(), linha.get(1).toString(), linha.get(2).toString(), Boolean.parseBoolean(linha.get(3).toString()), linha.get(4).toString()));
                }
            }
            return trabalhos;
        } catch (Exception e) {
            System.err.println("Erro ao obter trabalhos por disciplina: " + e.getMessage());
            return List.of(); // Retorna uma lista vazia em caso de erro
        }
    }

    /**
     * Retrieves the grade of a specific assignment (Trabalho) for a student.
     *
     * @param nomeTrabalho     The name of the assignment.
     * @param codigoDisciplina The code of the associated discipline.
     * @param raAluno          The RA of the student.
     * @return The grade of the assignment, or -1 if not found or an error occurs.
     */
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

    /**
     * Retrieves all evaluation methods (Prova and Trabalho) associated with a specific student.
     *
     * @param raAluno The RA of the student.
     * @return A list of MetodoDeAvaliacao objects representing the evaluations of the student.
     */
    public List<MetodoDeAvaliacao> getAvaliacoesAluno(String raAluno) {
        List<MetodoDeAvaliacao> avaliacoes = new ArrayList<>();
        try {
            List<List<Object>> dataAlunoProva = sheetsFacade.lerDados("AlunoProva", "A", "D");
            for (List<Object> linha : dataAlunoProva) {
                if (linha.get(0).toString().equals(raAluno)) {
                    Prova prova = getProva(linha.get(1).toString(), linha.get(2).toString());
                    if (prova != null) {
                        prova.setNota(Integer.parseInt(linha.get(3).toString()));
                        avaliacoes.add(prova);
                    }
                }
            }

            List<List<Object>> dataAlunoTrabalho = sheetsFacade.lerDados("AlunoTrabalho", "A", "D");
            for (List<Object> linha : dataAlunoTrabalho) {
                if (linha.get(0).toString().equals(raAluno)) {
                    Trabalho trabalho = getTrabalho(linha.get(1).toString(), linha.get(2).toString());
                    if (trabalho != null) {
                        trabalho.setNota(Integer.parseInt(linha.get(3).toString()));
                        avaliacoes.add(trabalho);
                    }
                }
            }

        } catch (Exception e) {
            System.err.println("Erro ao obter avaliações do aluno: " + e.getMessage());
        }
        return avaliacoes;
    }

    /**
     * Retrieves all classes (Aula) associated with a specific discipline.
     *
     * @param codigoDisciplina The code of the discipline.
     * @return A list of Aula objects representing the classes of the discipline.
     */
    public List<Aula> getAulasDiscipla(String codigoDisciplina) {
        try {
            List<List<Object>> dataAula = sheetsFacade.lerDados("Aula", "A", "E");
            List<Aula> aulas = new ArrayList<>();
            for (List<Object> linha : dataAula) {
                if (linha.get(0).toString().equals(codigoDisciplina)) {
                    aulas.add(new Aula(linha.get(1).toString(), linha.get(2).toString(), linha.get(3).toString(), linha.get(0).toString(), linha.get(4).toString()));
                }
            }
            return aulas;
        } catch (Exception e) {
            System.err.println("Erro ao obter aulas da disciplina: " + e.getMessage());
            return List.of(); // Retorna uma lista vazia em caso de erro
        }
    }

    /**
     * Retrieves all TodoItems from the Google Sheets.
     *
     * @return A list of TodoItem objects representing the tasks.
     */
    public List<TodoItem> getTodoItens() {
        List<TodoItem> todoItems = new ArrayList<>();
        try {
            List<List<Object>> dataTodoItem = sheetsFacade.lerDados("TodoItem", "A", "G");
            for (List<Object> linha : dataTodoItem) {
                Disciplina disciplina = getDisciplina(linha.get(2).toString());
                MetodoDeAvaliacao avaliacao = getProva(linha.get(3).toString(), linha.get(2).toString());

                if (avaliacao == null) avaliacao = getTrabalho(linha.get(3).toString(), linha.get(2).toString());

                TodoItem todoItem = new TodoItem(linha.get(0).toString(), linha.get(1).toString(), disciplina, avaliacao, linha.get(4).toString(), linha.get(5).toString());
                todoItem.setConcluido(Boolean.parseBoolean(linha.get(6).toString()));
                todoItems.add(todoItem);
            }
        } catch (Exception e) {
            System.err.println("Erro ao obter TodoItems: " + e.getMessage());
        }
        return todoItems;
    }

    /**
     * Adds a list of classes (Aula) to a specific discipline.
     *
     * @param codigoDisciplina The code of the discipline.
     * @param aulas            A list of Aula objects to be added.
     */
    public void adicionarAulasDisciplina(String codigoDisciplina, List<Aula> aulas) {
        try {
            List<List<Object>> novasAulas = new ArrayList<>();
            for (Aula aula : aulas) {
                novasAulas.add(List.of(codigoDisciplina, aula.getHorarioInicio(), aula.getHorarioFim(), aula.getDiaSemana(), aula.getLocal()));
            }
            sheetsFacade.escreverDados("Aula", "A", "E", novasAulas);
        } catch (Exception e) {
            System.err.println("Erro ao adicionar aulas à disciplina: " + e.getMessage());
        }
    }

    /**
     * Adds a new student (Aluno) to the Google Sheets.
     *
     * @param aluno The Aluno object to be added.
     */
    public void adicionarAluno(Aluno aluno) {
        try {
            List<List<Object>> novoAluno = List.of(List.of(aluno.getRa(), aluno.getNome(), aluno.getCurso(), aluno.getCR(), aluno.getSenha(), aluno.getCaminhoFoto() != null ? aluno.getCaminhoFoto() : "" // Garante que não seja nulo
            ));
            sheetsFacade.escreverDados("Aluno", "A", "F", novoAluno);
        } catch (Exception e) {
            System.err.println("Erro ao adicionar aluno: " + e.getMessage());
        }
    }

    /**
     * Adds a new discipline (Disciplina) to the Google Sheets.
     *
     * @param disciplina The Disciplina object to be added.
     */
    public void adicionarDisciplina(Disciplina disciplina) {
        try {
            List<List<Object>> novaDisciplina = List.of(List.of(disciplina.getCodigo(), disciplina.getNome(), disciplina.getPED(), disciplina.getCreditos(), disciplina.getProfessor(), "0"));
            sheetsFacade.escreverDados("Disciplina", "A", "F", novaDisciplina);
        } catch (Exception e) {
            System.err.println("Erro ao adicionar disciplina: " + e.getMessage());
        }
    }

    /**
     * Adds a discipline to a specific student.
     *
     * @param aluno      The Aluno object representing the student.
     * @param disciplina The Disciplina object to be added to the student.
     */
    public void adicionarDisciplinaAluno(Aluno aluno, Disciplina disciplina) {
        try {
            List<List<Object>> novaDisciplinaAluno = List.of(List.of(aluno.getRa(), disciplina.getCodigo(), 0));
            sheetsFacade.escreverDados("AlunoDisciplina", "A", "C", novaDisciplinaAluno);
        } catch (Exception e) {
            System.err.println("Erro ao adicionar disciplina ao aluno: " + e.getMessage());
        }
    }

    /**
     * Adds a new exam (Prova) to the Google Sheets.
     *
     * @param prova The Prova object to be added.
     */
    public void adicionarProva(Prova prova) {
        try {
            List<List<Object>> novaProva = List.of(List.of(prova.getNome(), prova.getLocal(), prova.getDuracao(), prova.getData(), prova.getCodigoDisciplina(), prova.getHorarioInicio()));
            sheetsFacade.escreverDados("Prova", "A", "F", novaProva);
        } catch (Exception e) {
            System.err.println("Erro ao adicionar prova: " + e.getMessage());
        }
    }

    /**
     * Adds a grade for a specific exam (Prova) for a student.
     *
     * @param raAluno          The RA of the student.
     * @param nomeProva        The name of the exam.
     * @param codigoDisciplina The code of the associated discipline.
     * @param nota             The grade to be added.
     */
    public void adicionarNotaProva(String raAluno, String nomeProva, String codigoDisciplina, int nota) {
        try {
            List<List<Object>> novaNotaProva = List.of(List.of(raAluno, nomeProva, codigoDisciplina, nota));
            sheetsFacade.escreverDados("AlunoProva", "A", "D", novaNotaProva);
        } catch (Exception e) {
            System.err.println("Erro ao adicionar nota da prova: " + e.getMessage());
        }
    }

    /**
     * Adds a new assignment (Trabalho) to the Google Sheets.
     *
     * @param trabalho The Trabalho object to be added.
     */
    public void adicionarTrabalho(Trabalho trabalho) {
        try {
            List<List<Object>> novoTrabalho = List.of(List.of(trabalho.getNome(), trabalho.getDataInicio(), trabalho.getDataEntrega(), trabalho.getEmGrupo(), trabalho.getGrupo(), trabalho.getCodigoDisciplina()));
            sheetsFacade.escreverDados("Trabalho", "A", "F", novoTrabalho);
        } catch (Exception e) {
            System.err.println("Erro ao adicionar trabalho: " + e.getMessage());
        }
    }

    /**
     * Adds a grade for a specific assignment (Trabalho) for a student.
     *
     * @param raAluno          The RA of the student.
     * @param nomeTrabalho     The name of the assignment.
     * @param codigoDisciplina The code of the associated discipline.
     * @param nota             The grade to be added.
     */
    public void adicionarNotaTrabalho(String raAluno, String nomeTrabalho, String codigoDisciplina, int nota) {
        try {
            List<List<Object>> novaNotaTrabalho = List.of(List.of(raAluno, nomeTrabalho, codigoDisciplina, nota));
            sheetsFacade.escreverDados("AlunoTrabalho", "A", "D", novaNotaTrabalho);
        } catch (Exception e) {
            System.err.println("Erro ao adicionar nota do trabalho: " + e.getMessage());
        }
    }

    /**
     * Adds a new TodoItem to the Google Sheets.
     *
     * @param raAluno  The RA of the student.
     * @param todoItem The TodoItem object to be added.
     */
    public void adicionarTodoItem(String raAluno, TodoItem todoItem) {
        try {
            List<List<Object>> novoTodoItem = List.of(List.of(raAluno, todoItem.getNome(), todoItem.getDisciplina().getCodigo(), todoItem.getAvaliacao().getNome(), todoItem.getPrioridade(), todoItem.getData(), String.valueOf(todoItem.getConcluido())));
            sheetsFacade.escreverDados("TodoItem", "A", "G", novoTodoItem);
        } catch (Exception e) {
            System.err.println("Erro ao adicionar TodoItem: " + e.getMessage());
        }
    }

    /**
     * Deletes a student (Aluno) from the Google Sheets.
     *
     * @param aluno The Aluno object to be deleted.
     */
    public void deletarAluno(Aluno aluno) {
        try {
            List<Aluno> dadosAluno = getAlunos();
            for (int index = 0; index < dadosAluno.size(); index++) {
                Aluno alunoAtual = dadosAluno.get(index);
                if (alunoAtual.getRa().equals(aluno.getRa())) {
                    sheetsFacade.deletarDados("Aluno", "A", "F", index + 2); // +2 para pular o cabeçalho e ajustar o índice
                    return;
                }
            }

        } catch (Exception e) {
            System.err.println("Erro ao deletar aluno: " + e.getMessage());
        }
    }

    /**
     * Deletes a discipline (Disciplina) from the Google Sheets.
     *
     * @param disciplina The Disciplina object to be deleted.
     */
    public void deletarDisciplina(Disciplina disciplina) {
        try {
            List<Disciplina> dadosDisciplina = getDisciplinas();
            for (int index = 0; index < dadosDisciplina.size(); index++) {
                Disciplina disciplinaAtual = dadosDisciplina.get(index);
                if (disciplinaAtual.getCodigo().equals(disciplina.getCodigo())) {
                    sheetsFacade.deletarDados("Disciplina", "A", "F", index + 2); // +2 para pular o cabeçalho e ajustar o índice
                    return;
                }
            }

        } catch (Exception e) {
            System.err.println("Erro ao deletar disciplina: " + e.getMessage());
        }
    }

    /**
     * Deletes an exam (Prova) from the Google Sheets.
     *
     * @param codigoDisciplina The code of the associated discipline.
     * @param prova            The Prova object to be deleted.
     */
    public void deletarProva(String codigoDisciplina, Prova prova) {
        try {
            List<Prova> dadosProva = getProvas();
            for (int index = 0; index < dadosProva.size(); index++) {
                Prova provaAtual = dadosProva.get(index);
                if (provaAtual.getNome().equals(prova.getNome()) && codigoDisciplina.equals(codigoDisciplina)) {
                    sheetsFacade.deletarDados("Prova", "A", "F", index + 2); // +2 para pular o cabeçalho e ajustar o índice
                    return;
                }
            }

        } catch (Exception e) {
            System.err.println("Erro ao deletar prova: " + e.getMessage());
        }
    }

    /**
     * Deletes an assignment (Trabalho) from the Google Sheets.
     *
     * @param codigoDisciplina The code of the associated discipline.
     * @param trabalho         The Trabalho object to be deleted.
     */
    public void deletarTrabalho(String codigoDisciplina, Trabalho trabalho) {
        try {
            List<Trabalho> dadosTrabalho = getTrabalhos();
            for (int index = 0; index < dadosTrabalho.size(); index++) {
                Trabalho trabalhoAtual = dadosTrabalho.get(index);
                if (trabalhoAtual.getNome().equals(trabalho.getNome()) && codigoDisciplina.equals(codigoDisciplina)) {
                    sheetsFacade.deletarDados("Trabalho", "A", "F", index + 2); // +2 para pular o cabeçalho e ajustar o índice
                    return;
                }
            }

        } catch (Exception e) {
            System.err.println("Erro ao deletar trabalho: " + e.getMessage());
        }
    }

    /**
     * Deletes a TodoItem from the Google Sheets.
     *
     * @param todoItem The TodoItem object to be deleted.
     * @param raAluno  The RA of the student associated with the TodoItem.
     */
    public void deletarTodoItem(TodoItem todoItem, String raAluno) {
        try {
            List<List<Object>> dadosTodoItem = sheetsFacade.lerDados("TodoItem", "A", "G");
            for (int index = 0; index < dadosTodoItem.size(); index++) {
                List<Object> linha = dadosTodoItem.get(index);
                if (linha.get(0).toString().equals(raAluno) && linha.get(1).toString().equals(todoItem.getNome())) {
                    sheetsFacade.deletarDados("TodoItem", "A", "G", index + 2); // +2 para pular o cabeçalho e ajustar o índice
                    return;
                }
            }

        } catch (Exception e) {
            System.err.println("Erro ao deletar TodoItem: " + e.getMessage());
        }
    }
}