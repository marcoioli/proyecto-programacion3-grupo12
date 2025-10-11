package Habitaciones;

import Configuracion.CatalogoCostos;
import java.math.*;


/**
 * Representa una habitación de Terapia Intensiva dentro de la clínica.
 * <p>
 * Extiende {@link Habitacion} e implementa una función de costo con
 * <b>crecimiento potencial</b> respecto a la cantidad de días internado.
 * </p>
 *
 *
 * <p><b>Rol en el sistema:</b></p>
 * <ul>
 *   <li>Subclase concreta de {@link Habitacion}.</li>
 *   <li>Utilizada en internaciones críticas donde el costo se incrementa rápidamente con los días.</li>
 *   <li>El cálculo del costo depende de los parámetros configurados en {@link CatalogoCostos}.</li>
 * </ul>
 *
 * <p><b>Casos de uso:</b></p>
 * <ul>
 *   <li>Asignación de pacientes en terapia intensiva durante internaciones.</li>
 *   <li>Cálculo de costos en el módulo de facturación (a través de {@code ItemInternacion}).</li>
 * </ul>
 */

public final class TerapiaIntensiva extends Habitacion {

    /**
     * Crea una nueva habitación de Terapia Intensiva con el identificador especificado.
     *
     * @param id identificador único lógico (por ejemplo, "TI1", "TI2", etc.)
     *
     * <p><b>Precondiciones:</b></p>
     * <ul>
     *   <li>{@code id} debe ser una cadena no nula ni vacía.</li>
     *   <li>No debe existir otra habitación registrada con el mismo identificador.</li>
     * </ul>
     *
     * <p><b>Postcondición:</b> se crea una habitación de terapia intensiva registrada en el sistema.</p>
     */
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