package projetofinal.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a list of TodoItems.
 * Provides methods to manage and manipulate the list of tasks.
 */
public class TodoList {
    private String id;
    private List<TodoItem> todoList;

    /**
     * Constructs a new TodoList.
     * Initializes an empty list of TodoItems.
     */
    public TodoList() {
        this.todoList = new ArrayList<>();
    }

    /**
     * Adds a single TodoItem to the list.
     *
     * @param item The TodoItem to be added.
     */
    public void adicionarItem(TodoItem item) {
        todoList.add(item);
    }

    /**
     * Adds multiple TodoItems to the list.
     *
     * @param items A list of TodoItems to be added.
     */
    public void adicionarTodoItens(List<TodoItem> items) {
        todoList.addAll(items);
    }

    /**
     * Marks a specific TodoItem as completed and removes it from the list.
     *
     * @param item The TodoItem to be marked as completed and removed.
     */
    public void concluirTarefa(TodoItem item) {
        item.concluir(true);
        todoList.remove(item);
    }

    /**
     * Retrieves the list of TodoItems.
     *
     * @return A list of TodoItems.
     */
    public List<TodoItem> listarItems() {
        return todoList;
    }
}
