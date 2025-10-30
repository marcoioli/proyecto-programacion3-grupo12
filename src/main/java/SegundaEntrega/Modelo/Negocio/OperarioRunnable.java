package SegundaEntrega.Modelo.Negocio;

import SegundaEntrega.Modelo.Datos.Personas.Operario;
import java.util.Random;

/**
 * Tarea (Runnable) que simula las solicitudes de mantenimiento del operario.
 * Esta clase se ejecuta en un hilo separado.
 */
public class OperarioRunnable implements Runnable {

    // --- Atributos ---
    // Necesita referencias al modelo para poder interactuar
    private final Operario operario;
    private final Ambulancia ambulancia;
    private final Simulador simulador; // Para saber cuándo detenerse
    private final Random random = new Random();

    // --- CONSTRUCTOR ---
    /**
     * Constructor de la tarea del operario.
     * Recibe los objetos del modelo con los que necesita interactuar.
     * * @param operario El operario que realiza la acción.
     * @param ambulancia La ambulancia (monitor) a la que se le pedirán solicitudes.
     * @param simulador El gestor de la simulación (para saber si debe parar).
     */
    public OperarioRunnable(Operario operario, Ambulancia ambulancia, Simulador simulador) {
        // Asignar los parámetros recibidos a los atributos internos
        this.operario = operario;
        this.ambulancia = ambulancia;
        this.simulador = simulador;
    }
    // --- FIN DEL CONSTRUCTOR ---


    /**
     * Lógica de ejecución del hilo del operario.
     * Se ejecuta en un bucle mientras la simulación esté activa,
     * pidiendo mantenimiento periódicamente.
     */
    @Override
    public void run() {
        System.out.println("INICIO TAREA: Operario " + operario.getNombreCompleto());

        // El operario intenta pedir mantenimiento periódicamente
        // mientras la simulación esté activa.
        while (simulador.isSimulacionActiva()) {
            try {
                // Espera un tiempo aleatorio antes de solicitar mantenimiento
                // (ej: entre 5 y 15 segundos)
                Thread.sleep(random.nextInt(10000) + 5000);
            } catch (InterruptedException e) {
                // Si la simulación ya no está activa, es una interrupción normal para finalizar
                if (!simulador.isSimulacionActiva()) {
                    System.out.println("Operario " + operario.getNombre() + " interrumpido para finalizar.");
                } else {
                    // Si fue interrumpido por otra razón
                    System.err.println("WARN: Hilo Operario " + operario.getNombre() + " interrumpido inesperadamente.");
                }
                Thread.currentThread().interrupt(); // Restablecer estado de interrupción
                break; // Salir del bucle
            }

            // Verificar de nuevo antes de hacer la solicitud,
            // por si se finalizó mientras dormía
            if (!simulador.isSimulacionActiva()) {
                break;
            }

            System.out.println("Operario " + operario.getNombre() + " intenta solicitar mantenimiento.");
            // Llama al método synchronized de la ambulancia (actúa como monitor)
            ambulancia.solicitarMantenimiento(operario.getNombreCompleto());
            System.out.println("Operario " + operario.getNombre() + " - Solicitud de mantenimiento PROCESADA.");
        }

        System.out.println("FIN TAREA: Operario " + operario.getNombreCompleto());
    }
}