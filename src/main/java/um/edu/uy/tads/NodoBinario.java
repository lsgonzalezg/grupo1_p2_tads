package um.edu.uy.tads;

public class NodoBinario<K,T> {
    public NodoBinario(K key, T data) {
        this.key = key;
        this.data = data;
    }

    private K key;
    private T data;
    private NodoBinario<K, T> leftChild;
    private NodoBinario<K, T> rightChild;


    public K getKey() {
        return key;
    }

    public T getData() {
        return data;
    }

    public NodoBinario<K, T> getLeftChild() {
        return leftChild;
    }

    public NodoBinario<K, T> getRightChild() {
        return rightChild;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setLeftChild(NodoBinario<K, T> leftChild) {
        this.leftChild = leftChild;
    }

    public void setRightChild(NodoBinario<K, T> rightChild) {
        this.rightChild = rightChild;
    }
}
