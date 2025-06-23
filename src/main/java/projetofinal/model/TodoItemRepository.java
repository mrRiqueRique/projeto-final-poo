package projetofinal.model;

public class TodoItemRepository extends Repository<TodoItem> {
    private static TodoItemRepository instancia;

    private TodoItemRepository() {
        super();
        carregarTodoItems();
    }

    public static TodoItemRepository getInstancia() {
        if (instancia == null) {
            instancia = new TodoItemRepository();
        }
        return instancia;
    }

    private void carregarTodoItems() {
        try {
            setItems(service.getTodoItens());
        } catch (Exception e) {
            System.err.println("Erro ao carregar todo items: " + e.getMessage());
        }
    }

    public TodoList getTodoListPorAluno(String raAluno) {
        TodoList todoList = new TodoList();
        todoList.adicionarTodoItens(getItems().stream().filter(todoItem -> todoItem.getRaAluno().equals(raAluno)).toList());
        return todoList;
    }

    public void adicionarTodoItem(String raAluno, TodoItem todoItem) {
        try {
            service.adicionarTodoItem(raAluno, todoItem);
            addItem(todoItem);
        } catch (Exception e) {
            System.err.println("Erro ao adicionar todo item: " + e.getMessage());
        }
    }
}