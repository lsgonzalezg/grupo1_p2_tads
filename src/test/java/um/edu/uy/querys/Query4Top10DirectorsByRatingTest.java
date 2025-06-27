package um.edu.uy.querys;

import org.junit.jupiter.api.Test;
import um.edu.uy.entities.Crew;
import um.edu.uy.entities.Movie;
import um.edu.uy.entities.Ratings;
import um.edu.uy.tads.MyArrayList;
import um.edu.uy.tads.MyHashTableLineal;

import static org.junit.jupiter.api.Assertions.*;

class Query4Top10DirectorsByRatingTest {

    @Test
    void top10DirectorsByRating() {
        MyHashTableLineal<Integer, Movie> moviesHash = new MyHashTableLineal<>(10);

        Movie movie1 = new Movie(null, null, 1, "es", 0L, "Pelicula A");
        Movie movie2 = new Movie(null, null, 2, "es", 0L, "Pelicula B");
        Movie movie3 = new Movie(null, null, 3, "en", 0L, "Pelicula C");

        Crew director1 = new Crew("dir1", "Director Uno", "Director");
        Crew director2 = new Crew("dir2", "Director Dos", "Director");

        MyArrayList<Crew> crewList1 = new MyArrayList<>();
        crewList1.add(director1);
        movie1.setCrew(crewList1);
        movie2.setCrew(crewList1);

        MyArrayList<Crew> crewList2 = new MyArrayList<>();
        crewList2.add(director2);
        movie3.setCrew(crewList2);

        MyArrayList<Ratings> ratings1 = new MyArrayList<>();
        for (int i = 0; i < 120; i++) {
            ratings1.add(new Ratings(1, 1, 4.0, null));
        }
        movie1.setRatings(ratings1);

        MyArrayList<Ratings> ratings2 = new MyArrayList<>();
        for (int i = 0; i < 150; i++) {
            ratings2.add(new Ratings(2, 2, 5.0, null));
        }
        movie2.setRatings(ratings2);

        MyArrayList<Ratings> ratings3 = new MyArrayList<>();
        for (int i = 0; i < 150; i++) {
            ratings3.add(new Ratings(3, 3, 3.0, null));
        }
        movie3.setRatings(ratings3);

        try {
            moviesHash.insert(1, movie1);
            moviesHash.insert(2, movie2);
            moviesHash.insert(3, movie3);
        } catch (Exception e) {
        }

        Query4Top10DirectorsByRating query = new Query4Top10DirectorsByRating(moviesHash);
        query.top10DirectorsByRating();
    }
}