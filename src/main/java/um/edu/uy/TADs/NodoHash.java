package um.edu.uy.TADs;

import lombok.Getter;

public class NodoHash<K extends Comparable,T> {
    private K Clave;
    private T Valor;

    public NodoHash(K clave, T valor) {
        this.Clave = clave;
        this.Valor = valor;
    }

    public K getClave() {
        return Clave;
    }

    public T getValor() {
        return Valor;
    }
}
