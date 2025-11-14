package com.silverydeluxe;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Hotel hotel = new Hotel("Silvery Deluxe");

        // Crear habitaciones de ejemplo
        hotel.agregarHabitacion(new Habitacion(101, "simple", 50000));
        hotel.agregarHabitacion(new Habitacion(102, "doble", 80000));
        hotel.agregarHabitacion(new Habitacion(201, "suite", 150000));

        // ======== BIENVENIDA ========
        System.out.println("====================================");
        System.out.println("    Bienvenido al Hotel Silvery Deluxe ");
        System.out.println("====================================\n");

        // ======== CONSENTIMIENTO DE DATOS ========
        boolean aceptarDatos = false;
        boolean respuestaValida = false;

        while (!respuestaValida) {
            System.out.println("¿Acepta el tratamiento de sus datos personales y el uso de cookies?");
            System.out.println("1. Sí, acepto y deseo continuar");
            System.out.println("2. No, salir del sistema");
            System.out.print("Seleccione una opción (1-2): ");

            if (sc.hasNextInt()) {
                int opcion = sc.nextInt();
                sc.nextLine(); // limpiar buffer
                if (opcion == 1) {
                    aceptarDatos = true;
                    respuestaValida = true;
                } else if (opcion == 2) {
                    System.out.println("\nGracias por usar nuestros servicios. ¡Hasta pronto!");
                    sc.close();
                    return; // termina el programa
                } else {
                    System.out.println("Opción inválida. Debe ser 1 o 2.\n");
                }
            } else {
                System.out.println("Entrada inválida. Debes ingresar un número.\n");
                sc.next();
            }
        }

        // ======== MENÚ PRINCIPAL ========
        boolean salir = false;
        Usuario usuarioActual = null;

        while (!salir) {
            System.out.println("\n=== Menú Principal del Hotel Silvery Deluxe ===");
            System.out.println("1. Registrarse");
            System.out.println("2. Loguearse");
            System.out.println("3. Hacer reserva");
            System.out.println("4. Consultar reservas");
            System.out.println("5. Cancelar reserva");
            System.out.println("6. Logout");
            System.out.println("7. Salir");

            int opcion = 0;
            boolean opcionValida = false;
            while (!opcionValida) {
                System.out.print("Elige una opción (1-7): ");
                if (sc.hasNextInt()) {
                    opcion = sc.nextInt();
                    sc.nextLine(); // limpiar buffer
                    if (opcion >= 1 && opcion <= 7) {
                        opcionValida = true;
                    } else {
                        System.out.println("Opción inválida. Debe ser un número entre 1 y 7.");
                    }
                } else {
                    System.out.println("Entrada inválida. Debes ingresar un número.");
                    sc.next(); // limpiar entrada inválida
                }
            }

            switch (opcion) {
                case 1:
                    usuarioActual = Usuario.registrarse();
                    hotel.agregarUsuario(usuarioActual);
                    break;
                case 2:
                    System.out.print("Email: ");
                    String email = sc.nextLine();
                    System.out.print("Contraseña: ");
                    String pass = sc.nextLine();
                    Usuario u = hotel.buscarUsuarioPorEmail(email);
                    if (u != null && u.loguearse(email, pass)) {
                        usuarioActual = u;
                    } else {
                        System.out.println("Login fallido.");
                    }
                    break;
                case 3:
                    if (usuarioActual != null) {
                        hotel.hacerReservaInteractiva(usuarioActual);
                    } else {
                        System.out.println("Debes loguearte primero.");
                    }
                    break;
                case 4:
                    if (usuarioActual != null) {
                        usuarioActual.consultarReservas();
                    } else {
                        System.out.println("Debes loguearte primero.");
                    }
                    break;
                case 5:
                    if (usuarioActual != null) {
                        int idReserva = 0;
                        boolean idValido = false;
                        while (!idValido) {
                            System.out.print("Ingresa ID de la reserva a cancelar: ");
                            if (sc.hasNextInt()) {
                                idReserva = sc.nextInt();
                                sc.nextLine(); // limpiar buffer
                                idValido = true;
                            } else {
                                System.out.println("ID inválido. Debe ser un número.");
                                sc.next();
                            }
                        }
                        usuarioActual.cancelarReserva(idReserva);
                    } else {
                        System.out.println("Debes loguearte primero.");
                    }
                    break;
                case 6:
                    if (usuarioActual != null) {
                        usuarioActual.desloguearse();
                        usuarioActual = null;
                    } else {
                        System.out.println("No hay usuario logueado.");
                    }
                    break;
                case 7:
                    salir = true;
                    System.out.println("\nGracias por usar nuestros servicios. ¡Hasta pronto!");
                    break;
            }
        }

        sc.close();
    }
}






