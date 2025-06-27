package um.edu.uy.querys;
import org.junit.jupiter.api.Test;
import um.edu.uy.entities.Movie;
import um.edu.uy.tads.MyHashTableLineal;

class Query2Top10MoviesByUserRatingTest {

    @Test
    void top10MoviesByUserRating() {
        MyHashTableLineal<Integer, Movie> moviesHash = new MyHashTableLineal<>(10);
        Movie m1 = new Movie(null, null, 1, "es", 0L, "Que paso ayer?");
        Movie m2 = new Movie(null, null, 2, "es", 0L, "Harry Potter");
        Movie m3 = new Movie(null, null, 3, "en", 0L, "Football I");
        Movie m4 = new Movie(null, null, 4, "en", 0L, "Basketball I");
        Movie m5 = new Movie(null, null, 5, "pt", 0L, "Basketball II");
        m1.setAverageRating(3.5);
        m2.setAverageRating(4.8);
        m3.setAverageRating(4.2);
        m4.setAverageRating(4.0);
        m5.setAverageRating(4.5);

        try {
            moviesHash.insert(m1.getId(), m1);
            moviesHash.insert(m2.getId(), m2);
            moviesHash.insert(m3.getId(), m3);
            moviesHash.insert(m4.getId(), m4);
            moviesHash.insert(m5.getId(), m5);
        } catch (Exception e) {
        }

        Query2Top10MoviesByUserRating query = new Query2Top10MoviesByUserRating(moviesHash);
        query.top10MoviesByUserRating();
    }
}