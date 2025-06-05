package um.edu.uy.TADs;
import um.edu.uy.exceptions.ElementoYaExistenteException;

public interface MyHashTable<K extends Comparable,T>{
    void insertar (K clave, T valor) throws ElementoYaExistenteException;
    boolean pertenece (K clave);
    void borrar (K clave);
}

