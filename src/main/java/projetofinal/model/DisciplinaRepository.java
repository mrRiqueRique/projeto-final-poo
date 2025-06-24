package projetofinal.model;

import java.util.List;

/**
 * Classe responsável por gerenciar o repositório de disciplinas.
 * <p>
 * Esta classe implementa o padrão Singleton para garantir que apenas uma instância
 * do repositório seja criada durante a execução do programa. Ela fornece métodos
 * para realizar operações relacionadas às disciplinas, como carregar, buscar e adicionar disciplinas.
 */
public class DisciplinaRepository extends Repository<Disciplina> {
    private static DisciplinaRepository instancia;

    /**
     * Construtor privado para implementar o padrão Singleton.
     * Inicializa o repositório e tenta carregar as disciplinas do serviço externo.
     */
    private DisciplinaRepository() {
        super();
        carregarDisciplinas();
    }

    /**
     * Retorna a instância única de DisciplinaRepository.
     *
     * @return A instância de DisciplinaRepository.
     */
    public static DisciplinaRepository getInstancia() {
        if (instancia == null) {
            instancia = new DisciplinaRepository();
        }
        return instancia;
    }

    /**
     * Retorna a lista de disciplinas associadas a um aluno específico.
     *
     * @param raAluno O registro acadêmico (RA) do aluno.
     * @return A lista de disciplinas do aluno.
     */
    public List<Disciplina> getDisciplinasPorAluno(String raAluno) {
        return service.getDiciplinasDoAluno(raAluno);
    }

    /**
     * Retorna uma disciplina específica com base no código fornecido.
     *
     * @param codigoDisciplina O código da disciplina.
     * @return A disciplina correspondente ao código, ou `null` se não encontrada.
     */
    public Disciplina getDisciplina(String codigoDisciplina) {
        return getItems().stream().filter(s -> s.getCodigo().equals(codigoDisciplina)).findFirst().orElse(null);
    }

    /**
     * Carrega as disciplinas a partir do serviço externo.
     * <p>
     * Este método busca as disciplinas do serviço e as adiciona ao repositório.
     */
    private void carregarDisciplinas() {
        try {
            setItems(service.getDisciplinas());
        } catch (Exception e) {
            System.err.println("Erro ao carregar disciplinas: " + e.getMessage());
        }
    }

    /**
     * Adiciona uma nova disciplina ao repositório e ao serviço externo.
     *
     * @param disciplina A disciplina a ser adicionada.
     */
    public void adicionarDisciplina(Disciplina disciplina) {
        try {
            addItem(disciplina);
            service.adicionarDisciplina(disciplina);
        } catch (Exception e) {
            System.err.println("Erro ao adicionar disciplina: " + e.getMessage());
        }
    }
}