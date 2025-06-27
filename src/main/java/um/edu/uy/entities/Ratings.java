package um.edu.uy.entities;
import java.time.LocalDate;

public class Ratings {
    private int userID;
    private int movieID;
    private double score;
    private LocalDate date;

    public Ratings(int userID, int movieID, double score, LocalDate date) {
        this.userID = userID;
        this.movieID = movieID;
        this.score = score;
        this.date = date;
    }

    public double getScore(){
        return score;
    }

    public LocalDate getDate() {
        return date;
    }
}
