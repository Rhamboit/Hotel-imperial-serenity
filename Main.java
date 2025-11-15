package com.silverydeluxe;
//Paquete donde está ubicada la clase
import java.util.Scanner;
//Se importa Scanner para leer datos del usuario por teclado
public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        //Crea un objeto Scanner para recibir entrada del usuario
        Hotel hotel = new Hotel("Silvery Deluxe");
        //Crea un objeto Hotel con el nombre "Silvery Deluxe"

        // ======== CREAR HABITACIONES DE EJEMPLO ========
        hotel.agregarHabitacion(new Habitacion(101, "simple", 50000));
        //Agrega una habitación simple con precio de 50,000
        hotel.agregarHabitacion(new Habitacion(102, "doble", 80000));
        //Agrega una habitación doble con precio de 80,000
        hotel.agregarHabitacion(new Habitacion(201, "suite", 150000));
        //Agrega una habitación tipo con precio de 150,000

        // ======== BIENVENIDA ========
        System.out.println("====================================");
        System.out.println("    Bienvenido al Hotel Silvery Deluxe ");
        System.out.println("====================================\n");
        //Imprime mensaje de bienvenida

        // ======== CONSENTIMIENTO DE DATOS ========
        boolean aceptarDatos = false; //Si el usuario acepta los términos
        boolean respuestaValida = false; //Controla que la respuesta sea corre

        while (!respuestaValida) {
            //Bucle que se repite hasta que el usuario escriba 1 o 2 
            System.out.println("¿Acepta el tratamiento de sus datos personales y el uso de cookies?");
            System.out.println("1. Sí, acepto y deseo continuar");
            System.out.println("2. No, salir del sistema");
            System.out.print("Seleccione una opción (1-2): ");

            if (sc.hasNextInt()) {
                //Verifica que la entrada sea un número
                int opcion = sc.nextInt();
                sc.nextLine(); // limpiar buffer del teclado
                if (opcion == 1) {
                    aceptarDatos = true;
                    respuestaValida = true;
                    //Acepta y continúa
                } else if (opcion == 2) {
                    //Si elige 2, cierra el programa
                    System.out.println("\nGracias por usar nuestros servicios. ¡Hasta pronto!");
                    sc.close();
                    return; // termina el programa
                } else {
                    System.out.println("Opción inválida. Debe ser 1 o 2.\n");
                    //Si escribe otro número
                }
            } else {
                System.out.println("Entrada inválida. Debes ingresar un número.\n");
                sc.next(); //Limpia entrada no válida
            }
            }
        }

        // ======== MENÚ PRINCIPAL ========
        boolean salir = false; //Controla si el menú termina
        Usuario usuarioActual = null; //Guarda el usuario que está loqueado

        while (!salir) {
            //Bucle del menú principal
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
            //validavión para que la opción sea entre 1 y 7
            while (!opcionValida) {
                System.out.print("Elige una opción (1-7): ");
                if (sc.hasNextInt()) {
                    opcion = sc.nextInt();
                    sc.nextLine(); // limpiar buffer
                    if (opcion >= 1 && opcion <= 7) {
                        opcionValida = true; //La opción es válida
                    } else {
                        System.out.println("Opción inválida. Debe ser un número entre 1 y 7.");
                    }
                } else {
                    System.out.println("Entrada inválida. Debes ingresar un número.");
                    sc.next(); // limpiar entrada inválida
                }
            }
            // ======== ACCIONES SEGÚN LA OPCIÓN ========
            switch (opcion) {
                case 1: //Registrarse
                    usuarioActual = Usuario.registrarse();
                    hotel.agregarUsuario(usuarioActual);
                    //Agrega el usuario al hotel
                    break;
                case 2: //Loguearse
                    System.out.print("Email: ");
                    String email = sc.nextLine();
                    System.out.print("Contraseña: ");
                    String pass = sc.nextLine();
                    Usuario u = hotel.buscarUsuarioPorEmail(email);
                    //Busca un usuario con ese email
                    if (u != null && u.loguearse(email, pass)) {
                        //Si existe y la contraseña es correcta
                        usuarioActual = u;
                    } else {
                        System.out.println("Login fallido.");
                    }
                    break;
                case 3: //Hacer reserva
                    if (usuarioActual != null) {
                        hotel.hacerReservaInteractiva(usuarioActual);
                        //Método para crear una reserba paso a paso
                    } else {
                        System.out.println("Debes loguearte primero.");
                    }
                    break;
                case 4: //Consultar reservas
                    if (usuarioActual != null) {
                        usuarioActual.consultarReservas();
                        //Muestra todas sus reservas
                    } else {
                        System.out.println("Debes loguearte primero.");
                    }
                    break;
                case 5: //Cancelar reserva
                    if (usuarioActual != null) {
                        int idReserva = 0;
                        boolean idValido = false;
                        //Validación para ingresar un ID numérico
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
                        //Cancela la reserva si existe
                    } else {
                        System.out.println("Debes loguearte primero.");
                    }
                    break;
                case 6: //Logout
                    if (usuarioActual != null) {
                        usuarioActual.desloguearse();
                        //Método opcional para marcar que cerró sesión
                        usuarioActual = null;
                    } else {
                        System.out.println("No hay usuario logueado.");
                    }
                    break;
                case 7: //Salir
                    salir = true;
                    System.out.println("\nGracias por usar nuestros servicios. ¡Hasta pronto!");
                    break;
            }
        }

        sc.close();
        //Cierra el scanner al final
    }
}







