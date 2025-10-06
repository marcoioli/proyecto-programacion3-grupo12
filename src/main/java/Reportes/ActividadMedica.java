package Reportes;

import Facturacion.Facturable.Consulta;
import Facturacion.Factura;
import Personas.Medico;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


/**
 * Servicio que genera el reporte de actividad de un médico en un período.
 */

public final class ActividadMedica {

    /**
     * Genera el reporte de actividad para un médico dentro de un rango de fechas.
     * La fecha de la consulta corresponde a la fecha de la factura.
     *
     * @param medico médico a reportar
     * @param desde fecha inicial (inclusive)
     * @param hasta fecha final (inclusive)
     * @param facturas lista de facturas del sistema
     * @return objeto ReporteActividad con los datos calculados
     */
    public ReporteActividad generar(Medico medico, LocalDate desde, LocalDate hasta, List<Factura> facturas) {
        // Agrupar las consultas por fecha (solo las del médico indicado)
        Map<LocalDate, List<Consulta>> consultasPorDia = facturas.stream()
                .filter(f -> !f.getFecha().isBefore(desde) && !f.getFecha().isAfter(hasta))
                .flatMap(f -> f.getItems().stream())
                .filter(i -> i instanceof Consulta)
                .map(i -> (Consulta) i)
                .filter(c -> c.getMedico().equals(medico))
                .collect(Collectors.groupingBy(Consulta::getFecha, TreeMap::new, Collectors.toList()));
        //filtra facturas en el periodo, selecciona las del medico indicado y las agrupa por fecha

        // Calcular total
        double total = consultasPorDia.values().stream()
                .flatMap(List::stream)
                .mapToDouble(Consulta::getHonorario)
                .sum();

        return new ReporteActividad(medico, consultasPorDia, total);
    }

    /**
     * Imprime el reporte formateado por consola.
     */
    public void imprimir(ReporteActividad reporte, LocalDate desde, LocalDate hasta) {
        System.out.println("----------------------------------------------------------");
        System.out.println("Reporte de actividad médica");
        System.out.println("Médico: " + reporte.getMedico().getNombre() + " " + reporte.getMedico().getApellido() +
                " - " + reporte.getMedico().getEspecialidad());
        System.out.println("Periodo: " + desde + " al " + hasta);
        System.out.println("----------------------------------------------------------");

        reporte.getConsultasPorDia().forEach((fecha, consultas) -> {
            System.out.println("\nFecha: " + fecha);
            for (Consulta c : consultas) {
                System.out.printf(" - Paciente: %-20s Honorario: $%10.2f%n",
                        c.getPaciente().getNombre() + " " + c.getPaciente().getApellido(),
                        c.getHonorario());
            }
        });

        System.out.println("----------------------------------------------------------");
        System.out.printf("Total honorarios: $%.2f%n", reporte.getTotalHonorarios());
        System.out.println("----------------------------------------------------------\n");
    }
}
