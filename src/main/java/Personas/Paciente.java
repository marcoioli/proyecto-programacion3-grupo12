package Personas;

/**
 * Representa a un paciente dentro del sistema.
 * Extiende {@link Persona} y agrega el número de historia clínica
 * y el rango etario, utilizado para resolver la prioridad en la sala
 * de espera mediante el patrón <b>Double Dispatch</b>.
 *
 * @see
 */

public final class Paciente extends Persona {
    private final String nroHistoriaClinica;
    private PrioridadSala rango; // Nino/Joven/Mayor

    /**
     * Crea un paciente identificado por su historia clínica.
     *
     * @param dni documento de identidad
     * @param nombre nombre de pila
     * @param apellido apellido
     * @param nroHistoriaClinica número de historia clínica
     * @param rango instancia concreta de {@link PrioridadSala}
     */

    public Paciente(String dni, String nombre, String apellido, String matricula, String nroHistoriaClinica, PrioridadSala rango) {
        super(dni,nombre,apellido);
        this.nroHistoriaClinica = nroHistoriaClinica;
        this.rango = rango;
    }

}
