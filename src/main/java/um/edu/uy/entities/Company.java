package um.edu.uy.entities;

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

    public void addMovie(Movie movieToAdd){
        if(!movies.existeElemento(movieToAdd)){
            movies.add(movieToAdd);
        }
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public MyLinkedList<Movie> getMovies() {
        return movies;
    }


    public long calculateTotalRevenue(){
        long totalRevenue = 0;
        for(int i=0; i<movies.obtenerLargo(); i++){
            Movie movie = movies.get(i);
            if (movie != null) {
                totalRevenue += movie.getRevenue();
            }
        }
        return totalRevenue;
    }
}
