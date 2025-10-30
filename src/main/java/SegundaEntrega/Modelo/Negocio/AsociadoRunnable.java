package SegundaEntrega.Modelo.Negocio;

import SegundaEntrega.Modelo.Datos.Personas.Asociado;
import java.util.Random;

/**
 * Tarea (Runnable) que simula las solicitudes de un asociado a la ambulancia.
 */
public class AsociadoRunnable implements Runnable {

    private final Asociado asociado;
    private final Ambulancia ambulancia;
    private final int cantSolicitudes;
    private final Random random = new Random();

    /**
     * Constructor.
     * @param asociado El asociado que realizará las solicitudes.
     * @param ambulancia La instancia compartida de la ambulancia.
     * @param cantSolicitudes El número total de solicitudes a realizar.
     */
    public AsociadoRunnable(Asociado asociado, Ambulancia ambulancia, int cantSolicitudes) {
        this.asociado = asociado;
        this.ambulancia = ambulancia;
        this.cantSolicitudes = cantSolicitudes;
    }

    @Override
    public void run() {
        System.out.println("INICIO TAREA: Asociado " + asociado.getNombreCompleto() + " (DNI: " + asociado.getDni() + ")");
        for (int i = 1; i <= cantSolicitudes; i++) {
            // Simular un tiempo entre solicitudes
            try {
                Thread.sleep(random.nextInt(3000) + 1000); // Espera entre 1 y 4 segundos
            } catch (InterruptedException e) {
                System.err.println("WARN: Hilo Asociado " + asociado.getDni() + " interrumpido. Finalizando tarea.");
                Thread.currentThread().interrupt(); // Restablecer estado de interrupción
                break; // Salir del bucle si es interrumpido
            }

            // Decidir aleatoriamente qué tipo de solicitud hacer
            boolean solicitarTraslado = random.nextBoolean();

            System.out.println("Asociado " + asociado.getDni() + " - Solicitud #" + i);
            if (solicitarTraslado) {
                ambulancia.solicitarTraslado(asociado.getDni());
            } else {
                ambulancia.solicitarAtencionDomicilio(asociado.getDni());
            }
            System.out.println("Asociado " + asociado.getDni() + " - Solicitud #" + i + " PROCESADA.");

            // Podríamos verificar aquí si se pidió finalizar la simulación externamente
            // if (!simulador.isSimulacionActiva()) { break; }
            // Para esto, el Runnable necesitaría una referencia al Simulador.

        }
        System.out.println("FIN TAREA: Asociado " + asociado.getNombreCompleto() + " completó sus " + cantSolicitudes + " solicitudes.");
    }
}