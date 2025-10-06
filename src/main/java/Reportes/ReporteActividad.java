package Reportes;

import Personas.Medico;
import Facturacion.Facturable.Consulta;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Contiene la información resultante del reporte de actividad médica:
 * - Médico
 * - Consultas agrupadas por fecha
 * - Total de honorarios
 */
public final class ReporteActividad {

    private final Medico medico;
    private final Map<LocalDate, List<Consulta>> consultasPorDia;
    private final double totalHonorarios;

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