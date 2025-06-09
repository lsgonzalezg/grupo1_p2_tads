package um.edu.uy.entidades;

public class Company {
    private String id;
    private String name;
    private Movie[] movies;

    public Company(String id, String name, Movie[] movies) {
        this.id = id;
        this.name = name;
        this.movies = movies;
    }
}
