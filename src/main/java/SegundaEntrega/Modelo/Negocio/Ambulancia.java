package SegundaEntrega.Modelo.Negocio;

import SegundaEntrega.Patrones.PatronState.*; // Importar todos los estados e interfaz
import java.util.Observable; // Para Observer

/**
 * Representa la ambulancia de la clínica. Es el contexto del patrón State
 * y actúa como monitor para la concurrencia.
 * Notifica a los observadores sobre cambios en su estado.
 */
public class Ambulancia extends Observable {

    private IEstadoAmbulancia estadoActual;
    // Podríamos añadir otros atributos si fueran necesarios (ej: ubicación, patente, etc.)

    /**
     * Constructor. Inicia la ambulancia en estado Disponible.
     */
    public Ambulancia() {
        // Estado inicial
        this.estadoActual = new EstadoDisponible(this);
        System.out.println("Ambulancia creada en estado: Disponible");
        notificarCambioEstado(); // Notificar estado inicial
    }

    /**
     * Cambia el estado actual de la ambulancia y notifica a los observadores.
     * Este método es llamado por las clases Estado concretas.
     * @param nuevoEstado El nuevo estado a establecer.
     */
    public void setEstado(IEstadoAmbulancia nuevoEstado) {
        System.out.println("Transición de estado: " + estadoActual.getClass().getSimpleName() + " -> " + nuevoEstado.getClass().getSimpleName());
        this.estadoActual = nuevoEstado;
        notificarCambioEstado();
    }

    /**
     * Devuelve el estado actual (principalmente para testing o información).
     * @return El objeto de estado actual.
     */
    public IEstadoAmbulancia getEstadoActual() {
        return estadoActual;
    }

    /**
     * Devuelve el nombre simple del estado actual.
     * @return String con el nombre del estado (ej: "EstadoDisponible").
     */
    public String getNombreEstadoActual() {
        return estadoActual.getClass().getSimpleName();
    }


    // --- Métodos de Solicitud (Actúan como Monitor) ---
    // Usan synchronized y wait/notifyAll para manejar concurrencia

    /**
     * Intenta procesar una solicitud de atención a domicilio.
     * Delega la acción al estado actual. Bloquea si no puede atender ahora.
     * @param solicitante Identificador del solicitante (ej: DNI del asociado).
     */
    public synchronized void solicitarAtencionDomicilio(String solicitante) {
        System.out.println("--> " + solicitante + " solicita ATENCION A DOMICILIO. Estado actual: " + getNombreEstadoActual());
        while (!estadoActual.puedeAtenderDomicilio()) { // Condición de espera
            System.out.println("    " + solicitante + " esperando para At. Domicilio (Ambulancia ocupada: " + getNombreEstadoActual() + ")");
            try {
                wait(); // Libera el lock y espera
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restablece el flag de interrupción
                System.err.println("WARN: " + solicitante + " interrumpido mientras esperaba At. Domicilio.");
                return; // Salir si fue interrumpido
            }
            System.out.println("    " + solicitante + " despierta para reintentar At. Domicilio. Estado ahora: " + getNombreEstadoActual());
        }
        // Si sale del while, puede atender
        System.out.println("    Atendiendo At. Domicilio para " + solicitante + ". Estado: " + getNombreEstadoActual());
        estadoActual.solicitarAtencionDomicilio(); // El estado se encargará de la transición
        notifyAll(); // Despierta a otros hilos por si el nuevo estado les sirve
    }

    /**
     * Intenta procesar una solicitud de traslado a la clínica.
     * Delega la acción al estado actual. Bloquea si no puede atender ahora.
     * @param solicitante Identificador del solicitante (ej: DNI del asociado).
     */
    public synchronized void solicitarTraslado(String solicitante) {
        System.out.println("--> " + solicitante + " solicita TRASLADO. Estado actual: " + getNombreEstadoActual());
        while (!estadoActual.puedeTrasladar()) { // Condición de espera
            System.out.println("    " + solicitante + " esperando para Traslado (Ambulancia ocupada: " + getNombreEstadoActual() + ")");
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("WARN: " + solicitante + " interrumpido mientras esperaba Traslado.");
                return;
            }
            System.out.println("    " + solicitante + " despierta para reintentar Traslado. Estado ahora: " + getNombreEstadoActual());
        }
        System.out.println("    Atendiendo Traslado para " + solicitante + ". Estado: " + getNombreEstadoActual());
        estadoActual.solicitarTraslado();
        notifyAll();
    }

    /**
     * Intenta procesar una solicitud de mantenimiento.
     * Delega la acción al estado actual. Bloquea si no puede atender ahora.
     * @param solicitante Identificador del solicitante (ej: "Operario").
     */
    public synchronized void solicitarMantenimiento(String solicitante) {
        System.out.println("--> " + solicitante + " solicita MANTENIMIENTO. Estado actual: " + getNombreEstadoActual());
        while (!estadoActual.puedeIrATaller()) { // Condición de espera
            System.out.println("    " + solicitante + " esperando para Mantenimiento (Ambulancia ocupada: " + getNombreEstadoActual() + ")");
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("WARN: " + solicitante + " interrumpido mientras esperaba Mantenimiento.");
                return;
            }
            System.out.println("    " + solicitante + " despierta para reintentar Mantenimiento. Estado ahora: " + getNombreEstadoActual());
        }
        System.out.println("    Atendiendo Mantenimiento solicitado por " + solicitante + ". Estado: " + getNombreEstadoActual());
        estadoActual.solicitarMantenimiento();
        notifyAll();
    }

    /**
     * Simula el evento de retorno automático a la clínica (ej: fin de viaje).
     * Delega la acción al estado actual. Este método podría no necesitar ser synchronized
     * si solo es llamado internamente por los estados o un temporizador controlado.
     * Sin embargo, si puede ser invocado externamente de forma concurrente, debería serlo.
     * Por simplicidad y seguridad inicial, lo hacemos synchronized.
     */
    public synchronized void retornarAClinica() {
        System.out.println("--> Evento: RETORNO AUTOMATICO. Estado actual: " + getNombreEstadoActual());
        // Aquí no hay espera, el retorno siempre se intenta
        estadoActual.retornarAClinica();
        // No es necesario verificar 'puedeRetornar()' porque la lógica está en el estado.
        // El estado decidirá si la transición es válida (ej: si está regresando, pasa a disponible).
        notifyAll(); // Despertar hilos por si el nuevo estado les permite actuar
    }


    // --- Notificación para Observer ---

    /**
     * Notifica a los observadores (Vista) que el estado ha cambiado.
     * Envía el nombre del estado actual como argumento.
     */
    private void notificarCambioEstado() {
        setChanged(); // Marca que hubo un cambio
        notifyObservers(getNombreEstadoActual()); // Envía el nombre del estado actual
    }
}