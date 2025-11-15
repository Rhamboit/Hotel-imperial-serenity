package com.silverydeluxe;
//El archivo pertenece al paquete del proyecto
import java.util.concurrent.atomic.AtomicInteger;
//AtomicInteger permite generar IDs únicos y seguros entre hilos
public class Reserva {
    //Contador estático para generar IDs únicos automáticamente
    private static final AtomicInteger contador = new AtomicInteger(1); // ID único automático
    //Meses permitidos para hacer reservas
    public static final String[] MESES_DISPONIBLES = {"septiembre", "octubre", "noviembre"};

    private int idReserva; //Identificador único de la reseva
    private Usuario usuario; //Usuario que realizó la reserva
    private Habitacion habitacion; //Habitación reservada
    private String mes; // mes de la reserva
    private int diaInicio; //Día inicial 
    private int diaFin; //Día final
    private String estado; // Estado de la reserva: "activa" o "cancelada"
    // ======================================================
    //  CONSTRUCTOR: CREA UNA RESERVA NUEVA Y VALIDA DATOS
    // ======================================================
    public Reserva(Usuario usuario, Habitacion habitacion, String mes, int diaInicio, int diaFin) {
        mes = mes.toLowerCase();
        //Se pasa el mes a minúsculas para evitar errores por mayúsculas
        if (!validarMes(mes)) {
            //Validción del mes permitido
            throw new IllegalArgumentException("Mes inválido para la reserva: " + mes);
        }
        //Validación del ragon de días
        if (diaInicio < 1 || diaInicio > 30 || diaFin < 1 || diaFin > 30 || diaInicio > diaFin) {
            throw new IllegalArgumentException("Rango de días inválido: " + diaInicio + " - " + diaFin);
        }
        //ID único asignado automáticamente
        this.idReserva = contador.getAndIncrement();
        //Datos
        this.usuario = usuario;
        this.habitacion = habitacion;
        this.mes = mes;
        this.diaInicio = diaInicio;
        this.diaFin = diaFin;
        //Estado inicial
        this.estado = "activa";
    }

    // ======================================================
    // VALIDAR MES DISPONIBLE
    // ======================================================
    private boolean validarMes(String mes) {
        for (String m : MESES_DISPONIBLES) {
            if (m.equalsIgnoreCase(mes)) return true; //Si coincide, es válido
        }
        return false; //No está en la lista
    }

    // ======================================================
   // GETTERS (obtener información de los atributos)
   // ======================================================
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

    // ======================================================
    // CALCULAR PRECIO TOTAL DE LA RESERVA
    // ======================================================
    public double calcularTotal () {
        int numDias = diaFin - diaInicio + 1; //Cantidad de días reservados
        return numDias * habitacion.getPrecio(); //Precio = días * precio habita
    }

    // ======================================================
    // CANCELAR LA RESERVA
    // ======================================================
    public void cancelar() {
        if (estado.equals("activa")) {
            //Cambiar estado a cancelada
            estado = "cancelada";
            //Liberar esos días en la habitación
            habitacion.liberarRango(mes, diaInicio, diaFin);
            System.out.println("Reserva #" + idReserva + " cancelada.");
        } else {
            //Si ya estaba cancelada
            System.out.println("La reserva #" + idReserva + " ya está cancelada.");
        }
    }

    // ======================================================
    // MOSTRAR INFORMACIÓN FORMATEADA EN CONSOLA
    // ======================================================
    public void mostrarInfo() {
        System.out.println("ID Reserva: " + idReserva);
        System.out.println("Usuario: " + usuario.getNombre() + " " + usuario.getApellido());
        System.out.println("Habitación #" + habitacion.getNumero() + " (" + habitacion.getTipo() + ")");
        System.out.println("Días: " + diaInicio + " al " + diaFin);
        System.err.println("Precio total: Cop_" + calcularTotal()); //System.err en rojo
        System.out.println("Mes: " + mes);
        System.out.println("Estado: " + estado);
        System.out.println("----------------------");
    }
}




