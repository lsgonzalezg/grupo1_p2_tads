import um.edu.uy.entidades.MoviesUM;

import java.time.LocalDateTime;
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
                    long inicio = System.currentTimeMillis();
                    moviesUM.cargarDatos();
                    long fin = System.currentTimeMillis();
                    System.out.println("Tiempo de ejecucion:" + (fin - inicio));
                    break;
                case 2:
                    System.out.println("----------------------------------------------");
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
}