package projetofinal.filters;

import java.util.ArrayList;
import java.util.List;

import projetofinal.model.TodoItem;

// verificar se cria outro filtro que ordena pela prioridade
public class FiltroPorPrioridade implements Filter<TodoItem> {
    
    private String prioridade;

    public FiltroPorPrioridade(String prioridade){
        this.prioridade = prioridade;
    }


    @Override
    public List<TodoItem> meetCriteria(List<TodoItem> TodoItens){
        List<TodoItem> TodoItensFiltrados = new ArrayList<TodoItem>();

        for(TodoItem item: TodoItens){
            if (item.getPrioridade() != null &&
                item.getPrioridade().toLowerCase().trim().equals(prioridade)) {
                TodoItensFiltrados.add(item);
            }
        }

        return TodoItensFiltrados;
    }

    
}
