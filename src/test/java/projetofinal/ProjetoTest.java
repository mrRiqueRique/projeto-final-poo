package projetofinal;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import projetofinal.model.Aluno;
import projetofinal.model.Aula;
import projetofinal.model.Disciplina;
import projetofinal.model.Prova;
import projetofinal.model.TodoItem;
import projetofinal.model.TodoList;
import projetofinal.model.Trabalho;

public class ProjetoTest {

    // Teste dos métodos do Aluno de cadastrarDisciplina, getDisciplina e finalizarDisciplina
    @Test 
    public void CadastrarDisciplina(){
        Aluno aluno = new Aluno("281773", "Gabriela Taniguchi", "Ciência da Computação", "123");
        Disciplina poo = new Disciplina("MC322", "Programação Orientada a Objetos", "Gabriel De Freitas Leite", 4, 0, "Marcos Raimundo");
        aluno.cadastrarDisciplina(poo);

        assertEquals(poo, aluno.getDisciplinas().get(0));

        aluno.finalizarDisciplina(poo);
        List<Disciplina> disciplinas = aluno.getDisciplinas();

        boolean eliminada = true;

        for(Disciplina disciplinaAluno: disciplinas){
            if(disciplinaAluno.equals(poo))
                eliminada = false;
        }

        assertTrue(eliminada);
    }

    // Teste dos métodos get da classe Aula
    @Test
    public void GetAulas(){
        Aula aula = new Aula("19:00", "21:00", "quinta-feira", "MC322", "PB 17");

        assertEquals("MC322", aula.getDisciplina());
        assertEquals("quinta-feira", aula.getDiaSemana());
        assertEquals("19:00", aula.getHorarioInicio());
        assertEquals("21:00", aula.getHorarioFim());
        assertEquals("PB 17", aula.getLocal());
    }

    // Teste dos métodos de adicionaeAvaliação e os getters da classe Disciplina
    @Test
    public void Disciplinas(){
        Disciplina poo = new Disciplina("MC322", "Programação Orientada a Objetos", "Gabriel De Freitas Leite", 4, 0, "Marcos Raimundo");
        poo.computarFalta();
        Trabalho trabalho_final = new Trabalho("projeto-final", "09/06/2025", "23/06/2025", true, "MC322");
        Prova prova = new Prova("P1 de poo", "PB 13", "2 horas", "02/06/2025", "19:00", "21:00");
        poo.adicionarAvaliacao(trabalho_final);
        poo.adicionarAvaliacao(prova);       

        assertEquals(1, poo.getFaltas());
        assertTrue(poo.getAvaliacoes().contains(trabalho_final), "Trabalho não está presente");
        assertTrue(poo.getAvaliacoes().contains(prova), "Prova não está presente");
        assertEquals("MC322", poo.getCodigo());
        assertEquals("Programação Orientada a Objetos", poo.getNome());
        assertEquals("Gabriel De Freitas Leite", poo.getPED());
        assertEquals(4, poo.getCreditos());
        assertEquals("Marcos Raimundo", poo.getProfessor());
    }

    // Teste dos métodos getters da classe Prova
    @Test
    public void ProvaGetters() {
        Prova prova = new Prova("P1 de poo", "PB 13", "2 horas", "02/06/2025", "19:00", "MC322");

        assertEquals("PB 13", prova.getLocal());
        assertEquals("2 horas", prova.getDuracao());
        assertEquals("02/06/2025", prova.getData());
        assertEquals("19:00", prova.getHorarioInicio());
        assertEquals("MC322", prova.getCodigoDisciplina());
    }

    // Teste do método alterarLocal da classe Prova
    @Test
    public void ProvaAlterarLocal() {
        Prova prova = new Prova("P1", "Sala 1", "1 hora", "10/07/2025", "08:00", "MC102");
        prova.alterarLocal("Sala 3");
        assertEquals("Sala 3", prova.getLocal());
    }

    // Teste do método alterarData da classe Prova
    @Test
    public void ProvaAlterarData() {
        Prova prova = new Prova("P1", "Sala", "1h", "10/07/2025", "10:00", "MC202");
        prova.alterarData("15/07/2025");  
        assertEquals("15/07/2025", prova.getData());
    }

    // Teste da exceção DataInvalidaException
    @Test
    public void DataInvalidaException() {
        Prova prova = new Prova("P1", "Sala", "1h", "10/07/2025", "10:00", "MC202");

        
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
        
        prova.alterarData("01/01/2020");  
        String expectedOutput = "Não é possível inserir uma data passada\n";
        assertEquals(expectedOutput, outputStreamCaptor.toString());
    }

    // Teste da exceção DisciplinaNaoEncontradaException
    @Test
    public void DisciplinaNaoEncontradaException() {
        Aluno aluno = new Aluno("281773", "Gabriela Taniguchi", "Ciência da Computação", "123");
        Disciplina poo = new Disciplina("MC322", "Programação Orientada a Objetos", "Gabriel De Freitas Leite", 4, 0, "Marcos Raimundo");
        
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));

        aluno.finalizarDisciplina(poo);
         
        String expectedOutput = "Disciplina não encontrada\n";
        assertEquals(expectedOutput, outputStreamCaptor.toString());
    }

    // Teste dos getters e setters da classe Trabalho
    @Test
    public void TrabalhoGettersESetters() {

        Trabalho trab = new Trabalho("Projeto A", "23/06/2025", "30/06/2025", true, "MC322");

        assertEquals("23/06/2025", trab.getDataInicio());
        assertEquals("30/06/2025", trab.getDataEntrega());
        assertEquals("MC322", trab.getCodigoDisciplina());
        assertTrue(trab.getEmGrupo());

        trab.setEmGrupo(false);
        assertFalse(trab.getEmGrupo());
    }

    // Teste dos getters e setters da classe TodoItem
    @Test
    public void TodoItemGettersESetters() {
        Disciplina disc = new Disciplina("MC102", "Algoritmos", "Ieremies", 6, 0, "Santiago");
        Prova avaliacao = new Prova("P1", "PB 16", "2h", "15/07/2025", "10:00", "MC102");
        TodoItem item = new TodoItem("123456", "Estudar para prova", disc, avaliacao, "Alta", "10/07/2025");

        // Getters
        assertEquals("123456", item.getRaAluno());
        assertEquals("Estudar para prova", item.getNome());
        assertEquals(disc, item.getDisciplina());
        assertEquals(avaliacao, item.getAvaliacao());
        assertEquals("Alta", item.getPrioridade());
        assertEquals("10/07/2025", item.getData());
        assertFalse(item.getConcluido());

        // Setters
        item.setNome("Fazer resumo");
        assertEquals("Fazer resumo", item.getNome());

        item.setPrioridade("Média");
        assertEquals("Média", item.getPrioridade());

        item.setConcluido(true);
        assertTrue(item.getConcluido());

        Disciplina novaDisc = new Disciplina("MC202", "Estruturas de Dados", "Lucas", 6, 0, "Lehilton");
        item.setDisciplina(novaDisc);
        assertEquals(novaDisc, item.getDisciplina());
    }

    // Teste do método concluir tarefa da classe TodoItem
    @Test
    public void ConcluirTodoItem() {
        TodoItem item = new TodoItem("123", "Tarefa", null, null, "Baixa", "12/08/2025");
        assertFalse(item.getConcluido());

        item.concluir(true);
        assertTrue(item.getConcluido());

        item.concluir(false);
        assertFalse(item.getConcluido());
    }

    // Teste do método adicionarItem da clase TodoList
    @Test
    public void AdicionarItemTodoList() {
        TodoList lista = new TodoList();
        TodoItem item = new TodoItem("281773", "Finalizar projeto final", null, null, "Alta", "23/06/2025");

        lista.adicionarItem(item);

        assertEquals(1, lista.listarItems().size());
        assertTrue(lista.listarItems().contains(item));
    }

    // Teste do método adicionarTodoItens da classe TodoList
    @Test
    public void AdicionarTodoItens() {
        TodoList lista = new TodoList();
        TodoItem item1 = new TodoItem("281773", "Finalizar projeto final", null, null, "Alta", "23/06/2025");
        TodoItem item2 = new TodoItem("281773", "Estudar probabilidade", null, null, "Alta", "23/06/2025");
        TodoItem item3 = new TodoItem("281773", "Estudar cálculo III", null, null, "Alta", "23/06/2025");

        List<TodoItem> tarefas = Arrays.asList(item1, item2, item3);
        lista.adicionarTodoItens(tarefas);

        assertEquals(3, lista.listarItems().size());
        assertTrue(lista.listarItems().containsAll(tarefas));
    }
    
}
