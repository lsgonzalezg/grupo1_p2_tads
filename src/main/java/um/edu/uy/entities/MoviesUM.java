package um.edu.uy.entities;
import com.opencsv.CSVReader;
import um.edu.uy.exceptions.ElementDosentExistException;
import um.edu.uy.tads.*;
import um.edu.uy.exceptions.ElementAlreadyExistException;

import java.io.FileReader;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MoviesUM {
    private MyHashTableLineal<Integer, Movie> movies;
    private MyArrayList<Genre> genres;
    private MyArrayList<Ratings> ratings;
    private MyHashTableLineal<Integer, Company> companies;
    private MyHashTableLineal<String, Country> countries;
    private MyArrayList<Language> languages;
    private MyHashTableLineal<Integer, Collection> collections;

    public MoviesUM() {
        this.movies = new MyHashTableLineal<>(13);
        this.genres = new MyArrayList<>();
        this.ratings = new MyArrayList<>();
        this.companies = new MyHashTableLineal<>(13);
        this.countries = new MyHashTableLineal<>(13);
        this.languages = new MyArrayList<>();
        this.collections = new MyHashTableLineal<>(13);
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
                }catch (ElementDosentExistException e) {
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
                MyArrayList<Cast> cast = converterStringCast(nextRecord[0]);
                MyArrayList<Crew> crew = converterStringCrew(nextRecord[1]);
                movieToAddCredits.setCast(cast);
                movieToAddCredits.setCrew(crew);
            }
        } catch (Exception e) {
        }
    }

    private MyArrayList<Cast> converterStringCast(String stringCast) {
        if (stringCast == null) {
            return new MyArrayList<>();
        }

        MyArrayList<Cast> castList = new MyArrayList<>();
        Pattern pattern = Pattern.compile("\\{'cast_id':\\s*\\d+,\\s*'character':\\s*'[^']+',\\s*'credit_id':\\s*'[^']+',\\s*'gender':\\s*\\d+,\\s*'id':\\s*\\d+,\\s*'name':\\s*'[^']+',\\s*'order':\\s*\\d+,\\s*'profile_path':\\s*(?:'[^']+'|None)\\}");
        Matcher matcher = pattern.matcher(stringCast);

        while (matcher.find()) {
            try {

                Cast newCast = new Cast(Converters.converterInt(matcher.group(0)),
                        matcher.group(1),
                        matcher.group(2),
                        Converters.converterInt(matcher.group(3)),
                        Converters.converterInt(matcher.group(4)),
                        matcher.group(5),
                        Converters.converterInt(matcher.group(6)),
                        matcher.group(7));
                castList.add(newCast);
            } catch (Exception e) {
            }
        }
        return castList;
    }

    private MyArrayList<Crew> converterStringCrew(String stringCrew) {
        if (stringCrew == null) {
            return new MyArrayList<>();
        }

        MyArrayList<Crew> crewList = new MyArrayList<>();
        Pattern pattern = Pattern.compile("\\{'credit_id':\\s*'[^']+',\\s*'department':\\s*'[^']+',\\s*'gender':\\s*\\d+,\\s*'id':\\s*\\d+,\\s*'job':\\s*'[^']+',\\s*'name':\\s*'[^']+',\\s*'profile_path':\\s*(?:'[^']+'|None)\\}(?:,\\s*\\{'credit_id':\\s*'[^']+',\\s*'department':\\s*'[^']+',\\s*'gender':\\s*\\d+,\\s*'id':\\s*\\d+,\\s*'job':\\s*'[^']+',\\s*'name':\\s*'[^']+',\\s*'profile_path':\\s*(?:'[^']+'|None)\\})");
        Matcher matcher = pattern.matcher(stringCrew);

        while (matcher.find()) {
            try {

                Crew newCrew = new Crew(Converters.converterInt(matcher.group(0)),
                        matcher.group(1),
                        Converters.converterInt(matcher.group(2)),
                        matcher.group(3),
                        matcher.group(4),
                        matcher.group(5),
                        matcher.group(6));
                crewList.add(newCrew);
            } catch (Exception e) {
            }
        }
        return crewList;
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
            }
        } catch (Exception e) {
        }
    }

    public void top5MoviesRatingsByLanguage() {
        MyHeapImpl<Integer, Movie> englishTop = new MyHeapImpl<>(5, false);
        MyHeapImpl<Integer, Movie> spanishTop = new MyHeapImpl<>(5, false);
        MyHeapImpl<Integer, Movie> italianTop = new MyHeapImpl<>(5, false);
        MyHeapImpl<Integer, Movie> frenchTop = new MyHeapImpl<>(5, false);
        MyHeapImpl<Integer, Movie> portugueseTop = new MyHeapImpl<>(5, false);

        try {
            for (NodeHash<Integer, Movie> node : movies) {
                Movie movie = node.getValor();

                if (movie.getRatingsCount() == 0) {
                    continue;}
                int numRatings = movie.getRatingsCount();
                    if ("en".equals(movie.getOriginalLanguage())) {
                        if (englishTop.obtenerTamano() < 5) {
                            englishTop.insert(numRatings, movie);
                        } else {
                            int minRatings = englishTop.peek().getKey();
                            if (numRatings > minRatings) {
                                englishTop.remove();
                                englishTop.insert(numRatings, movie);
                            }
                        }
                    }
                    if ("es".equals(movie.getOriginalLanguage())) {
                        if (spanishTop.obtenerTamano() < 5) {
                            spanishTop.insert(numRatings, movie);
                        } else {
                            int minRatings = spanishTop.peek().getKey();
                            if (numRatings > minRatings) {
                                spanishTop.remove();
                                spanishTop.insert(numRatings, movie);
                            }
                        }
                    }
                    if ("it".equals(movie.getOriginalLanguage())) {
                        if (italianTop.obtenerTamano() < 5) {
                            italianTop.insert(numRatings, movie);
                        } else {
                            int minRatings = italianTop.peek().getKey();
                            if (numRatings > minRatings) {
                                italianTop.remove();
                                italianTop.insert(numRatings, movie);
                            }
                        }
                    }
                    if ("fr".equals(movie.getOriginalLanguage())) {
                        if (frenchTop.obtenerTamano() < 5) {
                            frenchTop.insert(numRatings, movie);
                        } else {
                            int minRatings = frenchTop.peek().getKey();
                            if (numRatings > minRatings) {
                                frenchTop.remove();
                                frenchTop.insert(numRatings, movie);
                            }
                        }
                    }
                    if ("pt".equals(movie.getOriginalLanguage())) {
                        if (portugueseTop.obtenerTamano() < 5) {
                            portugueseTop.insert(numRatings, movie);
                        } else {
                            int minRatings = portugueseTop.peek().getKey();
                            if (numRatings > minRatings) {
                                portugueseTop.remove();
                                portugueseTop.insert(numRatings, movie);
                            }
                        }
                    }
            }
        } catch (Exception e) {
        }
        System.out.println("Top de las peliculas que más calificación por idioma:");
        printTopByLanguage(englishTop);
        printTopByLanguage(spanishTop);
        printTopByLanguage(italianTop);
        printTopByLanguage(frenchTop);
        printTopByLanguage(portugueseTop);
    }

    private void printTopByLanguage(MyHeapImpl<Integer, Movie> heap) {
        //Invierto el resultado porque esta ordenado al reves.
        MyArrayList<Movie> result = new MyArrayList<>();
        while (heap.obtenerTamano() > 0) {
            try {
                result.add(heap.remove().getData());
            } catch (Exception e) {}
        }

        System.out.println("");
        for (int i = result.size() - 1; i >= 0; i--) {
            Movie movie = result.get(i);
            System.out.println(movie.getId() + ", " + movie.getTitle() + ", " + movie.getRatingsCount() + ", " + movie.getOriginalLanguage());
        }
    }

    public void top5RevenuesByCollections() {
        try{
            Collection[] topCollections = new Collection[5];
            int cant_Collections = 0;

            for (NodeHash<Integer, Collection> node : collections) {
                Collection collection = node.getValor();
                long revenueTotal = collection.calculateTotalRevenue();

                if(cant_Collections < 5){
                    insertSortedByRevenue(topCollections, collection, ++cant_Collections);
                }else{
                    long worstRevenue = topCollections[4].calculateTotalRevenue();
                    if(revenueTotal > worstRevenue){
                        insertSortedByRevenue(topCollections, collection, 5);
                    }
                }
            }
            System.out.println("Top 5 de las colecciones que mas generaron:");
            for (int i = 0; i < topCollections.length; i++) {
                if (topCollections[i] != null) {
                    System.out.println(topCollections[i].getId() + ", " + topCollections[i].getName() + ", " + topCollections[i].getMovies().obtenerLargo() + ", [" + topCollections[i].toString() + "] ," + topCollections[i].calculateTotalRevenue());
                }
            }
        } catch (Exception e) {
        }
    }

    private void insertSortedByRevenue(Collection[] array, Collection newCollection, int cant_Top) {
        long newCompanyRevenue = newCollection.calculateTotalRevenue();
        int i = cant_Top - 1;

        while (i > 0 && array[i - 1] != null && array[i -1].calculateTotalRevenue() < newCompanyRevenue) {
            array[i] = array[i - 1];
            i--;
        }
        array[i] = newCollection;
    }

    public void top10MoviesByUserRating() {
        MyHeapImpl<Double, Integer> moviesAndTheirRating = new MyHeapImpl<Double, Integer>(movies.tamanio(), true);
        for (NodeHash<Integer, Movie> movie:movies) {
            Double average = averageRating(movie.getValor());
            if (average >0){
                moviesAndTheirRating.insert(average,movie.getClave());
            }
        }
        System.out.println("Top 10 de las películas con mejor calificación promedio:");
        for (int top10 = 0; top10<10;top10++){
            try{
                NodoHeap<Double,Integer> top = moviesAndTheirRating.remove();
                Movie movie = movies.search(top.getData());
                System.out.println(movie.getId()+", " + movie.getTitle()+", " + top.getKey());
            }catch (Exception e){

            }
        }
    }

    private double averageRating(Movie movie){
        double avg = 0;
        MyArrayList<Ratings> ratings = movie.getRatings();
        int size = movie.getRatings().size();
        if(size<100){
            return 0;
        }
        for (int ratingPlace = 0; ratingPlace<size;ratingPlace++){
            avg+=ratings.get(ratingPlace).getScore();
        }
        return (avg/size);
    }
}

