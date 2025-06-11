package um.edu.uy.TADs;
import um.edu.uy.exceptions.ElementoYaExistenteException;

public class myHashTableAbiertaLinkedList<K extends Comparable,T> implements MyHashTable<K,T>{
    private MyLinkedList<NodoHash<K,T>>[] hashTable;
    int size;

    public myHashTableAbiertaLinkedList(int size) {
        this.hashTable = new MyLinkedList[size];
        this.size = size;
        for(int i =0;i<size;i++){
            hashTable[i] = new MyLinkedList<>();
        }
    }

    @Override
    public void insertar(K clave, T valor) throws ElementoYaExistenteException {

        if (pertenece(clave)) {
            throw new ElementoYaExistenteException("El elemento ya exsite");
        }

        int indice = myHashCode(clave);
        NodoHash<K,T> nuevoNodo = new NodoHash<>(clave,valor);
        hashTable[indice].add(nuevoNodo);
    }

    private int myHashCode(K clave){
        int indice = Math.abs(clave.hashCode())%size;
        return indice;
    }

    @Override
    public boolean pertenece(K clave) {
        int indice = myHashCode(clave);
        MyLinkedList<NodoHash<K,T>> listaABuscar = hashTable[indice];
        for (int i = 0;i<listaABuscar.obtenerLargo();i++){
            if(listaABuscar.get(i).getClave().equals(clave)){
                return true;
            }
        }
        return false;
    }

    @Override
    public void borrar(K clave) {
        if(pertenece(clave)){
            int indice = myHashCode(clave);
            MyLinkedList<NodoHash<K,T>> listaABuscar = hashTable[indice];
            int indiceABorrar = buscarIndice(clave,listaABuscar);
            listaABuscar.remove(indiceABorrar);
        }

    }

    private int buscarIndice(K clave,MyLinkedList<NodoHash<K,T>> listaABuscar){
        int indice = 0;
        for (int i = 0;i<listaABuscar.obtenerLargo();i++){
            if(listaABuscar.get(i).getClave().equals(clave)){
                return indice;
            }
            indice++;
        }
        return indice;
    }

    public T buscar(K clave) {
        int indice = myHashCode(clave);
        MyLinkedList<NodoHash<K,T>> listaABuscar = hashTable[indice];
        for (int i = 0; i < listaABuscar.obtenerLargo(); i++){
            NodoHash<K, T> nodoActual = listaABuscar.get(i);
            if(nodoActual.getClave().equals(clave)){
                return nodoActual.getValor();
            }
        }
        return null;
    }
}
