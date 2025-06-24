package um.edu.uy.interfaces;
import um.edu.uy.exceptions.ElementAlreadyExistException;

public interface MyHashTable<K extends Comparable,T>{
    void insert (K clave, T valor) throws ElementAlreadyExistException;
    boolean belongs (K clave);
    void borrar (K clave);
}

