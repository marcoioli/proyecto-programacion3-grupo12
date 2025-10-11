package Habitaciones;

import Configuracion.CatalogoCostos;

import java.util.Objects;

public abstract class Habitacion {

    /** Identificador/codigo interno de la habitacion. */
    private final String id;
    /**
     * Crea una habitacion base.
     *
     * @param id identificador unico logico (no nulo ni vacio)
     *
     */
    protected Habitacion(String id) {
        // verificar si ID ya existe?? excepcion
        this.id = id;
    }

    /** @return identificador/codigo de la habitacion */
    public String getId() { return id; }


    /**
     * Calcula el costo total de una internacion en esta habitacion.
     * Recibe dias>0
     * @param dias cantidad de días de internacion (debe ser &gt; 0)
     * @param costos catalogo de costos y parametros del sistema
     * @return costo total calculado en {@code double}
     * @throws IllegalArgumentException si {@code dias} &lt;= 0 o {@code costos} es nulo
     */

    public final double calcularCosto(int dias, CatalogoCostos costos) {
        //manejo de excepcioen
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
