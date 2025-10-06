package Sala;

import Personas.Paciente;
import java.util.*;

/**
 * Representa el patio común donde esperan los pacientes que no pudieron
 * acceder a la sala de espera privada.
 * <p>
 * Se implementa como una cola
 * Solo existe una instancia de patio (patrón <b>Singleton</b>).
 * </p>
 */


public class Patio {

    private static final Patio INSTANCE = new Patio();
    private final Deque<Paciente> cola = new ArrayDeque<>();
    //deque se usa como cola fifo


    private Patio() {}

    /**
     * Obtiene la instancia única del patio (Singleton).
     *
     * @return instancia global del patio
     */

    public static Patio getInstance() {
        Patio ref = null;
        ref = INSTANCE;
        return ref;
    }

    /**
     * Mueve un paciente al final de la cola del patio.
     *
     * @param p paciente a agregar
     */

    public void moverAlPatio(Paciente p) {
        boolean pacienteValido = (p != null);
        if (pacienteValido) {
            cola.addLast(p);
        }
    }

    /**
     * Obtiene y remueve el siguiente paciente de la cola (si existe).
     *
     * @return paciente dentro de Optional, o vacío si no hay
     */
    public Optional<Paciente> sacarSiguiente() {
        Optional<Paciente> resultado;
        Paciente siguiente = null;

        if (!cola.isEmpty()) {
            siguiente = cola.pollFirst();
        }
        resultado = Optional.ofNullable(siguiente);
        return resultado;
    }

    /**
     * Retorna la cantidad de pacientes en el patio.
     *
     * @return número de pacientes en la cola
     */
    public int cantidadEnPatio() {
        int cantidad = 0;
        cantidad = cola.size();
        return cantidad;
    }

}
