package um.edu.uy.querys;
import um.edu.uy.entities.User;
import um.edu.uy.tads.MyHashTableLineal;
import um.edu.uy.entities.Genre;
import um.edu.uy.tads.NodeHash;

public class Query6UsersWithMostRatingsByGenre {

    private final MyHashTableLineal<Integer, User> users;
    private final MyHashTableLineal<Integer, Genre> genres;

    public Query6UsersWithMostRatingsByGenre(MyHashTableLineal<Integer, User> users, MyHashTableLineal<Integer, Genre> genres) {
        this.users = users;
        this.genres = genres;
    }

    public void usersWithMostRatingsByGenre() {
        MyHashTableLineal<Integer, User> topUsersByGenre = new MyHashTableLineal<>(17);

        for (NodeHash<Integer, User> node : users) {
            User user = node.getValor();
            MyHashTableLineal<Integer, Integer> ratingsPorGenero = user.getRatingsByGenre();

            for (NodeHash<Integer, Integer> nodeGenre : ratingsPorGenero) {
                int genreId = nodeGenre.getClave();
                int cantidad = nodeGenre.getValor();

                try {
                    if (!topUsersByGenre.belongs(genreId)) {
                        topUsersByGenre.insert(genreId, user);
                    } else {
                        User actual = topUsersByGenre.search(genreId);
                        int maxActual = actual.getCantRatingsByGenre(genreId);
                        if (cantidad > maxActual) {
                            topUsersByGenre.borrar(genreId);
                            topUsersByGenre.insert(genreId, user);
                        }
                    }
                } catch (Exception e) {
                }
            }
        }

        System.out.println("Usuario con más calificaciones por genero:");
        for (NodeHash<Integer, User> nodo : topUsersByGenre) {
            int genreId = nodo.getClave();
            User user = nodo.getValor();
            int cantidad = user.getCantRatingsByGenre(genreId);

            try {
                Genre genero = genres.search(genreId);
                System.out.println(user.getId() + "," + genero.getName() + "," + cantidad);
            } catch (Exception e) {
            }
        }
    }
}

