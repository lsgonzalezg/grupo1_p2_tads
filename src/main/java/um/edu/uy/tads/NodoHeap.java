package um.edu.uy.tads;


public class NodoHeap<K extends Comparable<K>,T> implements Comparable<NodoHeap<K,T>>{
    private K key;
    private T data;
    private NodoHeap<K,T> leftChild;
    private NodoHeap<K,T> rightChild;
    private NodoHeap<K,T> parent;

    public NodoHeap(K key,T data) {
        this.key = key;
        this.data = data;
        this.leftChild = null;
        this.rightChild = null;
        this.parent = null;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public NodoHeap<K, T> getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(NodoHeap<K, T> leftChild) {
        this.leftChild = leftChild;
    }

    public NodoHeap<K, T> getRightChild() {
        return rightChild;
    }

    public void setRightChild(NodoHeap<K, T> rightChild) {
        this.rightChild = rightChild;
    }

    public NodoHeap<K, T> getParent() {
        return parent;
    }

    public void setParent(NodoHeap<K, T> parent) {
        this.parent = parent;
    }

    @Override
        public int compareTo(NodoHeap<K, T> nodoAComparar) {
            return this.key.compareTo(nodoAComparar.key);
        }
    }
