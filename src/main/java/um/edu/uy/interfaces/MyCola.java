package um.edu.uy.interfaces;

public interface MyCola<T> {
    void enqueue (T elemto);
    T dequeue ();
    boolean isEmpty();
}
