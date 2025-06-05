package projetofinal.filters;

import java.util.ArrayList;
import java.util.List;

import projetofinal.model.Disciplina;
import projetofinal.model.TodoItem;

public class FiltroPorDisciplina implements Filter<TodoItem> {
    
    private Disciplina disciplina;

    public FiltroPorDisciplina(Disciplina disciplina){
        this.disciplina = disciplina;
    }


    @Override
    public List<TodoItem> meetCriteria(List<TodoItem> TodoItens){
        List<TodoItem> TodoItensFiltrados = new ArrayList<TodoItem>();

        for(TodoItem item: TodoItens){
            if(item.getData().equals(disciplina)){
                TodoItensFiltrados.add(item);
            }
        }

        return TodoItensFiltrados;
    }

    
}
