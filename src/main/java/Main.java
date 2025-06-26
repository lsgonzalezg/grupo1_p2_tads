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

            String input = scanner.nextLine();
            //puse esto porque si ponias por ejemplo una barra se rompia el prog
            try {
                opcion = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Opción no válida. Intente de nuevo.");
                continue;
            }

            switch (opcion) {
                case 1:
                    System.out.println("----------------------------------------------");
                    long start = System.currentTimeMillis();
                    moviesUM.loadData();
                    long finish = System.currentTimeMillis();
                    System.out.println("Tiempo de ejecucion:" + (finish - start) + " milisegundos");
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
                    moviesUM.ejecutarConsulta1();
                    long finish = System.currentTimeMillis();
                    System.out.println("Tiempo de ejecucion:" + (finish - start) + " milisegundos");
                    break;
                case 2:
                    System.out.println("----------------------------------------------");
                    long startCase2 = System.currentTimeMillis();
                    moviesUM.ejecutarConsulta2();
                    long finishCase2 = System.currentTimeMillis();
                    System.out.println("Tiempo de ejecucion:" + (finishCase2 - startCase2) + " milisegundos");
                    break;
                case 3:
                    System.out.println("----------------------------------------------");
                    long start3 = System.currentTimeMillis();
                    moviesUM.ejecutarConsulta3();
                    long finish3 = System.currentTimeMillis();
                    System.out.println("Tiempo de ejecucion:" + (finish3 - start3) + " milisegundos");
                    break;
                case 4:
                    System.out.println("----------------------------------------------");
                    long start4 = System.currentTimeMillis();
                    moviesUM.ejecutarConsulta4();
                    long finish4 = System.currentTimeMillis();
                    System.out.println("Tiempo de ejecucion:" + (finish4 - start4) + " milisegundos");
                    break;
                case 5:
                    System.out.println("----------------------------------------------");
                    long start5 = System.currentTimeMillis();
                    moviesUM.ejecutarConsulta5();
                    long finish5 = System.currentTimeMillis();
                    System.out.println("Tiempo de ejecucion:" + (finish5 - start5) + " milisegundos");
                    break;
                case 6:
                    System.out.println("----------------------------------------------");
                    long start6 = System.currentTimeMillis();
                    moviesUM.ejecutarConsulta6();
                    long finish6 = System.currentTimeMillis();
                    System.out.println("Tiempo de ejecucion:" + (finish6 - start6) + " milisegundos");
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