package um.edu.uy.entidades;

import um.edu.uy.tads.MyLinkedList;

public class Collection {
    private Integer id;
    private String name;
    private String poster_path;
    private String backdrop_path;
    private MyLinkedList<Movie> movies;

    public Collection(Integer id, String name, String poster_path, String backdrop_path) {
        this.id = id;
        this.name = name;
        this.poster_path = poster_path;
        this.backdrop_path = backdrop_path;
        this.movies = new MyLinkedList<>();
    }

    public void agragarMovie(Movie movieAAgregar){
        if(!movies.existeElemento(movieAAgregar)){
            movies.add(movieAAgregar);
        }
    }

    public int getId(){
        return this.id;
    }
}
