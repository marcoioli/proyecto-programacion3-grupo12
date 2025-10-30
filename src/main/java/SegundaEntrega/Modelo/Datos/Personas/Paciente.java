package SegundaEntrega.Modelo.Datos.Personas;

import SegundaEntrega.Modelo.Datos.Personas.Persona;
import SegundaEntrega.Modelo.Datos.Sala.PrioridadSala;

/**
 * Representa a un paciente registrado dentro del sistema de la clínica.
 * <p>
 * Extiende {@link Personas.Persona} y agrega atributos específicos del dominio médico,
 * como el número de historia clínica y el rango etario.
 * Este último se utiliza para resolver conflictos de prioridad en la sala de espera
 * mediante el patrón <b>Double Dispatch</b>.
 * </p>
 *
 * <p><b>Rol en el sistema:</b></p>
 * <ul>
 *   <li>Entidad principal en los módulos de ingreso, atención e internación.</li>
 *   <li>Participa en la resolución de prioridades en la sala de espera a través de {@link PrioridadSala}.</li>
 *   <li>Se asocia a las facturas emitidas al egreso del paciente.</li>
 * </ul>
 *
 * <p><b>Casos de uso:</b></p>
 * <ul>
 *   <li>Registro de pacientes en el sistema.</li>
 *   <li>Determinación de prioridad frente a otros pacientes en la sala de espera.</li>
 *   <li>Identificación y vinculación con historiales clínicos y facturación.</li>
 * </ul>
 *
 */

public final class Paciente extends Persona {
    private final String nroHistoriaClinica;
    private PrioridadSala rango; // Nino/Joven/Mayor

    /**
     * Crea un nuevo paciente identificado por su número de historia clínica.
     *
     * @param dni documento nacional de identidad (no nulo ni vacío)
     * @param nombre nombre de pila del paciente
     * @param apellido apellido del paciente
     * @param domicilio domicilio del paciente
     * @param ciudad ciudad de residencia
     * @param telefono teléfono de contacto
     * @param nroHistoriaClinica número de historia clínica (no nulo ni vacío)
     * @param rango rango etario representado por una instancia concreta de {@link PrioridadSala}
     *
     * <p><b>Precondiciones:</b></p>
     * <ul>
     *   <li>Todos los datos personales deben ser válidos y no nulos.</li>
     *   <li>{@code nroHistoriaClinica} debe ser único en el sistema.</li>
     *   <li>{@code rango} debe ser una instancia concreta de {@link PrioridadSala} (por ejemplo, Niño, Joven o Mayor).</li>
     * </ul>
     *
     * <p><b>Postcondición:</b> se crea un paciente correctamente identificado con su rango de prioridad establecido.</p>

     */
    public Paciente(String dni, String nombre, String apellido, String domicilio, String ciudad, String telefono, String nroHistoriaClinica, PrioridadSala rango) {
        super(dni,nombre,apellido,domicilio,ciudad,telefono);
        this.nroHistoriaClinica = nroHistoriaClinica;
        this.rango = rango;
    }

    public PrioridadSala getRango() {
        return rango;
    }


}
