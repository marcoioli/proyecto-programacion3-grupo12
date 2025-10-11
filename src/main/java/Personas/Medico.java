package Personas;

import Configuracion.Especialidad;
import Configuracion.Posgrado;
import Configuracion.TipoContratacion;

public class Medico extends Persona {

    /**
     * Representa a un médico registrado en la clínica.
     * <p>
     * Hereda de {@link Persona} e incorpora información profesional
     * como matrícula, especialidad, tipo de contratación y nivel de posgrado.
     * </p>
     *
     * <p><b>Rol en el sistema:</b></p>
     * <ul>
     *   <li>Entidad principal del módulo de atención y facturación.</li>
     *   <li>Sus honorarios se calculan mediante el patrón {@code Decorator}
     *       combinando incrementos por {@link Especialidad}, {@link Posgrado} y {@link TipoContratacion}.</li>
     *   <li>Se utiliza en la creación de objetos {@link Facturacion.Facturable.Consulta}
     *       y en reportes de actividad médica.</li>
     * </ul>
     *
     * <p><b>Dominio:</b></p>
     * <ul>
     *   <li>Existen tres tipos de especialidad: <b>Clínica</b>, <b>Cirugía</b> y <b>Pediatría</b>.</li>
     *   <li>Pueden ser contratados como <b>Plantel Permanente</b> o <b>Residente</b>.</li>
     *   <li>Pueden tener título de <b>Magíster</b>, <b>Doctor</b> o <b>Ninguno</b>.</li>
     *   <li>El honorario base inicial común a todos es de $20.000 (configurable).</li>
     * </ul>
     *
     * <p><b>Casos de uso:</b></p>
     * <ul>
     *   <li>Registro de médicos en el sistema clínico.</li>
     *   <li>Asignación de médicos a consultas o internaciones.</li>
     *   <li>Cálculo y reporte de honorarios profesionales.</li>
     * </ul>
     */

    private final String matricula;
    private final Especialidad especialidad;
    private final TipoContratacion tipoContratacion;
    private final Posgrado posgrado;
    private double honorarioBase = 20000.0; // valor inicial común (puede modificarse)


    /**
     * Crea un nuevo médico registrado en la clínica.
     *
     * @param dni documento nacional de identidad (no nulo ni vacío)
     * @param nombre nombre del médico
     * @param apellido apellido del médico
     * @param domicilio domicilio del médico
     * @param ciudad ciudad de residencia
     * @param telefono teléfono de contacto
     * @param matricula número de matrícula profesional (no nulo ni vacío)
     * @param especialidad especialidad médica (no nula)
     * @param tipoContratacion tipo de contratación (no nulo)
     * @param posgrado nivel de posgrado académico (no nulo)
     *
     * <p><b>Precondiciones:</b></p>
     * <ul>
     *   <li>Todos los parámetros deben ser válidos y no nulos.</li>
     *   <li>La matrícula debe ser única dentro del sistema (control a nivel de registro).</li>
     * </ul>
     *
     * <p><b>Postcondición:</b> se crea un médico con sus atributos profesionales correctamente inicializados.</p>
     *
     * <p><b>Ejemplo:</b></p>
     * <pre>
     * Medico m = new Medico("12345678", "Ana", "Gómez", "Belgrano 1200",
     *                       "Mar del Plata", "223-5551212",
     *                       "MP-4567", Especialidad.CIRUGIA,
     *                       TipoContratacion.PLANTEL_PERMANENTE, Posgrado.DOCTOR);
     * </pre>
     */
    public Medico(String dni, String nombre, String apellido, String domicilio, String ciudad, String telefono, String matricula, Especialidad especialidad, TipoContratacion tipoContratacion, Posgrado posgrado) {
        super(dni, nombre, apellido, domicilio, ciudad, telefono);
        this.matricula = matricula;
        this.especialidad = especialidad;
        this.tipoContratacion = tipoContratacion;
        this.posgrado = posgrado;
    }

    public String getMatricula() {
        return matricula;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public TipoContratacion getTipoContratacion() {
        return tipoContratacion;
    }

    public Posgrado getPosgrado() {
        return posgrado;
    }

}


