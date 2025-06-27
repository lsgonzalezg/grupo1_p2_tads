package um.edu.uy.entities;
import um.edu.uy.tads.MyArrayList;
import um.edu.uy.tads.MyHashTableLineal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Converters {

    public static int converterInt(String number) {
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static double converterDouble(String number) {
        try {
            return Double.parseDouble(number);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    public static LocalDate converterTimestamp(String timestamp) {
        try {
            long seconds = Long.parseLong(timestamp);
            return Instant.ofEpochSecond(seconds).atZone(ZoneId.systemDefault()).toLocalDate();
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static long converterLong(String number) {
        try {
            return Long.parseLong(number);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static Genre[] converterStringGeneros(String genreStr, MyHashTableLineal<Integer, Genre> tablaGenre) {
        if (genreStr == null || genreStr.length() <= 2) return null;

        Pattern pattern = Pattern.compile("\\{'id':\\s*(\\d+),\\s*'name':\\s*'([^']+)'\\}");
        Matcher matcher = pattern.matcher(genreStr);
        MyArrayList<Genre> genreOfMovie = new MyArrayList<>();

        while (matcher.find()) {
            int id = Integer.parseInt(matcher.group(1));
            String name = matcher.group(2);
            try {
                if (!tablaGenre.belongs(id)) {
                    tablaGenre.insert(id, new Genre(id, name));
                }
                genreOfMovie.add(tablaGenre.search(id));
            } catch (Exception e) {
            }
        }

        Genre[] resultado = new Genre[genreOfMovie.size()];
        for (int i = 0; i < genreOfMovie.size(); i++) {
            resultado[i] = genreOfMovie.get(i);
        }
        return resultado;
    }

    public static Collection converterStringCollection(String stringCollection, MyHashTableLineal<Integer, Collection> collections) {
        if (stringCollection == null) {
            return null;
        }

        Pattern pattern = Pattern.compile("\\{'id':\\s*(\\d+),\\s*'name':\\s*'([^']*)',\\s*'poster_path':\\s*'([^']*)',\\s*'backdrop_path':\\s*(None|'[^']*')\\}");
        Matcher matcher = pattern.matcher(stringCollection);

        if (matcher.find()) {
            try {
                int id = Integer.parseInt(matcher.group(1));

                if (collections.belongs(id)) {
                    return collections.search(id);
                } else {
                    String name = matcher.group(2);

                    Collection newCollection = new Collection(id, name);
                    collections.insert(id, newCollection);
                    return newCollection;
                }
            } catch (Exception e) {
                return null;
            }
        }

        return null;
    }

    public static MyArrayList<Cast> converterStringCast(String stringCast) {
        if (stringCast == null || stringCast.length() < 3) {
            return new MyArrayList<>();
        }

        MyArrayList<Cast> castList = new MyArrayList<>();
        Pattern pattern = Pattern.compile("\\{'cast_id':\\s*(\\d+),\\s*'character':\\s*'(.*?)',\\s*'credit_id':\\s*'(.*?)',\\s*'gender':\\s*(\\d+),\\s*'id':\\s*(\\d+),\\s*'name':\\s*'(.*?)',\\s*'order':\\s*(\\d+),\\s*'profile_path':\\s*(.*?)\\}");
        Matcher matcher = pattern.matcher(stringCast);

        while (matcher.find()) {
            try {
                String id = matcher.group(5);
                String name = matcher.group(6).replace("'", "''");
                Cast newCast = new Cast(id, name);
                castList.add(newCast);
            } catch (Exception e) {
            }
        }
        return castList;
    }

    public static MyArrayList<Crew> converterStringCrew(String stringCrew) {
        if (stringCrew == null || stringCrew.length() < 3) {
            return new MyArrayList<>();
        }

        MyArrayList<Crew> crewList = new MyArrayList<>();
        Pattern pattern = Pattern.compile("\\{'credit_id':\\s*'(.*?)',\\s*'department':\\s*'(.*?)',\\s*'gender':\\s*(\\d+),\\s*'id':\\s*(\\d+),\\s*'job':\\s*'(.*?)',\\s*'name':\\s*'(.*?)',\\s*'profile_path':\\s*(.*?)\\}");
        Matcher matcher = pattern.matcher(stringCrew);

        while (matcher.find()) {
            try {;
                String id = matcher.group(4);
                String job = matcher.group(5).replace("'", "''");
                String name = matcher.group(6).replace("'", "''");


                Crew newCrew = new Crew(id, job, name);
                crewList.add(newCrew);
            } catch (Exception e) {
            }
        }
        return crewList;
    }
}
