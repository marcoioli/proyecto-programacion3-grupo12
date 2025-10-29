package SegundaEntrega.Modelo.Datos.Personas;

import java.util.Objects; // Necesario para Objects.hash

/**
 * Representa a un asociado de la clínica que puede usar la ambulancia.
 * No interviene en la facturación médica.
 */
public class Asociado extends Persona { // Hereda de Persona

    // Atributos específicos si los hubiera (por ahora ninguno según el enunciado)

    /**
     * Constructor para un nuevo asociado.
     * @param nombre Nombre del asociado.
     * @param apellido Apellido del asociado.
     * @param dni DNI del asociado (usado para identificación única).
     * @param domicilio Domicilio del asociado.
     * @param telefono Teléfono del asociado.
     * @param ciudad Ciudad del asociado.
     */
    public Asociado(String nombre, String apellido, String dni, String domicilio, String telefono, String ciudad) {
        // Llama al constructor de la superclase Persona
        super(nombre, apellido, dni, domicilio, telefono, ciudad);
    }

    // Constructor vacío (puede ser útil para algunas operaciones o frameworks)
    public Asociado() {
        super(); // Llama al constructor vacío de Persona (si existe) o al por defecto
    }

    /**
     * Compara este Asociado con otro objeto para verificar igualdad.
     * Dos asociados son iguales si tienen el mismo DNI.
     * @param o El objeto a comparar.
     * @return true si son iguales (mismo DNI), false en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // Si es la misma instancia, son iguales
        // Si el objeto es nulo o no es una instancia de Asociado (o subclase), no son iguales
        if (o == null || !(o instanceof Asociado)) return false;
        // Hacemos el casting seguro sabiendo que es un Asociado
        Asociado asociado = (Asociado) o;
        // Comparamos por DNI (heredado de Persona)
        // Usamos Objects.equals para manejar correctamente DNIs nulos (aunque no deberían serlo)
        return Objects.equals(getDni(), asociado.getDni());
    }

    /**
     * Genera un código hash para el Asociado, basado únicamente en el DNI.
     * Si dos asociados son equals(), deben tener el mismo hashCode().
     * @return El código hash basado en el DNI.
     */
    @Override
    public int hashCode() {
        // Usamos Objects.hash para generar un hash basado en el DNI
        // Esto asegura que si los DNIs son iguales (o ambos nulos), el hash será el mismo.
        return Objects.hash(getDni());
    }

    /**
     * Representación en String del Asociado.
     * @return Una cadena con los datos del asociado.
     */
    @Override
    public String toString() {
        // Podemos personalizar esto o usar el de Persona
        return "Asociado [" + super.toString() + "]"; // Llama al toString() de Persona
    }
}