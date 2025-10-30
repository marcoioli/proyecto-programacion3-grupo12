package SegundaEntrega.Modelo.Negocio;

import SegundaEntrega.Modelo.Datos.Personas.Asociado;
import SegundaEntrega.Modelo.Datos.Personas.Operario; // Asumiendo que tenemos la clase Operario

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Orquesta la simulación concurrente de solicitudes a la ambulancia.
 */
public class Simulador {

    private final Ambulancia ambulancia;
    private final GestorAsociados gestorAsociados; // Para obtener la lista de asociados
    private final Operario operario; // El operario que solicita mantenimiento
    private ExecutorService executorService; // Gestiona los hilos
    private volatile boolean simulacionActiva = false; // Controla si la simulación está corriendo

    /**
     * Constructor.
     * @param ambulancia La instancia compartida de la ambulancia.
     * @param gestorAsociados El gestor para obtener los asociados.
     * @param operario El operario de la simulación.
     */
    public Simulador(Ambulancia ambulancia, GestorAsociados gestorAsociados, Operario operario) {
        this.ambulancia = ambulancia;
        this.gestorAsociados = gestorAsociados;
        this.operario = operario;
    }

    /**
     * Inicia la simulación creando y ejecutando los hilos para asociados y operario.
     * @param cantSolicitudesPorAsociado Número de solicitudes que hará cada asociado.
     */
    public synchronized void iniciarSimulacion(int cantSolicitudesPorAsociado) {
        if (simulacionActiva) {
            System.out.println("SIMULADOR: La simulación ya está en curso.");
            return;
        }
        if (cantSolicitudesPorAsociado <= 0) {
            System.out.println("SIMULADOR: Cantidad de solicitudes debe ser mayor a 0.");
            return;
        }

        System.out.println("\n--- INICIANDO SIMULACION ---");
        System.out.println("Solicitudes por asociado: " + cantSolicitudesPorAsociado);

        List<Asociado> asociados = gestorAsociados.getListadoAsociados();
        if (asociados.isEmpty()) {
            System.out.println("SIMULADOR: No hay asociados para iniciar la simulación.");
            return;
        }

        // Usar un ExecutorService es más moderno y manejable que crear Threads directamente
        // Se crea un pool con un hilo por asociado + 1 para el operario
        executorService = Executors.newFixedThreadPool(asociados.size() + 1);
        simulacionActiva = true;

        // Crear y lanzar hilos (tareas) para cada asociado
        for (Asociado a : asociados) {
            AsociadoRunnable tareaAsociado = new AsociadoRunnable(a, ambulancia, cantSolicitudesPorAsociado);
            executorService.submit(tareaAsociado);
            System.out.println("SIMULADOR: Iniciando tarea para asociado " + a.getNombreCompleto());
        }

        // Crear y lanzar hilo (tarea) para el operario
        OperarioRunnable tareaOperario = new OperarioRunnable(operario, ambulancia, this); // Pasa el simulador para control
        executorService.submit(tareaOperario);
        System.out.println("SIMULADOR: Iniciando tarea para operario " + operario.getNombreCompleto());

        System.out.println("SIMULADOR: Todas las tareas han sido iniciadas.");
        // El ExecutorService maneja los hilos. La simulación corre en background.
        // Podríamos añadir lógica aquí para esperar que termine si fuera necesario,
        // pero el enunciado pide finalización manual o por completar tareas.
    }

    /**
     * Intenta finalizar la simulación de forma ordenada.
     * Pide a los hilos que terminen y espera un tiempo prudencial.
     */
    public synchronized void finalizarSimulacion() {
        if (!simulacionActiva || executorService == null) {
            System.out.println("SIMULADOR: La simulación no está activa.");
            return;
        }

        System.out.println("\n--- FINALIZANDO SIMULACION ---");
        simulacionActiva = false; // Señal para que los hilos terminen sus bucles si lo verifican

        // Intenta apagar el executor service ordenadamente
        executorService.shutdown(); // No acepta nuevas tareas, espera que las actuales terminen
        try {
            // Espera un tiempo máximo para que terminen las tareas actuales
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                // Si no terminaron a tiempo, fuerza la detención
                System.err.println("SIMULADOR WARN: Timeout esperando finalización, forzando cierre...");
                executorService.shutdownNow(); // Intenta interrumpir los hilos activos
                // Espera un poco más después de forzar
                if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                    System.err.println("SIMULADOR ERROR: ExecutorService no pudo finalizar.");
                }
            }
        } catch (InterruptedException ie) {
            // Si el hilo actual es interrumpido mientras espera
            System.err.println("SIMULADOR WARN: Interrumpido mientras esperaba finalización.");
            executorService.shutdownNow(); // Intenta forzar de nuevo
            Thread.currentThread().interrupt(); // Restablece el estado de interrupción
        }

        System.out.println("--- SIMULACION FINALIZADA ---");
        // Resetear el executor para posible reinicio (o manejar estado de finalización)
        executorService = null;
    }

    /**
     * Verifica si la simulación está actualmente activa.
     * Usado por los Runnables para saber si deben continuar.
     * @return true si la simulación está activa, false si se pidió finalizar.
     */
    public boolean isSimulacionActiva() {
        return simulacionActiva;
    }
}