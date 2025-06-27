package um.edu.uy.entities;

import um.edu.uy.tads.MyLinkedList;

public class Collection {
    private Integer id;
    private String name;
    private MyLinkedList<Movie> movies;

    private long totalRevenue;

    public Collection(Integer id, String name) {
        this.id = id;
        this.name = name;
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

    public long getTotalRevenue() {
        return totalRevenue;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < movies.obtenerLargo(); i++) {
            sb.append(movies.get(i).getId());
            if (i < movies.obtenerLargo() - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }
}
