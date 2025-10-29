// Paquete actualizado
package SegundaEntrega.Modelo.Negocio;

// Imports actualizados
import SegundaEntrega.Modelo.Datos.Personas.Medico;
import SegundaEntrega.Modelo.Datos.Personas.Paciente;
import SegundaEntrega.Modelo.Datos.Facturable.IFacturable; // Asegúrate que IFacturable esté aquí

// Imports Java Standard
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa una factura emitida por la clínica.
 * Contiene paciente, médico (opcional), fecha e ítems facturables.
 */
public class Factura {

    private Paciente paciente;
    private Medico medico; // Puede ser null
    private LocalDate fecha;
    private List<IFacturable> items;
    private double importeTotal;

    /**
     * Constructor.
     * @param paciente Paciente (no nulo).
     * @param medico Médico (puede ser nulo).
     * @param fecha Fecha (no nula).
     */
    public Factura(Paciente paciente, Medico medico, LocalDate fecha) {
        assert paciente != null : "Paciente no puede ser nulo";
        assert fecha != null : "Fecha no puede ser nula";
        this.paciente = paciente;
        this.medico = medico;
        this.fecha = fecha;
        this.items = new ArrayList<>();
        this.importeTotal = 0.0;
    }

    /**
     * Agrega un ítem facturable a la factura y recalcula el total.
     * @param item Ítem a agregar (no nulo).
     */
    public void agregarItem(IFacturable item) {
        if (item != null) {
            this.items.add(item);
            this.calcularTotal(); // Recalcula al agregar
        }
    }

    /**
     * Calcula y actualiza el importe total sumando los subtotales de los ítems.
     */
    public void calcularTotal() {
        this.importeTotal = 0.0;
        if (this.items != null) {
            // Asume que IFacturable tiene un método getSubtotal() o similar
            // Necesitamos Double Dispatch aquí si el cálculo depende del tipo de IFacturable
            // O un método común en IFacturable como getFacturableSubtotal()
            for (IFacturable item : this.items) {
                // **MODIFICACIÓN:** Usamos un nombre de método genérico asumido en IFacturable
                this.importeTotal += item.subtotal();
            }
        }
        assert this.importeTotal >= 0 : "Importe total negativo";
    }

    // --- Getters ---
    public Paciente getPaciente() { return paciente; }
    public Medico getMedico() { return medico; }
    public LocalDate getFecha() { return fecha; }
    public List<IFacturable> getItems() { return new ArrayList<>(items); } // Devuelve copia
    public double getImporteTotal() { return importeTotal; }

    // --- Setters (Usar con cuidado, no recalcular total automáticamente aquí) ---
    public void setPaciente(Paciente paciente) { this.paciente = paciente; }
    public void setMedico(Medico medico) { this.medico = medico; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        // ... (igual que antes, usando getFacturableSubtotal() para mostrar costos)
        sb.append("Factura {\n");
        sb.append("  Fecha: ").append(fecha).append("\n");
        sb.append("  Paciente: ").append(paciente != null ? paciente.getNombreCompleto() : "N/A").append("\n");
        sb.append("  Medico: ").append(medico != null ? medico.getNombreCompleto() : "N/A").append("\n");
        sb.append("  Items: [\n");
        if (items != null) {
            for (IFacturable item : items) {
                // Asume que IFacturable tiene un buen toString() y el método getFacturableSubtotal()
                sb.append("    - ").append(item.toString()).append(" --> $").append(String.format("%.2f", item.subtotal())).append("\n");
            }
        }
        sb.append("  ]\n");
        sb.append("  Importe Total: $").append(String.format("%.2f", importeTotal)).append("\n");
        sb.append("}");
        return sb.toString();
    }
}