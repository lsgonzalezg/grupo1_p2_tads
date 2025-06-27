package um.edu.uy.entities;
import um.edu.uy.tads.MyHashTableLineal;


public class User {
    private int id;
    private MyHashTableLineal<Integer, Integer> ratingsByGenre;

    public User(int id) {
        this.id = id;
        this.ratingsByGenre = new MyHashTableLineal<>(17);
    }

    public void addRatingByGenero(Movie movie) {
        if (movie.getGenres() == null) return;

        for (Genre genero : movie.getGenres()) {
            if (genero == null || genero.getId() == null) continue;

            int genreId = genero.getId();
            try {
                int actual = 0;
                if (ratingsByGenre.belongs(genreId)) {
                    actual = ratingsByGenre.search(genreId);
                    //Borro la vieja para insertar la nueva actualizada
                    ratingsByGenre.borrar(genreId);
                }
                ratingsByGenre.insert(genreId, actual + 1);
            } catch (Exception e) {
            }
        }
    }

    public int getCantRatingsByGenre(int genreId) {
        if (ratingsByGenre == null) return 0;
        try {
            return ratingsByGenre.search(genreId);
        } catch (Exception e) {
            return 0;
        }
    }

    public MyHashTableLineal<Integer, Integer> getRatingsByGenre() {
        return ratingsByGenre;
    }

    public int getId() {
        return id;
    }

}
