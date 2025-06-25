package um.edu.uy.entities;
import java.util.Date;

public class Ratings {
    private int userID;
    private int movieID;
    private double score;
    private Date date;

    public Ratings(int userID, int movieID, double score, Date date) {
        this.userID = userID;
        this.movieID = movieID;
        this.score = score;
        this.date = date;
    }

    public double getScore(){
        return score;
    }
}
