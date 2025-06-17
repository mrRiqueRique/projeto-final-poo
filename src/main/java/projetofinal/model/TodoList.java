package projetofinal.model;

import java.util.ArrayList;
import java.util.List;

public class TodoList {
    private String id;
    private List<TodoItem> todoList;

    public TodoList(){
        this.todoList = new ArrayList<>();
    }

    public void adicionarItem(TodoItem item){
        todoList.add(item);
    }

    public void concluirTarefa(TodoItem item){
        item.concluir(true);
        todoList.remove(item);
    }
    
}
