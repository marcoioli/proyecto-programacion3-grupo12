package SegundaEntrega.Modelo.Datos.Sala;

/**
 * Define el contrato del patrón <b>Double Dispatch</b> para la resolución de prioridades
 * entre pacientes de distintos rangos etarios en la sala de espera.
 * <p>
 * Cada implementación concreta ({@link Nino}, {@link Joven}, {@link Mayor})
 * representa un rango etario distinto y define su comportamiento frente
 * a los demás tipos.
 * </p>
 *
 * <p><b>Reglas generales del sistema:</b></p>
 * <ul>
 *   <li><b>Niño</b> tiene prioridad sobre <b>Joven</b> y <b>Mayor</b>.</li>
 *   <li><b>Joven</b> tiene prioridad sobre <b>Mayor</b>, pero no sobre <b>Niño</b>.</li>
 *   <li>Entre iguales (<b>Niño–Niño</b>, <b>Joven–Joven</b>, <b>Mayor–Mayor</b>) → {@link Resolucion#EMPATE}.</li>
 * </ul>
 *
 * <p><b>Rol en el sistema:</b></p>
 * <ul>
 *   <li>Utilizado en {@link SalaDeEsperaPrivada#ingresar(Personas.Paciente)} para resolver conflictos.</li>
 *   <li>Colabora con {@link Resolucion} para expresar el resultado del enfrentamiento.</li>
 * </ul>
 *
 *
 * @see Resolucion
 * @see Nino
 * @see Joven
 * @see Mayor
 */

public interface PrioridadSala {

    /**
     * Determina el resultado de la comparación de prioridad entre esta instancia
     * y otra
     * <p>
     * La implementación concreta no compara directamente los tipos, sino que delega
     * la decisión al otro objeto llamando a su método correspondiente
     * ({@link #vsNino()}, {@link #vsJoven()}, {@link #vsMayor()}).
     * </p>
     *
     * @param otro la prioridad del otro paciente (no nula)
     * @return una instancia de {@link Resolucion} según las reglas del sistema
     *
     * <p><b>Precondición:</b> {@code otro} no puede ser {@code null}.</p>
     * <p><b>Postcondición:</b> se devuelve una resolución coherente con las reglas de prioridad.</p>
     */

    Resolucion resolverContra(PrioridadSala otro);
    /** @return resolución cuando el otro es de tipo Niño */

    /**
     * Define el resultado cuando el otro paciente es de tipo {@link Nino}.
     *
     * @return resolución correspondiente según las reglas del negocio
     */
    Resolucion vsNino();
    /** @return resolución cuando el otro es de tipo Joven */

    /**
     * Define el resultado cuando el otro paciente es de tipo {@link Joven}.
     *
     * @return resolución correspondiente según las reglas del negocio
     */
    Resolucion vsJoven();
    /** @return resolución cuando el otro es de tipo Mayor */

    /**
     * Define el resultado cuando el otro paciente es de tipo {@link Mayor}.
     *
     * @return resolución correspondiente según las reglas del negocio
     */
    Resolucion vsMayor();

}

