package SegundaEntrega.Modelo.Datos.Sala;

/**
 * Implementación concreta de {@link PrioridadSala} que representa a un paciente del rango etario <b>Nino</b>.
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

public final class Nino implements PrioridadSala {

    @Override
    public Resolucion resolverContra(PrioridadSala otro) {
        return otro.vsNino();
    }

    @Override
    public Resolucion vsNino() { return Resolucion.EMPATE; }

    @Override
    public Resolucion vsJoven() { return Resolucion.GANA_ESTE; } // Niño gana a joven

    @Override
    public Resolucion vsMayor() { return Resolucion.GANA_OTRO; } // Mayor gana a niño
}
