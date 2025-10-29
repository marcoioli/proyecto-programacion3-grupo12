package SegundaEntrega.Patrones.PatronState;

import SegundaEntrega.Modelo.Negocio.Ambulancia; // Importar Ambulancia (Contexto)

/**
 * Interfaz que define el comportamiento de la ambulancia según su estado.
 * Cada estado concreto implementará esta interfaz.
 */
public interface IEstadoAmbulancia {

    /**
     * Acción a realizar cuando se solicita atención a domicilio.
     * La implementación decidirá si cambia el estado o informa que no puede.
     * @param ambulancia El contexto (la ambulancia) cuyo estado puede cambiar.
     */
    void solicitarAtencionDomicilio();

    /**
     * Acción a realizar cuando se solicita traslado a la clínica.
     * @param ambulancia El contexto.
     */
    void solicitarTraslado();

    /**
     * Acción a realizar cuando se solicita mantenimiento.
     * @param ambulancia El contexto.
     */
    void solicitarMantenimiento();

    /**
     * Acción a realizar para el retorno automático a la clínica.
     * @param ambulancia El contexto.
     */
    void retornarAClinica();

    // Métodos para verificar si una acción es posible en el estado actual
    // Estos los usará la Ambulancia (monitor) en las condiciones de wait()

    /**
     * Verifica si en el estado actual se puede atender una solicitud de domicilio.
     * @return true si es posible, false si no.
     */
    boolean puedeAtenderDomicilio();

    /**
     * Verifica si en el estado actual se puede atender una solicitud de traslado.
     * @return true si es posible, false si no.
     */
    boolean puedeTrasladar();

    /**
     * Verifica si en el estado actual se puede enviar la ambulancia a taller.
     * @return true si es posible, false si no.
     */
    boolean puedeIrATaller();

    // Podríamos añadir un método puedeRetornar() si fuera necesario verificarlo,
    // pero usualmente el retorno es una acción interna del estado.
}