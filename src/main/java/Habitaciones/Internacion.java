package Habitaciones;
import java.util.Objects;


/**
 * Agrupa una habitacion con la cantidad de dias de internacion.
 * <p>Entidad de transporte simple (DTO/record-equivalente) con validaciones minimas.</p>
 */
public final class Internacion {

    private final Habitacion habitacion;
    private final int dias;

    /**
     * Crea una internacion.
     * @param habitacion habitacion asignada (no nula)
     * @param dias cantidad de dias (&gt; 0)
     *
     */
    public Internacion(Habitacion habitacion, int dias) {
        Objects.requireNonNull(habitacion, "La habitación no puede ser nula");
        //verificar qeu dias no sea 0?? precondicion??
        this.habitacion = habitacion;
        this.dias = dias;
    }


    /** @return habitacion asignada */
    public Habitacion getHabitacion() {
        return habitacion;
    }


    /** @return cantidad de dias de internacion */
    public int getDias() {
        return dias;
    }
}
