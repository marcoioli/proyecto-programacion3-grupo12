package SegundaEntrega.Patrones.PatronState;

import SegundaEntrega.Modelo.Negocio.Ambulancia; // Importar el contexto

/**
 * Estado que representa a la ambulancia mientras traslada un paciente a la clínica.
 * Corresponde al estado (2) de la tabla de transiciones.
 */
public class EstadoTrasladandoPaciente implements IEstadoAmbulancia {

    private final Ambulancia ambulancia; // Referencia al contexto (la ambulancia)

    /**
     * Constructor. Asocia este estado con el contexto (ambulancia).
     * @param ambulancia La ambulancia a la que este estado manejará.
     */
    public EstadoTrasladandoPaciente(Ambulancia ambulancia) {
        this.ambulancia = ambulancia;
    }

    @Override
    public void solicitarAtencionDomicilio() {
        // Según la tabla: permanece en 2)
        System.out.println("   Ambulancia (Trasladando Paciente): No puede atender domicilio ahora. Solicitud ignorada.");
    }

    @Override
    public void solicitarTraslado() {
        // Según la tabla: informa que no puede (permanece en 2)
        System.out.println("   Ambulancia (Trasladando Paciente): Ya está en un traslado. Solicitud ignorada.");
    }

    @Override
    public void solicitarMantenimiento() {
        // Según la tabla: informa que no puede (permanece en 2)
        System.out.println("   Ambulancia (Trasladando Paciente): No puede ir a taller ahora. Solicitud ignorada.");
    }

    /**
     * Implementación del evento "Retorno automático a clínica".
     * En este estado, interpretamos que este evento significa que el traslado
     * HA FINALIZADO y la ambulancia ha llegado a la clínica.
     * Por lo tanto, transiciona a Disponible (Estado 1).
     *
     * (Nota: La tabla del enunciado indica "permanece en 2" para esta celda,
     * lo cual crearía un estado sin salida. Esta implementación asume que
     * lógicamente debe pasar a Disponible al finalizar el traslado.)
     */
    @Override
    public void retornarAClinica() {
        System.out.println("   Ambulancia (Trasladando Paciente) -> Disponible (Traslado finalizado, llegó a la clínica)");
        // Transición: 2 -> 1
        this.ambulancia.setEstado(new EstadoDisponible(this.ambulancia));
    }

    // --- Verificaciones para el Monitor (wait/notify) ---

    /**
     * @return false (No puede iniciar una nueva atención domicilio)
     */
    @Override
    public boolean puedeAtenderDomicilio() {
        return false;
    }

    /**
     * @return false (Ya está en un traslado)
     */
    @Override
    public boolean puedeTrasladar() {
        return false;
    }

    /**
     * @return false (No puede ir a taller mientras traslada un paciente)
     */
    @Override
    public boolean puedeIrATaller() {
        return false;
    }
}