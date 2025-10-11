package Personas;

public abstract class Persona {

    /**
     *  Clase abstracta que representa a una persona dentro del sistema de la clinica.
     *  Contiene los datos identificatorios y de contacto comunes a pacientes y medicos.
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
     * @param dni documento de identidad, no debe ser nulo ni vacio
     * @param nombre nombre de pila
     * @param apellido apellido
     */

    public Persona(String dni, String nombre, String apellido) {
        this.dni= dni;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
