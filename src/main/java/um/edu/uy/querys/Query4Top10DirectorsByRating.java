package um.edu.uy.querys;

import um.edu.uy.entities.Crew;
import um.edu.uy.entities.Movie;
import um.edu.uy.entities.Ratings;
import um.edu.uy.tads.MyArrayList;
import um.edu.uy.tads.MyHashTableLineal;
import um.edu.uy.tads.MyHeapImpl;
import um.edu.uy.tads.NodeHash;
import um.edu.uy.tads.NodoHeap;

public class Query4Top10DirectorsByRating {

    private MyHashTableLineal<Integer, Movie> movies;

    public Query4Top10DirectorsByRating(MyHashTableLineal<Integer, Movie> movies) {
        this.movies = movies;
    }

    public void top10DirectorsByRating() {
        MyHashTableLineal<String, MyArrayList<Movie>> directorsMovies = new MyHashTableLineal<>(13);
        MyHashTableLineal<String, String> directorNames = new MyHashTableLineal<>(13);

        for (NodeHash<Integer, Movie> movieNode : movies) {
            Movie movie = movieNode.getValor();
            if (movie.getCrew() != null) {
                for (int i = 0; i < movie.getCrew().size(); i++) {
                    Crew crewMember = movie.getCrew().get(i);
                    if (crewMember != null && "Director".equals(crewMember.getJob())) {
                        try {
                            if (!directorsMovies.belongs(crewMember.getId())) {
                                directorsMovies.insert(crewMember.getId(), new MyArrayList<>());
                                directorNames.insert(crewMember.getId(), crewMember.getName());
                            }
                            directorsMovies.search(crewMember.getId()).add(movie);
                        } catch (Exception e) {
                            // Se ignora la excepcion para continuar con el proximo
                        }
                    }
                }
            }
        }

        MyHeapImpl<Double, String> topDirectors = new MyHeapImpl<>(10, false);

        for (NodeHash<String, MyArrayList<Movie>> directorNode : directorsMovies) {
            String directorId = directorNode.getClave();
            MyArrayList<Movie> directedMovies = directorNode.getValor();
            double totalRating = 0;
            int amountOfMovies = 0;

            for (int i = 0; i < directedMovies.size(); i++) {
                Movie movie = directedMovies.get(i);
                double avgRating = averageRating(movie);
                if(avgRating >0){
                    totalRating+=avgRating;
                    amountOfMovies++;
                }
            }

            if (amountOfMovies > 0) {
                double directorAverage = totalRating / amountOfMovies;
                if(topDirectors.obtenerTamano()<10){
                    topDirectors.insert(directorAverage,directorId);
                } else if(topDirectors.peek().getKey() < directorAverage){
                    topDirectors.remove();
                    topDirectors.insert(directorAverage,directorId);
                }
            }
        }

        System.out.println("Top 10 de los directores que mejor calificación tienen:");
        MyArrayList<NodoHeap<Double,String>> results = new MyArrayList();
        while (topDirectors.obtenerTamano() > 0){
            results.add(topDirectors.remove());
        }

        for(int i = results.size() - 1; i >= 0; i--){
            try{
                NodoHeap<Double,String> directorId = results.get(i);
                String directorName = directorNames.search(directorId.getData());
                int amountOfMovies = directorsMovies.search(directorId.getData()).size();
                double rating = directorId.getKey();
                System.out.println(directorName +", "+amountOfMovies+", "+rating);

            }catch (Exception e){
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