package projetofinal;

import java.util.ArrayList;
import java.util.List;

public class TodoList {

    private List<TodoItem> todoList;

    public TodoList(){
        this.todoList = new ArrayList<>();
    }

    public void adicionarItem(TodoItem item){
        todoList.add(item);
    }
    
}
