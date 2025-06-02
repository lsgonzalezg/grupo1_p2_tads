package um.edu.uy.entities;
import java.io.FileReader;
import com.opencsv.CSVReader;

public class CargadorDeDatos {

    public CargadorDeDatos() {

    }

    public void cargarMovies(){
        try {
            FileReader filereader = new FileReader("src/main/resources/movies_metadata.csv");

            // create csvReader object passing
            // file reader as a parameter
            CSVReader csvReader = new CSVReader(filereader);
            String[] nextRecord;

            // we are going to read data line by line
            while ((nextRecord = csvReader.readNext()) != null) {
                for (String cell : nextRecord) {
                    System.out.print(cell + "\t");
                }
                System.out.println();
                System.out.println(nextRecord[0] + nextRecord[1]);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
