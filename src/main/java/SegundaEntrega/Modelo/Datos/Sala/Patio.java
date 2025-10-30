package SegundaEntrega.Modelo.Datos.Sala;

import SegundaEntrega.Modelo.Datos.Personas.Paciente;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Optional;

/**
 * Representa el patio común donde esperan los pacientes que no pudieron
 * acceder a la sala de espera privada.
 * <p>
 * Se implementa como una <b>cola FIFO</b> utilizando un {@link Deque},
 * garantizando que los pacientes sean atendidos en el orden de llegada.
 * </p>
 *
 * <p><b>Patrón aplicado:</b></p>
 * <ul>
 *   <li><b>Singleton</b>: solo existe una instancia global del patio
 *       accesible mediante {@link #getInstance()}.</li>
 *   <li>Permite centralizar el manejo de los pacientes en espera.</li>
 * </ul>
 *
 * <p><b>Rol en el sistema:</b></p>
 * <ul>
 *   <li>Parte del módulo de resolución de conflictos de sala de espera.</li>
 *   <li>Recibe los pacientes que pierden o empatan una comparación de prioridad
 *       en {@link SalaDeEsperaPrivada}.</li>
 *   <li>Permite consultar y extraer los pacientes en orden de llegada.</li>
 * </ul>
 *
 * <p><b>Casos de uso:</b></p>
 * <ul>
 *   <li>Ingreso de pacientes derivados desde la sala privada.</li>
 *   <li>Gestión de la cola de espera para posterior atención.</li>
 *   <li>Consulta del estado o tamaño de la cola del patio.</li>
 * </ul>
 */


public class Patio {

    private static final Patio INSTANCE = new Patio();
    private final Deque<Paciente> cola = new ArrayDeque<>();
    //deque se usa como cola fifo

    /** Constructor privado para garantizar una única instancia (Singleton). */
    private Patio() {}

    /**
     * Obtiene la instancia única del patio.
     *
     * @return referencia global al único objeto {@code Patio}
     *
     * <p><b>Postcondición:</b> siempre devuelve la misma instancia en todo el sistema.</p>
     *
     */
    public static Patio getInstance() {
        Patio ref = null;
        ref = INSTANCE;
        return ref;
    }


    /**
     * Mueve un paciente al final de la cola del patio.
     *
     * @param p paciente a agregar (no nulo)
     *
     * <p><b>Precondición:</b> el paciente debe estar correctamente inicializado.</p>
     * <p><b>Postcondición:</b> el paciente se agrega al final de la cola FIFO.</p>
     *
     */
    public void moverAlPatio(Paciente p) {
        boolean pacienteValido = (p != null);
        if (pacienteValido) {
            cola.addLast(p);
        }
    }


    /**
     * Obtiene y remueve el siguiente paciente de la cola, si existe.
     *
     * @return un {@link Optional} que contiene el siguiente paciente, o vacío si no hay nadie en espera
     *
     * <p><b>Precondición:</b> la instancia del patio debe existir (Singleton ya inicializado).</p>
     * <p><b>Postcondición:</b> si la cola no está vacía, se devuelve y elimina el primer paciente.</p>
     *
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
     * Retorna la cantidad de pacientes actualmente en el patio.
     *
     * @return número de pacientes en la cola (≥ 0)
     *
     * <p><b>Postcondición:</b> el valor retornado refleja el estado actual de la cola.</p>
     *
     */
    public int cantidadEnPatio() {
        int cantidad = 0;
        cantidad = cola.size();
        return cantidad;
    }

}
