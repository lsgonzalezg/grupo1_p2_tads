package um.edu.uy.tads;

public class NodeBST <K extends Comparable<K>, T> {
    private K key;
    private T data;
    private NodeBST <K, T> leftChild;
    private NodeBST <K, T> rightChild;

    public NodeBST(K key, T data) {
        this.key = key;
        this.data = data;
    }

    public K getKey() {
        return key;
    }

    public T getData() {
        return data;
    }

    public NodeBST<K, T> getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(NodeBST<K, T> leftChild) {
        this.leftChild = leftChild;
    }

    public NodeBST<K, T> getRightChild() {
        return rightChild;
    }

    public void setRightChild(NodeBST<K, T> rightChild) {
        this.rightChild = rightChild;
    }
}