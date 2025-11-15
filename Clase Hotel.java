package com.silverydeluxe;
// Indica que esta clase pertenece al paquete del proyecto
import java.util.ArrayList;
import java.util.Scanner;

public class Hotel {

    private String nombre; // Nombre del hotel
    private ArrayList<Usuario> usuarios; // Lista de usuarios registrados
    private ArrayList<Habitacion> habitaciones; // Lista de habitaciones del hotel
    // =============================================================
    // CONSTRUCTOR: Inicializa el hotel con su nombre y sus listas
    // =============================================================
    public Hotel(String nombre) {
        this.nombre = nombre;
        this.usuarios = new ArrayList<>();
        this.habitaciones = new ArrayList<>();
    }

    // Getter del nombre del hotel
    public String getNombre() {
        return nombre;
    }
    // =============================================================
    // MÉTODOS PARA GESTIONAR USUARIOS
    // =============================================================
    // Agregar un usuario nuevo al hotel
    public void agregarUsuario(Usuario usuario) {
        usuarios.add(usuario);
        System.out.println("Usuario agregado al hotel.");
    }

    // Buscar un usuario por su email para el login
    public Usuario buscarUsuarioPorEmail(String email) {
        for (Usuario u : usuarios) {
            if (u.getEmail().equals(email)) {
                return u; // Se encontró el usuario
            }
        }
        return null; // No se encontró
    }
    // =============================================================
    // MÉTODOS PARA GESTIONAR HABITACIONES
    // =============================================================
    // Agregar una nueva habitación al hotel
    public void agregarHabitacion(Habitacion habitacion) {
        habitaciones.add(habitacion);
    }

    // Buscar habitación por su tipo (simple, doble, suite)
    public Habitacion buscarHabitacionPorTipo(String tipo) {
        for (Habitacion h : habitaciones) {
            if (h.getTipo().equalsIgnoreCase(tipo)) {
                return h;
            }
        }
        return null; // No existe una habitación con ese tipo
    }

    // Mostrar todas las habitaciones registradas
    public void mostrarHabitaciones() {
        System.out.println("=== Habitaciones de " + nombre + " ===");
        for (Habitacion h : habitaciones) {
            h.mostrarInfo();
        }
    }
    // =============================================================
    // MÉTODO PRINCIPAL: HACER RESERVA INTERACTIVA 
    // =============================================================
    // Hacer reserva interactiva por rango de días con validaciones
    public void hacerReservaInteractiva(Usuario usuario) {
        // Validar que el usuario esté logueado
        if (!usuario.isLogueado()) {
            System.out.println("Debes iniciar sesión para hacer una reserva.");
            return;
        }

        Scanner sc = new Scanner(System.in);
        System.out.println("=== Hacer Reserva ===");

        // -----------------------------------------
        // SELECCIÓN DEL TIPO DE HABITACIÓN
        // -----------------------------------------
        Habitacion habitacion = null;
        while (habitacion == null) {
            System.out.print("Tipo de habitación (1.simple, 2.doble, 3.suite): ");
            String tipo = sc.nextLine();
            // Buscar habitación según el tipo indicado
            habitacion = buscarHabitacionPorTipo(tipo);
            if (habitacion == null) {
                System.out.println("Tipo inválido o no disponible. Intenta de nuevo.");
            }
        }

        // -----------------------------------------
        // SELECCIÓN DEL MES
        // -----------------------------------------
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

        // -----------------------------------------
        // MOSTRAR DÍAS DISPONIBLES DE ESA HABITACIÓN
        // -----------------------------------------
        ArrayList<Integer> diasLibres = habitacion.diasDisponibles(mesElegido);
        if (diasLibres.isEmpty()) {
            System.out.println("No hay días disponibles en " + mesElegido + " para esta habitación.");
            return;
        }
        System.out.println("Días disponibles en " + mesElegido + ": " + diasLibres);

        // -----------------------------------------
        // INGRESAR RANGO DE DÍAS
        // -----------------------------------------
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

        // -----------------------------------------
        // CALCULAR PRECIO TOTAL
        // -----------------------------------------
        double total = habitacion.getPrecio() * (diaFin - diaInicio + 1);
        System.out.println("El precio total para esta reserva será: Cop_" + total);

        // -------------------------------------------------
        // VALIDACIÓN DE TARJETA Y CVC
        // -------------------------------------------------
        String numeroTarjeta;
        String cvc;
        // Número de tarjeta (solo dígitos)
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

        // -----------------------------------------
        // INTENTAR RESERVAR
        // -----------------------------------------
        if (habitacion.reservarRango(mesElegido, diaInicio, diaFin)) {
            // Crear la reserva
            Reserva reserva = new Reserva(usuario, habitacion, mesElegido, diaInicio, diaFin);
            // Guardarla en el usuario
            usuario.agregarReserva(reserva);
            System.out.println("Reserva realizada con éxito del día " + diaInicio + " al " + diaFin + " de " + mesElegido + ".");
        } else {
            System.out.println("No se pudo realizar la reserva.");
        }
    }

    // =============================================================
    // MOSTRAR TODAS LAS RESERVAS DEL HOTEL
    // =============================================================
    public void mostrarTodasLasReservas() {
        System.out.println("=== Todas las reservas de " + nombre + " ===");
        for (Usuario u : usuarios) {
            u.consultarReservas();
        }
    }
}




