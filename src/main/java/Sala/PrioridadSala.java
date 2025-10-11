package Sala;

public interface PrioridadSala {
    /**
     * Resuelve la prioridad contra otra instancia, delegando en el tipo concreto del otro.
     * @param otro otra prioridad
     * @return {@link Resolucion} segun las reglas de negocio
     */

    Resolucion resolverContra(PrioridadSala otro);
    /** @return resolución cuando el otro es de tipo Niño */

    Resolucion vsNino();
    /** @return resolución cuando el otro es de tipo Joven */

    Resolucion vsJoven();
    /** @return resolución cuando el otro es de tipo Mayor */

    Resolucion vsMayor();

}

