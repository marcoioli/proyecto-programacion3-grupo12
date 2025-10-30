package SegundaEntrega.Modelo.Datos.Habitaciones;

import SegundaEntrega.Modelo.Datos.Configuraciones.CatalogoCostos;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Clase abstracta base que representa una habitación de internación dentro de la clínica.
 * <p>
 * Define los atributos y comportamiento común a todas las habitaciones
 * (identificador, unicidad y cálculo de costo). Cada tipo de habitación concreta
 * (compartida, privada o de terapia intensiva) redefine el método
 * {@link #CalcularCosto(int, CatalogoCostos)} con su propia lógica de facturación.
 * </p>
 *
 * <p><b>Rol en el sistema:</b></p>
 * <ul>
 *   <li>Clase base del módulo de habitaciones.</li>
 *   <li>Padre de las subclases concretas {@link HabCompartida}, {@code HabPrivada} y {@code TerapiaIntensiva}.</li>
 *   <li>Colabora con el módulo de facturación al proveer los costos de internación.</li>
 * </ul>
 *
 * <p><b>Casos de uso:</b></p>
 * <ul>
 *   <li>Creación y registro de habitaciones únicas en el sistema.</li>
 *   <li>Cálculo del costo total de una internación según tipo y días.</li>
 * </ul>
 */
public abstract class Habitacion {

    private final String id;
    private static final Set<String> habitaciones = new HashSet<>();

    /**
     * Crea una nueva habitación con un identificador único.
     *
     * @param id identificador lógico (no nulo ni vacío)
     *
     * @throws IllegalArgumentException si {@code id} es {@code null}, vacío o ya está en uso
     *
     * <p><b>Precondiciones:</b></p>
     * <ul>
     *   <li>{@code id} debe ser una cadena no nula ni vacía.</li>
     *   <li>No debe existir otra habitación registrada con el mismo ID.</li>
     * </ul>
     *
     * <p><b>Postcondición:</b> se registra la nueva habitación y su identificador queda reservado en el sistema.</p>
     *
     * <p><b>Ejemplo:</b></p>
     * <pre>
     * Habitacion h1 = new HabCompartida("H1");
     * Habitacion h2 = new HabPrivada("H2"); // Correcto
     * Habitacion h3 = new HabPrivada("H1"); // Lanza excepción (ID ya usado)
     * </pre>
     */
    protected Habitacion(String id) {

        if (id == null || id.isEmpty())
            throw new IllegalArgumentException("El identificador no puede ser nulo ni vacío.");
        if (habitaciones.contains(id))
            throw new IllegalArgumentException("El identificador '" + id + "' ya está en uso.");
        this.id = id;
        habitaciones.add(id);
    }

    /**
     * Retorna el identificador único de la habitación.
     *
     * @return identificador o código lógico de la habitación
     *
     * <p><b>Precondición:</b> la habitación debe haber sido correctamente inicializada.</p>
     * <p><b>Postcondición:</b> el valor retornado es inmutable y único dentro del sistema.</p>
     */
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
     * Método que define la lógica de cálculo del costo
     * específica para cada tipo de habitación.
     *
     * @param dias cantidad de días de internación
     * @param costos catálogo de costos del sistema
     * @return costo total según el tipo de habitación
     *
     *      <p><b>Precondiciones:</b></p>
     *      <ul>
     *        <li>{@code dias} debe ser mayor que 0.</li>
     *        <li>{@code c} debe estar correctamente inicializado con los costos del sistema.</li>
     *      </ul>
     *
     *      <p><b>Postcondición:</b> devuelve el costo total de internación, mayor o igual al costo de asignación.</p>
     *
     * <p><b>Debe ser implementado por:</b></p>
     * <ul>
     *   <li>{@link HabCompartida} → costo lineal por día + asignación.</li>
     *   <li>{@code HabPrivada} → costo base × factor según días + asignación.</li>
     *   <li>{@code TerapiaIntensiva} → costo base × (días ^ potencia) + asignación.</li>
     * </ul>
     */
    protected abstract double CalcularCosto(int dias, CatalogoCostos costos);


}
