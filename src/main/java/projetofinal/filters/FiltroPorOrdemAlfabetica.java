package projetofinal.filters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import projetofinal.model.TodoItem;

public class FiltroPorOrdemAlfabetica implements Filter<TodoItem> {
    
    @Override
    public List<TodoItem> meetCriteria(List<TodoItem> TodoItens){
        List<TodoItem> TodoItensOrdenados = new ArrayList<TodoItem>(TodoItens);

        
        Collections.sort(TodoItensOrdenados, new Comparator<TodoItem>(){
            @Override
            public int compare(TodoItem a, TodoItem b){
                return a.getNome().compareToIgnoreCase(b.getNome());
            }
        });

        return TodoItensOrdenados;
    }
    
}
