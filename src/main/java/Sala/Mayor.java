package Sala;


/**
 * Implementación concreta de {@link PrioridadSala} que representa a un paciente del rango etario <b>Mayor</b>.
 *
 * <p><b>Rol en el patrón:</b></p>
 * <ul>
 *   <li>Concreta la estrategia de prioridad correspondiente al rango “Mayor”.</li>
 *   <li>Responde a las invocaciones doblemente despachadas desde otros {@link PrioridadSala}.</li>
 *   <li>Permite determinar la prioridad sin usar estructuras condicionales complejas.</li>
 * </ul>
 *
 * <p><b>Casos de uso:</b></p>
 * <ul>
 *   <li>Comparación de prioridad entre pacientes dentro de {@link SalaDeEsperaPrivada#ingresar(Personas.Paciente)}.</li>
 *   <li>Determinación del paciente que permanece en la sala o es derivado al {@link Patio}.</li>
 * </ul>
 *
 */

public final class Mayor implements PrioridadSala {

    @Override
    public Resolucion resolverContra(PrioridadSala otro) {
        return otro.vsMayor();
    }

    @Override
    public Resolucion vsNino() { return Resolucion.GANA_ESTE; }

    @Override
    public Resolucion vsJoven() { return Resolucion.GANA_OTRO; }

    @Override
    public Resolucion vsMayor() { return Resolucion.EMPATE; }
}
