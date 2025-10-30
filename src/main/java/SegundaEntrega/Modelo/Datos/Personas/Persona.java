package SegundaEntrega.Modelo.Datos.Personas;

// Eliminamos los imports a Personas.Medico y Personas.Paciente ya que ahora
// Medico y Paciente estarán en este mismo paquete y extenderán esta clase.
// Si estuvieran en otro paquete, necesitaríamos imports actualizados.

import java.util.Objects; // Necesario para equals y hashCode si se implementan aquí

/**
 * Clase que representa a una persona dentro del sistema de la clínica.
 * Contiene los datos identificatorios y de contacto comunes.
 * Se ha modificado para no ser abstracta y añadir constructor vacío, getters y setters
 * necesarios para la persistencia y la herencia por Asociado y Operario.
 */
public class Persona { // Quitamos 'abstract' para poder instanciarla si fuera necesario y para que el DAO funcione más fácil

    // Atributos (los mantenemos protected si las subclases necesitan acceso directo,
    // o private si preferimos usar siempre getters/setters desde subclases)
    protected String dni;
    protected String nombre;
    protected String apellido;
    protected String domicilio;
    protected String ciudad;
    protected String telefono;

    /**
     * Constructor vacío.
     * Necesario para que el DAO pueda crear instancias vacías antes de llenarlas
     * con los datos de la base de datos.
     */
    public Persona() {
        // Inicialización por defecto (atributos quedan en null o valores predeterminados)
    }


    /**
     * Crea una nueva persona con los datos básicos de identificación y contacto.
     * (Constructor original mantenido).
     *
     * @param dni documento nacional de identidad (no nulo ni vacío)
     * @param nombre nombre de pila (no nulo ni vacío)
     * @param apellido apellido (no nulo ni vacío)
     * @param domicilio domicilio actual (puede ser vacío pero no nulo)
     * @param ciudad ciudad de residencia (puede ser vacío pero no nulo)
     * @param telefono número de teléfono (puede ser vacío pero no nulo)
     */
    public Persona(String dni, String nombre, String apellido, String domicilio, String ciudad, String telefono) {
        // Precondiciones (se podrían añadir validaciones más robustas aquí o en setters)
        assert dni != null && !dni.trim().isEmpty() : "El DNI no puede ser nulo o vacío";
        assert nombre != null && !nombre.trim().isEmpty() : "El nombre no puede ser nulo o vacío";
        assert apellido != null && !apellido.trim().isEmpty() : "El apellido no puede ser nulo o vacío";
        assert domicilio != null : "El domicilio no puede ser nulo (puede ser vacío)";
        assert ciudad != null : "La ciudad no puede ser nula (puede ser vacía)";
        assert telefono != null : "El teléfono no puede ser nulo (puede ser vacío)";

        this.dni= dni.trim(); // Guardar sin espacios extra
        this.nombre = nombre.trim();
        this.apellido = apellido.trim();
        this.domicilio = domicilio.trim();
        this.ciudad = ciudad.trim();
        this.telefono = telefono.trim();
    }

    // --- Getters Públicos ---
    public String getDni() {
        return dni;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getTelefono() {
        return telefono;
    }

    // Método auxiliar útil
    public String getNombreCompleto() {
        return (nombre != null ? nombre : "") + " " + (apellido != null ? apellido : "");
    }

    // --- Setters Públicos ---
    public void setDni(String dni) {
        // Añadir validación si es necesario
        this.dni = dni;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    // --- Otros Métodos ---
    @Override
    public String toString() {
        return "Persona{" +
                "dni='" + dni + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", domicilio='" + domicilio + '\'' +
                ", ciudad='" + ciudad + '\'' +
                ", telefono='" + telefono + '\'' +
                '}';
    }

    // --- Considerar añadir equals y hashCode basados en DNI ---
    // Si la regla de negocio es que DOS PERSONAS (no solo asociados)
    // son iguales si tienen el mismo DNI, implementarlos aquí.
    // Si no, dejar que las subclases (como Asociado) lo implementen.

    /*
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        // Importante: Usar getClass() != o.getClass() si SOLO objetos de la MISMA clase son iguales.
        // Usar !(o instanceof Persona) si subclases con mismo DNI también son iguales.
        // Para el caso de Asociado vs Operario con mismo DNI, instanceof es más flexible.
        if (o == null || !(o instanceof Persona)) return false;
        Persona persona = (Persona) o;
        // Comparar por DNI, manejando nulos
        return Objects.equals(getDni(), persona.getDni());
    }

    @Override
    public int hashCode() {
        // Basar el hash sólo en el DNI si equals se basa sólo en DNI
        return Objects.hash(getDni());
    }
    */
}