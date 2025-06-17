package um.edu.uy.entidades;

import um.edu.uy.tads.MyLinkedList;

public class Company {
    private Integer id;
    private String name;
    private MyLinkedList<Movie> movies;

    public Company(Integer id, String name) {
        this.id = id;
        this.name = name;
        this.movies = new MyLinkedList<>();
    }

    public void addMovie(Movie movieAAgregar){
        if(!movies.existeElemento(movieAAgregar)){
            movies.add(movieAAgregar);
        }
    }
}
