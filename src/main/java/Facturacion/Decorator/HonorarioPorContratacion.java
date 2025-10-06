package Facturacion.Decorator;

import Configuracion.TipoContratacion;

/**
 * Incrementa el honorario según la modalidad de contratación.
 */

public final class HonorarioPorContratacion extends HonorarioDecorator {

    private final TipoContratacion tipo;

    public HonorarioPorContratacion(Honorario inner, TipoContratacion tipo) {
        super(inner);
        this.tipo = tipo;
    }

    @Override
    public double calcular() {
        double base = inner.calcular();
        double incremento = 0.0;

        if (tipo == TipoContratacion.PLANTEL_PERMANENTE) {
            incremento = 0.10; // +10%
        } else if (tipo == TipoContratacion.RESIDENTE) {
            incremento = 0.05; // +5%
        }

        return base + (base * incremento);
    }
}