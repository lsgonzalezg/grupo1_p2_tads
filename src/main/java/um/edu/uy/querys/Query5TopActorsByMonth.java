package um.edu.uy.querys;
import um.edu.uy.entities.*;
import um.edu.uy.tads.MyArrayList;
import um.edu.uy.tads.MyHashTableLineal;
import um.edu.uy.tads.NodeHash;
import java.time.LocalDate;

public class Query5TopActorsByMonth {

    private MyHashTableLineal<Integer, Movie> movies;

    public Query5TopActorsByMonth(MyHashTableLineal<Integer, Movie> movies) {
        this.movies = movies;
    }

    public void TopActorByMonth() {
        MyHashTableLineal<Integer, MyHashTableLineal<String, Integer>> ratingsByActorAndMonth = new MyHashTableLineal<>(13);
        MyHashTableLineal<Integer, MyHashTableLineal<String, MyHashTableLineal<Integer, Boolean>>> moviesByActorAndMonth = new MyHashTableLineal<>(13);
        MyHashTableLineal<String, String> actorNames = new MyHashTableLineal<>(13);

        for (NodeHash<Integer, Movie> movieNode : movies) {
            Movie movie = movieNode.getValor();
            MyArrayList<Ratings> ratings = movie.getRatings();
            MyArrayList<Cast> cast = movie.getCast();


            for (int i = 0; i < ratings.size(); i++) {
                Ratings rating = ratings.get(i);
                LocalDate ratingDate = rating.getDate();

                int month = ratingDate.getMonthValue();

                for (int j = 0; j < cast.size(); j++) {
                    Cast actor = cast.get(j);
                    String actorId = actor.getId();

                    try {
                        if (!actorNames.belongs(actorId)) {
                            actorNames.insert(actorId, actor.getName());
                        }

                        MyHashTableLineal<String, Integer> monthlyRatingCounts;
                        if (!ratingsByActorAndMonth.belongs(month)) {
                            monthlyRatingCounts = new MyHashTableLineal<>(13);
                            ratingsByActorAndMonth.insert(month, monthlyRatingCounts);
                        } else {
                            monthlyRatingCounts = ratingsByActorAndMonth.search(month);
                        }
                        int currentCount;
                        if (monthlyRatingCounts.belongs(actorId)) {
                            currentCount = monthlyRatingCounts.search(actorId);
                        } else {
                            currentCount = 0;
                        }
                        monthlyRatingCounts.borrar(actorId);
                        monthlyRatingCounts.insert(actorId, currentCount + 1);

                        MyHashTableLineal<String, MyHashTableLineal<Integer, Boolean>> monthlyMovieSets;
                        if (!moviesByActorAndMonth.belongs(month)) {
                            monthlyMovieSets = new MyHashTableLineal<>(13);
                            moviesByActorAndMonth.insert(month, monthlyMovieSets);
                        } else {
                            monthlyMovieSets = moviesByActorAndMonth.search(month);
                        }
                        MyHashTableLineal<Integer, Boolean> actorMovieSet;
                        if (!monthlyMovieSets.belongs(actorId)) {
                            actorMovieSet = new MyHashTableLineal<>(13);
                            monthlyMovieSets.insert(actorId, actorMovieSet);
                        } else {
                            actorMovieSet = monthlyMovieSets.search(actorId);
                        }
                        if(!actorMovieSet.belongs(movie.getId())){
                            actorMovieSet.insert(movie.getId(), true);
                        }

                    } catch (Exception e) {
                    }
                }
            }
        }

        System.out.println("Actor con más calificaciones recibidas en cada mes del año:");
        for (int month = 1; month <= 12; month++) {
            String topActorName = "";
            int maxRatings = 0;
            String topActorId = null;

            try {
                if (ratingsByActorAndMonth.belongs(month)) {
                    MyHashTableLineal<String, Integer> monthlyCounts = ratingsByActorAndMonth.search(month);

                    for (NodeHash<String, Integer> actorCountNode : monthlyCounts) {
                        if (actorCountNode.getValor() > maxRatings) {
                            maxRatings = actorCountNode.getValor();
                            topActorId = actorCountNode.getClave();
                        }
                    }

                    if (topActorId != null) {
                        topActorName = actorNames.search(topActorId);
                    }
                }
            } catch (Exception e) {
            }

            String monthName = getMonthName(month);

                int movieCount = 0;
                try {
                    if (moviesByActorAndMonth.belongs(month)) {
                        MyHashTableLineal<String, MyHashTableLineal<Integer, Boolean>> monthlyMovieSets = moviesByActorAndMonth.search(month);
                        if (monthlyMovieSets.belongs(topActorId)) {
                            movieCount = monthlyMovieSets.search(topActorId).tamanio();
                        }
                    }
                } catch (Exception e) {
                }

                System.out.println(monthName+", "+ topActorName+", "+ movieCount+", "+ maxRatings);
            }
        }


    private String getMonthName(int monthNumber) {
        switch (monthNumber) {
            case 1: return "Enero";
            case 2: return "Febrero";
            case 3: return "Marzo";
            case 4: return "Abril";
            case 5: return "Mayo";
            case 6: return "Junio";
            case 7: return "Julio";
            case 8: return "Agosto";
            case 9: return "Septiembre";
            case 10: return "Octubre";
            case 11: return "Noviembre";
            case 12: return "Diciembre";
            default: return "Mes inválido";
        }
    }
}