package SegundaEntrega.Patrones.PatronState;

import SegundaEntrega.Modelo.Negocio.Ambulancia;
import java.util.Random;

public class EstadoEnTaller implements IEstadoAmbulancia {

    private final Ambulancia ambulancia;
    private final Random random = new Random();

    public EstadoEnTaller(Ambulancia ambulancia) {
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
        System.out.println("   Ambulancia En Taller: No puede atender domicilio.");
        // permanece en 5)
    }

    @Override
    public void solicitarTraslado() {
        System.out.println("   Ambulancia En Taller: No puede realizar traslado.");
        // informa que no puede
    }

    @Override
    public void solicitarMantenimiento() {
        // Si el operario pide mantenimiento DE NUEVO mientras ya está en taller.
        System.out.println("   Ambulancia ya está En Taller.");
        // permanece en 5)
        // La tabla dice "pasa a 6", lo cual es raro si ya está en taller.
        // Interpretaremos que "solicitarMantenimiento" en este estado significa
        // "el mantenimiento ha terminado". Cambiamos a EstadoRegresandoDelTaller.
        System.out.println("   Ambulancia En Taller -> Regresando Del Taller (Mantenimiento finalizado)");
        ambulancia.setEstado(new EstadoRegresandoDelTaller(ambulancia));

    }

    @Override
    public void retornarAClinica() {
        System.out.println("   Ambulancia En Taller: No puede retornar a clínica ahora.");
        // permanece en 5)
    }

    // --- Verificaciones ---
    @Override
    public boolean puedeAtenderDomicilio() { return false; }
    @Override
    public boolean puedeTrasladar() { return false; }
    @Override
    public boolean puedeIrATaller() { return false; } // Ya está en taller
}