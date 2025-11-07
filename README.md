# Hotel-imperial-serenity
//CODIGO DE INICIO//
package com.reservaciones;

import java.util.Scanner;

public class Registro {

public static void main(String[] args) {
    // Objeto Scanner para la entrada de datos
    Scanner sn = new Scanner(System.in);

    System.out.println("---  BIENVENIDO AL REGISTRO ---");
    System.out.print("Crea un nombre de usuario: ");
    String usuario = sn.nextLine();

    System.out.print("Crea una contraseña: ");
    String password = sn.nextLine();

    // Simulación de registro exitoso
    if (!usuario.isEmpty() && !password.isEmpty()) {
        System.out.println("\n ¡Registro completado con éxito!");
        System.out.println("Ahora te redirigiremos al menú de reservaciones...");
        
        // Llama al método estático que inicia el menú
        MenuReservaciones.iniciarMenu(); 
    } else {
        System.out.println("\n El usuario y la contraseña no pueden estar vacíos.");
    }
    
    sn.close(); // Cerrar el Scanner al finalizar el programa

//RESERVACION//

    package com.reservaciones;

import java.time.LocalDate;
import java.util.Scanner;
import java.util.ArrayList;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit; // Opcional: para cálculo de días

// Clase de ejemplo para una Reserva (Necesaria para la Opción 6)
class Reserva {
private String id;
private LocalDate fecha;
private String cliente;

public Reserva(String id, LocalDate fecha, String cliente) {
    this.id = id;
    this.fecha = fecha;
    this.cliente = cliente;
}

public LocalDate getFecha() {
    return fecha;
}

@Override
public String toString() {
    return "ID: " + id + ", Cliente: " + cliente + ", Fecha: " + fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
}
}

public class MenuReservaciones {

// Nota: El Scanner se declara como estático para que todas las funciones lo usen
private static Scanner sn = new Scanner(System.in);
private static ArrayList<Reserva> listaReservas = new ArrayList<>(); 

/**
 * Método estático para iniciar el menú principal. 
 * Es llamado desde la clase Registro.
 */
public static void iniciarMenu() {
    boolean salir = false;
    int opcion;

    // Datos de prueba
    listaReservas.add(new Reserva("R001", LocalDate.now().plusMonths(1), "Juan Perez")); 
    listaReservas.add(new Reserva("R002", LocalDate.now().plusMonths(2).plusDays(15), "Ana Gómez")); 
    listaReservas.add(new Reserva("R003", LocalDate.now().plusMonths(4), "Luis García")); // Fuera de 3 meses
    // ----------------------------------------------------------------------

    // **NOTA IMPORTANTE:** Se utiliza un nuevo Scanner dentro de esta función
    // para evitar conflictos si el Scanner de la clase Registro ya se cerró.
    // Si el programa no funciona, prueba a reabrir el Scanner aquí, o a pasarlo como argumento.
    Scanner menuScanner = new Scanner(System.in); 


    while (!salir) {
        mostrarOpciones();
        try {
            opcion = menuScanner.nextInt();
            menuScanner.nextLine(); // Consumir el salto de línea

            switch (opcion) {
                case 1:
                    // La opción de registrar cuenta ya se hizo en la clase 'Registro'
                    System.out.println(" Ya estás registrado y logueado.");
                    break; 
                case 2:
                    System.out.println(" Ya has iniciado sesión (simulado por el registro).");
                    break; 
                case 3:
                    reservar();
                    break;
                case 4:
                    cancelar();
                    break;
                case 5:
                    modificarReservacion();
                    break;
                case 6:
                    mostrarReservacionesProximos3Meses();
                    break;
                case 7:
                    salir = true;
                    System.out.println(" Gracias por usar el sistema. ¡Adiós!");
                    break;
                default:
                    System.out.println(" Opción inválida. Introduce un número del 1 al 7.");
            }
        } catch (java.util.InputMismatchException e) {
            System.out.println(" Entrada no válida. Debes introducir un número.");
            menuScanner.nextLine(); // Limpiar el buffer del scanner
        }
        System.out.println("---");
    }
    menuScanner.close();
}

// --- Funciones de Soporte y Opciones ---

private static void mostrarOpciones() {
    System.out.println("\n***  MENÚ DE RESERVACIONES ***");
    System.out.println("1. Registrar cuenta (Ya completado)");
    System.out.println("2. Iniciar sesión (Ya completado)");
    System.out.println("3. Reservaciones");
    System.out.println("4. Cancelación");
    System.out.println("5. Modificar reservaciones");
    System.out.println("6. Mostrar reservaciones (Próximos 3 meses)");
    System.out.println("7. Salir");
    System.out.print(" Introduce una opción (1-7): ");
}

private static void reservar() {
    System.out.println("\n[3] Funcionalidad: Crear una nueva reservación...");
}

private static void cancelar() {
    System.out.println("\n[4] Funcionalidad: Cancelar reservación...");
}

private static void modificarReservacion() {
    System.out.println("\n[5] Funcionalidad: Modificar reservación...");
}

private static void mostrarReservacionesProximos3Meses() {
    System.out.println("\n[6]  Reservaciones programadas para los próximos 3 meses:");
    
    LocalDate hoy = LocalDate.now();
    LocalDate fechaLimite = hoy.plusMonths(3);
    
    boolean encontradas = false;

    for (Reserva reserva : listaReservas) {
        LocalDate fechaReserva = reserva.getFecha();
        
        // Filtra: fecha futura Y no más de 3 meses de distancia
        if (fechaReserva.isAfter(hoy) && !fechaReserva.isAfter(fechaLimite)) {
            System.out.println("   - " + reserva.toString());
            encontradas = true;
        }
    }

    if (!encontradas) {
        System.out.println("   -> No hay reservaciones en los próximos 3 meses.");
    }
}
}
}
}
