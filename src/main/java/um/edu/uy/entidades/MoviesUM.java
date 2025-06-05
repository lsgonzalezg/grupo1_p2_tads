package um.edu.uy.entidades;
import com.opencsv.CSVReader;
import java.io.FileReader;

public class MoviesUM {
    //MyHashTable movies;
    public void MoviesUM(){
        //this.movies = new MyHashTable<Integer,Movies>;
    }

    public void cargarDatos(){
        cargarMovies();
    }

    public void cargarMovies(){
        try {
            FileReader filereader = new FileReader("src/main/resources/movies_metadata.csv");

            CSVReader csvReader = new CSVReader(filereader);
            String[] nextRecord;

            while ((nextRecord = csvReader.readNext()) != null) {
                System.out.println(nextRecord[3] + nextRecord[18]);
                agregarMovie(nextRecord[0],nextRecord[1],nextRecord[2],nextRecord[3],nextRecord[4],nextRecord[5],nextRecord[6],nextRecord[7],nextRecord[8],nextRecord[9],nextRecord[10],nextRecord[11],nextRecord[12],nextRecord[13],nextRecord[14],nextRecord[15],nextRecord[16],nextRecord[17],nextRecord[18]);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void agregarMovie(String adult, String collection, String budget, String generes, String homepage, String id, String imdb_id, String originalLenguaje, String originalTittle, String overview, String productionCompanies, String productionCountry, String releaseDate, String revenue,String runtime, String spokenLenguajes, String status, String tagline, String title){



    }
}
