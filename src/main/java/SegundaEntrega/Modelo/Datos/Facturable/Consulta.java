package SegundaEntrega.Modelo.Datos.Facturable;

// Imports actualizados a la nueva estructura de paquetes
import SegundaEntrega.Modelo.Datos.Personas.Medico;
import SegundaEntrega.Modelo.Datos.Personas.Paciente;
import SegundaEntrega.Modelo.Negocio.Clinica; // Para acceder al Catálogo de Costos

import java.time.LocalDate;

/**
 * Representa una consulta médica.
 * Implementa IFacturable para ser cobrada en una Factura.
 */
public class Consulta implements IFacturable {

    private Paciente paciente;
    private Medico medico;
    private LocalDate fecha;
    // private String motivo; // Puedes añadir más atributos si los tenías

    public Consulta(Paciente paciente, Medico medico, LocalDate fecha) {
        assert paciente != null : "Paciente no puede ser nulo";
        assert medico != null : "Médico no puede ser nulo";
        assert fecha != null : "Fecha no puede ser nula";

        this.paciente = paciente;
        this.medico = medico;
        this.fecha = fecha;
    }

    // --- GETTERS (si los necesitas) ---
    public Paciente getPaciente() { return paciente; }
    public Medico getMedico() { return medico; }
    public LocalDate getFecha() { return fecha; }

    // --- IMPLEMENTACIÓN DE IFacturable (CORREGIDA) ---

    /**
     * Devuelve el costo de la consulta (implementa IFacturable.subtotal).
     * Busca el costo en el Catálogo de Costos de la Clínica.
     * @return El costo (subtotal) de la consulta.
     */
    @Override
    public double subtotal() { // <-- Nombre de método corregido
        try {
            // Llama al getter que SÍ existe en CatalogoCostos
            return Clinica.getInstance().getCatalogoCostos().getCostoConsultaMedica();
        } catch (Exception e) {
            System.err.println("Error al obtener costo de consulta: " + e.getMessage());
            return 0.0; // Devolver 0 en caso de error
        }
    }

    /**
     * Descripción para mostrar en la factura.
     */
    @Override
    public String toString() {
        return "Consulta Médica (" + fecha.toString() + ") - Dr. " + medico.getApellido();
    }
}