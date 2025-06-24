package um.edu.uy.interfaces;


import um.edu.uy.exceptions.EmptyTreeException;
import um.edu.uy.exceptions.NodeFull;
import um.edu.uy.tads.MyLinkedList;
import um.edu.uy.tads.NodeBinary;

public interface MyTree <K,T>{
    T find(K key);
    void insert (K key, T data, K parentKey) throws NodeFull;
    void delete (K key) throws EmptyTreeException;
    int size(NodeBinary<K,T> nodo);
    int countLeaf(NodeBinary<K,T> nodo);
    int countCompleteElements(NodeBinary<K,T> nodo);
    MyLinkedList<K> inOrder();
    MyLinkedList<K> preOrder();
    MyLinkedList<K> posOrder();
    MyLinkedList<K> porNivel();
    void loadPosfijaExpression(String posfija);
}
