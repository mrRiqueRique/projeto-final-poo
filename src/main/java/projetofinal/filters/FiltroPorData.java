package projetofinal.filters;

import java.util.ArrayList;
import java.util.List;

import projetofinal.model.TodoItem;

public class FiltroPorData implements Filter<TodoItem> {

    private String data;

    public FiltroPorData(String data){
        this.data = data;
    }

    @Override
    public List<TodoItem> meetCriteria(List<TodoItem> TodoItens){
        List<TodoItem> TodoItensFiltrados = new ArrayList<TodoItem>();

        for(TodoItem item: TodoItens){
            if(item.getData().equals(data)){
                TodoItensFiltrados.add(item);
            }
        }

        return TodoItensFiltrados;
    }


    
}
