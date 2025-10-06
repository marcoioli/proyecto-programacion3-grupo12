package Habitaciones;

import Configuracion.CatalogoCostos;

import java.util.Objects;

public abstract class Habitacion {

    /** Identificador/código interno de la habitación. */
    private final String id;
    /**
     * Crea una habitación base.
     *
     * @param id identificador único lógico (no nulo ni vacío)
     *
     */
    protected Habitacion(String id) {
        // verificar si ID ya existe?? excepcion
        this.id = id;
    }

    /** @return identificador/código de la habitación */
    public String getId() { return id; }


    /**
     * Calcula el costo total de una internación en esta habitación.
     * Recibe dias>0
     * @param dias cantidad de días de internación (debe ser &gt; 0)
     * @param costos catálogo de costos y parámetros del sistema
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
