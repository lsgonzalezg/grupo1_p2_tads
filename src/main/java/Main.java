import um.edu.uy.entities.MoviesUM;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        MoviesUM moviesUM = new MoviesUM();
        Scanner scanner = new Scanner(System.in);
        int opcion = 0;

        while (opcion != 3) {
            System.out.println("Menú principal:");
            System.out.println("Seleccione la opción que desee:");
            System.out.println("1. Carga de datos");
            System.out.println("2. Ejecutar consultas");
            System.out.println("3. Salir");
            System.out.print("Opción: ");

            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    System.out.println("----------------------------------------------");
                    long start = System.currentTimeMillis();
                    moviesUM.loadData();
                    long finish = System.currentTimeMillis();
                    System.out.println("Tiempo de ejecucion:" + (start - finish) + " milisegundos");
                    break;
                case 2:
                    System.out.println("----------------------------------------------");
                    menuConsultas(moviesUM);
                    break;
                case 3:
                    System.out.println("----------------------------------------------");
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }

            System.out.println("----------------------------------------------");
        }

        scanner.close();
    }

    public static void menuConsultas(MoviesUM moviesUM){
        Scanner scanner = new Scanner(System.in);
        int opcion2 = 0;
        while (opcion2 != 7) {
            System.out.println("Menú Consultas:");
            System.out.println("Seleccione la opción que desee:");
            System.out.println("1. Top 5 de las películas que más calificaciones por idioma.");
            System.out.println("2. Top 10 de las películas que mejor calificaciones media tienen por parte de los usuarios.");
            System.out.println("3. Top 5 de las colecciones que más ingresos generaron.");
            System.out.println("4. Top 10 de los directores que mejor calificación tienen.");
            System.out.println("5. Actor con más calificaciones recibidas en cada mes del año.");
            System.out.println("6. Usuarios con más calificaciones por género.");
            System.out.println("7. Salir");
            System.out.print("Opción: ");

            opcion2 = scanner.nextInt();

            switch (opcion2) {
                case 1:
                    System.out.println("----------------------------------------------");
                    long start = System.currentTimeMillis();
                    moviesUM.top5MoviesRatingsByLanguage();
                    long finish = System.currentTimeMillis();
                    System.out.println("Tiempo de ejecucion:" + (start - finish) + " milisegundos");
                    break;
                case 2:
                    System.out.println("----------------------------------------------");
                    break;
                case 3:
                    System.out.println("----------------------------------------------");
                    break;
                case 4:
                    System.out.println("----------------------------------------------");
                    break;
                case 5:
                    System.out.println("----------------------------------------------");
                    break;
                case 6:
                    System.out.println("----------------------------------------------");
                    break;
                case 7:
                    System.out.println("----------------------------------------------");
                    System.out.println("Saliendo de las consultas...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }

            System.out.println("----------------------------------------------");
        }
    }
}