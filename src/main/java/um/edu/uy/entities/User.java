package um.edu.uy.entities;
import um.edu.uy.tads.MyArrayList;

public class User {
    private int id;
    private MyArrayList<Ratings> ratings;

    public User(int id) {
        this.id = id;
    }

    public void addRating(Ratings rating) {
        ratings.add(rating);
    }

    public int cantRatings() {
        return ratings.size();
    }

    public MyArrayList<Ratings> getRatings() {
        return ratings;
    }

}
