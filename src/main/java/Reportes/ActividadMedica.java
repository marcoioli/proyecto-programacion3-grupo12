package Reportes;

import Facturacion.Facturable.Consulta;
import Facturacion.Factura;
import Personas.Medico;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


/**
 * Servicio encargado de generar y mostrar reportes de actividad médica.
 * <p>
 * Permite obtener un resumen de las consultas realizadas por un {@link Medico}
 * en un rango de fechas determinado, basándose en la información registrada
 * en las {@link Factura}s del sistema.
 * </p>
 *
 * <p><b>Rol en el sistema:</b></p>
 * <ul>
 *   <li>Componente del módulo de reportes.</li>
 *   <li>Colabora con el módulo de facturación al procesar las consultas emitidas.</li>
 *   <li>Genera objetos de tipo {@link ReporteActividad} que encapsulan la información resumida.</li>
 * </ul>
 *
 * <p><b>Casos de uso:</b></p>
 * <ul>
 *   <li>Consulta de la actividad profesional de un médico en un período específico.</li>
 *   <li>Generación de reportes administrativos o contables basados en honorarios médicos.</li>
 *   <li>Visualización de las consultas agrupadas cronológicamente por día.</li>
 * </ul>
 */

public final class ActividadMedica {

    /**
     * Genera el reporte de actividad para un médico dentro de un rango de fechas.
     *
     * @param medico médico a reportar (no nulo)
     * @param desde fecha inicial del período (inclusive)
     * @param hasta fecha final del período (inclusive)
     * @param facturas lista completa de facturas del sistema
     * @return un objeto {@link ReporteActividad} con el detalle de consultas y el total de honorarios
     *
     *
     * <p><b>Precondiciones:</b></p>
     * <ul>
     *   <li>{@code medico} debe estar registrado en el sistema.</li>
     *   <li>{@code facturas} debe contener las facturas del período analizado.</li>
     *   <li>{@code desde} y {@code hasta} no pueden ser nulos ni inconsistentes.</li>
     * </ul>
     *
     * <p><b>Postcondición:</b> se devuelve un {@link ReporteActividad} que agrupa las consultas del médico
     * por fecha y calcula el total de honorarios.</p>
     *
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
