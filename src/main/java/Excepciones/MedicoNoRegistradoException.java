package Excepciones;

public class MedicoNoRegistradoException extends RuntimeException {

    public MedicoNoRegistradoException(String dni) {

        super("El medico con DNI " + dni + " no está registrado en el sistema.");
    }
}
