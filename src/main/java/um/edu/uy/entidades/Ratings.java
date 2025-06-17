package um.edu.uy.entidades;

import java.util.Date;

public class Ratings {
    private int userID;
    private int movieID;
    private double puntaje;
    private Date date;

    public Ratings(int userID, int movieID, double puntaje, Date date) {
        this.userID = userID;
        this.movieID = movieID;
        this.puntaje = puntaje;
        this.date = date;
    }

    public double getPuntaje(){
        return puntaje;
    }
}
