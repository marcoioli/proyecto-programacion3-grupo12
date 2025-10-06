package Facturacion.Decorator;

import Configuracion.Especialidad;

/**
 * Incrementa el honorario según la especialidad médica.
 */

public final class HonorarioPorEspecialidad extends HonorarioDecorator {

    private final Especialidad especialidad;

    public HonorarioPorEspecialidad(Honorario inner, Especialidad especialidad) {
        super(inner);
        this.especialidad = especialidad;
    }

    @Override
    public double calcular() {
        double base = inner.calcular();
        double incremento = 0.0;

        switch (especialidad) {
            case CLINICA: incremento = 0.05;
                        break;// +5%
            case CIRUGIA: incremento = 0.10;
                        break;// +10%
            case PEDIATRIA: incremento = 0.07;
                        break;// +7%
        }

        return base + (base * incremento);
    }
}
