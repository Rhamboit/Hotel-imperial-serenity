package com.silverydeluxe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Habitacion {

    private int numero;
    private String tipo; // simple, doble, suite
    private double precio;

    // Control de disponibilidad por día de cada mes
    private HashMap<String, HashSet<Integer>> diasOcupados;

    // Constructor
    public Habitacion(int numero, String tipo, double precio) {
        this.numero = numero;
        this.tipo = tipo;
        this.precio = precio;
        this.diasOcupados = new HashMap<>();

        // Inicializar los meses disponibles
        for (String mes : Reserva.MESES_DISPONIBLES) {
            diasOcupados.put(mes.toLowerCase(), new HashSet<>());
        }
    }

    // Consultar días disponibles de un mes
    public ArrayList<Integer> diasDisponibles(String mes) {
        ArrayList<Integer> disponibles = new ArrayList<>();
        mes = mes.toLowerCase();
        for (int i = 1; i <= 30; i++) { // asumimos 30 días por mes
            if (!diasOcupados.get(mes).contains(i)) {
                disponibles.add(i);
            }
        }
        return disponibles;
    }

    // Reservar un rango de días (inclusive) si todos están libres
    public boolean reservarRango(String mes, int inicio, int fin) {
        mes = mes.toLowerCase();

        // Validación básica de rango
        if (inicio < 1 || fin > 30 || inicio > fin) {
            System.out.println("Rango de días inválido.");
            return false;
        }

        // Verificar que todos los días estén libres
        for (int dia = inicio; dia <= fin; dia++) {
            if (diasOcupados.get(mes).contains(dia)) {
                System.out.println("El día " + dia + " de " + mes + " no está disponible. Reserva fallida.");
                return false;
            }
        }

        // Reservar todos los días
        for (int dia = inicio; dia <= fin; dia++) {
            diasOcupados.get(mes).add(dia);
        }
        System.out.println("Habitación " + numero + " reservada del día " + inicio + " al " + fin + " de " + mes + ".");
        return true;
    }

    // Liberar un rango de días (inclusive) al cancelar
    public void liberarRango(String mes, int inicio, int fin) {
        mes = mes.toLowerCase();
        for (int dia = inicio; dia <= fin; dia++) {
            diasOcupados.get(mes).remove(dia);
        }
        System.out.println("Días del " + inicio + " al " + fin + " de " + mes + " liberados en habitación " + numero + ".");
    }

    // Mostrar información de la habitación
    public void mostrarInfo() {
        System.out.println("Habitación #" + numero);
        System.out.println("Tipo: " + tipo);
        System.out.println("Precio: $" + precio);
        System.out.println("Días ocupados por mes:");
        for (String mes : diasOcupados.keySet()) {
            System.out.println("- " + mes + ": " + diasOcupados.get(mes));
        }
        System.out.println("----------------------");
    }

    // Getters
    public int getNumero() {
        return numero;
    }

    public String getTipo() {
        return tipo;
    }

    public double getPrecio() {
        return precio;
    }
}


