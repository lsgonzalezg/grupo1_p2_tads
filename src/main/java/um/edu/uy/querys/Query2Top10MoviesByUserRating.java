package um.edu.uy.querys;

import um.edu.uy.tads.MyHashTableLineal;

import um.edu.uy.entities.Movie;
import um.edu.uy.entities.Ratings;
import um.edu.uy.tads.MyArrayList;
import um.edu.uy.tads.MyHeapImpl;
import um.edu.uy.tads.NodeHash;
import um.edu.uy.tads.NodoHeap;

public class Query2Top10MoviesByUserRating {
    private final MyHashTableLineal<Integer, Movie> movies;

    public Query2Top10MoviesByUserRating(MyHashTableLineal<Integer, Movie> movies) {
        this.movies = movies;
    }
    public void top10MoviesByUserRating() {
        MyHeapImpl<Double, Integer> moviesAndTheirRating = new MyHeapImpl<Double, Integer>(movies.tamanio(), true);
        for (NodeHash<Integer, Movie> movie : movies) {
            Double average = averageRating(movie.getValor());
            if (average > 0) {
                moviesAndTheirRating.insert(average, movie.getClave());
            }
        }
        System.out.println("Top 10 de las películas con mejor calificación promedio:");
        for (int top10 = 0; top10 < 10; top10++) {
            try {
                NodoHeap<Double, Integer> top = moviesAndTheirRating.remove();
                Movie movie = movies.search(top.getData());
                System.out.println(movie.getId() + ", " + movie.getTitle() + ", " + top.getKey());
            } catch (Exception e) {

            }
        }
    }

    private double averageRating(Movie movie) {
        double avg = 0;
        MyArrayList<Ratings> ratings = movie.getRatings();
        int size = movie.getRatings().size();
        if (size < 100) {
            return 0;
        }
        for (int ratingPlace = 0; ratingPlace < size; ratingPlace++) {
            avg += ratings.get(ratingPlace).getScore();
        }
        return (avg / size);
    }
}
