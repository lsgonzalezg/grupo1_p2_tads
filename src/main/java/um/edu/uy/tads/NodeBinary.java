package um.edu.uy.tads;

public class NodeBinary<K,T> {
    public NodeBinary(K key, T data) {
        this.key = key;
        this.data = data;
    }

    private K key;
    private T data;
    private NodeBinary<K, T> leftChild;
    private NodeBinary<K, T> rightChild;


    public K getKey() {
        return key;
    }

    public T getData() {
        return data;
    }

    public NodeBinary<K, T> getLeftChild() {
        return leftChild;
    }

    public NodeBinary<K, T> getRightChild() {
        return rightChild;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setLeftChild(NodeBinary<K, T> leftChild) {
        this.leftChild = leftChild;
    }

    public void setRightChild(NodeBinary<K, T> rightChild) {
        this.rightChild = rightChild;
    }
}
