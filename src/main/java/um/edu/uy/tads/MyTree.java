package um.edu.uy.tads;


import um.edu.uy.exceptions.EmptyTreeException;
import um.edu.uy.exceptions.NodoLLeno;

public interface MyTree <K,T>{
    T find(K key);
    void insert (K key, T data, K parentKey) throws NodoLLeno;
    void delete (K key) throws EmptyTreeException;
    int size(NodoBinario<K,T> nodo);
    int countLeaf(NodoBinario<K,T> nodo);
    int countCompleteElements(NodoBinario<K,T> nodo);
    MyLinkedList<K> inOrder();
    MyLinkedList<K> preOrder();
    MyLinkedList<K> posOrder();
    MyLinkedList<K> porNivel();
    void loadPosfijaExpression(String posfija);
}
