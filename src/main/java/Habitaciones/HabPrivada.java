package Habitaciones;

import Configuracion.CatalogoCostos;
import java.math.*;
import Excepciones.*;

/**
 * Representa una habitación privada dentro de la clínica.
 * <p><b>Reglas de cálculo:</b></p>
 * <ul>
 *   <li><b>Día 1:</b> se aplica {@code factorPrivadaDia1}.</li>
 *   <li><b>Días 2 a 5:</b> se aplica {@code factorPrivadaHasta5Dias}.</li>
 *   <li><b>Más de 5 días:</b> se aplica {@code factorPrivadaMasDe5Dias}.</li>
 * </ul>
 * <p>Siempre se adiciona el costo de asignación base definido en {@link CatalogoCostos}.</p>
 *
 * <p><b>Rol en el sistema:</b></p>
 * <ul>
 *   <li>Subclase concreta de {@link Habitacion}.</li>
 *   <li>Utilizada en internaciones individuales donde el paciente ocupa una habitación exclusiva.</li>
 *   <li>Su costo se calcula según los valores configurados en {@link CatalogoCostos}.</li>
 * </ul>
 *
 * <p><b>Casos de uso:</b></p>
 * <ul>
 *   <li>Asignación de pacientes a habitaciones privadas durante internaciones.</li>
 *   <li>Cálculo de costos en facturación mediante {@code ItemInternacion}.</li>
 * </ul>
 */

public final class HabPrivada extends Habitacion {

    /**
     * Crea una nueva habitación privada con el identificador especificado.
     *
     * @param id identificador único lógico (por ejemplo, "P1", "P2", etc.)
     *
     * <p><b>Precondiciones:</b></p>
     * <ul>
     *   <li>{@code id} debe ser una cadena no nula ni vacía.</li>
     *   <li>No debe existir otra habitación con el mismo identificador.</li>
     * </ul>
     *
     * <p><b>Postcondición:</b> se crea una habitación privada registrada en el sistema.</p>
     */
    public HabPrivada(String id) {
        super(id);
    }

    @Override
    protected double CalcularCosto(int dias, CatalogoCostos c) {
        double factor;
        if (dias == 1) {
            factor = c.getFactorPrivadaDia1();
        } else if (dias <= 5) {
            factor = c.getFactorPrivadaHasta5Dias();
        } else {
            factor = c.getFactorPrivadaMasDe5Dias();
        }

        double base = c.getCostoHabPrivada() * dias;
        double total = c.getCostoAsignacion() + (base * factor);
        return total;
    }
}
