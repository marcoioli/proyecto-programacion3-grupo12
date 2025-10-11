package Facturacion.Facturable;

import Personas.Medico;
import Personas.Paciente;
import java.time.LocalDate;

/**
 * Representa una consulta médica realizada por un {@link Medico} a un {@link Paciente}.
 *   <p><b>Rol en el sistema:</b></p>
 *   <ul>
 *     <li>Elemento facturable individual (consulta médica).</li>
 *     <li>Se utiliza en la composición de facturas emitidas al egreso de un paciente.</li>
 *     <li>Su costo se calcula con un recargo del 20% sobre el honorario base del médico.</li>
 *   </ul>
 *
 *   <p><b>Casos de uso:</b></p>
 *   <ul>
 *     <li>Generación de facturas en el módulo de egreso.</li>
 *     <li>Registro de consultas en el reporte de actividad médica del profesional.</li>
 *   </ul>
 *
 */

public final class Consulta implements IFacturable {

    private final Medico medico;
    private final Paciente paciente;
    private final LocalDate fecha;
    private final double honorario;

    /**
     * Crea una nueva consulta médica entre un médico y un paciente.
     *
     * @param medico médico que realizó la atención
     * @param paciente paciente atendido
     * @param fecha fecha en la que se realizó la consulta
     * @param honorario honorario base del médico, antes del recargo al paciente
     *
     * <p><b>Precondiciones:</b></p>
     * <ul>
     *   <li>{@code medico} y {@code paciente} deben estar registrados en el sistema.</li>
     *   <li>{@code fecha} no puede ser {@code null}.</li>
     *   <li>{@code honorario} debe ser mayor o igual a 0.</li>
     * </ul>
     *
     * <p><b>Postcondición:</b> se crea un objeto {@code Consulta} listo para ser incluido en una factura.</p>
     */
    public Consulta(Medico medico, Paciente paciente, LocalDate fecha, double honorario) {
        this.medico = medico;
        this.paciente = paciente;
        this.fecha = fecha;
        this.honorario = honorario;
    }


    /**
     * Calcula el subtotal correspondiente a la consulta médica.
     *
     * @return monto total a cobrar por esta consulta
     *
     * <p><b>Precondición:</b> el honorario debe haber sido establecido correctamente al crear la instancia.</p>
     * <p><b>Postcondición:</b> el valor retornado es mayor o igual al honorario del médico.</p>
     *
     * <p><b>Ejemplo:</b></p>
     * <pre>
     * Consulta c = new Consulta(medico, paciente, LocalDate.now(), 20000);
     * c.subtotal(); // → 24000.0 (20% adicional)
     * </pre>
     */
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
