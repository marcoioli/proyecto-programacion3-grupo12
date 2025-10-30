package SegundaEntrega.Modelo.Excepciones;

/**
 * Excepción que se lanza cuando se intenta agregar un asociado
 * que ya existe en el sistema (identificado por su DNI).
 * <p>
 * Extiende {@link Exception} para ser una excepción comprobada (checked exception),
 * lo que obliga a manejarla o declararla en los métodos que puedan lanzarla.
 * </p>
 */
public class AsociadoDuplicadoException extends Exception {

    /**
     * Constructor que acepta un mensaje detallado sobre el error.
     * @param message El mensaje que describe la causa de la excepción
     * (ej: "Ya existe un asociado con el DNI: XXXXXXXX").
     */
    public AsociadoDuplicadoException(String message) {
        super(message); // Llama al constructor de la clase base Exception
    }

    /**
     * Constructor que acepta un mensaje y una causa original (otra excepción).
     * Útil si esta excepción es causada por otro error subyacente.
     * @param message El mensaje descriptivo.
     * @param cause La excepción original que provocó este error.
     */
    public AsociadoDuplicadoException(String message, Throwable cause) {
        super(message, cause); // Llama al constructor de Exception que acepta una causa
    }
}