package Facturacion.Decorator;
import Configuracion.CatalogoCostos;

/**
 * Representa el valor inicial del honorario médico antes de aplicar cualquier incremento.
 * <p>
 * Esta clase es el <b>componente concreto</b> base del patrón {@code Decorator},
 * sobre el cual se aplican los distintos decoradores de incremento
 * ({@link HonorarioPorEspecialidad}, {@link HonorarioPorPosgrado},
 * {@link HonorarioPorContratacion}, etc.).
 * </p>
 *
 * <p><b>Rol en el patrón:</b></p>
 * <ul>
 *   <li>Implementa la interfaz {@link Honorario}.</li>
 *   <li>Actúa como base para los decoradores que amplían el cálculo de honorarios.</li>
 * </ul>
 *
 * <p><b>Casos de uso:</b></p>
 * <ul>
 *   <li>Como punto de partida en {@link FabricaHonorario} para construir la cadena de decoradores.</li>
 *   <li>Para representar honorarios fijos o sin incrementos.</li>
 * </ul>
 */

public class HonorarioBasico implements Honorario {

    private final double base;

    /**
     * Crea un honorario base tomando el valor inicial desde el catálogo de costos.
     *
     * @param costos instancia de {@link CatalogoCostos} desde donde se obtiene el costo base
     *
     * <p><b>Precondiciones:</b></p>
     * <ul>
     *   <li>{@code costos} debe estar correctamente inicializado.</li>
     *   <li>{@code costos.getCostoAsignacion()} debe ser mayor o igual a 0.</li>
     * </ul>
     *
     * <p><b>Postcondición:</b> se crea un honorario base listo para ser decorado.</p>
     */
    public HonorarioBasico(CatalogoCostos costos) {
        this.base = costos.getCostoAsignacion(); // o un valor específico de honorario base
    }

    /**
     * Crea un honorario base con un valor inicial específico.
     *
     * @param base valor base del honorario
     *
     * <p><b>Precondiciones:</b></p>
     * <ul>
     *   <li>{@code base} debe ser mayor o igual a 0.</li>
     * </ul>
     *
     * <p><b>Postcondición:</b> se crea un honorario base con el valor indicado.</p>
     */
    public HonorarioBasico(double base) {
        this.base = base;
    }

    @Override
    public double calcular() {
        return base;
    }
}
