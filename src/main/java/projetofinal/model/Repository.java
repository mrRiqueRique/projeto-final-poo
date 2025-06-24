package projetofinal.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe abstrata que representa um repositório genérico.
 * <p>
 * Esta classe fornece métodos básicos para gerenciar uma lista de itens,
 * como adicionar, remover e definir itens. Também inicializa um serviço
 * associado ao repositório.
 *
 * @param <T> O tipo de item que será armazenado no repositório.
 */
public abstract class Repository<T> {
    private List<T> items = new ArrayList<>();
    protected Service service;

    /**
     * Construtor protegido que inicializa o repositório e o serviço associado.
     */
    protected Repository() {
        this.service = new Service();
    }

    /**
     * Retorna a lista de itens armazenados no repositório.
     *
     * @return A lista de itens.
     */
    public List<T> getItems() {
        return items;
    }

    /**
     * Adiciona um item ao repositório.
     *
     * @param item O item a ser adicionado.
     */
    public void addItem(T item) {
        items.add(item);
    }

    /**
     * Remove um item do repositório.
     *
     * @param item O item a ser removido.
     */
    public void removeItem(T item) {
        items.remove(item);
    }

    /**
     * Define a lista de itens do repositório.
     *
     * @param items A nova lista de itens.
     */
    public void setItems(List<T> items) {
        this.items = items;
    }
}