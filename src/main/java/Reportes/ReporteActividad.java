package Reportes;

import Personas.Medico;
import Facturacion.Facturable.Consulta;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Representa el resultado generado por el servicio {@link ActividadMedica}.
 * <p>
 * Contiene toda la información relevante del reporte de actividad de un {@link Medico}:
 * </p>
 * <ul>
 *   <li>El médico reportado.</li>
 *   <li>Las consultas realizadas, agrupadas cronológicamente por fecha.</li>
 *   <li>El total acumulado de honorarios profesionales en el período.</li>
 * </ul>
 *
 * <p><b>Rol en el sistema:</b></p>
 * <ul>
 *   <li>Es devuelto por {@link ActividadMedica#generar(Medico, LocalDate, LocalDate, java.util.List)}.</li>
 *   <li>Es consumido por {@link ActividadMedica#imprimir(ReporteActividad, LocalDate, LocalDate)} para la visualización.</li>
 * </ul>
 *
 * <p><b>Casos de uso:</b></p>
 * <ul>
 *   <li>Transporte de los resultados del análisis de actividad médica.</li>
 * </ul>
 */

public final class ReporteActividad {

    private final Medico medico;
    private final Map<LocalDate, List<Consulta>> consultasPorDia;
    private final double totalHonorarios;

    /**
     * Crea un nuevo reporte de actividad médica.
     *
     * @param medico médico reportado (no nulo)
     * @param consultasPorDia mapa que agrupa las {@link Consulta}s por fecha (no nulo, puede estar vacío)
     * @param totalHonorarios suma total de los honorarios del médico en el período (≥ 0)
     *
     * <p><b>Precondiciones:</b></p>
     * <ul>
     *   <li>{@code medico} debe ser una instancia válida registrada en el sistema.</li>
     *   <li>{@code consultasPorDia} no debe ser {@code null}.</li>
     *   <li>{@code totalHonorarios} debe ser mayor o igual a 0.</li>
     * </ul>
     *
     * <p><b>Postcondición:</b> se crea un objeto inmutable {@code ReporteActividad} listo para su visualización o exportación.</p>
     *
     */
    public ReporteActividad(Medico medico, Map<LocalDate, List<Consulta>> consultasPorDia, double totalHonorarios) {
        this.medico = medico;
        this.consultasPorDia = consultasPorDia;
        this.totalHonorarios = totalHonorarios;
    }

    public Medico getMedico() {
        return medico;
    }

    public Map<LocalDate, List<Consulta>> getConsultasPorDia() {
        return consultasPorDia;
    }

    public double getTotalHonorarios() {
        return totalHonorarios;
    }
}