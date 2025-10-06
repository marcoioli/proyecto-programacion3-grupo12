package Sala;

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
