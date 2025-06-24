package projetofinal.model;

/**
 * Classe responsável por gerenciar o repositório de alunos.
 * <p>
 * Esta classe implementa o padrão Singleton para garantir que apenas uma instância
 * do repositório seja criada durante a execução do programa. Ela fornece métodos
 * para realizar operações como adicionar, remover, atualizar e buscar alunos.
 * <p>
 * Os dados dos alunos são carregados a partir de um serviço externo, como o Google Sheets,
 * e manipulados através de métodos específicos.
 */
public class AlunoRepository extends Repository<Aluno> {
    private static AlunoRepository instancia;

    /**
     * Construtor privado para implementar o padrão Singleton.
     * Inicializa o repositório e tenta carregar os alunos do serviço externo.
     */
    private AlunoRepository() {
        super();
        try {
            carregarAlunosDoSheets();
        } catch (Exception e) {
            System.err.println("Erro ao inicializar AlunoRepository: " + e.getMessage());
        }
    }

    /**
     * Retorna a instância única de AlunoRepository.
     *
     * @return A instância de AlunoRepository.
     */
    public static AlunoRepository getInstancia() {
        if (instancia == null) {
            instancia = new AlunoRepository();
        }
        return instancia;
    }

    /**
     * Busca um aluno pelo RA (Registro Acadêmico).
     *
     * @param ra O RA do aluno a ser buscado.
     * @return O aluno correspondente ao RA, ou `null` se não encontrado.
     */
    public Aluno getAlunoPorRa(String ra) {
        return getItems().stream().filter(a -> a.getRa().equals(ra)).findFirst().orElse(null);
    }

    /**
     * Carrega os alunos a partir do serviço externo (ex.: Google Sheets).
     * <p>
     * Este método utiliza o serviço para buscar os alunos e os define na lista de itens.
     */
    private void carregarAlunosDoSheets() {
        try {
            setItems(service.getAlunos());
        } catch (Exception e) {
            System.err.println("Erro ao carregar alunos do Sheets: " + e.getMessage());
        }
    }

    /**
     * Adiciona um novo aluno ao repositório.
     *
     * @param aluno O aluno a ser adicionado.
     */
    public void adicionarAluno(Aluno aluno) {
        try {
            addItem(aluno);
            service.adicionarAluno(aluno);
        } catch (Exception e) {
            System.err.println("Erro ao adicionar aluno: " + e.getMessage());
        }
    }

    /**
     * Remove um aluno do repositório com base no RA.
     *
     * @param ra O RA do aluno a ser removido.
     */
    public void removerAluno(String ra) {
        Aluno aluno = getAlunoPorRa(ra);
        if (aluno != null) {
            removeItem(aluno);
            service.deletarAluno(aluno);
        } else {
            System.out.println("Aluno não encontrado.");
        }
    }

    /**
     * Atualiza os dados de um aluno no repositório.
     *
     * @param alunoVelho O aluno antigo cujos dados serão atualizados.
     * @param alunoNovo  O aluno com os novos dados.
     */
    public void atualizarAluno(Aluno alunoVelho, Aluno alunoNovo) {
        if (alunoVelho == null) {
            System.out.println("Aluno não encontrado.");
        } else {
            service.atualizarAluno(alunoVelho, alunoNovo);
        }
    }
}