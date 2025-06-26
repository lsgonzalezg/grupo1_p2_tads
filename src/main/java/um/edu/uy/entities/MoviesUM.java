package um.edu.uy.entities;
import com.opencsv.CSVReader;
import um.edu.uy.exceptions.ElementDosentExistException;
import um.edu.uy.querys.Query2Top10MoviesByUserRating;
import um.edu.uy.querys.Query3Top5RevenuesByCollections;
import um.edu.uy.querys.Query6UsersWithMostRatingsByGenre;
import um.edu.uy.tads.*;
import um.edu.uy.exceptions.ElementAlreadyExistException;
import um.edu.uy.querys.Query1Top5MoviesRatingsByLanguage;
import java.io.FileReader;
import java.util.Date;

public class MoviesUM {
    private MyHashTableLineal<Integer, Movie> movies;
    private MyHashTableLineal<Integer, Genre> genres;
    private MyArrayList<Ratings> ratings;
    private MyHashTableLineal<Integer, Company> companies;
    private MyHashTableLineal<String, Country> countries;
    private MyArrayList<Language> languages;
    private MyHashTableLineal<Integer, Collection> collections;
    private MyHashTableLineal<Integer, User> users;

    public MoviesUM() {
        this.movies = new MyHashTableLineal<>(13);
        this.genres = new MyHashTableLineal<>(13);
        this.ratings = new MyArrayList<>();
        this.companies = new MyHashTableLineal<>(13);
        this.countries = new MyHashTableLineal<>(13);
        this.languages = new MyArrayList<>();
        this.collections = new MyHashTableLineal<>(13);
        this.users = new MyHashTableLineal<>(13);
    }

    public void loadData() {
        try {
            loadMovies();
            loadCredits();
            loadRatings();
        } catch (Exception e) {
        }
    }

    public void loadMovies() {
        try {
            FileReader filereader = new FileReader("src/main/resources/movies_metadata.csv");

            CSVReader csvReader = new CSVReader(filereader);
            String[] nextRecord;


            csvReader.readNext();

            while ((nextRecord = csvReader.readNext()) != null) {

                addMovie(nextRecord[0],// adult
                        nextRecord[1],// belongs_to_collection
                        nextRecord[2],// budget
                        nextRecord[3],// genres
                        nextRecord[4],// homepage
                        nextRecord[5],// id
                        nextRecord[6],// imdb_id
                        nextRecord[7],// original_language
                        nextRecord[8],// original_title
                        nextRecord[9],// overview
                        nextRecord[10],// production_companies
                        nextRecord[11],// production_countries
                        nextRecord[12],// release_date
                        nextRecord[13],// revenue
                        nextRecord[14],// runtime
                        nextRecord[15],// spoken_languages
                        nextRecord[16],// status
                        nextRecord[17],// tagline
                        nextRecord[18]// title
                );
            }

        } catch (Exception e) {
        }
    }

    public void addMovie(String adult, String collection, String budget, String genre,
                         String homepage, String id, String imdb_id, String originalLenguage,
                         String originalTitle, String overview, String productionCompanies,
                         String productionCountry, String releaseDate, String revenue, String runtime,
                         String spokenLenguages, String status, String tagline, String title) {

        int intid;
        try {
            intid = Converters.converterInt(id);
        } catch (NumberFormatException e) {
            return;
        }

        long longRevenue = Converters.converterLong(revenue);
        Genre[] arrayGenres = Converters.converterStringGeneros(genre, genres);
        Company[] arrayCompany = Converters.converterStringCompany(productionCompanies, companies);
        Country[] arrayCountry = Converters.converterStringCountry(productionCountry, countries);
        Language[] arrayLanguages = Converters.converterStringLanguages(spokenLenguages, languages);
        Collection objectCollection = Converters.converterStringCollection(collection, collections);

        Movie newMovie = new Movie(adult,
                objectCollection,
                budget,
                arrayGenres,
                homepage,
                intid,
                imdb_id,
                originalLenguage,
                originalTitle,
                overview,
                arrayCompany,
                arrayCountry,
                releaseDate,
                longRevenue,
                runtime,
                arrayLanguages,
                status,
                tagline,
                title);

        try {
            movies.insert(intid, newMovie);

            if (newMovie.getProductionCompanies() != null) {
                for (Company company : newMovie.getProductionCompanies()) {
                    if (company != null) {
                        company.addMovie(newMovie);
                    }
                }
            }

            if (newMovie.getCollection() != null) {
                Collection colletionOfMovie = newMovie.getCollection();
                Integer idCollection = colletionOfMovie.getId();
                if (!collections.belongs(idCollection)) {
                    collections.insert(idCollection, colletionOfMovie);
                }
                try {
                    colletionOfMovie = collections.search(idCollection);
                    colletionOfMovie.addMovie(newMovie);
                } catch (ElementDosentExistException e) {
                }
            }
        } catch (ElementAlreadyExistException e) {
        }
    }

    private void loadCredits() {
        try {
            FileReader filereader = new FileReader("src/main/resources/credits.csv");

            CSVReader csvReader = new CSVReader(filereader);
            String[] nextRecord;

            csvReader.readNext();

            while ((nextRecord = csvReader.readNext()) != null) {
                int idmovie = Converters.converterInt(nextRecord[2]);
                Movie movieToAddCredits = movies.search(idmovie);
                MyArrayList<Cast> cast = Converters.converterStringCast(nextRecord[0]);
                MyArrayList<Crew> crew = Converters.converterStringCrew(nextRecord[1]);
                movieToAddCredits.setCast(cast);
                movieToAddCredits.setCrew(crew);
            }
        } catch (Exception e) {
        }
    }

    private void loadRatings() {
        try {
            FileReader filereader = new FileReader("src/main/resources/ratings_1mm.csv");

            CSVReader csvReader = new CSVReader(filereader);
            String[] nextRecord;


            csvReader.readNext();

            while ((nextRecord = csvReader.readNext()) != null) {
                addRating(nextRecord[0], // userID
                        nextRecord[1], // movieID
                        nextRecord[2], // score
                        nextRecord[3]); // timestamp
            }

        } catch (Exception e) {
        }
    }

    private void addRating(String userID, String movieID, String score, String date) {

        int userIDint;
        int movieIDint;
        double scoreDouble;
        Date timestampDate;

        try {
            userIDint = Converters.converterInt(userID);
            movieIDint = Converters.converterInt(movieID);
            scoreDouble = Converters.converterDouble(score);
            timestampDate = Converters.converterTimestamp(date);
        } catch (NumberFormatException e) {
            return;
        }

        Ratings newRating = new Ratings(userIDint, movieIDint, scoreDouble, timestampDate);
        try {
            if (movies.belongs(movieIDint)) {
                Movie movie = movies.search(movieIDint);
                movie.addRating(newRating);
                ratings.add(newRating);
                if (!users.belongs(userIDint)) {
                    try {
                        users.insert(userIDint, new User(userIDint));
                    } catch (Exception e) {
                    }
                }
                try {
                    User user = users.search(userIDint);
                    user.addRatingByGenero(movie);
                } catch (Exception e) {
                }
            }
        }catch (Exception e) {
        }
    }

    public void ejecutarConsulta1() {
        Query1Top5MoviesRatingsByLanguage consulta1 = new Query1Top5MoviesRatingsByLanguage(movies);
        consulta1.top5MoviesRatingsByLanguage();
    }

    public void ejecutarConsulta2() {
        Query2Top10MoviesByUserRating consulta2 = new Query2Top10MoviesByUserRating(movies);
        consulta2.top10MoviesByUserRating();
    }

    public void ejecutarConsulta3() {
        Query3Top5RevenuesByCollections consulta3 = new Query3Top5RevenuesByCollections(collections);
        consulta3.top5RevenuesByCollections();
    }

    public void ejecutarConsulta4() {
    }

    public void ejecutarConsulta5() {
    }

    public void ejecutarConsulta6() {
        Query6UsersWithMostRatingsByGenre consulta6 = new Query6UsersWithMostRatingsByGenre(users, genres);
        consulta6.usersWithMostRatingsByGenre();
    }
}

