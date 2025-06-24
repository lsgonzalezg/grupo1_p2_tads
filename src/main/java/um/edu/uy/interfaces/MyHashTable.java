package um.edu.uy.interfaces;
import um.edu.uy.exceptions.ElementAlreadyExistException;
import um.edu.uy.exceptions.ElementDosentExistException;


public interface MyHashTable<K extends Comparable<K>, V> {
    void insert(K clave, V valor) throws ElementAlreadyExistException;
    V search(K clave) throws ElementDosentExistException;
    boolean belongs(K clave);
    void borrar(K clave);
    int tamanio();
}