package projetofinal.filters;

import java.util.List;

import projetofinal.model.TodoItem;

public class AndFilter implements Filter<TodoItem>{


    private Filter<TodoItem> filtro1;
    private Filter<TodoItem> filtro2;

    public AndFilter(Filter<TodoItem> filtro1, Filter<TodoItem> filtro2){
        this.filtro1 = filtro1;
        this.filtro2 = filtro2;
    }


    @Override
    public List<TodoItem> meetCriteria(List<TodoItem> TodoItens){
        List<TodoItem> primeiroFiltro = filtro1.meetCriteria(TodoItens);
        return filtro2.meetCriteria(primeiroFiltro);
    }
    
}
