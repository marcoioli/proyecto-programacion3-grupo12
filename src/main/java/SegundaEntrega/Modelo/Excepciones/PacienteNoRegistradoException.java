package SegundaEntrega.Modelo.Excepciones;

/**
 * Excepción que indica que se intentó operar con un paciente
 * que no está registrado en el sistema de la clínica.
 *
 * <p><b>Uso recomendado:</b></p>
 * <ul>
 *   <li>Debe lanzarse solo en casos donde la ausencia del paciente impida continuar la operación.</li>
 *   <li>No debe usarse para validar datos nulos o vacíos (eso se controla previamente).</li>
 * </ul>
 */

public class PacienteNoRegistradoException extends RuntimeException {
    public PacienteNoRegistradoException(String dni) {
        super("El paciente con DNI " + dni + " no está registrado en el sistema.");
    }

    public PacienteNoRegistradoException() {
        super("El paciente no está registrado en el sistema.");
    }
}
