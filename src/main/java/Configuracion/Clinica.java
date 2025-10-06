package Configuracion;

/**
 * Representa los datos generales de la clínica.
 */

public final class Clinica {

    private final String nombre;
    private final String direccion;
    private final String telefono;
    private final String ciudad;

    public Clinica(String nombre, String direccion, String telefono, String ciudad) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.ciudad = ciudad;
    }

    public String getNombre() { return nombre; }
    public String getDireccion() { return direccion; }
    public String getTelefono() { return telefono; }
    public String getCiudad() { return ciudad; }

    @Override
    public String toString() {
        return nombre + " - " + ciudad + " (" + telefono + ")";
    }

}
