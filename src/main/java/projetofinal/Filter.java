package projetofinal;

import java.util.List;

public interface Filter<T> {
    
    List<T> meetCriteria(List<T> elementos);
    
}