package projetofinal.model;

import java.util.List;

/**
 * Repository class for managing TodoItem objects.
 * This class provides methods to load, retrieve, and add TodoItems.
 */
public class TodoItemRepository extends Repository<TodoItem> {
    private static TodoItemRepository instancia;

    /**
     * Private constructor to enforce singleton pattern.
     * Initializes the repository and loads TodoItems from the service.
     */
    private TodoItemRepository() {
        super();
        carregarTodoItems();
    }

    /**
     * Retrieves the singleton instance of TodoItemRepository.
     *
     * @return The singleton instance of TodoItemRepository.
     */
    public static TodoItemRepository getInstancia() {
        if (instancia == null) {
            instancia = new TodoItemRepository();
        }
        return instancia;
    }

    /**
     * Loads TodoItems from the service and sets them in the repository.
     * Handles any exceptions that occur during the loading process.
     */
    private void carregarTodoItems() {
        try {
            setItems(service.getTodoItens());
        } catch (Exception e) {
            System.err.println("Erro ao carregar todo items: " + e.getMessage());
        }
    }

    /**
     * Retrieves a TodoList containing all TodoItems associated with a specific student.
     *
     * @param raAluno The RA (Registro Acadêmico) of the student.
     * @return A TodoList containing the student's TodoItems.
     */
    public TodoList getTodoListPorAluno(String raAluno) {
        TodoList todoList = new TodoList();
        List<TodoItem> todoItemsPorAluno = getItems().stream().filter(todoItem -> todoItem.getRaAluno().equals(raAluno)).toList();
        todoList.adicionarTodoItens(todoItemsPorAluno);
        return todoList;
    }

    /**
     * Adds a new TodoItem for a specific student.
     * Updates the service and the repository with the new item.
     *
     * @param raAluno  The RA (Registro Acadêmico) of the student.
     * @param todoItem The TodoItem to be added.
     */
    public void adicionarTodoItem(String raAluno, TodoItem todoItem) {
        try {
            service.adicionarTodoItem(raAluno, todoItem);
            addItem(todoItem);
        } catch (Exception e) {
            System.err.println("Erro ao adicionar todo item: " + e.getMessage());
        }
    }
}