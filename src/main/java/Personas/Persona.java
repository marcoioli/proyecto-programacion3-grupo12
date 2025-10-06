package Personas;

public abstract class Persona {

    /**
     *  Clase abstracta que representa a una persona dentro del sistema de la clínica.
     *  Contiene los datos identificatorios y de contacto comunes a pacientes y médicos.
     *  <p>Es la superclase de {@link Medico} y {@link Paciente}.</p>
     */

    protected String dni;
    protected String nombre;
    protected String apellido;
    protected String domicilio;
    protected String ciudad;
    protected String telefono;

    /**
     * Constructor de la clase Persona.
     *
     * @param dni documento de identidad, no debe ser nulo ni vacío
     * @param nombre nombre de pila
     * @param apellido apellido
     */

    public Persona(String dni, String nombre, String appelido) {
        this.dni= dni;
        this.nombre = nombre;
        this.apellido = apellido;
    }



}
