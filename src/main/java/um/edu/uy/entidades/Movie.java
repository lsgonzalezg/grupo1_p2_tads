package um.edu.uy.entidades;

import um.edu.uy.TADs.MyArrayList;

public class Movie {
    private String adult;
    private Collection collection;
    private String budget;
    private Genero[] genres;
    private String homepage;
    private int id;
    private String imdb_id;
    private String originalLanguaje;
    private String originalTitle;
    private String overview;
    private Company[] productionCompanies;
    private Country[] productionCountry;
    private String releaseDate;//esto podria ser date
    private long revenue;
    private String runtime;
    private Languaje[] spokenLanguajes;
    private String status;
    private String tagline;
    private String title;
    private MyArrayList<Ratings> ratings;
    public Movie(String adult, Collection collection, String budget, Genero[] genres, String homepage, int id, String imdb_id, String originalLanguaje, String originalTitle, String overview, Company[] productionCompanies, Country[] productionCountry, String releaseDate, long revenue, String runtime, Languaje[] spokenLanguajes, String status, String tagline, String title) {
        this.adult = adult;
        this.collection = collection;
        this.budget = budget;
        this.genres = genres;
        this.homepage = homepage;
        this.id = id;
        this.imdb_id = imdb_id;
        this.originalLanguaje = originalLanguaje;
        this.originalTitle = originalTitle;
        this.overview = overview;
        this.productionCompanies = productionCompanies;
        this.productionCountry = productionCountry;
        this.releaseDate = releaseDate;
        this.revenue = revenue;
        this.runtime = runtime;
        this.spokenLanguajes = spokenLanguajes;
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

    public void agregarRating(Ratings rating) {
        ratings.add(rating);
    }
}
