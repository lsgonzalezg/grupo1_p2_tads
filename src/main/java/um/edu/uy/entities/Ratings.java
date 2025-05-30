package um.edu.uy.entities;
import java.io.FileReader;
import com.opencsv.CSVReader;

public class Ratings {

    public Ratings() {
    }

    public void cargarDatos(){
        try {
            // Create an object of filereader
            // class with CSV file as a parameter.
            FileReader filereader = new FileReader("src/main/resources/ratings_1mm.csv");

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
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
