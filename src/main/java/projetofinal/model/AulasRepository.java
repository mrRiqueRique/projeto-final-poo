package projetofinal.model;

import java.util.List;

/**
 * Classe responsável por gerenciar o repositório de aulas.
 * <p>
 * Esta classe implementa o padrão Singleton para garantir que apenas uma instância
 * do repositório seja criada durante a execução do programa. Ela fornece métodos
 * para realizar operações relacionadas às aulas, como adicionar e listar aulas
 * associadas a uma disciplina.
 */
public class AulasRepository extends Repository<Aula> {
    private static AulasRepository instancia;

    /**
     * Construtor privado para implementar o padrão Singleton.
     */
    private AulasRepository() {
        super();
    }

    /**
     * Retorna a instância única de AulasRepository.
     *
     * @return A instância de AulasRepository.
     */
    public static AulasRepository getInstancia() {
        if (instancia == null) {
            instancia = new AulasRepository();
        }
        return instancia;
    }

    /**
     * Adiciona uma lista de aulas a uma disciplina específica.
     *
     * @param codigoDisciplina O código da disciplina.
     * @param aulas            A lista de aulas a serem adicionadas.
     */
    public void adicionarAulasDisciplina(String codigoDisciplina, List<Aula> aulas) {
        service.adicionarAulasDisciplina(codigoDisciplina, aulas);
    }

    /**
     * Lista todas as aulas associadas a uma disciplina específica.
     *
     * @param codigoDisciplina O código da disciplina.
     * @return A lista de aulas da disciplina.
     */
    public List<Aula> listarAulasPorDisciplina(String codigoDisciplina) {
        return service.getAulasDiscipla(codigoDisciplina);
    }
}