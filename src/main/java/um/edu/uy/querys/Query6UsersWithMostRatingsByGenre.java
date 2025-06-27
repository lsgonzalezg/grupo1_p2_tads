package um.edu.uy.querys;
import um.edu.uy.entities.User;
import um.edu.uy.tads.MyHashTableLineal;
import um.edu.uy.entities.Genre;
import um.edu.uy.tads.MyHeapImpl;
import um.edu.uy.tads.NodeHash;
import um.edu.uy.tads.NodoHeap;

public class Query6UsersWithMostRatingsByGenre {

    private  MyHashTableLineal<Integer, User> users;
    private  MyHashTableLineal<Integer, Genre> genres;

    public Query6UsersWithMostRatingsByGenre(MyHashTableLineal<Integer, User> users, MyHashTableLineal<Integer, Genre> genres) {
        this.users = users;
        this.genres = genres;
    }

    public void usersWithMostRatingsByGenre() {
        MyHashTableLineal<Integer, User> topUsersByGenre = new MyHashTableLineal<>(13);

        for (NodeHash<Integer, User> node : users) {
            User user = node.getValor();
            //Agarro el Hash de cada usuario que la clave es el idGenre
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

        MyHeapImpl<Integer, NodeHash<Integer, User>> heap = new MyHeapImpl<>(101, true);


        //Inserta los del Hash en el Heap
        for (NodeHash<Integer, User> nodo : topUsersByGenre) {
            int genreId = nodo.getClave();
            User user = nodo.getValor();
            int cantidad = user.getCantRatingsByGenre(genreId);
            heap.insert(cantidad, nodo);
        }

        System.out.println("Top 10 géneros con más calificaciones:");
        int i = 0;
        while (i < 10 && heap.obtenerTamano() > 0) {
            NodoHeap<Integer, NodeHash<Integer, User>> nodoHeap = heap.remove();
            NodeHash<Integer, User> nodo = nodoHeap.getData();
            int genreId = nodo.getClave();
            User user = nodo.getValor();
            int cantidad = nodoHeap.getKey(); // esta es la cantidad

            try {
                Genre genero = genres.search(genreId);
                System.out.println(user.getId() + "," + genero.getName() + "," + cantidad);
            } catch (Exception e) {}
            i++;
        }
    }
}

