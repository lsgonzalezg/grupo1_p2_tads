package um.edu.uy.tads;

public class Node<T> {
    T elemento;
    Node proximo;
    int prioridad;


    public Node(T elemento) {
        this.elemento = elemento;
        this.proximo = null;
        this.prioridad = 0;
    }

    public T getElemento() {
        return elemento;
    }

    public int getPrioridad() {
        return prioridad;
    }
    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    public void setElemento(T elemento) {
        this.elemento = elemento;
    }

    public Node<T> getProximo() {
        return proximo;
    }

    public void setProximo(Node<T> proximo) {
        this.proximo = proximo;
    }
}
