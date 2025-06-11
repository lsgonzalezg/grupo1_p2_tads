package um.edu.uy.entidades;

public class Movie {
    String adult;
    Collection collection;
    String budget;
    Genero[] genres;
    String homepage;
    int id;
    String imdb_id;
    String originalLanguaje;
    String originalTitle;
    String overview;
    Company[] productionCompanies;
    Country[] productionCountry;
    String releaseDate;//esto podria ser date
    long revenue;
    String runtime;
    Languaje[] spokenLanguajes;
    String status;
    String tagline;
    String title;

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
}
