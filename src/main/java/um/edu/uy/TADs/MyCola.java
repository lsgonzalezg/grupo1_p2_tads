package um.edu.uy.TADs;

public interface MyCola<T> {
    void enqueue (T elemto);
    T dequeue ();
    boolean isEmpty();
}
