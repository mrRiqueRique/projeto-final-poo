package projetofinal.filters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import projetofinal.model.TodoItem;

public class FiltroPorLetra implements Filter<TodoItem>{
    
    private String letraInicial;

    public FiltroPorLetra(String letraInicial) {
        this.letraInicial = letraInicial.toLowerCase(); 
    }

    @Override
    public List<TodoItem> meetCriteria(List<TodoItem> todoItens) {
        List<TodoItem> TodoItensOrdenados  = new ArrayList<>();

        // Filtra itens cujo nome começa com a letra desejada (ignorando maiúsculas/minúsculas)
        for (TodoItem item : todoItens) {
            if (item.getNome().toLowerCase().startsWith(letraInicial)) {
                TodoItensOrdenados .add(item);
            }
        }

        // Ordena alfabeticamente, ignorando maiúsculas/minúsculas
        Collections.sort(TodoItensOrdenados , new Comparator<TodoItem>() {
            @Override
            public int compare(TodoItem a, TodoItem b) {
                return a.getNome().compareToIgnoreCase(b.getNome());
            }
        });

        return TodoItensOrdenados ;
    }
    
}
