package um.edu.uy.entities;
import com.opencsv.CSVReader;
import um.edu.uy.tads.MyArrayList;
import um.edu.uy.tads.MyLinkedList;
import um.edu.uy.tads.NodeHash;
import um.edu.uy.tads.MyHashTableAbiertaLinkedList;
import um.edu.uy.exceptions.ElementAlreadyExistException;
import java.io.FileReader;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MoviesUM {
    private MyHashTableAbiertaLinkedList<Integer, Movie> movies;
    private MyArrayList<Genre> genre;
    private MyArrayList<Ratings> ratings;
    private MyHashTableAbiertaLinkedList<Integer,Company> companies;
    private MyHashTableAbiertaLinkedList<String,Country> countries;
    private MyArrayList<Language> languages;
    private MyHashTableAbiertaLinkedList<Integer,Collection> collections;

    public MoviesUM() {
        this.movies = new MyHashTableAbiertaLinkedList<>(5003);
        this.genre = new MyArrayList<>();
        this.ratings = new MyArrayList<>();
        this.companies = new MyHashTableAbiertaLinkedList<>(307);
        this.countries = new MyHashTableAbiertaLinkedList<>(307);
        this.languages = new MyArrayList<>();
        this.collections = new MyHashTableAbiertaLinkedList<>(149);
    }

    public void loadData() {
        loadMovies();
        System.out.println(movies.search(862).getTitle());
        System.out.println(movies.search(55123).getTitle());
        loadCredits();
        loadRatings();
        System.out.println(movies.search(862).getRatings().get(0).getScore());
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

        }
        catch (Exception e) {}
    }

    public void addMovie(String adult, String collection, String budget, String genres,
                             String homepage, String id, String imdb_id, String originalLenguage,
                             String originalTitle, String overview, String productionCompanies,
                             String productionCountry, String releaseDate, String revenue, String runtime,
                             String spokenLenguages, String status, String tagline, String title) {

        int intid;
        try {
            intid = converterInt(id);
        } catch (NumberFormatException e) {
            return;
        }

        int intrevenue = converterInt(revenue);
        Genre[] arrayGenres = converterStringGeneros(genres);
        Company[] arrayCompany = converterStringCompany(productionCompanies);
        Country[] arrayCountry = converterStringCountry(productionCountry);
        Language[] arrayLanguages = converterStringLanguages(spokenLenguages);
        Collection objectCollection = converterStringCollection(collection);

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
                                intrevenue,
                                runtime,
                arrayLanguages,
                                status,
                                tagline,
                                title);

        try {
            movies.insert(intid, newMovie);

            if(newMovie.getProductionCompanies() != null){
                for (Company company : newMovie.getProductionCompanies()) {
                    if (company != null) {
                        company.addMovie(newMovie);
                    }
                }
            }


            if (newMovie.getCollection() != null) {
                Collection colletionOfMovie = newMovie.getCollection();
                Integer idCollection = colletionOfMovie.getId();
                if(!collections.belongs(idCollection)){
                    collections.insert(idCollection, colletionOfMovie);
                }
                colletionOfMovie.addMovie(newMovie);
            }
        }
        catch (ElementAlreadyExistException e) {}
    }

    private int converterInt(String number) {
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private double converterDouble(String number) {
        try {
            return Double.parseDouble(number);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    private Date converterTimestamp(String timestamp) {
        try{
            long seconds = Long.parseLong(timestamp);
            return new Date(seconds * 1000);
        }catch (NumberFormatException e){
            return null;
        }
    }

    private Genre[] converterStringGeneros(String stringGenres) {
        if(stringGenres ==null){
            return new Genre[0];
        }
        MyArrayList<Genre> generosList = new MyArrayList<>();
        Pattern pattern = Pattern.compile("\\{'id'\\s*:\\s*(\\d+),\\s*'name'\\s*:\\s*'([^']*)'\\}");
        Matcher matcher = pattern.matcher(stringGenres);

        while (matcher.find()) {
            try {
                Integer id = Integer.parseInt(matcher.group(1));
                String name = matcher.group(2);
                Genre aux = new Genre(id,name);

                if (genre.pertenece(aux)) {
                    generosList.add(genre.get(id));
                } else {
                    generosList.add(aux);
                    genre.add(aux);
                }
            }
            catch (Exception e) {
            }
        }
        Genre[] result = new Genre[generosList.size()];
        for (int i = 0; i < generosList.size(); i++) {
            result[i] = generosList.get(i);
        }
        return result;
    }

    private Company[] converterStringCompany(String stringCompanies) {
        if(stringCompanies ==null){
            return new Company[0];
        }
        MyArrayList<Company> companyList = new MyArrayList<>();

        Pattern pattern = Pattern.compile("\\{'name'\\s*:\\s*'([^']*)',\\s*'id'\\s*:\\s*(\\d+)\\}");
        Matcher matcher = pattern.matcher(stringCompanies);

        while (matcher.find()) {
            try {
                String name = matcher.group(1);
                Integer id = Integer.parseInt(matcher.group(2));

                if (companies.belongs(id)) {
                    companyList.add(companies.search(id));
                } else {
                    Company c = new Company(id, name);
                    companyList.add(c);
                    companies.insert(id, c);
                }
            }
            catch (Exception e) {
            }
        }
        Company[] result = new Company[companyList.size()];
        for (int i = 0; i < companyList.size(); i++) {
            result[i] = companyList.get(i);
        }

        return result;
    }

    private Country[] converterStringCountry(String stringCountries){
        if(stringCountries ==null){
            return new Country[0];
        }
        MyArrayList<Country> countriesList = new MyArrayList<>();

        Pattern pattern = Pattern.compile("\\{'iso_3166_1'\\s*:\\s*'([A-Z]{2})'\\s*,\\s*'name'\\s*:\\s*'([^']+)'\\}");
        Matcher matcher = pattern.matcher(stringCountries);

        while (matcher.find()) {
            try {
                String id = matcher.group(1);
                String name = matcher.group(2);

                if (countries.belongs(id)) {
                    countriesList.add(countries.search(id));
                } else {
                    Country c = new Country(id, name);
                    countriesList.add(c);
                    countries.insert(id, c);
                }
            }
            catch (Exception e) {
            }
        }
        Country[] result = new Country[countriesList.size()];
        for (int i = 0; i < countriesList.size(); i++) {
            result[i] = countriesList.get(i);
        }

        return result;
    }

    private Language[] converterStringLanguages(String stringLanguages){
        if(stringLanguages ==null){
            return new Language[0];
        }
        MyArrayList<Language> languagesList = new MyArrayList<>();

        Pattern pattern = Pattern.compile("\\{'iso_639_1'\\s*:\\s*'([a-z]{2})'\\s*,\\s*'name'\\s*:\\s*'([^']*)'\\}");
        Matcher matcher = pattern.matcher(stringLanguages);

        while (matcher.find()) {
            try {
                Integer id = Integer.parseInt(matcher.group(1));
                String name = matcher.group(2);
                Language aux = new Language(id,name);

                if (languages.pertenece(aux)) {
                    languagesList.add(languages.get(id));
                } else {
                    languagesList.add(aux);
                    languages.add(aux);
                }
            }
            catch (Exception e) {
            }
        }
        Language[] result = new Language[languagesList.size()];
        for (int i = 0; i < languagesList.size(); i++) {
            result[i] = languagesList.get(i);
        }

        return result;

    }

    private Collection converterStringCollection(String stringCollection){
        if (stringCollection == null) {
            return null;
        }

        Pattern pattern = Pattern.compile("\\{'id':\\s*(\\d+),\\s*'name':\\s*'([^']*)',\\s*'poster_path':\\s*'([^']*)',\\s*'backdrop_path':\\s*(None|'[^']*')\\}");
        Matcher matcher = pattern.matcher(stringCollection);

        if (matcher.find()) {
            try{
                int id = Integer.parseInt(matcher.group(1));

                if (collections.belongs(id)) {
                    return collections.search(id);
                } else {
                    String name = matcher.group(2);
                    String posterPath = matcher.group(3).equals("None") ? null : matcher.group(3).replace("'", "");
                    String backdropPath = matcher.group(4).equals("None") ? null : matcher.group(4).replace("'", "");

                    Collection newCollection = new Collection(id, name, posterPath, backdropPath);
                    return newCollection;
                }
            } catch (Exception e) {
                return null;
            }
        }

        return null;
    }

    private void loadCredits(){
        try {
            FileReader filereader = new FileReader("src/main/resources/credits.csv");

            CSVReader csvReader = new CSVReader(filereader);
            String[] nextRecord;

            csvReader.readNext();

            while ((nextRecord = csvReader.readNext()) != null) {
                int idmovie = converterInt(nextRecord[2]);
                Movie movieToAddCredits = movies.search(idmovie);
                MyArrayList<Cast> cast = converterStringCast(nextRecord[0]);
                MyArrayList<Crew> crew = converterStringCrew(nextRecord[1]);
                movieToAddCredits.setCast(cast);
                movieToAddCredits.setCrew(crew);
            }
        }
        catch (Exception e){}
    }

    private MyArrayList<Cast> converterStringCast(String stringCast){
        if(stringCast ==null){
            return new MyArrayList<>();
        }

        MyArrayList<Cast> castList = new MyArrayList<>();
        Pattern pattern = Pattern.compile("\\{'cast_id':\\s*\\d+,\\s*'character':\\s*'[^']+',\\s*'credit_id':\\s*'[^']+',\\s*'gender':\\s*\\d+,\\s*'id':\\s*\\d+,\\s*'name':\\s*'[^']+',\\s*'order':\\s*\\d+,\\s*'profile_path':\\s*(?:'[^']+'|None)\\}");
        Matcher matcher = pattern.matcher(stringCast);

        while (matcher.find()) {
            try {

                Cast newCast = new Cast(converterInt(matcher.group(0)),
                                                        matcher.group(1),
                                                        matcher.group(2),
                                                        converterInt(matcher.group(3)),
                                                        converterInt(matcher.group(4)),
                                                        matcher.group(5),
                                                        converterInt(matcher.group(6)),
                                                        matcher.group(7));
                castList.add(newCast);
            }
            catch (Exception e) {
            }
        }
        return castList;
    }

    private MyArrayList<Crew> converterStringCrew(String stringCrew){
        if(stringCrew ==null){
            return new MyArrayList<>();
        }

        MyArrayList<Crew> crewList = new MyArrayList<>();
        Pattern pattern = Pattern.compile("\\{'credit_id':\\s*'[^']+',\\s*'department':\\s*'[^']+',\\s*'gender':\\s*\\d+,\\s*'id':\\s*\\d+,\\s*'job':\\s*'[^']+',\\s*'name':\\s*'[^']+',\\s*'profile_path':\\s*(?:'[^']+'|None)\\}(?:,\\s*\\{'credit_id':\\s*'[^']+',\\s*'department':\\s*'[^']+',\\s*'gender':\\s*\\d+,\\s*'id':\\s*\\d+,\\s*'job':\\s*'[^']+',\\s*'name':\\s*'[^']+',\\s*'profile_path':\\s*(?:'[^']+'|None)\\})");
        Matcher matcher = pattern.matcher(stringCrew);

        while (matcher.find()) {
            try {

                Crew newCrew = new Crew(converterInt(matcher.group(0)),
                        matcher.group(1),
                        converterInt(matcher.group(2)),
                        matcher.group(3),
                        matcher.group(4),
                        matcher.group(5),
                        matcher.group(6));
                crewList.add(newCrew);
            }
            catch (Exception e) {
            }
        }
        return crewList;
    }

    private void loadRatings(){
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

        }
        catch (Exception e) {}
    }

    private void addRating(String userID, String movieID, String score, String date){

        int userIDint;
        int movieIDint;
        double scoreDouble;
        Date timestampDate;

        try{
            userIDint = converterInt(userID);
            movieIDint = converterInt(movieID);
            scoreDouble = converterDouble(score);
            timestampDate = converterTimestamp(date);

        } catch (NumberFormatException e){
            return;
        }

        Ratings newRating = new Ratings(userIDint, movieIDint, scoreDouble, timestampDate);
        try{
            if(movies.belongs(movieIDint)){
                Movie movie = movies.search(movieIDint);
                movie.addRating(newRating);
                ratings.add(newRating);
            }
        }
        catch (Exception e) {
        }
    }

    public void top5MoviesRatingsByLanguage() {
        MyLinkedList<Integer> keysPeliculas = movies.claves();
        Movie[] english = new Movie[5];

        int cant_EnglishMovies = 0;

        for (int i = 0; i < keysPeliculas.obtenerLargo(); i++) {
            Movie movie = movies.search(keysPeliculas.get(i));
            if(movie.getRatings()==null){
                continue;
            }
            String language = movie.getOriginalLanguage();
            int cant_ratings = movie.getRatings().size();

            if(language.equals("en")){
                if(cant_EnglishMovies < 5){
                    insertSortedByRatings(english, movie, ++cant_EnglishMovies);
                }
                else {
                    int worstRatingCount = english[4].getRatings().size();
                    if (cant_ratings > worstRatingCount) {
                        insertSortedByRatings(english, movie, 5);
                    }
                }
            }
        }
        System.out.println("Top 5 películas en inglés por cantidad de ratings:");
        for (int i = 0; i < english.length; i++) {
            if (english[i] != null) {
                System.out.println(english[i].getId() + ", " + english[i].getTitle() + ", " + english[i].getRatings().size() + ", " + english[i].getOriginalLanguage());
            }

        }
    }

    private void insertSortedByRatings(Movie[] array, Movie newMovie, int cant_Top) {
        int newMovieRating = newMovie.getRatings().size();
        int i = cant_Top - 1;

        while (i > 0 && array[i - 1] != null && array[i -1].getRatings().size() < newMovieRating) {
            array[i] = array[i - 1];
            i--;
        }
        array[i] = newMovie;
    }

    public void top5RevenuesPerCompanies() {
        Company[] topCompanies = new Company[5];
        int cant_Companies = 0;
        MyLinkedList<Integer> clavesCompanies = companies.claves();

        for (int i = 0; i < clavesCompanies.obtenerLargo(); i++) {
            Company company = companies.search(clavesCompanies.get(i));
            long revenueTotal = company.calculateTotalRevenue();

            if(cant_Companies < 5){
                insertSortedByRevenue(topCompanies,company,++cant_Companies);
            }else{
                long worstRevenue = topCompanies[4].calculateTotalRevenue();
                if(revenueTotal > worstRevenue){
                    insertSortedByRevenue(topCompanies, company, 5);
                }
            }
        }
        System.out.println("Top 5 de las colecciones que mas generaron:");
        for (int i = 0; i < topCompanies.length; i++) {
            if (topCompanies[i] != null) {
                System.out.println(topCompanies[i].getId() + ", " + topCompanies[i].getName() + ", " + topCompanies[i].getMovies().obtenerLargo() + ", " + topCompanies[i].calculateTotalRevenue());
            }

        }
    }

    private void insertSortedByRevenue(Company[] array, Company newCompany, int cant_Top) {
        long newCompanyRevenue = newCompany.calculateTotalRevenue();
        int i = cant_Top - 1;

        while (i > 0 && array[i - 1] != null && array[i -1].calculateTotalRevenue() < newCompanyRevenue) {
            array[i] = array[i - 1];
            i--;
        }
        array[i] = newCompany;
    }

    public void top10DirectorsByAverageRating() {
        MyLinkedList<Integer> keysPeliculas = movies.claves();
    }

    public void top10PeliculasPorMediaDeusuario(){
        MyArrayList<NodeHash<Integer,Double>> listaMoviesRatings = new MyArrayList<>();
        for (int lugarHash = 0 ; lugarHash<movies.getSize();lugarHash++){
            if(!movies.estaLugarVacio(lugarHash)){
                //for(int lugarLinkedList = 0; lugarLinkedList< movies.getHashTable(lugarHash).obtenerLargo();lugarLinkedList++){
                 //   NodoHash<Integer,Double> peliculaConSuRating = new NodoHash<>(movies.getHashTable(lugarHash).get(lugarLinkedList).getClave(),ratingMedioMovie(movies.getHashTable(lugarHash).get(lugarLinkedList).getValor()));
                }
            }
        }
    }



