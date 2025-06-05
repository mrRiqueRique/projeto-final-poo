package projetofinal;

import java.util.ArrayList;
import java.util.List;

public class FiltroPorMetodoDeAvaliacao implements Filter<TodoItem> {
    
    private MetodoDeAvaliacao metodo;

    public FiltroPorMetodoDeAvaliacao(MetodoDeAvaliacao metodo){
        this.metodo = metodo;
    }


    @Override
    public List<TodoItem> meetCriteria(List<TodoItem> TodoItens){
        List<TodoItem> TodoItensFiltrados = new ArrayList<TodoItem>();

        for(TodoItem item: TodoItens){
            if(item.getData().equals(metodo)){
                TodoItensFiltrados.add(item);
            }
        }

        return TodoItensFiltrados;
    }

    
}
