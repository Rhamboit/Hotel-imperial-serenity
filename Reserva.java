package com.silverydeluxe;

import java.util.concurrent.atomic.AtomicInteger;

public class Reserva {

    private static final AtomicInteger contador = new AtomicInteger(1); // ID único automático

    public static final String[] MESES_DISPONIBLES = {"septiembre", "octubre", "noviembre"};

    private int idReserva;
    private Usuario usuario;
    private Habitacion habitacion;
    private String mes; // mes de la reserva
    private int diaInicio;
    private int diaFin;
    private String estado; // "activa" o "cancelada"

    // Constructor con validación básica
    public Reserva(Usuario usuario, Habitacion habitacion, String mes, int diaInicio, int diaFin) {
        mes = mes.toLowerCase();

        if (!validarMes(mes)) {
            throw new IllegalArgumentException("Mes inválido para la reserva: " + mes);
        }

        if (diaInicio < 1 || diaInicio > 30 || diaFin < 1 || diaFin > 30 || diaInicio > diaFin) {
            throw new IllegalArgumentException("Rango de días inválido: " + diaInicio + " - " + diaFin);
        }

        this.idReserva = contador.getAndIncrement();
        this.usuario = usuario;
        this.habitacion = habitacion;
        this.mes = mes;
        this.diaInicio = diaInicio;
        this.diaFin = diaFin;
        this.estado = "activa";
    }

    // Validar mes
    private boolean validarMes(String mes) {
        for (String m : MESES_DISPONIBLES) {
            if (m.equalsIgnoreCase(mes)) return true;
        }
        return false;
    }

    // Getters
    public int getIdReserva() {
        return idReserva;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Habitacion getHabitacion() {
        return habitacion;
    }

    public String getMes() {
        return mes;
    }

    public int getDiaInicio() {
        return diaInicio;
    }

    public int getDiaFin() {
        return diaFin;
    }

    public String getEstado() {
        return estado;
    }

    //precio de la reserva

    public double calcularTotal () {
        int numDias = diaFin - diaInicio + 1;
        return numDias * habitacion.getPrecio();
    }

    // Cancelar reserva
    public void cancelar() {
        if (estado.equals("activa")) {
            estado = "cancelada";
            habitacion.liberarRango(mes, diaInicio, diaFin);
            System.out.println("Reserva #" + idReserva + " cancelada.");
        } else {
            System.out.println("La reserva #" + idReserva + " ya está cancelada.");
        }
    }

    // Mostrar información de la reserva
    public void mostrarInfo() {
        System.out.println("ID Reserva: " + idReserva);
        System.out.println("Usuario: " + usuario.getNombre() + " " + usuario.getApellido());
        System.out.println("Habitación #" + habitacion.getNumero() + " (" + habitacion.getTipo() + ")");
        System.out.println("Días: " + diaInicio + " al " + diaFin);
        System.err.println("Precio total: Cop_" + calcularTotal());
        System.out.println("Mes: " + mes);
        System.out.println("Estado: " + estado);
        System.out.println("----------------------");
    }
}



