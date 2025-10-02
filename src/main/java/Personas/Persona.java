package Personas;

public abstract class Persona {

    /**
     * Clase Persona
     * Padre de Medico y Paciente
     */

    protected String dni;
    protected String nombre;
    protected String apellido;
    protected String domicilio;
    protected String ciudad;
    protected String telefono;

    public Persona(String dni, String nombre, String appelido) {
        this.dni= dni;
        this.nombre = nombre;
        this.apellido = apellido;
    }

}
