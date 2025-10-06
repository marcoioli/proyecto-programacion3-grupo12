package Habitaciones;
import java.util.Objects;


/**
 * Agrupa una habitación con la cantidad de días de internación.
 * <p>Entidad de transporte simple (DTO/record-equivalente) con validaciones mínimas.</p>
 */
public final class Internacion {

    private final Habitacion habitacion;
    private final int dias;

    /**
     * Crea una internación.
     * @param habitacion habitación asignada (no nula)
     * @param dias cantidad de días (&gt; 0)
     *
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
