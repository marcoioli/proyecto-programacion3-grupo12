package Excepciones;

public class PacienteNoRegistradoException extends RuntimeException {
    public PacienteNoRegistradoException(String dni) {
        super("El paciente con DNI " + dni + " no está registrado en el sistema.");
    }

    public PacienteNoRegistradoException() {
        super("El paciente no está registrado en el sistema.");
    }
}
