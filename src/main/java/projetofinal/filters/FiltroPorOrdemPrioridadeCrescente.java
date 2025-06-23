package projetofinal.filters;

import projetofinal.model.TodoItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FiltroPorOrdemPrioridadeCrescente implements Filter<TodoItem> {

    @Override
    public List<TodoItem> meetCriteria(List<TodoItem> todoItens) {
        List<TodoItem> ordenados = new ArrayList<>(todoItens);

        Collections.sort(ordenados, new Comparator<TodoItem>() {
            @Override
            public int compare(TodoItem a, TodoItem b) {
                return getPesoPrioridade(a.getPrioridade()) - getPesoPrioridade(b.getPrioridade());
            }
        });

        return ordenados;
    }

    private int getPesoPrioridade(String prioridade) {
        if (prioridade == null) return Integer.MAX_VALUE; // Nulos no final

        switch (prioridade.toLowerCase()) {
            case "baixa": return 1;
            case "média": return 2;
            case "alta": return 3;
            default: return Integer.MAX_VALUE; // Valores desconhecidos também no final
        }
    }
}
