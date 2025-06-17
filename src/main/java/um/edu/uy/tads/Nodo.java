package um.edu.uy.tads;

public class Nodo <T> {
    T elemento;
    Nodo proximo;
    int prioridad;


    public Nodo(T elemento) {
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

    public Nodo<T> getProximo() {
        return proximo;
    }

    public void setProximo(Nodo<T> proximo) {
        this.proximo = proximo;
    }
}
