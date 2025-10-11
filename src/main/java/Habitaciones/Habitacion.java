package Habitaciones;

import Configuracion.CatalogoCostos;

import java.util.*;

public abstract class Habitacion {


    /** Identificador/código interno de la habitación.
     * si este es nulo o ya esta usado lanza excepcion */
    private final String id;
    private static final Set<String> habitaciones = new HashSet<>();
    /**
     * Crea una habitacion base.
     *
     * @param id identificador unico logico (no nulo ni vacio)
     *
     */
    protected Habitacion(String id) {

        if (id == null || id.isEmpty())
            throw new IllegalArgumentException("El identificador no puede ser nulo ni vacío.");
        if (habitaciones.contains(id))
            throw new IllegalArgumentException("El identificador '" + id + "' ya está en uso.");
        this.id = id;
        habitaciones.add(id);
    }

    /** @return identificador/codigo de la habitacion */
    public String getId() { return id; }


    /**
     * Calcula el costo total de una internacion en esta habitacion.
     * Recibe dias>0
     * @param dias cantidad de días de internacion (debe ser &gt; 0)
     * @param costos catalogo de costos y parametros del sistema
     * @return costo total calculado en {@code double}
     * @throws NullPointerException si {@code costos} es nulo
     */

    public final double calcularCosto(int dias, CatalogoCostos costos) throws NullPointerException {
        //manejo de excepciones
        Objects.requireNonNull(costos, "El catálogo de costos no puede ser nulo");
        return CalcularCosto(dias, costos);
    }

    /**
     * Implementacion especifica por tipo de habitacion
     * @param dias
     * @param costos
     * @return
     */

    protected abstract double CalcularCosto(int dias, CatalogoCostos costos);


}
