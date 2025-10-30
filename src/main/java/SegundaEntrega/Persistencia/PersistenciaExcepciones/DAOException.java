package SegundaEntrega.Persistencia.PersistenciaExcepciones;

/**
 * Excepción personalizada para errores ocurridos en la capa de persistencia (DAO).
 * Permite encapsular excepciones específicas de JDBC (como SQLException)
 * u otros errores relacionados con el acceso a datos.
 */
public class DAOException extends Exception {

    /**
     * Constructor que acepta un mensaje de error.
     * @param message El mensaje detallando el error.
     */
    public DAOException(String message) {
        super(message);
    }

    /**
     * Constructor que acepta un mensaje de error y la causa original.
     * Útil para envolver excepciones como SQLException.
     * @param message El mensaje detallando el error.
     * @param cause La excepción original que causó este error (ej: SQLException).
     */
    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }
}