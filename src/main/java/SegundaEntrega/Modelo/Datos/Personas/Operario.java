package SegundaEntrega.Modelo.Datos.Personas;

/**
 * Representa a un operario de la clínica, encargado de solicitar mantenimiento.
 */
public class Operario extends Persona { // Hereda de Persona

    // Atributos específicos del operario, si los hubiera (ej: idEmpleado, turno, etc.)
    // private String idEmpleado;

    /**
     * Constructor para un nuevo Operario.
     * @param nombre Nombre del operario.
     * @param apellido Apellido del operario.
     * @param dni DNI del operario.
     * @param domicilio Domicilio del operario.
     * @param telefono Teléfono del operario.
     * @param ciudad Ciudad del operario.
     * // Añadir otros parámetros si hay atributos específicos
     */
    public Operario(String nombre, String apellido, String dni, String domicilio, String telefono, String ciudad /*, String idEmpleado */) {
        super(nombre, apellido, dni, domicilio, telefono, ciudad);
        // this.idEmpleado = idEmpleado;
    }

    // Constructor vacío (puede ser útil)
    public Operario() {
        super();
    }

    // Getters y Setters para atributos específicos si los hubiera
    /*
    public String getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(String idEmpleado) {
        this.idEmpleado = idEmpleado;
    }
    */

    /**
     * Representación en String del Operario.
     * @return Una cadena con los datos del operario.
     */
    @Override
    public String toString() {
        // Podemos personalizar esto
        return "Operario [" + super.toString() + /* ", ID Empleado=" + idEmpleado + */ "]";
    }

    // Nota: No necesitamos sobreescribir equals() y hashCode() a menos que
    // queramos una lógica de igualdad específica para Operarios (diferente de Persona).
    // Por defecto, usará la de Persona (si la tiene) o la de Object (comparación por instancia).
}