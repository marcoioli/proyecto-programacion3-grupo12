package SegundaEntrega.Patrones.PatronState;

import SegundaEntrega.Modelo.Negocio.Ambulancia;
import java.util.Random;

public class EstadoRegresandoDelTaller implements IEstadoAmbulancia {

    private final Ambulancia ambulancia;
    private final Random random = new Random();

    public EstadoRegresandoDelTaller(Ambulancia ambulancia) {
        this.ambulancia = ambulancia;
        simularTiempoDeAtencion();
    }

    /**
     * Inicia un nuevo hilo (timer) para simular la duración de la
     * atención a domicilio. Cuando termina, llama a retornarAClinica()
     * para transicionar al siguiente estado.
     */
    // Archivo: EstadoRegresandoDelTaller.java

    /**
     * Simula el tiempo de viaje de regreso desde el taller.
     * Cuando termina, llama a retornarAClinica() para pasar a Disponible.
     */
    private void simularTiempoDeAtencion() {
        System.out.println("   ... Ambulancia REGRESANDO DE TALLER (simulando tiempo)..."); // <-- Mensaje corregido
        new Thread(() -> {
            try {
                Thread.sleep(random.nextInt(3000) + 2000); // Simular viaje
                System.out.println("   ... Ambulancia LLEGÓ a la clínica desde el taller."); // <-- Mensaje corregido
                ambulancia.retornarAClinica(); // Transiciona a Estado 1 (Disponible)
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("WARN: Hilo de simulación de regreso de taller interrumpido.");
            }
        }).start();
    }

    @Override
    public void solicitarAtencionDomicilio() {
        System.out.println("   Ambulancia Regresando Del Taller: No puede atender domicilio ahora.");
        // permanece en 6)
    }

    @Override
    public void solicitarTraslado() {
        System.out.println("   Ambulancia Regresando Del Taller: No puede realizar traslado ahora.");
        // informa que no puede
    }

    @Override
    public void solicitarMantenimiento() {
        System.out.println("   Ambulancia Regresando Del Taller: No puede volver a taller ahora.");
        // informa que no puede
    }

    @Override
    public void retornarAClinica() {
        // Se llama cuando finaliza el viaje de regreso del taller
        System.out.println("   Ambulancia Regresando Del Taller -> Disponible (Llegó a la clínica)");
        ambulancia.setEstado(new EstadoDisponible(ambulancia));
    }

    // --- Verificaciones ---
    @Override
    public boolean puedeAtenderDomicilio() { return false; }
    @Override
    public boolean puedeTrasladar() { return false; }
    @Override
    public boolean puedeIrATaller() { return false; }
}