package um.edu.uy.entidades;
import com.opencsv.CSVReader;
import um.edu.uy.tads.MyArrayList;
import um.edu.uy.tads.MyLinkedList;
import um.edu.uy.tads.NodoHash;
import um.edu.uy.tads.MyHashTableAbiertaLinkedList;
import um.edu.uy.exceptions.ElementoYaExistenteException;
import java.io.FileReader;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MoviesUM {
    private MyHashTableAbiertaLinkedList<Integer, Movie> movies;
    private MyArrayList<Genero> generos;
    private MyArrayList<Ratings> ratings;
    private MyHashTableAbiertaLinkedList<Integer,Company> companies;
    private MyHashTableAbiertaLinkedList<String,Country> countries;
    private MyArrayList<Languaje> lenguajes;
    private MyHashTableAbiertaLinkedList<Integer,Collection> collections;

    public MoviesUM() {
        this.movies = new MyHashTableAbiertaLinkedList<>(5003);
        this.generos = new MyArrayList<>();
        this.ratings = new MyArrayList<>();
        this.companies = new MyHashTableAbiertaLinkedList<>(307);
        this.countries = new MyHashTableAbiertaLinkedList<>(307);
        this.lenguajes = new MyArrayList<>();
        this.collections = new MyHashTableAbiertaLinkedList<>(149);
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
                Integer id = Integer.parseInt(matcher.group(1));
                String nombre = matcher.group(2);
                Languaje auxiliar = new Languaje(id,nombre);

                if (lenguajes.pertenece(auxiliar)) {
                    lenguajesList.add(lenguajes.get(id));
                } else {
                    lenguajesList.add(auxiliar);
                    lenguajes.add(auxiliar);
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
            if(movies.pertenece(movieIDint)){
                Movie pelicula = movies.buscar(movieIDint);
                pelicula.agregarRating(nuevoRating);
                ratings.add(nuevoRating);
            }
        }
        catch (Exception e) {
        }
    }

    public void top5PeliculasRatingsPorIdiomas() {
        MyLinkedList<Integer> clavesPeliculas = movies.claves();

        Movie[] ingles = new Movie[5];
        Movie[] espanol = new Movie[5];
        Movie[] italiano = new Movie[5];
        Movie[] portugues = new Movie[5];
        Movie[] frances = new Movie[5];

        int cantidadPeliculasIngles = 0;

        for (int i =0; i < clavesPeliculas.obtenerLargo(); i++) {
            Movie pelicula = movies.buscar(clavesPeliculas.get(i));
            String idioma = pelicula.getOriginalLanguaje();
            int cant_evaluaciones = pelicula.getRatings().size();

            if(idioma.equals("en")){
                if(cantidadPeliculasIngles < 5){
                    ingles[cantidadPeliculasIngles++] = pelicula;
                }
                else{
                    int indiceMinEvaluaciones = 0;
                    for (int j = 1; j < 5; j++){
                        if(ingles[j].getRatings().size() < ingles[indiceMinEvaluaciones].getRatings().size()){
                            indiceMinEvaluaciones = j;
                        }
                    }
                    if(cant_evaluaciones > ingles[indiceMinEvaluaciones].getRatings().size()){
                        ingles[indiceMinEvaluaciones] = pelicula;
                    }
                }
            }
        }
        System.out.println("Top 5 películas en inglés por cantidad de ratings:");
        for (int i = 0; i < ingles.length; i++) {
            if (ingles[i] != null) {
                System.out.println(ingles[i].getId() + ", " + ingles[i].getTitle() + ", " + ingles[i].getRatings().size() + ", " + ingles[i].getOriginalLanguaje());
            }
        }
    }

    public void top10PeliculasPorMediaDeusuario(){
        MyArrayList<NodoHash<Integer,Double>> listaMoviesRatings = new MyArrayList<>();
        for (int lugarHash = 0 ; lugarHash<movies.getSize();lugarHash++){
            if(!movies.estaLugarVacio(lugarHash)){
                //for(int lugarLinkedList = 0; lugarLinkedList< movies.getHashTable(lugarHash).obtenerLargo();lugarLinkedList++){
                 //   NodoHash<Integer,Double> peliculaConSuRating = new NodoHash<>(movies.getHashTable(lugarHash).get(lugarLinkedList).getClave(),ratingMedioMovie(movies.getHashTable(lugarHash).get(lugarLinkedList).getValor()));
                }
            }
        }
    }

    //private double ratingMedioMovie(Movie peliculaRatingAPromediar){
    //    double promedio = 0;
    //    MyArrayList<Ratings> ratingsDePelicula = peliculaRatingAPromediar.getRatings();
    //    if(ratingsDePelicula == null){
    //        return promedio;
    //    }
    //    for(int lugarRating = 0;lugarRating< ratingsDePelicula.size();lugarRating++){
    //        promedio = promedio + ratingsDePelicula.get(lugarRating).getPuntaje();
    //    }
    //    return promedio/ratingsDePelicula.size();
    //}


