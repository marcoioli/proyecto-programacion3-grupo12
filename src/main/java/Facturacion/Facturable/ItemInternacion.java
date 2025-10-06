package Facturacion.Facturable;

import Habitaciones.Internacion;

/**
 * Representa una internación como un ítem facturable.
 */
public final class ItemInternacion implements IFacturable {

    private final Internacion internacion;
    private final double costo;

    public ItemInternacion(Internacion internacion, double costo) {
        this.internacion = internacion;
        this.costo = costo;
    }

    @Override
    public double subtotal() {
        return costo;
    }

    public Internacion getInternacion() {
        return internacion;
    }

}