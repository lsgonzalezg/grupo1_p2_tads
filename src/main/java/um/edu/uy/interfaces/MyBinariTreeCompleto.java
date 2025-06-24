package um.edu.uy.interfaces;

public interface MyBinariTreeCompleto<K,T>{
    void insert(K key, T data);
    void remove(K key);
    String toString();

}
