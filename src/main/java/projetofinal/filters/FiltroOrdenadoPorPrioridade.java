package projetofinal.filters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import projetofinal.model.TodoItem;

public class FiltroOrdenadoPorPrioridade implements Filter<TodoItem> {

    @Override
    public List<TodoItem> meetCriteria(List<TodoItem> todoItens) {
        List<TodoItem> ordenados = new ArrayList<>(todoItens);

        // Define a ordem desejada: Alta > Média > Baixa
        Collections.sort(ordenados, new Comparator<TodoItem>() {
            @Override
            public int compare(TodoItem a, TodoItem b) {
                return getPrioridadeValor(a.getPrioridade()) - getPrioridadeValor(b.getPrioridade());
            }
        });

        return ordenados;
    }

    private int getPrioridadeValor(String prioridade) {
        switch (prioridade.toLowerCase()) {
            case "alta": return 0;
            case "media": return 1;
            case "baixa": return 2;
            default: return 3;
        }
    }
}
