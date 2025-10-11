package Facturacion.Decorator;

import Configuracion.Posgrado;

/**
 * Incrementa el honorario segun el posgrado del medico.
 */
public final class HonorarioPorPosgrado extends HonorarioDecorator {

    private final Posgrado posgrado;

    public HonorarioPorPosgrado(Honorario inner, Posgrado posgrado) {
        super(inner);
        this.posgrado = posgrado;
    }

    @Override
    public double calcular() {
        double base = inner.calcular();
        double incremento = 0.0;

        switch (posgrado) {
            case MAGISTER:  incremento = 0.05;
                            break;// +5%
            case DOCTOR:  incremento = 0.10;
                          break;// +10%
            case NINGUNO: incremento = 0.0;
                          break;
        }

        return base + (base * incremento);
    }
}
