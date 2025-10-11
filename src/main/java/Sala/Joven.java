package Sala;

/**
 * Implementación concreta de {@link PrioridadSala} que representa a un paciente del rango etario <b>Joven</b>.
 *
 * <p><b>Rol en el patrón:</b></p>
 * <ul>
 *   <li>Concreta la estrategia del rango etario “Joven”.</li>
 *   <li>Responde al mensaje doblemente despachado desde otro {@link PrioridadSala}.</li>
 *   <li>Evita estructuras condicionales múltiples, delegando la lógica al objeto correcto.</li>
 * </ul>
 *
 * <p><b>Casos de uso:</b></p>
 * <ul>
 *   <li>Comparación entre pacientes en {@link SalaDeEsperaPrivada#ingresar(Personas.Paciente)}.</li>
 *   <li>Determinación del paciente que permanece en la sala o pasa al {@link Patio}.</li>
 * </ul>
 *
 */

public final class Joven implements PrioridadSala {

    @Override
    public Resolucion resolverContra(PrioridadSala otro) {
        return otro.vsJoven();
    }

    @Override
    public Resolucion vsNino() { return Resolucion.GANA_OTRO; }

    @Override
    public Resolucion vsJoven() { return Resolucion.EMPATE; }

    @Override
    public Resolucion vsMayor() { return Resolucion.GANA_ESTE; }
}
