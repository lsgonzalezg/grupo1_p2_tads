package um.edu.uy.tads;

public interface MyCola<T> {
    void enqueue (T elemto);
    T dequeue ();
    boolean isEmpty();
}
