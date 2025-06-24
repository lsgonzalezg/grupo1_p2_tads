package um.edu.uy.entities;
import um.edu.uy.tads.MyHashTableLineal;


public class User {
    private int id;
    private MyHashTableLineal<Integer, Integer> ratingsByGenres;

    public User(int id) {
        this.id = id;
    }
}
