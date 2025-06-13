package um.edu.uy.entidades;
import com.opencsv.CSVReader;
import um.edu.uy.TADs.MyArrayList;
import um.edu.uy.TADs.myHashTableAbiertaLinkedList;
import um.edu.uy.exceptions.ElementoYaExistenteException;
import java.io.FileReader;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MoviesUM {
    private myHashTableAbiertaLinkedList<Integer, Movie> movies;
    private MyArrayList<Genero> generos;
    private MyArrayList<Ratings> ratings;
    private myHashTableAbiertaLinkedList<Integer,Company> companies;
    private myHashTableAbiertaLinkedList<String,Country> countries;
    private myHashTableAbiertaLinkedList<String, Languaje> lenguajes;
    private myHashTableAbiertaLinkedList<Integer,Collection> collections;

    public MoviesUM() {
        this.movies = new myHashTableAbiertaLinkedList<>(5003);
        this.generos = new MyArrayList<>();
        this.ratings = new MyArrayList<>();
        this.companies = new myHashTableAbiertaLinkedList<>(307);
        this.countries = new myHashTableAbiertaLinkedList<>(307);
        this.lenguajes = new myHashTableAbiertaLinkedList<>(307);
        this.collections = new myHashTableAbiertaLinkedList<>(149);
    }

    public void cargarDatos() {
        cargarMovies();
        cargarCredits();
        cargarRatings();
    }

    public void cargarMovies() {
        try {
            FileReader filereader = new FileReader("src/main/resources/movies_metadata.csv");

            CSVReader csvReader = new CSVReader(filereader);
            String[] nextRecord;


            csvReader.readNext();

            while ((nextRecord = csvReader.readNext()) != null) {
                agregarMovie(nextRecord[0],// adult
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

    public void agregarMovie(String adult, String collection, String budget, String generos,
                             String homepage, String id, String imdb_id, String originalLenguaje,
                             String originalTitle, String overview, String productionCompanies,
                             String productionCountry, String releaseDate, String revenue, String runtime,
                             String spokenLenguajes, String status, String tagline, String title) {

        int intid;
        try {
            intid = conversorAInt(id);
        } catch (NumberFormatException e) {
            return;
        }

        int intrevenue = conversorAInt(revenue);
        Genero[] arrayGeneros = conversorStringGeneros(generos);
        Company[] arrayCompany = conversorStringCompany(productionCompanies);
        Country[] arrayCountry = conversorStringCountry(productionCountry);
        Languaje[] arrayLanguajes = conversorStringLnguajes(spokenLenguajes);
        Collection objectCollection = conversorStringCollection(collection);

        Movie nuevaMovie = new Movie(adult,
                                objectCollection,
                                budget,
                                arrayGeneros,
                                homepage,
                                intid,
                                imdb_id,
                                originalLenguaje,
                                originalTitle,
                                overview,
                                arrayCompany,
                                arrayCountry,
                                releaseDate,
                                intrevenue,
                                runtime,
                                arrayLanguajes,
                                status,
                                tagline,
                                title);

        try {
            movies.insertar(intid, nuevaMovie);

            if(nuevaMovie.getProductionCompanies() != null){
                for (Company company : nuevaMovie.getProductionCompanies()) {
                    if (company != null) {
                        company.addMovie(nuevaMovie);
                    }
                }
            }


            if (nuevaMovie.getCollection() != null) {
                Collection collecionDePelicula = nuevaMovie.getCollection();
                Integer idCollection = collecionDePelicula.getId();
                if(!collections.pertenece(idCollection)){
                    collections.insertar(idCollection, collecionDePelicula);
                }
                collecionDePelicula.agragarMovie(nuevaMovie);
            }
        }
        catch (ElementoYaExistenteException e) {}
    }

    private int conversorAInt(String numero) {
        try {
            return Integer.parseInt(numero);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private double conversorADouble(String numero) {
        try {
            return Double.parseDouble(numero);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    private Date conversorATimestamp(String timestamp) {
        try{
            long segundos = Long.parseLong(timestamp);
            return new Date(segundos * 1000);
        }catch (NumberFormatException e){
            return null;
        }
    }

    private Genero[] conversorStringGeneros(String stringGeneros) {
        if(stringGeneros ==null){
            return new Genero[0];
        }
        MyArrayList<Genero> generosList = new MyArrayList<>();
        Pattern pattern = Pattern.compile("\\{'id'\\s*:\\s*(\\d+),\\s*'name'\\s*:\\s*'([^']*)'\\}");
        Matcher matcher = pattern.matcher(stringGeneros);

        while (matcher.find()) {
            try {
                Integer id = Integer.parseInt(matcher.group(1));
                String nombre = matcher.group(2);
                Genero auxiliar = new Genero(id,nombre);

                if (generos.pertenece(auxiliar)) {
                    generosList.add(generos.get(id));
                } else {
                    generosList.add(auxiliar);
                    generos.add(auxiliar);
                }
            }
            catch (Exception e) {
            }
        }
        Genero[] resultado = new Genero[generosList.size()];
        for (int i = 0; i < generosList.size(); i++) {
            resultado[i] = generosList.get(i);
        }
        return resultado;
    }

    private Company[] conversorStringCompany(String stringCompanias) {
        if(stringCompanias ==null){
            return new Company[0];
        }
        MyArrayList<Company> companyList = new MyArrayList<>();

        Pattern pattern = Pattern.compile("\\{'name'\\s*:\\s*'([^']*)',\\s*'id'\\s*:\\s*(\\d+)\\}");
        Matcher matcher = pattern.matcher(stringCompanias);

        while (matcher.find()) {
            try {
                String nombre = matcher.group(1);
                Integer id = Integer.parseInt(matcher.group(2));

                if (companies.pertenece(id)) {
                    companyList.add(companies.buscar(id));
                } else {
                    Company c = new Company(id, nombre);
                    companyList.add(c);
                    companies.insertar(id, c);
                }
            }
            catch (Exception e) {
            }
        }
        Company[] resultado = new Company[companyList.size()];
        for (int i = 0; i < companyList.size(); i++) {
            resultado[i] = companyList.get(i);
        }

        return resultado;
    }

    private Country[] conversorStringCountry(String stringCountries){
        if(stringCountries ==null){
            return new Country[0];
        }
        MyArrayList<Country> countriesList = new MyArrayList<>();

        Pattern pattern = Pattern.compile("\\{'iso_3166_1'\\s*:\\s*'([A-Z]{2})'\\s*,\\s*'name'\\s*:\\s*'([^']+)'\\}");
        Matcher matcher = pattern.matcher(stringCountries);

        while (matcher.find()) {
            try {
                String id = matcher.group(1);
                String nombre = matcher.group(2);

                if (countries.pertenece(id)) {
                    countriesList.add(countries.buscar(id));
                } else {
                    Country c = new Country(id, nombre);
                    countriesList.add(c);
                    countries.insertar(id, c);
                }
            }
            catch (Exception e) {
            }
        }
        Country[] resultado = new Country[countriesList.size()];
        for (int i = 0; i < countriesList.size(); i++) {
            resultado[i] = countriesList.get(i);
        }

        return resultado;
    }

    private Languaje[] conversorStringLnguajes(String stringLenguajes){
        if(stringLenguajes ==null){
            return new Languaje[0];
        }
        MyArrayList<Languaje> lenguajesList = new MyArrayList<>();

        Pattern pattern = Pattern.compile("\\{'iso_639_1'\\s*:\\s*'([a-z]{2})'\\s*,\\s*'name'\\s*:\\s*'([^']*)'\\}");
        Matcher matcher = pattern.matcher(stringLenguajes);

        while (matcher.find()) {
            try {
                String id = matcher.group(1);
                String nombre = matcher.group(2);

                if (lenguajes.pertenece(id)) {
                    lenguajesList.add(lenguajes.buscar(id));
                } else {
                    Languaje l = new Languaje(id, nombre);
                    lenguajesList.add(l);
                    lenguajes.insertar(id, l);
                }
            }
            catch (Exception e) {
            }
        }
        Languaje[] resultado = new Languaje[lenguajesList.size()];
        for (int i = 0; i < lenguajesList.size(); i++) {
            resultado[i] = lenguajesList.get(i);
        }

        return resultado;

    }

    private Collection conversorStringCollection(String stringCollection){
        if (stringCollection == null) {
            return null;
        }

        Pattern pattern = Pattern.compile("\\{'id':\\s*(\\d+),\\s*'name':\\s*'([^']*)',\\s*'poster_path':\\s*'([^']*)',\\s*'backdrop_path':\\s*(None|'[^']*')\\}");
        Matcher matcher = pattern.matcher(stringCollection);

        if (matcher.find()) {
            try{
                int id = Integer.parseInt(matcher.group(1));

                if (collections.pertenece(id)) {
                    return collections.buscar(id);
                } else {
                    String name = matcher.group(2);
                    String posterPath = matcher.group(3).equals("None") ? null : matcher.group(3).replace("'", "");
                    String backdropPath = matcher.group(4).equals("None") ? null : matcher.group(4).replace("'", "");

                    Collection nuevaCollection = new Collection(id, name, posterPath, backdropPath);
                    return nuevaCollection;
                }
            } catch (Exception e) {
                return null;
            }
        }

        return null;
    }

    private void cargarCredits(){
        try {
            FileReader filereader = new FileReader("src/main/resources/credits.csv");

            CSVReader csvReader = new CSVReader(filereader);
            String[] nextRecord;

            csvReader.readNext();

            while ((nextRecord = csvReader.readNext()) != null) {
                int idmovie = conversorAInt(nextRecord[2]);
                Movie movieAAgregarCredits = movies.buscar(idmovie);
                MyArrayList<Cast> cast = conversorStringCast(nextRecord[0]);
                MyArrayList<Crew> crew = conversorStringCrew(nextRecord[1]);
                movieAAgregarCredits.setCast(cast);
                movieAAgregarCredits.setCrew(crew);
            }
        }
        catch (Exception e){}
    }

    private MyArrayList<Cast> conversorStringCast(String stringCast){
        if(stringCast ==null){
            return new MyArrayList<>();
        }

        MyArrayList<Cast> castList = new MyArrayList<>();
        Pattern pattern = Pattern.compile("\\{'cast_id':\\s*\\d+,\\s*'character':\\s*'[^']+',\\s*'credit_id':\\s*'[^']+',\\s*'gender':\\s*\\d+,\\s*'id':\\s*\\d+,\\s*'name':\\s*'[^']+',\\s*'order':\\s*\\d+,\\s*'profile_path':\\s*(?:'[^']+'|None)\\}");
        Matcher matcher = pattern.matcher(stringCast);

        while (matcher.find()) {
            try {

                Cast nuevoCast = new Cast(conversorAInt(matcher.group(0)),
                                                        matcher.group(1),
                                                        matcher.group(2),
                                                        conversorAInt(matcher.group(3)),
                                                        conversorAInt(matcher.group(4)),
                                                        matcher.group(5),
                                                        conversorAInt(matcher.group(6)),
                                                        matcher.group(7));
                castList.add(nuevoCast);
            }
            catch (Exception e) {
            }
        }
        return castList;
    }

    private MyArrayList<Crew> conversorStringCrew(String stringCrew){
        if(stringCrew ==null){
            return new MyArrayList<>();
        }

        MyArrayList<Crew> crewList = new MyArrayList<>();
        Pattern pattern = Pattern.compile("\\{'credit_id':\\s*'[^']+',\\s*'department':\\s*'[^']+',\\s*'gender':\\s*\\d+,\\s*'id':\\s*\\d+,\\s*'job':\\s*'[^']+',\\s*'name':\\s*'[^']+',\\s*'profile_path':\\s*(?:'[^']+'|None)\\}(?:,\\s*\\{'credit_id':\\s*'[^']+',\\s*'department':\\s*'[^']+',\\s*'gender':\\s*\\d+,\\s*'id':\\s*\\d+,\\s*'job':\\s*'[^']+',\\s*'name':\\s*'[^']+',\\s*'profile_path':\\s*(?:'[^']+'|None)\\})");
        Matcher matcher = pattern.matcher(stringCrew);

        while (matcher.find()) {
            try {

                Crew nuevoCrew = new Crew(conversorAInt(matcher.group(0)),
                        matcher.group(1),
                        conversorAInt(matcher.group(2)),
                        matcher.group(3),
                        matcher.group(4),
                        matcher.group(5),
                        matcher.group(6));
                crewList.add(nuevoCrew);
            }
            catch (Exception e) {
            }
        }
        return crewList;
    }

    private void cargarRatings(){
        try {
            FileReader filereader = new FileReader("src/main/resources/ratings_1mm.csv");

            CSVReader csvReader = new CSVReader(filereader);
            String[] nextRecord;


            csvReader.readNext();

            while ((nextRecord = csvReader.readNext()) != null) {
                agregarRating(nextRecord[0], // userID
                        nextRecord[1], // movieID
                        nextRecord[2], // puntaje
                        nextRecord[3]); // timestamp
            }

        }
        catch (Exception e) {}
    }

    private void agregarRating(String userID, String movieID, String puntaje, String date){

        int userIDint;
        int movieIDint;
        double puntajeDouble;
        Date timestampDate;

        try{
            userIDint = conversorAInt(userID);
            movieIDint = conversorAInt(movieID);
            puntajeDouble = conversorADouble(puntaje);
            timestampDate = conversorATimestamp(date);

        } catch (NumberFormatException e){
            return;
        }

        Ratings nuevoRating = new Ratings(userIDint, movieIDint, puntajeDouble, timestampDate);
        try{
            ratings.add(nuevoRating);
            if(movies.pertenece(movieIDint)){
                Movie pelicula = movies.buscar(movieIDint);
                pelicula.agregarRating(nuevoRating);
            }
        }
        catch (Exception e) {
        }
    }
}

