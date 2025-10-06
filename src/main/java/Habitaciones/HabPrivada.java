package Habitaciones;

import Configuracion.CatalogoCostos;
import java.math.*;

/**
 * Habitación privada: costo por día multiplicado por un factor según tramos de estancia.
 * <ul>
 * <li>Día 1: factor {@code factorPrivadaDia1}</li>
 * <li>Días 2..5: factor {@code factorPrivadaHasta5Dias}</li>
 * <li>Días &gt; 5: factor {@code factorPrivadaMasDe5Dias}</li>
 * </ul>
 * Siempre se adiciona el costo de asignación.
 */

public final class HabPrivada extends Habitacion {

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
