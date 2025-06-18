package projetofinal.filters;
import java.util.Comparator;
import projetofinal.model.TodoItem;

public class ComparatorPrioridade implements Comparator<TodoItem> {

    private int prioridadeToInt(String prioridade) {
        if (prioridade == null) return Integer.MAX_VALUE; 
        switch (prioridade.toLowerCase().trim()) {
            case "alta":
                return 1;
            case "média":
                return 2;
            case "baixa":
                return 3;
            default:
                return Integer.MAX_VALUE; 
        }
    }

    @Override
    public int compare(TodoItem t1, TodoItem t2) {
        int p1 = prioridadeToInt(t1.getPrioridade());
        int p2 = prioridadeToInt(t2.getPrioridade());

        return Integer.compare(p1, p2);
    }
}
