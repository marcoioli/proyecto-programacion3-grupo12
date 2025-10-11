package Habitaciones;

import Configuracion.CatalogoCostos;
import java.math.*;
import Excepciones.*;

/**
 * Habitacion privada: costo por dia multiplicado por un factor segun tramos de estancia.
 * <ul>
 * <li>Dia 1: factor {@code factorPrivadaDia1}</li>
 * <li>Dias 2..5: factor {@code factorPrivadaHasta5Dias}</li>
 * <li>Dias &gt; 5: factor {@code factorPrivadaMasDe5Dias}</li>
 * </ul>
 * Siempre se adiciona el costo de asignacion.
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
