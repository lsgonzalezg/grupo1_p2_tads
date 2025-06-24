package um.edu.uy.entities;
import um.edu.uy.tads.MyArrayList;
import um.edu.uy.tads.MyHashTableLineal;
import java.util.Date;
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

    public static Date converterTimestamp(String timestamp) {
        try {
            long seconds = Long.parseLong(timestamp);
            return new Date(seconds * 1000);
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

    public static Genre[] converterStringGeneros(String stringGenres, MyArrayList<Genre> genres) {
        if (stringGenres == null) {
            return new Genre[0];
        }
        MyArrayList<Genre> genresList = new MyArrayList<>();
        Pattern pattern = Pattern.compile("\\{'id'\\s*:\\s*(\\d+),\\s*'name'\\s*:\\s*'([^']*)'\\}");
        Matcher matcher = pattern.matcher(stringGenres);

        while (matcher.find()) {
            try {
                Integer id = Integer.parseInt(matcher.group(1));
                String name = matcher.group(2);
                Genre aux = new Genre(id, name);

                if (genres.pertenece(aux)) {
                    genresList.add(genres.get(id));
                } else {
                    genresList.add(aux);
                    genres.add(aux);
                }
            } catch (Exception e) {
            }
        }
        Genre[] result = new Genre[genresList.size()];
        for (int i = 0; i < genresList.size(); i++) {
            result[i] = genresList.get(i);
        }
        return result;
    }

    public static Company[] converterStringCompany(String stringCompanies, MyHashTableLineal<Integer, Company> companies) {
        if (stringCompanies == null) {
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
            } catch (Exception e) {
            }
        }
        Company[] result = new Company[companyList.size()];
        for (int i = 0; i < companyList.size(); i++) {
            result[i] = companyList.get(i);
        }

        return result;
    }

    public static Country[] converterStringCountry(String stringCountries, MyHashTableLineal<String, Country> countries) {
        if (stringCountries == null) {
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
            } catch (Exception e) {
            }
        }
        Country[] result = new Country[countriesList.size()];
        for (int i = 0; i < countriesList.size(); i++) {
            result[i] = countriesList.get(i);
        }

        return result;
    }

    public static Language[] converterStringLanguages(String stringLanguages, MyArrayList<Language> languages) {
        if (stringLanguages == null) {
            return new Language[0];
        }
        MyArrayList<Language> languagesList = new MyArrayList<>();

        Pattern pattern = Pattern.compile("\\{'iso_639_1'\\s*:\\s*'([a-z]{2})'\\s*,\\s*'name'\\s*:\\s*'([^']*)'\\}");
        Matcher matcher = pattern.matcher(stringLanguages);

        while (matcher.find()) {
            try {
                Integer id = Integer.parseInt(matcher.group(1));
                String name = matcher.group(2);
                Language aux = new Language(id, name);

                if (languages.pertenece(aux)) {
                    languagesList.add(languages.get(id));
                } else {
                    languagesList.add(aux);
                    languages.add(aux);
                }
            } catch (Exception e) {
            }
        }
        Language[] result = new Language[languagesList.size()];
        for (int i = 0; i < languagesList.size(); i++) {
            result[i] = languagesList.get(i);
        }

        return result;

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
                    String posterPath = matcher.group(3).equals("None") ? null : matcher.group(3).replace("'", "");
                    String backdropPath = matcher.group(4).equals("None") ? null : matcher.group(4).replace("'", "");

                    Collection newCollection = new Collection(id, name, posterPath, backdropPath);
                    collections.insert(id, newCollection);
                    return newCollection;
                }
            } catch (Exception e) {
                return null;
            }
        }

        return null;
    }

}
