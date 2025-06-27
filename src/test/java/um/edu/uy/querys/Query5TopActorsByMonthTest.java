package um.edu.uy.querys;

import org.junit.jupiter.api.Test;
import um.edu.uy.entities.Cast;
import um.edu.uy.entities.Movie;
import um.edu.uy.entities.Ratings;
import um.edu.uy.tads.MyArrayList;
import um.edu.uy.tads.MyHashTableLineal;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class Query5TopActorsByMonthTest {

    @Test
    void topActorByMonth() {
        MyHashTableLineal<Integer, Movie> moviesHash = new MyHashTableLineal<>(10);


        Cast actor1 = new Cast("a1", "Actor Uno");
        Cast actor2 = new Cast("a2", "Actor Dos");

        Movie movie1 = new Movie(null, null, 1, "es", 0L, "Movie Enero");
        MyArrayList<Cast> castList1 = new MyArrayList<>();
        castList1.add(actor1);
        castList1.add(actor2);
        movie1.setCast(castList1);
        MyArrayList<Ratings> ratings1 = new MyArrayList<>();
        for (int i = 0; i < 3; i++) {
            ratings1.add(new Ratings(100 + i, 1, 4.0, LocalDate.of(2023, 1, 10)));
        }
        for (int i = 0; i < 1; i++) {
            ratings1.add(new Ratings(200 + i, 1, 3.0, LocalDate.of(2023, 1, 15)));
        }
        movie1.setRatings(ratings1);

        Movie movie2 = new Movie(null, null, 2, "es", 0L, "Movie Febrero");
        MyArrayList<Cast> castList2 = new MyArrayList<>();
        castList2.add(actor1);
        movie2.setCast(castList2);
        MyArrayList<Ratings> ratings2 = new MyArrayList<>();

        for (int i = 0; i < 5; i++) {
            ratings2.add(new Ratings(300 + i, 2, 5.0, LocalDate.of(2023, 2, 5)));
        }
        movie2.setRatings(ratings2);

        try {
            moviesHash.insert(movie1.getId(), movie1);
            moviesHash.insert(movie2.getId(), movie2);
        } catch (Exception e) {
        }

        Query5TopActorsByMonth query = new Query5TopActorsByMonth(moviesHash);
        query.TopActorByMonth();
    }
}