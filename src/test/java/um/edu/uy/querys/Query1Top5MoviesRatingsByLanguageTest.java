package um.edu.uy.querys;
import org.junit.jupiter.api.Test;
import um.edu.uy.entities.Movie;
import um.edu.uy.tads.MyHashTableLineal;


class Query1Top5MoviesRatingsByLanguageTest {

    @Test
    void top5MoviesRatingsByLanguage() {
        MyHashTableLineal<Integer, Movie> moviesHash = new MyHashTableLineal<>(10);
        Movie m1 = new Movie(null, null, 1, "es", 0L, "Que paso ayer?");
        Movie m2 = new Movie(null, null, 2, "es", 0L, "Harry Potter");
        Movie m3 = new Movie(null, null, 3, "en", 0L, "Football I");
        Movie m4 = new Movie(null, null, 4, "en", 0L, "Basketball I");
        Movie m5 = new Movie(null, null, 5, "pt", 0L, "Basketball II");
        m1.setRatingsCount(100);
        m2.setRatingsCount(300);
        m3.setRatingsCount(200);
        m4.setRatingsCount(150);
        m5.setRatingsCount(250);

        try {
            moviesHash.insert(m1.getId(), m1);
            moviesHash.insert(m2.getId(), m2);
            moviesHash.insert(m3.getId(), m3);
            moviesHash.insert(m4.getId(), m4);
            moviesHash.insert(m5.getId(), m5);
        } catch (Exception e) {
        }

        Query1Top5MoviesRatingsByLanguage query = new Query1Top5MoviesRatingsByLanguage(moviesHash);
        query.top5MoviesRatingsByLanguage();

    }
}