package um.edu.uy.TADs;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class NodoHash<K extends Comparable,T> {
    private K clave;
    private T valor;

    public NodoHash(K clave, T valor) {
        this.clave = clave;
        this.valor = valor;
    }
}
