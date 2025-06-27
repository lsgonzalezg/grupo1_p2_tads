package um.edu.uy.querys;
import org.junit.jupiter.api.Test;
import um.edu.uy.entities.Collection;
import um.edu.uy.entities.Movie;
import um.edu.uy.tads.MyHashTableLineal;

class Query3Top5RevenuesByCollectionsTest {

    @Test
    void top5RevenuesByCollections() {
        MyHashTableLineal<Integer, Collection> collectionsHash = new MyHashTableLineal<>(10);
        Collection coleccionA = new Collection(1, "Coleccion A");
        Collection coleccionB = new Collection(2, "Coleccion B");
        Collection coleccionC = new Collection(3, "Coleccion C");

        coleccionA.addMovie(new Movie(coleccionA, null, 1, "es", 1000L, "Pelicula 1"));
        coleccionA.addMovie(new Movie(coleccionA, null, 2, "es", 2000L, "Pelicula 2"));
        coleccionB.addMovie(new Movie(coleccionB, null, 3, "en", 3000L, "Pelicula 3"));
        coleccionB.addMovie(new Movie(coleccionB, null, 4, "en", 4000L, "Pelicula 4"));
        coleccionC.addMovie(new Movie(coleccionC, null, 5, "pt", 500L, "Pelicula 5"));

        try {
            collectionsHash.insert(coleccionA.getId(), coleccionA);
            collectionsHash.insert(coleccionB.getId(), coleccionB);
            collectionsHash.insert(coleccionC.getId(), coleccionC);
        } catch (Exception e) {
        }

        Query3Top5RevenuesByCollections query = new Query3Top5RevenuesByCollections(collectionsHash);
        query.top5RevenuesByCollections();
    }
}