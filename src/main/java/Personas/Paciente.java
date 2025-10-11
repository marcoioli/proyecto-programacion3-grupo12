package Personas;

import Sala.PrioridadSala;

/**
 * Representa a un paciente dentro del sistema.
 * Extiende {@link Persona} y agrega el numero de historia clinica
 * y el rango etario, utilizado para resolver la prioridad en la sala
 * de espera mediante el patron <b>Double Dispatch</b>.
 *
 * @see
 */

public final class Paciente extends Persona {
    private final String nroHistoriaClinica;
    private PrioridadSala rango; // Nino/Joven/Mayor

    /**
     * Crea un paciente identificado por su historia clinica.
     *
     * @param dni documento de identidad
     * @param nombre nombre de pila
     * @param apellido apellido
     * @param nroHistoriaClinica número de historia clínica
     * @param rango instancia concreta de {@link PrioridadSala}
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
