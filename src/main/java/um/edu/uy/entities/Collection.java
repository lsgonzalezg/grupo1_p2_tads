package um.edu.uy.entities;

import um.edu.uy.tads.MyLinkedList;

public class Collection {
    private Integer id;
    private String name;
    private String poster_path;
    private String backdrop_path;
    private MyLinkedList<Movie> movies;
    private long totalRevenue;

    public Collection(Integer id, String name, String poster_path, String backdrop_path) {
        this.id = id;
        this.name = name;
        this.poster_path = poster_path;
        this.backdrop_path = backdrop_path;
        this.movies = new MyLinkedList<>();
    }

    public void addMovie(Movie movieToAdd){
        if(!movies.existeElemento(movieToAdd)){
            movies.add(movieToAdd);
            this.totalRevenue += movieToAdd.getRevenue();
        }
    }

    public long calculateTotalRevenue(){
        return totalRevenue;
    }

    public int getId(){
        return this.id;
    }

    public MyLinkedList<Movie> getMovies() {
        return movies;
    }

    public String getName() {
        return name;
    }

}
