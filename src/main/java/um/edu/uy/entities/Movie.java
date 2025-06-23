package um.edu.uy.entities;

import um.edu.uy.tads.MyArrayList;

public class Movie {
    private String adult;
    private Collection collection;
    private String budget;
    private Genre[] genres;
    private String homepage;
    private int id;
    private String imdb_id;
    private String originalLanguage;
    private String originalTitle;
    private String overview;
    private Company[] productionCompanies;
    private Country[] productionCountry;
    private String releaseDate;//esto podria ser date
    private long revenue;
    private String runtime;
    private Language[] spokenLanguages;
    private String status;
    private String tagline;
    private String title;
    private MyArrayList<Ratings> ratings;
    private MyArrayList<Cast> cast;
    private MyArrayList<Crew> crew;

    public Movie(String adult, Collection collection, String budget, Genre[] genres, String homepage, int id, String imdb_id, String originalLanguage, String originalTitle, String overview, Company[] productionCompanies, Country[] productionCountry, String releaseDate, long revenue, String runtime, Language[] spokenLanguages, String status, String tagline, String title) {
        this.adult = adult;
        this.collection = collection;
        this.budget = budget;
        this.genres = genres;
        this.homepage = homepage;
        this.id = id;
        this.imdb_id = imdb_id;
        this.originalLanguage = originalLanguage;
        this.originalTitle = originalTitle;
        this.overview = overview;
        this.productionCompanies = productionCompanies;
        this.productionCountry = productionCountry;
        this.releaseDate = releaseDate;
        this.revenue = revenue;
        this.runtime = runtime;
        this.spokenLanguages = spokenLanguages;
        this.status = status;
        this.tagline = tagline;
        this.title = title;
    }

    public Company[] getProductionCompanies() {
        return productionCompanies;
    }

    public Collection getCollection() {
        return collection;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void addRating(Ratings rating) {
        ratings.add(rating);
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

}
