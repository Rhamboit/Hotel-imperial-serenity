package com.silverydeluxe;

import java.util.ArrayList;
import java.util.Scanner;

public class Hotel {

    private String nombre;
    private ArrayList<Usuario> usuarios;
    private ArrayList<Habitacion> habitaciones;

    // Constructor
    public Hotel(String nombre) {
        this.nombre = nombre;
        this.usuarios = new ArrayList<>();
        this.habitaciones = new ArrayList<>();
    }

    // Getter del nombre
    public String getNombre() {
        return nombre;
    }

    // Agregar usuario
    public void agregarUsuario(Usuario usuario) {
        usuarios.add(usuario);
        System.out.println("Usuario agregado al hotel.");
    }

    // Buscar usuario por email
    public Usuario buscarUsuarioPorEmail(String email) {
        for (Usuario u : usuarios) {
            if (u.getEmail().equals(email)) {
                return u;
            }
        }
        return null;
    }

    // Agregar habitación
    public void agregarHabitacion(Habitacion habitacion) {
        habitaciones.add(habitacion);
    }

    // Buscar habitación por tipo
    public Habitacion buscarHabitacionPorTipo(String tipo) {
        for (Habitacion h : habitaciones) {
            if (h.getTipo().equalsIgnoreCase(tipo)) {
                return h;
            }
        }
        return null;
    }

    // Mostrar habitaciones
    public void mostrarHabitaciones() {
        System.out.println("=== Habitaciones de " + nombre + " ===");
        for (Habitacion h : habitaciones) {
            h.mostrarInfo();
        }
    }

    // Hacer reserva interactiva por rango de días con validaciones
    public void hacerReservaInteractiva(Usuario usuario) {
        if (!usuario.isLogueado()) {
            System.out.println("Debes iniciar sesión para hacer una reserva.");
            return;
        }

        Scanner sc = new Scanner(System.in);
        System.out.println("=== Hacer Reserva ===");

        // Selección de tipo de habitación
        Habitacion habitacion = null;
        while (habitacion == null) {
            System.out.print("Tipo de habitación (1.simple, 2.doble, 3.suite): ");
            String tipo = sc.nextLine();
            habitacion = buscarHabitacionPorTipo(tipo);
            if (habitacion == null) {
                System.out.println("Tipo inválido o no disponible. Intenta de nuevo.");
            }
        }

        // Selección de mes válido
        String mesElegido = "";
        boolean mesValido = false;
        while (!mesValido) {
            System.out.println("Meses disponibles para reservar:");
            for (String mes : Reserva.MESES_DISPONIBLES) {
                System.out.println("- " + mes);
            }
            System.out.print("Elige un mes: ");
            mesElegido = sc.nextLine().toLowerCase();
            for (String m : Reserva.MESES_DISPONIBLES) {
                if (m.equalsIgnoreCase(mesElegido)) {
                    mesValido = true;
                    break;
                }
            }
            if (!mesValido) {
                System.out.println("Mes no válido. Intenta de nuevo.");
            }
        }

        // Mostrar días disponibles
        ArrayList<Integer> diasLibres = habitacion.diasDisponibles(mesElegido);
        if (diasLibres.isEmpty()) {
            System.out.println("No hay días disponibles en " + mesElegido + " para esta habitación.");
            return;
        }
        System.out.println("Días disponibles en " + mesElegido + ": " + diasLibres);

        // Ingreso de día de inicio y día de fin con validación
        int diaInicio = 0, diaFin = 0;
        boolean rangoValido = false;
        while (!rangoValido) {
            System.out.print("Ingresa día de inicio: ");
            if (sc.hasNextInt()) {
                diaInicio = sc.nextInt();
            } else {
                sc.next(); // limpiar entrada inválida
                System.out.println("Día inválido. Debe ser un número entre 1 y 30.");
                continue;
            }

            System.out.print("Ingresa día de fin: ");
            if (sc.hasNextInt()) {
                diaFin = sc.nextInt();
                sc.nextLine(); // limpiar buffer
            } else {
                sc.next(); // limpiar entrada inválida
                System.out.println("Día inválido. Debe ser un número entre 1 y 30.");
                continue;
            }

            if (diaInicio < 1 || diaInicio > 30 || diaFin < 1 || diaFin > 30 || diaInicio > diaFin) {
                System.out.println("Rango de días inválido. Intenta de nuevo.");
            } else {
                rangoValido = true;
            }
        }

        // Mostrar precio total
        double total = habitacion.getPrecio() * (diaFin - diaInicio + 1);
        System.out.println("El precio total para esta reserva será: Cop_" + total);

        // --- Inicio: validación de tarjeta y CVC ---
        String numeroTarjeta;
        String cvc;

        // Pedir número de tarjeta (solo números, 13-19 dígitos)
        while (true) {
            System.out.print("Ingresa el número de tarjeta (solo dígitos): ");
            numeroTarjeta = sc.nextLine().trim();
            if (numeroTarjeta.matches("\\d{13,19}")) {
                break;
            } else {
                System.out.println("Número de tarjeta inválido. Debe contener solo dígitos.");
            }
        }

        // Pedir CVC (solo números, 3 o 4 dígitos)
        while (true) {
            System.out.print("Ingresa el CVC (3 o 4 dígitos): ");
            cvc = sc.nextLine().trim();
            if (cvc.matches("\\d{3,4}")) {
                break;
            } else {
                System.out.println("CVC inválido. Debe ser 3 o 4 dígitos numéricos.");
            }
        }

        // Aviso de privacidad: no guardamos datos de pago
        System.out.println("Datos de pago validados");
        // --- Fin: validación de tarjeta y CVC ---

        // Intentar reservar
        if (habitacion.reservarRango(mesElegido, diaInicio, diaFin)) {
            Reserva reserva = new Reserva(usuario, habitacion, mesElegido, diaInicio, diaFin);
            usuario.agregarReserva(reserva);
            System.out.println("Reserva realizada con éxito del día " + diaInicio + " al " + diaFin + " de " + mesElegido + ".");
        } else {
            System.out.println("No se pudo realizar la reserva.");
        }
    }

    // Mostrar todas las reservas
    public void mostrarTodasLasReservas() {
        System.out.println("=== Todas las reservas de " + nombre + " ===");
        for (Usuario u : usuarios) {
            u.consultarReservas();
        }
    }
}




