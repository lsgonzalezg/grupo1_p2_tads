package um.edu.uy.entities;
import um.edu.uy.tads.MyArrayList;


public class User {
    private int id;
    private MyArrayList<Ratings> ratings;

    public User(int id) {
        this.id = id;
    }
}
