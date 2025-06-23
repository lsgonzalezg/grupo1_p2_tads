package um.edu.uy.tads;


public class NodeHash<K extends Comparable,T> {
    private K Clave;
    private T Valor;

    public NodeHash(K clave, T valor) {
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
