package Habitaciones;

import Configuracion.CatalogoCostos;
import Excepciones.*;

/**
 * Habitacion compartida: costo lineal por día + costo de asignacion.
 */
public final class HabCompartida extends Habitacion {

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