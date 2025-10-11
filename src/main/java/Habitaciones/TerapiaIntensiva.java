package Habitaciones;

import Configuracion.CatalogoCostos;
import java.math.*;

/**
 * Terapia Intensiva: crecimiento potencial del costo con los dias.
 * <p>
 * Implementacion parametrizable: {@code costoTI * (dias ^ potenciaTI)} + asignacion.
 * Donde {@code potenciaTI} y {@code costoTI} provienen de {@link CatalogoCostos}.
 * </p>
 */

public final class TerapiaIntensiva extends Habitacion {
    public TerapiaIntensiva(String id) {
        super(id);
    }

    @Override
    protected double CalcularCosto(int dias, CatalogoCostos c) {
        double componenteDias = Math.pow(dias, c.getPotenciaTI());
        double total = c.getCostoAsignacion() + c.getCostoTerapiaIntensiva() * componenteDias;
        return total;

    }

}