package Facturacion.Facturable;

import Personas.Medico;
import Personas.Paciente;
import java.time.LocalDate;

/**
 * Representa una consulta medica realizada por un medico a un paciente.
 */
public final class Consulta implements IFacturable {

    private final Medico medico;
    private final Paciente paciente;
    private final LocalDate fecha;
    private final double honorario;

    public Consulta(Medico medico, Paciente paciente, LocalDate fecha, double honorario) {
        this.medico = medico;
        this.paciente = paciente;
        this.fecha = fecha;
        this.honorario = honorario;
    }

    @Override
    public double subtotal() {
        // se cobra al paciente un 20% más del honorario del médico
        return honorario * 1.20;
    }

    public LocalDate getFecha() {
        return fecha;
    }
    public Medico getMedico() {
        return medico;
    }
    public Paciente getPaciente() {
        return paciente;
    }
    public double getHonorario() {
        return honorario;
    }
}
