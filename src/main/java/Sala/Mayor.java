package Sala;

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
