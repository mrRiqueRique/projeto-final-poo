package projetofinal.model;

import java.util.ArrayList;
import java.util.List;

public abstract class Repository<T> {
    private List<T> items = new ArrayList<>();
    protected Service service;

    protected Repository() {
        this.service = new Service();
    }

    public List<T> getItems() {
        return items;
    }

    public void addItem(T item) {
        items.add(item);
    }

    public void removeItem(T item) {
        items.remove(item);
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}