package um.edu.uy.entidades;

public class Movie {
    String adult;
    String collection;
    String budget;
    Genres[] generes;
    String homepage;
    int id;
    String imdb_id;
    String originalLenguaje;
    String originalTittle;
    String overview;
    Company[] productionCompanies;
    Country[] productionCountry;
    String releaseDate;//esto podria ser date
    int revenue;
    String runtime;
    Lenguaje[] spokenLenguajes;
    String status;
    String tagline;
    String title;

    public Movie(String adult, String collection, String budget, Genres[] generes, String homepage, int id, String imdb_id, String originalLenguaje, String originalTittle, String overview, Company[] productionCompanies, Country[] productionCountry, String releaseDate, int revenue, String runtime, Lenguaje[] spokenLenguajes, String status, String tagline, String title) {
        this.adult = adult;
        this.collection = collection;
        this.budget = budget;
        this.generes = generes;
        this.homepage = homepage;
        this.id = id;
        this.imdb_id = imdb_id;
        this.originalLenguaje = originalLenguaje;
        this.originalTittle = originalTittle;
        this.overview = overview;
        this.productionCompanies = productionCompanies;
        this.productionCountry = productionCountry;
        this.releaseDate = releaseDate;
        this.revenue = revenue;
        this.runtime = runtime;
        this.spokenLenguajes = spokenLenguajes;
        this.status = status;
        this.tagline = tagline;
        this.title = title;
    }
}
