package projetofinal;

import java.util.ArrayList;
import java.util.List;

public class FiltroPorNome implements Filter<TodoItem> {
    
    private String nome;

    public FiltroPorNome(String nome){
        this.nome = nome;
    }


    @Override
    public List<TodoItem> meetCriteria(List<TodoItem> TodoItens){
        List<TodoItem> TodoItensFiltrados = new ArrayList<TodoItem>();

        for(TodoItem item: TodoItens){
            if(item.getData().equals(nome)){
                TodoItensFiltrados.add(item);
            }
        }

        return TodoItensFiltrados;
    }

    
}
