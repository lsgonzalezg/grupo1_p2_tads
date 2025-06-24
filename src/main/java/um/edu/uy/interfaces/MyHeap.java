package um.edu.uy.interfaces;


import um.edu.uy.tads.NodoHeap;

public interface MyHeap<K extends Comparable<K>,T>{
    void insert(K key,T data);
    NodoHeap<K,T> remove();
    int obtenerTamano();
}
