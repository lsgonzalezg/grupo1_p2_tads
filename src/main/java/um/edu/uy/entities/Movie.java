package um.edu.uy.entities;

import um.edu.uy.tads.MyArrayList;

public class Movie {
    private Collection collection;
    private Genre[] genres;
    private int id;
    private String originalLanguage;
    private long revenue;
    private String title;
    private MyArrayList<Ratings> ratings;
    private MyArrayList<Cast> cast;
    private MyArrayList<Crew> crew;
    private int ratingsCount = 0;

    public Movie(Collection collection, Genre[] genres, int id, String originalLanguage, long revenue, String title) {
        this.collection = collection;
        this.genres = genres;
        this.id = id;
        this.originalLanguage = originalLanguage;
        this.revenue = revenue;
        this.title = title;
        this.ratings = new MyArrayList<>();
    }

    public Collection getCollection() {
        return collection;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void addRating(Ratings rating) {
        ratings.add(rating);
        ratingsCount++;
    }

    public int getRatingsCount() {
        return ratingsCount;
    }

    public void setCrew(MyArrayList<Crew> crew) {
        this.crew = crew;
    }

    public void setCast(MyArrayList<Cast> cast) {
        this.cast = cast;
    }

    public MyArrayList<Ratings> getRatings(){
        return ratings;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    public long getRevenue() {
        return revenue;
    }

    public Genre[] getGenres() {
        return genres;
    }

    public MyArrayList<Cast> getCast() {
        return cast;
    }

    public MyArrayList<Crew> getCrew() {
        return crew;
    }
}
