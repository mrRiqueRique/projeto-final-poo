package projetofinal.model;

    import java.util.ArrayList;
    import java.util.List;

    public class AlunoRepository {
        private static AlunoRepository instancia;
        private List<Aluno> alunos = new ArrayList<>();
        private Service service;

        private AlunoRepository() {
            try {
                service = new Service();
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

        // todo - alterar dados do aluno
        // todo - deletar aluno
    }