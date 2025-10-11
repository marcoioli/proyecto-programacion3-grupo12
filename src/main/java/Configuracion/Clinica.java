package Configuracion;

/**
 * Representa los datos generales de la clínica.
 * <p>
 * Esta clase modela la información institucional básica de la clínica
 * (nombre, dirección, teléfono y ciudad), una vez creada,
 * sus valores no pueden modificarse.
 * </p>
 *
 * <p><b>Uso:</b></p>
 * <ul>
 *   <li>Se utiliza para identificar a la clínica en reportes y facturas.</li>
 *   <li>Debe existir una única instancia por sistema (componente de configuración).</li>
 * </ul>
 */

public final class Clinica {

    private final String nombre;
    private final String direccion;
    private final String telefono;
    private final String ciudad;

    /**
     * Crea una nueva instancia de {@code Clinica}.
     *
     * @param nombre nombre de la clínica
     * @param direccion dirección física de la clínica
     * @param telefono número de contacto telefónico
     * @param ciudad ciudad donde está ubicada la clínica
     *
     * <p><b>Precondiciones:</b></p>
     * <ul>
     *   <li>{@code nombre}, {@code direccion}, {@code telefono} y {@code ciudad} no deben ser {@code null} ni cadenas vacías.</li>
     * </ul>
     *
     * <p><b>Postcondición:</b> se crea una instancia con los valores dados.</p>
     */
    public Clinica(String nombre, String direccion, String telefono, String ciudad) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.ciudad = ciudad;
    }
    /** @return el nombre de la clínica */
    public String getNombre() { return nombre; }

    /** @return la dirección de la clínica */
    public String getDireccion() { return direccion; }

    /** @return el teléfono de contacto de la clínica */
    public String getTelefono() { return telefono; }

    /** @return la ciudad donde se encuentra la clínica */
    public String getCiudad() { return ciudad; }

}
