package um.edu.uy.querys;

import um.edu.uy.entities.Movie;
import um.edu.uy.tads.MyArrayList;
import um.edu.uy.tads.MyHashTableLineal;
import um.edu.uy.tads.MyHeapImpl;
import um.edu.uy.tads.NodeHash;

public class Query1Top5MoviesRatingsByLanguage {
    private MyHashTableLineal<Integer, Movie> movies;

    public Query1Top5MoviesRatingsByLanguage(MyHashTableLineal<Integer, Movie> movies) {
        this.movies = movies;
    }

    public void top5MoviesRatingsByLanguage() {
        //Creo los heap para guardar el top en orden con la clave de la cantidad de Ratings
        MyHeapImpl<Integer, Movie> englishTop = new MyHeapImpl<>(5, false);
        MyHeapImpl<Integer, Movie> spanishTop = new MyHeapImpl<>(5, false);
        MyHeapImpl<Integer, Movie> italianTop = new MyHeapImpl<>(5, false);
        MyHeapImpl<Integer, Movie> frenchTop = new MyHeapImpl<>(5, false);
        MyHeapImpl<Integer, Movie> portugueseTop = new MyHeapImpl<>(5, false);

        try {
            for (NodeHash<Integer, Movie> node : movies) {
                Movie movie = node.getValor();

                if (movie.getRatingsCount() == 0) {
                    continue;
                }
                int numRatings = movie.getRatingsCount();
                if ("en".equals(movie.getOriginalLanguage())) {
                    //Si todavia no estsa lleno, lo meto directamente
                    if (englishTop.obtenerTamano() < 5) {
                        englishTop.insert(numRatings, movie);
                    } else {
                        //Tomo el ultimo que va a ser el que tenga menos Ratings y lo comparo con el que voy a agregar
                        int minRatings = englishTop.peek().getKey();
                        if (numRatings > minRatings) {
                            englishTop.remove();
                            englishTop.insert(numRatings, movie);
                        }
                    }
                }
                if ("es".equals(movie.getOriginalLanguage())) {
                    if (spanishTop.obtenerTamano() < 5) {
                        spanishTop.insert(numRatings, movie);
                    } else {
                        int minRatings = spanishTop.peek().getKey();
                        if (numRatings > minRatings) {
                            spanishTop.remove();
                            spanishTop.insert(numRatings, movie);
                        }
                    }
                }
                if ("it".equals(movie.getOriginalLanguage())) {
                    if (italianTop.obtenerTamano() < 5) {
                        italianTop.insert(numRatings, movie);
                    } else {
                        int minRatings = italianTop.peek().getKey();
                        if (numRatings > minRatings) {
                            italianTop.remove();
                            italianTop.insert(numRatings, movie);
                        }
                    }
                }
                if ("fr".equals(movie.getOriginalLanguage())) {
                    if (frenchTop.obtenerTamano() < 5) {
                        frenchTop.insert(numRatings, movie);
                    } else {
                        int minRatings = frenchTop.peek().getKey();
                        if (numRatings > minRatings) {
                            frenchTop.remove();
                            frenchTop.insert(numRatings, movie);
                        }
                    }
                }
                if ("pt".equals(movie.getOriginalLanguage())) {
                    if (portugueseTop.obtenerTamano() < 5) {
                        portugueseTop.insert(numRatings, movie);
                    } else {
                        int minRatings = portugueseTop.peek().getKey();
                        if (numRatings > minRatings) {
                            portugueseTop.remove();
                            portugueseTop.insert(numRatings, movie);
                        }
                    }
                }
            }
        } catch (Exception e) {
        }
        System.out.println("Top de las peliculas que más calificación por idioma:");
        printTopByLanguage(englishTop);
        printTopByLanguage(frenchTop);
        printTopByLanguage(italianTop);
        printTopByLanguage(spanishTop);
        printTopByLanguage(portugueseTop);
    }

    private void printTopByLanguage(MyHeapImpl<Integer, Movie> heap) {
        //Invierto el resultado porque esta ordenado al reves.
        MyArrayList<Movie> result = new MyArrayList<>();
        while (heap.obtenerTamano() > 0) {
            try {
                result.add(heap.remove().getData());
            } catch (Exception e) {
            }
        }

        System.out.println("");
        for (int i = result.size() - 1; i >= 0; i--) {
            Movie movie = result.get(i);
            System.out.println(movie.getId() + ", " + movie.getTitle() + ", " + movie.getRatingsCount() + ", " + movie.getOriginalLanguage());
        }
    }
}
