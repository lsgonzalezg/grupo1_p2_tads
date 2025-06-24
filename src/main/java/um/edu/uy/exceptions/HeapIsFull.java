package um.edu.uy.exceptions;

public class HeapIsFull extends RuntimeException {
    public HeapIsFull(String message) {
        super(message);
    }
}
