package um.edu.uy.entidades;

import java.util.Date;

public class Ratings {
    private Usuario usuario;
    private Movie movie;
    private double puntaje;
    private Date date;

    public Ratings(Usuario usuario, Movie movie, int puntaje, Date date) {
        this.usuario = usuario;
        this.movie = movie;
        this.puntaje = puntaje;
        this.date = date;
    }
}
