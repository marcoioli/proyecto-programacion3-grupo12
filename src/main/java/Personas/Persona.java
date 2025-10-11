package Personas;

/**
 * Clase abstracta que representa a una persona dentro del sistema de la clínica.
 * <p>
 * Contiene los datos identificatorios y de contacto comunes a todas las personas
 * del sistema (pacientes y médicos).
 * </p>
 *
 * <p><b>Rol en el sistema:</b></p>
 * <ul>
 *   <li>Superclase de {@link Medico} y {@link Paciente}.</li>
 * </ul>
 *
 * <p><b>Casos de uso:</b></p>
 * <ul>
 *   <li>Registro de médicos y pacientes en el sistema.</li>
 *   <li>Acceso unificado a información de identidad y contacto.</li>
 * </ul>
 */

public abstract class Persona {

    protected String dni;
    protected String nombre;
    protected String apellido;
    protected String domicilio;
    protected String ciudad;
    protected String telefono;

    /**
     * Crea una nueva persona con los datos básicos de identificación y contacto.
     *
     * @param dni documento nacional de identidad (no nulo ni vacío)
     * @param nombre nombre de pila (no nulo ni vacío)
     * @param apellido apellido (no nulo ni vacío)
     * @param domicilio domicilio actual (puede ser vacío pero no nulo)
     * @param ciudad ciudad de residencia (puede ser vacío pero no nulo)
     * @param telefono número de teléfono (puede ser vacío pero no nulo)
     *
     * <p><b>Precondiciones:</b></p>
     * <ul>
     *   <li>{@code dni}, {@code nombre} y {@code apellido} deben ser cadenas no nulas ni vacías.</li>
     *   <li>{@code domicilio}, {@code ciudad} y {@code telefono} pueden ser opcionales, pero no nulos.</li>
     * </ul>
     *
     * <p><b>Postcondición:</b> se crea una instancia base lista para ser extendida por {@link Medico} o {@link Paciente}.</p>
     */

    public Persona(String dni, String nombre, String apellido, String domicilio, String ciudad, String telefono) {
        this.dni= dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.domicilio = domicilio;
        this.ciudad = ciudad;
        this.telefono = telefono;
    }

    public String getDni() {
        return dni;
    }


    public String getNombre() {
        return nombre;
    }


    public String getApellido() {
        return apellido;
    }




}
