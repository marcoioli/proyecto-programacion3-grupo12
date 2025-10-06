package Facturacion.Decorator;

/**
 * Clase abstracta que define la estructura general de un Decorator de honorario.
 */

public abstract class HonorarioDecorator implements Honorario {

    protected final Honorario inner;

    protected HonorarioDecorator(Honorario inner) {
        this.inner = inner;
    }

    @Override
    public abstract double calcular();
}
