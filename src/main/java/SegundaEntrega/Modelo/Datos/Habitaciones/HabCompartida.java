package SegundaEntrega.Modelo.Datos.Habitaciones;

import SegundaEntrega.Modelo.Datos.Configuraciones.CatalogoCostos;

/**
 * Representa una habitación compartida dentro de la clínica.
 * <p>
 * Extiende {@link Habitacion} e implementa el cálculo de costo total
 * según las reglas del sistema:
 * </p>
 *
 *
 * <p><b>Rol en el sistema:</b></p>
 * <ul>
 *   <li>Subclase concreta del patrón de herencia {@link Habitacion}.</li>
 *   <li>Utilizada en el cálculo de internaciones facturables mediante {@code ItemInternacion}.</li>
 *   <li>Su costo se obtiene a partir de los valores definidos en {@link CatalogoCostos}.</li>
 * </ul>
 *
 * <p><b>Casos de uso:</b></p>
 * <ul>
 *   <li>Asignación de pacientes a habitaciones compartidas durante internaciones.</li>
 *   <li>Cálculo de costos en el módulo de facturación.</li>
 * </ul>
 */

public final class HabCompartida extends Habitacion {

    /**
     * Crea una nueva habitación compartida con el identificador especificado.
     *
     * @param id identificador único de la habitación (por ejemplo, "C1", "C2", etc.)
     * <p><b>Precondiciones:</b></p>
     * <ul>
     *   <li>{@code id} debe ser una cadena no nula ni vacía.</li>
     * </ul>
     *
     * <p><b>Postcondición:</b> se crea una habitación compartida identificada y lista para su asignación.</p>
     */
    public HabCompartida(String id) {
        super(id);
    }

    @Override
    protected double CalcularCosto(int dias, CatalogoCostos c) {
        double totalDias = c.getCostoHabCompartida()*dias;
        double total = c.getCostoAsignacion()+ totalDias;
        return total;
    }
}