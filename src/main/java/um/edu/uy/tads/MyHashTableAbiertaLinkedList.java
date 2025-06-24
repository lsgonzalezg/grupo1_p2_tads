package um.edu.uy.tads;
import um.edu.uy.exceptions.ElementAlreadyExistException;
import um.edu.uy.interfaces.MyHashTable;

public class MyHashTableAbiertaLinkedList<K extends Comparable,T> implements MyHashTable<K,T> {
    private MyLinkedList<NodeHash<K,T>>[] hashTable;
    private int size;

    public MyHashTableAbiertaLinkedList(int size) {
        this.hashTable = new MyLinkedList[size];
        this.size = size;
        for(int i =0;i<size;i++){
            hashTable[i] = new MyLinkedList<>();
        }
    }

    public int getSize() {
        return size;
    }
    public MyLinkedList<NodeHash<K,T>> getHashTable(int lugar){
        return hashTable[lugar];
    }

    @Override
    public void insert(K clave, T valor) throws ElementAlreadyExistException {

        if (belongs(clave)) {
            throw new ElementAlreadyExistException("El elemento ya exsite");
        }

        int indice = myHashCode(clave);
        NodeHash<K,T> nuevoNodo = new NodeHash<>(clave,valor);
        hashTable[indice].add(nuevoNodo);
    }

    private int myHashCode(K clave){
        int indice = Math.abs(clave.hashCode())%size;
        return indice;
    }

    @Override
    public boolean belongs(K clave) {
        int indice = myHashCode(clave);
        MyLinkedList<NodeHash<K,T>> listaABuscar = hashTable[indice];
        for (int i = 0;i<listaABuscar.obtenerLargo();i++){
            if(listaABuscar.get(i).getClave().equals(clave)){
                return true;
            }
        }
        return false;
    }

    @Override
    public void borrar(K clave) {
        if(belongs(clave)){
            int indice = myHashCode(clave);
            MyLinkedList<NodeHash<K,T>> listaABuscar = hashTable[indice];
            int indiceABorrar = buscarIndice(clave,listaABuscar);
            listaABuscar.remove(indiceABorrar);
        }

    }

    private int buscarIndice(K clave,MyLinkedList<NodeHash<K,T>> listaABuscar){
        int indice = 0;
        for (int i = 0;i<listaABuscar.obtenerLargo();i++){
            if(listaABuscar.get(i).getClave().equals(clave)){
                return indice;
            }
            indice++;
        }
        return indice;
    }

    public T search(K clave) {
        int indice = myHashCode(clave);
        MyLinkedList<NodeHash<K,T>> listaABuscar = hashTable[indice];
        for (int i = 0; i < listaABuscar.obtenerLargo(); i++){
            NodeHash<K, T> nodoActual = listaABuscar.get(i);
            if(nodoActual.getClave().equals(clave)){
                return nodoActual.getValor();
            }
        }
        return null;
    }

    public boolean estaLugarVacio(int lugar){
        return hashTable[lugar].isEmpty();
    }

    public MyLinkedList<K> claves() {
        MyLinkedList<K> listaClaves = new MyLinkedList<>();

        for (int i = 0; i < size; i++) {
            MyLinkedList<NodeHash<K, T>> lista = hashTable[i];

            for (int j = 0; j < lista.obtenerLargo(); j++) {
                K clave = lista.get(j).getClave();
                listaClaves.add(clave);
            }
        }
        return listaClaves;
    }
}
