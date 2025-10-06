package Sala;

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
