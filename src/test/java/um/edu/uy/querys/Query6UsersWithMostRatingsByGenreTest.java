package um.edu.uy.querys;

import org.junit.jupiter.api.Test;
import um.edu.uy.entities.Genre;
import um.edu.uy.entities.User;
import um.edu.uy.tads.MyHashTableLineal;

import static org.junit.jupiter.api.Assertions.*;

class Query6UsersWithMostRatingsByGenreTest {

    @Test
    void usersWithMostRatingsByGenre() {
        MyHashTableLineal<Integer, User> users = new MyHashTableLineal<>(10);
        MyHashTableLineal<Integer, Genre> genres = new MyHashTableLineal<>(10);

        Genre genre1 = new Genre(1, "Accion");
        Genre genre2 = new Genre(2, "Comedia");
        Genre genre3 = new Genre(3, "Drama");

        try {
            genres.insert(genre1.getId(), genre1);
            genres.insert(genre2.getId(), genre2);
            genres.insert(genre3.getId(), genre3);
        } catch (Exception e) {
        }

        User user1 = new User(100);
        User user2 = new User(101);
        User user3 = new User(102);

        try {
            user1.getRatingsByGenre().insert(1, 5);
            user1.getRatingsByGenre().insert(2, 3);
            user2.getRatingsByGenre().insert(2, 10);
            user2.getRatingsByGenre().insert(3, 2);
            user3.getRatingsByGenre().insert(1, 7);
        }catch (Exception e) {
        }

        try {
            users.insert(user1.getId(), user1);
            users.insert(user2.getId(), user2);
            users.insert(user3.getId(), user3);
        } catch (Exception e) {
        }

        Query6UsersWithMostRatingsByGenre query = new Query6UsersWithMostRatingsByGenre(users, genres);
        query.usersWithMostRatingsByGenre();
    }
}