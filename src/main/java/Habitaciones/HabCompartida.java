package Habitaciones;

import Configuracion.CatalogoCostos;

/**
 * Habitación compartida: costo lineal por día + costo de asignación.
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