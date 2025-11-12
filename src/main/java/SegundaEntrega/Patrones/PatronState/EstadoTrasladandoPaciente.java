package SegundaEntrega.Patrones.PatronState;

import SegundaEntrega.Modelo.Negocio.Ambulancia;
import java.util.Random;

/**
 * Estado que representa a la ambulancia mientras traslada un paciente a la clínica.
 * Corresponde al estado (2) de la tabla de transiciones.
 */
public class EstadoTrasladandoPaciente implements IEstadoAmbulancia {

    private final Ambulancia ambulancia; // Referencia al contexto (la ambulancia)
    private final Random random = new Random();

    /**
     * Constructor. Asocia este estado con el contexto (ambulancia).
     * @param ambulancia La ambulancia a la que este estado manejará.
     */
    public EstadoTrasladandoPaciente(Ambulancia ambulancia) {
        this.ambulancia = ambulancia;
        simularTiempoDeAtencion();
    }

    /**
     * Inicia un nuevo hilo (timer) para simular la duración de la
     * atención a domicilio. Cuando termina, llama a retornarAClinica()
     * para transicionar al siguiente estado.
     */
    private void simularTiempoDeAtencion() {
        System.out.println("   ... Ambulancia ocupada en domicilio (simulando tiempo)...");
        new Thread(() -> {
            try {
                Thread.sleep(random.nextInt(3000) + 2000);
                System.out.println("   ... Atención a domicilio finalizada.");
                ambulancia.retornarAClinica();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("WARN: Hilo de simulación de atención interrumpido.");
            }
        }).start();
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