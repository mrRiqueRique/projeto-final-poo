package projetofinal.filters;

import java.util.ArrayList;
import java.util.List;

import projetofinal.model.Disciplina;
import projetofinal.model.TodoItem;

public class FiltroPorDisciplina implements Filter<TodoItem> {
    
    private String codigoDisciplina;

    public FiltroPorDisciplina(String codigoDisciplina){
        this.codigoDisciplina = codigoDisciplina;
    }


    @Override
    public List<TodoItem> meetCriteria(List<TodoItem> TodoItens){
        List<TodoItem> TodoItensFiltrados = new ArrayList<TodoItem>();

        for(TodoItem item: TodoItens){
            if(item.getDisciplina().getCodigo().equals(codigoDisciplina)){
                TodoItensFiltrados.add(item);
            }
        }

        return TodoItensFiltrados;
    }

    
}
