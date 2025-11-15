package com.silverydeluxe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Habitacion {

    private int numero; // Número de habitación (ej: 101, 102)
    private String tipo; // simple, doble, suite
    private double precio; // Precio por día

   // Mapa que guarda días ocupados por mes → Ej: "septiembre" -> {1,2,3,15}
    private HashMap<String, HashSet<Integer>> diasOcupados;

    // Constructor
    public Habitacion(int numero, String tipo, double precio) {
        this.numero = numero;
        this.tipo = tipo;
        this.precio = precio;
        this.diasOcupados = new HashMap<>();

        // Inicializa cada mes disponible con un conjunto vacío de días ocupados
        for (String mes : Reserva.MESES_DISPONIBLES) {
            diasOcupados.put(mes.toLowerCase(), new HashSet<>());
        }
    }

    // Retorna la lista de días disponibles en un mes
    public ArrayList<Integer> diasDisponibles(String mes) {
        ArrayList<Integer> disponibles = new ArrayList<>();
        mes = mes.toLowerCase();
        // Se asume que cada mes tiene 30 días
        for (int i = 1; i <= 30; i++) { // asumimos 30 días por mes
            // Si el día NO está en ocupados → está libre
            if (!diasOcupados.get(mes).contains(i)) {
                disponibles.add(i);
            }
        }
        return disponibles;
    }

    // Reservar un rango de días (inclusive) si todos están libres
    public boolean reservarRango(String mes, int inicio, int fin) {
        mes = mes.toLowerCase();

        // Verifica que el rango tenga sentido
        if (inicio < 1 || fin > 30 || inicio > fin) {
            System.out.println("Rango de días inválido.");
            return false;
        }

        // Revisa si algún día del rango ya está ocupado
        for (int dia = inicio; dia <= fin; dia++) {
            if (diasOcupados.get(mes).contains(dia)) {
                System.out.println("El día " + dia + " de " + mes + " no está disponible. Reserva fallida.");
                return false;
            }
        }

        // Marca todos los días como ocupados
        for (int dia = inicio; dia <= fin; dia++) {
            diasOcupados.get(mes).add(dia);
        }
        System.out.println("Habitación " + numero + " reservada del día " + inicio + " al " + fin + " de " + mes + ".");
        return true;
    }

    // Libera los días cuando una reserva es cancelada
    public void liberarRango(String mes, int inicio, int fin) {
        mes = mes.toLowerCase();
        // Quita los días del HashSet, liberándolos
        for (int dia = inicio; dia <= fin; dia++) {
            diasOcupados.get(mes).remove(dia);
        }
        System.out.println("Días del " + inicio + " al " + fin + " de " + mes + " liberados en habitación " + numero + ".");
    }

   // Imprime la información de la habitación
    public void mostrarInfo() {
        System.out.println("Habitación #" + numero);
        System.out.println("Tipo: " + tipo);
        System.out.println("Precio: $" + precio);
        System.out.println("Días ocupados por mes:");
        // Muestra los días ocupados de cada mes
        for (String mes : diasOcupados.keySet()) {
            System.out.println("- " + mes + ": " + diasOcupados.get(mes));
        }
        System.out.println("----------------------");
    }

    // Getters
    public int getNumero() {
        return numero; // devuelve número de habitación
    }

    public String getTipo() {
        return tipo; // devuelve el tipo de habitación
    }

    public double getPrecio() {
        return precio; // devuelve el precio por día
    }
}


