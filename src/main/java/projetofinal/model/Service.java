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
            // --> Alterado para "E" para incluir a coluna da foto. (Lari)
            List<List<Object>> dataAluno = sheetsFacade.lerDados("Aluno", "A", "E");
            List<Aluno> alunos = new ArrayList<>();
            for (List<Object> linha : dataAluno) {
                if (linha.isEmpty()) continue; // Pula linhas vazias
                
                // Lógica de criação do aluno para incluir CR e Foto.
                Aluno aluno = new Aluno(linha.get(0).toString(), linha.get(1).toString(), linha.get(2).toString());

                // Adiciona o CR se a coluna existir
                if (linha.size() > 3 && linha.get(3) != null && !linha.get(3).toString().isEmpty()) {
                    aluno.setCR(Double.parseDouble(linha.get(3).toString()));
                }
                
                // Adiciona o caminho da foto se a coluna existir
                if (linha.size() > 4 && linha.get(4) != null) {
                    aluno.setCaminhoFoto(linha.get(4).toString());
                }

                alunos.add(aluno);
            }
            return alunos;
        } catch (Exception e) {
            System.err.println("Erro ao obter alunos: " + e.getMessage());
            return List.of(); // Retorna uma lista vazia em caso de erro
        }
    }

    public Aluno getAluno(String ra) {
        try {
            // --> Alterado para "E" para incluir a coluna da foto. (Lari)
            List<Object> dataAluno = sheetsFacade.lerDadosLinhaPorId("Aluno", "A", "E", ra);
            if (dataAluno.isEmpty()) return null;
            
            // --> Lógica de criação do aluno melhorada para incluir CR e Foto. (Lari)
            Aluno aluno = new Aluno(dataAluno.get(0).toString(), dataAluno.get(1).toString(), dataAluno.get(2).toString());

            if (dataAluno.size() > 3 && dataAluno.get(3) != null && !dataAluno.get(3).toString().isEmpty()) {
                aluno.setCR(Double.parseDouble(dataAluno.get(3).toString()));
            }
            
            if (dataAluno.size() > 4 && dataAluno.get(4) != null) {
                aluno.setCaminhoFoto(dataAluno.get(4).toString());
            }

            return aluno;
        } catch (Exception e) {
            System.err.println("Erro ao obter aluno: " + e.getMessage());
            return null;
        }
    }

    public List<Disciplina> getDisciplinas() {
        try {
            List<List<Object>> dataDisciplina = sheetsFacade.lerDados("Disciplina", "A", "E");

            List<Disciplina> disciplinas = new ArrayList<>();
            for (List<Object> linha : dataDisciplina) {
                // todo - arrumar tratamento do ultimo parametro de [avaliações]
                disciplinas.add(new Disciplina(linha.get(0).toString(), linha.get(1).toString(), linha.get(2).toString(), Integer.parseInt(linha.get(3).toString()), 0, linha.get(4).toString()));
            }

            return disciplinas;
        } catch (Exception e) {
            System.err.println("Erro ao obter disciplinas: " + e.getMessage());
            return List.of(); // Retorna uma lista vazia em caso de erro
        }
    }

    public Disciplina getDisciplina(String codigo) {
        try {
            List<Object> dataDisciplina = sheetsFacade.lerDadosLinhaPorId("Disciplina", "A", "E", codigo);
            if (dataDisciplina.isEmpty()) return null;
            Disciplina disciplina = new Disciplina(dataDisciplina.get(0).toString(), dataDisciplina.get(1).toString(), dataDisciplina.get(2).toString(), Integer.parseInt(dataDisciplina.get(3).toString()), 0, dataDisciplina.get(4).toString());
            return disciplina;
        } catch (Exception e) {
            System.err.println("Erro ao obter disciplina: " + e.getMessage());
            return null;
        }
    }

    public List<Disciplina> getDiciplinasDoAluno(String raAluno) {
        List<Disciplina> disciplinasDoAluno = new ArrayList<>();
        try {
            List<List<Object>> dataDisciplinaAluno = sheetsFacade.lerDados("AlunoDisciplina", "A", "C");
            for (List<Object> linha : dataDisciplinaAluno) {
                if (linha.get(0).toString().equals(raAluno)) {
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
            List<List<Object>> dataProva = sheetsFacade.lerDados("Prova", "A", "F");
            List<Prova> provas = new ArrayList<>();
            for (List<Object> linha : dataProva) {

                provas.add(new Prova(linha.get(0).toString(), linha.get(1).toString(), linha.get(2).toString(), linha.get(3).toString(), linha.get(5).toString()));
            }
            return provas;
        } catch (Exception e) {
            System.err.println("Erro ao obter provas: " + e.getMessage());
            return List.of(); // Retorna uma lista vazia em caso de erro
        }
    }

    public Prova getProva(String nomeProva, String codigoDisciplina) {
        try {
            List<List<Object>> dataProva = sheetsFacade.lerDados("Prova", "A", "F");
            for (List<Object> linha : dataProva) {
                if (linha.get(0).toString().equals(nomeProva) && linha.get(4).toString().equals(codigoDisciplina)) {
                    return new Prova(linha.get(0).toString(), linha.get(1).toString(), linha.get(2).toString(), linha.get(3).toString(),linha.get(5).toString());
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
            List<List<Object>> dataProva = sheetsFacade.lerDados("Prova", "A", "F");
            List<Prova> provas = new ArrayList<>();
            for (List<Object> linha : dataProva) {
                if (linha.get(4).toString().equals(codigoDisciplina)) { // Filtra pelo código da disciplina
                    provas.add(new Prova(linha.get(0).toString(), linha.get(1).toString(), linha.get(2).toString(), linha.get(3).toString(),linha.get(5).toString()));
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

    // TO DO: ERRO!!!! NÃO CONSEGUE CRIAR O TRABALHO

    public Trabalho getTrabalho(String nomeTrabalho, String codigoDisciplina) {
        try {
            List<List<Object>> dataTrabalho = sheetsFacade.lerDados("Trabalho", "A", "F");
            for (List<Object> linha : dataTrabalho) {
                if (linha.get(0).toString().equals(nomeTrabalho) && linha.get(4).toString().equals(codigoDisciplina)) {
                    return new Trabalho(linha.get(0).toString(), linha.get(1).toString(), linha.get(2).toString(), false);
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
            // Aqui estava lendo até a coluna E (olhar comentário da linha 224)
            // Correção da linha abaixo pdoe ser:
            // List<List<Object>> dataTrabalho = sheetsFacade.lerDados("Trabalho", "A", "F");
            List<List<Object>> dataTrabalho = sheetsFacade.lerDados("Trabalho", "A", "E");
            List<Trabalho> trabalhos = new ArrayList<>();
            for (List<Object> linha : dataTrabalho) {

                // Mas aqui estamos tentando acessar a coluna F (índice 5)
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

    public TodoList getTodoListAluno(String raAluno) {
        TodoList todoList = new TodoList();
        try {
            List<List<Object>> dataTodoItem = sheetsFacade.lerDados("TodoItem", "A", "h");
            for (List<Object> linha : dataTodoItem) {
                if (linha.get(0).toString().equals(raAluno)) {
                    Disciplina disciplina = getDisciplina(linha.get(2).toString());

                    MetodoDeAvaliacao metodoDeAvaliacao;
                    Prova prova = getProva(linha.get(3).toString(), linha.get(2).toString());
                    if (prova == null)
                        metodoDeAvaliacao = getTrabalho(linha.get(3).toString(), linha.get(2).toString());
                    else metodoDeAvaliacao = prova;

                    TodoItem todoItem = new TodoItem(linha.get(1).toString(), disciplina, metodoDeAvaliacao, linha.get(4).toString(), linha.get(5).toString());
                    todoItem.setConcluido(Boolean.parseBoolean(linha.get(6).toString()));

                    todoList.adicionarItem(todoItem);
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao obter TodoLists do aluno: " + e.getMessage());
        }
        return todoList;
    }

    public List<MetodoDeAvaliacao> getAvaliacoesAluno(String raAluno){
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

    // add s

    public void adicionarAulasDisciplina(String codigoDisciplina, List<Aula> aulas) {
        try {
            List<List<Object>> novasAulas = new ArrayList<>();
            for (Aula aula : aulas) {
                novasAulas.add(List.of(codigoDisciplina, aula.getHorarioInicio(), aula.getHorarioFim(), aula.getDiaSemana()));
            }
            sheetsFacade.escreverDados("Aula", "A", "D", novasAulas);
        } catch (Exception e) {
            System.err.println("Erro ao adicionar aulas à disciplina: " + e.getMessage());
        }
    }

    public void adicionarAluno(Aluno aluno) {
        try {
            // --> Adicionado o 'getCaminhoFoto' na lista de dados a serem escritos. (Lari)
            List<List<Object>> novoAluno = List.of(
                List.of(
                    aluno.getRa(), 
                    aluno.getNome(), 
                    aluno.getCurso(), 
                    aluno.getCR(), 
                    aluno.getCaminhoFoto() != null ? aluno.getCaminhoFoto() : "" // Garante que não seja nulo
                )
            );
            // --> Alterado para "E" para incluir a nova coluna. (Lari)
            sheetsFacade.escreverDados("Aluno", "A", "E", novoAluno);
        } catch (Exception e) {
            System.err.println("Erro ao adicionar aluno: " + e.getMessage());
        }
    }

    public void adicionarDisciplina(Disciplina disciplina) {
        try {
            List<List<Object>> novaDisciplina = List.of(List.of(disciplina.getCodigo(), disciplina.getNome(), disciplina.getCreditos(), disciplina.getProfessor()));
            sheetsFacade.escreverDados("Disciplina", "A", "E", novaDisciplina);
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
            sheetsFacade.escreverDados("Prova", "A", "F", novaProva);
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

    public void atualizarAluno(Aluno alunoVelho, Aluno alunoNovo) {
        try {
            // --> Alterado para "E" para ler todas as colunas do aluno. (Lari)
            List<List<Object>> dadosAluno = sheetsFacade.lerDados("Aluno", "A", "E");

            // --> Adicionado o 'getCaminhoFoto' na nova linha de dados. (Lari)
            List<Object> linhaNova = new ArrayList<>(
                List.of(
                    alunoNovo.getRa(), 
                    alunoNovo.getNome(), 
                    alunoNovo.getCurso(), 
                    alunoNovo.getCR(),
                    alunoNovo.getCaminhoFoto() != null ? alunoNovo.getCaminhoFoto() : ""
                )
            );

            for (List<Object> linha : dadosAluno) {
                if (linha.get(0).toString().equals(alunoVelho.getRa())) {
                    // --> Alterado para "E" para atualizar o intervalo correto. (Lari)
                    sheetsFacade.atualizarDados("Aluno", "A", "E", List.of(linha), List.of(linhaNova));
                    return;
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao atualizar aluno: " + e.getMessage());
        }
    }

    public void atualizarDisciplina(Disciplina disciplinaVelha, Disciplina disciplinaNova) {
        try {
            List<List<Object>> dadosDisciplina = sheetsFacade.lerDados("Disciplina", "A", "E");
            List<Object> linhaNova = new ArrayList<>(List.of(disciplinaNova.getCodigo(), disciplinaNova.getNome(), disciplinaNova.getCreditos(), disciplinaNova.getProfessor()));
            for (List<Object> linha : dadosDisciplina) {
                if (linha.get(0).toString().equals(disciplinaVelha.getCodigo())) {
                    sheetsFacade.atualizarDados("Disciplina", "A", "E", List.of(linha), List.of(linhaNova));
                    return;
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao atualizar disciplina: " + e.getMessage());
        }
    }

    public void atualizarFalta(Disciplina disciplina, String raAluno) {
        try {
            // adiciona 1 falta ao aluno na disciplina
            List<List<Object>> dadosAlunoDisciplina = sheetsFacade.lerDados("AlunoDisciplina", "A", "C");
            for (List<Object> linha : dadosAlunoDisciplina) {
                if (linha.get(0).toString().equals(raAluno) && linha.get(1).toString().equals(disciplina.getCodigo())) {
                    int faltasAtuais = Integer.parseInt(linha.get(2).toString());
                    faltasAtuais++;
                    List<Object> novaLinha = new ArrayList<>(List.of(raAluno, disciplina.getCodigo(), faltasAtuais));
                    sheetsFacade.atualizarDados("AlunoDisciplina", "A", "C", List.of(linha), List.of(novaLinha));
                    disciplina.setFaltas(faltasAtuais);
                    return;
                }
            }

        } catch (Exception e) {
            System.err.println("Erro ao adicionar falta: " + e.getMessage());
        }
    }

    public void atulizarNotaProva(String raAluno, String nomeProva, String codigoDisciplina, int nota) {
        try {
            List<List<Object>> dadosNotaProva = sheetsFacade.lerDados("AlunoProva", "A", "D");
            List<Object> novaLinha = new ArrayList<>(List.of(raAluno, nomeProva, codigoDisciplina, nota));
            for (List<Object> linha : dadosNotaProva) {
                if (linha.get(0).toString().equals(raAluno) && linha.get(1).toString().equals(nomeProva) && linha.get(2).toString().equals(codigoDisciplina)) {
                    sheetsFacade.atualizarDados("AlunoProva", "A", "D", List.of(linha), List.of(novaLinha));
                    return;
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao atualizar nota da prova: " + e.getMessage());
        }
    }

    public void atualizarNotaTrabalho(String raAluno, String nomeTrabalho, String codigoDisciplina, int nota) {
        try {
            List<List<Object>> dadosNotaTrabalho = sheetsFacade.lerDados("AlunoTrabalho", "A", "D");
            List<Object> novaLinha = new ArrayList<>(List.of(raAluno, nomeTrabalho, codigoDisciplina, nota));
            for (List<Object> linha : dadosNotaTrabalho) {
                if (linha.get(0).toString().equals(raAluno) && linha.get(1).toString().equals(nomeTrabalho) && linha.get(2).toString().equals(codigoDisciplina)) {
                    sheetsFacade.atualizarDados("AlunoTrabalho", "A", "D", List.of(linha), List.of(novaLinha));
                    return;
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao atualizar nota do trabalho: " + e.getMessage());
        }
    }

    public void atualizarProva(String codigoDisciplina, Prova provaAntiga, Prova provaNova) {
        try {
            List<List<Object>> dadosProva = sheetsFacade.lerDados("Prova", "A", "F");
            List<Object> novaLinha = new ArrayList<>(List.of(provaNova.getNome(), provaNova.getLocal(), provaNova.getDuracao(), provaNova.getData(), provaNova.getHorarioInicio(),codigoDisciplina));
            for (List<Object> linha : dadosProva) {
                if (linha.get(0).toString().equals(provaAntiga.getNome()) && linha.get(4).toString().equals(codigoDisciplina)) {
                    sheetsFacade.atualizarDados("Prova", "A", "F", List.of(linha), List.of(novaLinha));
                    return;
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao atualizar prova: " + e.getMessage());
        }
    }

    public void atualizarTrabalho(String codigoDisciplina, Trabalho trabalhoAntigo, Trabalho trabalhoNovo) {
        try {
            List<List<Object>> dadosTrabalho = sheetsFacade.lerDados("Trabalho", "A", "F");
            List<Object> novaLinha = new ArrayList<>(List.of(trabalhoNovo.getNome(), trabalhoNovo.getDataInicio(), trabalhoNovo.getDataEntrega(), trabalhoNovo.getEmGrupo(), trabalhoNovo.getGrupo(), codigoDisciplina));
            for (List<Object> linha : dadosTrabalho) {
                if (linha.get(0).toString().equals(trabalhoAntigo.getNome()) && linha.get(5).toString().equals(codigoDisciplina)) {
                    sheetsFacade.atualizarDados("Trabalho", "A", "F", List.of(linha), List.of(novaLinha));
                    return;
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao atualizar trabalho: " + e.getMessage());
        }
    }

    public void atualizarStatusTodoItem(TodoItem todoItem, String raAluno) {
        try {
            // se o to do estiver concluído, atualiza para não concluído e vice-versa
            List<List<Object>> dadosTodoItem = sheetsFacade.lerDados("TodoItem", "A", "G");
            for (List<Object> linha : dadosTodoItem) {
                if (linha.get(0).toString().equals(raAluno) && linha.get(1).toString().equals(todoItem.getNome())) {
                    List<Object> novaLinha = new ArrayList<>(List.of(raAluno, todoItem.getNome(), todoItem.getDisciplina().getCodigo(), todoItem.getAvaliacao().getNome(), todoItem.getPrioridade(), todoItem.getData(), String.valueOf(!todoItem.getConcluido())));
                    sheetsFacade.atualizarDados("TodoItem", "A", "G", List.of(linha), List.of(novaLinha));
                    todoItem.setConcluido(!todoItem.getConcluido());
                    return;
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao atualizar TodoItem: " + e.getMessage());
        }
    }

    // delete

    public void deletarAluno(Aluno aluno) {
        try {
            List<Aluno> dadosAluno = getAlunos();
            for (int index = 0; index < dadosAluno.size(); index++) {
                Aluno alunoAtual = dadosAluno.get(index);
                if (alunoAtual.getRa().equals(aluno.getRa())) {
                    // --> Alterado para "E" para garantir que a linha inteira seja considerada (Lari)
                    sheetsFacade.deletarDados("Aluno", "A", "E", index + 2); // +2 para pular o cabeçalho e ajustar o índice
                    return;
                }
            }

        } catch (Exception e) {
            System.err.println("Erro ao deletar aluno: " + e.getMessage());
        }
    }

    public void deletarDisciplina(Disciplina disciplina) {
        try {
            List<Disciplina> dadosDisciplina = getDisciplinas();
            for (int index = 0; index < dadosDisciplina.size(); index++) {
                Disciplina disciplinaAtual = dadosDisciplina.get(index);
                if (disciplinaAtual.getCodigo().equals(disciplina.getCodigo())) {
                    sheetsFacade.deletarDados("Disciplina", "A", "E", index + 2); // +2 para pular o cabeçalho e ajustar o índice
                    return;
                }
            }

        } catch (Exception e) {
            System.err.println("Erro ao deletar disciplina: " + e.getMessage());
        }
    }

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
