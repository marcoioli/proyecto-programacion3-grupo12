package Habitaciones;
import java.util.Objects;



/**
 * Representa una internación de un paciente, asociando una {@link Habitacion}
 * con la cantidad de días de estadía.
 *
 * <p><b>Rol en el sistema:</b></p>
 * <ul>
 *   <li>Asocia la habitación utilizada con la duración de la internación.</li>
 *   <li>Se utiliza en {@link Facturacion.Facturable.ItemInternacion} como parte del cálculo de costos.</li>
 * </ul>
 *
 * <p><b>Casos de uso:</b></p>
 * <ul>
 *   <li>Registrar una internación en el sistema antes de generar la factura correspondiente.</li>
 *   <li>Calcular el costo de internación a partir del tipo de habitación y los días de estadía.</li>
 * </ul>
 */

public final class Internacion {

    private final Habitacion habitacion;
    private final int dias;

    /**
     * Crea una nueva internación asociando una habitación y la cantidad de días de estadía.
     *
     * @param habitacion habitación asignada (no nula)
     * @param dias cantidad de días de internación (&gt; 0)
     *
     * <p><b>Precondiciones:</b></p>
     * <ul>
     *   <li>{@code habitacion} debe ser una instancia válida de {@link Habitacion}.</li>
     *   <li>{@code dias} debe ser mayor que cero.</li>
     * </ul>
     *
     * <p><b>Postcondición:</b> se crea una internación inmutable con la habitación y duración indicadas.</p>
     *
     * <p><b>Ejemplo:</b></p>
     * <pre>
     * Habitacion hab = new HabPrivada("P1");
     * Internacion i = new Internacion(hab, 5);
     * System.out.println(i.getHabitacion().getId()); // "P1"
     * System.out.println(i.getDias()); // 5
     * </pre>
     */
    public Internacion(Habitacion habitacion, int dias) {
        Objects.requireNonNull(habitacion, "La habitación no puede ser nula");
        //verificar qeu dias no sea 0?? precondicion??
        this.habitacion = habitacion;
        this.dias = dias;
    }


    /** @return habitación asignada */
    public Habitacion getHabitacion() {
        return habitacion;
    }


    /** @return cantidad de días de internación */
    public int getDias() {
        return dias;
    }
}
