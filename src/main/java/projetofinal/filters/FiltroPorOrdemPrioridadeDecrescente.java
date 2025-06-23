package projetofinal.filters;

import projetofinal.model.TodoItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FiltroPorOrdemPrioridadeDecrescente implements Filter<TodoItem> {

    @Override
    public List<TodoItem> meetCriteria(List<TodoItem> todoItens) {
        List<TodoItem> ordenados = new ArrayList<>(todoItens);

        Collections.sort(ordenados, new Comparator<TodoItem>() {
            @Override
            public int compare(TodoItem a, TodoItem b) {
                return getPesoPrioridade(b.getPrioridade()) - getPesoPrioridade(a.getPrioridade());
            }
        });

        return ordenados;
    }

    private int getPesoPrioridade(String prioridade) {
        if (prioridade == null) return Integer.MIN_VALUE; // Nulos no final

        switch (prioridade.toLowerCase()) {
            case "alta": return 3;
            case "média": return 2;
            case "baixa": return 1;
            default: return Integer.MIN_VALUE; // Desconhecido → final da lista
        }
    }
}
