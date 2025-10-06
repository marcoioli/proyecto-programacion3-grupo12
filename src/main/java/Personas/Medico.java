package Personas;

public class Medico extends Persona {

    /**
     * Clase Medico, extiende de Persona
     * Existen tres tipos de especialidades: Clinica, Cirugia, Pediatria
     * Pueden ser contratados permantentes o residente
     * Pueden tener titulo de Posgrado, Magister o Doctor
     *
     */

    private final String matricula;
    private final Especialidad especialidad;
    private final TipoContratacion tipoContratacion;
    private final Posgrado posgrado;
    private double honorarioBase = 20000.0; // valor inicial común (puede modificarse)
2
    public Medico(String dni, String nombre, String apellido, String matricula, Especialidad especialidad, TipoContratacion tipoContratacion, Posgrado posgrado) {
        super(dni, nombre, apellido);
        this.matricula = matricula;
        this.especialidad = especialidad;
        this.tipoContratacion = tipoContratacion;
        this.posgrado = posgrado;
    }

}


