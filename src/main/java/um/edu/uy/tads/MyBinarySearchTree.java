package um.edu.uy.tads;


public interface MyBinarySearchTree<K extends Comparable<K>, T>
{
    T find(K key);
    void insert (K key, T data);
    void delete (K key);
    int size(NodeBST<K,T> nodo);
    int countLeaf(NodeBST<K,T> nodo);
    int countCompleteElements(NodeBST<K,T> nodo);
    MyLinkedList<K> inOrder();
    MyLinkedList<K> preOrder();
    MyLinkedList<K> posOrder();
    MyLinkedList<K> porNivel();
}
