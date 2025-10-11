package Facturacion.Facturable;

import Habitaciones.Internacion;

/**
 * Representa una internación registrada como un ítem facturable dentro de una factura.
 * <p>
 * Implementa la interfaz {@link IFacturable}, lo que permite tratar las internaciones
 * de la misma forma que otros conceptos facturables (por ejemplo, {@link Consulta}).
 * </p>
 *
 * <p><b>Rol en el sistema:</b></p>
 * <ul>
 *   <li>Elemento facturable que representa la estadía de un paciente en una habitación.</li>
 *   <li>Es incluido como un ítem dentro de la factura del paciente al momento del egreso.</li>
 * </ul>
 *
 * <p><b>Casos de uso:</b></p>
 * <ul>
 *   <li>Construcción de facturas en el módulo de egreso.</li>
 *   <li>Representación de internaciones en reportes de facturación.</li>
 * </ul>
 */

public final class ItemInternacion implements IFacturable {

    private final Internacion internacion;
    private final double costo;

    /**
     * Crea un nuevo ítem facturable correspondiente a una internación.
     *
     * @param internacion instancia de {@link Internacion} asociada a este ítem
     * @param costo monto total de la internación, expresado en {@code double}
     *
     * <p><b>Precondiciones:</b></p>
     * <ul>
     *   <li>{@code internacion} debe haber sido registrada y completada (con días y habitación válidos).</li>
     *   <li>{@code costo} debe ser mayor o igual a 0.</li>
     * </ul>
     *
     * <p><b>Postcondición:</b> se crea un objeto {@code ItemInternacion} inmutable listo para incluirse en una factura.</p>
     */
    public ItemInternacion(Internacion internacion, double costo) {
        this.internacion = internacion;
        this.costo = costo;
    }

    @Override
    public double subtotal() {
        return costo;
    }


    /**
     * Obtiene la internación asociada a este ítem facturable.
     *
     * @return instancia de {@link Internacion}
     */
    public Internacion getInternacion() {
        return internacion;
    }

}