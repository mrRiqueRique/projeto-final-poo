package projetofinal.model;

import java.util.List;

public class TodoItemRepository {
    private static TodoItemRepository instancia;
    private List<TodoItem> todoItems;
    private Service service;

    private TodoItemRepository() {
        service = new Service();
        carregarTodoItems();
    }

    public static TodoItemRepository getInstancia() {
        if (instancia == null) {
            instancia = new TodoItemRepository();
        }
        return instancia;
    }

    public List<TodoItem> getTodoItems() {
        return this.todoItems;
    }

    private void carregarTodoItems() {
        try {
            this.todoItems = service.getTodoItens();
        } catch (Exception e) {
            System.err.println("Erro ao carregar todo items: " + e.getMessage());
        }
    }

    public TodoList getTodoListPorAluno(String raAluno) {
        TodoList todoList = new TodoList();
        todoList.adicionarTodoItens(todoItems.stream().filter(todoItem -> todoItem.getRaAluno().equals(raAluno)).toList());
        return todoList;
    }

    public void adicionarTodoItem(String raAluno, TodoItem todoItem) {
        try {
            service.adicionarTodoItem(raAluno, todoItem);
            todoItems.add(todoItem);
        } catch (Exception e) {
            System.err.println("Erro ao adicionar todo item: " + e.getMessage());
        }
    }
}