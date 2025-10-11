package Facturacion.Decorator;

import Configuracion.Especialidad;

/**
 * Decorador concreto que incrementa el honorario médico según su especialidad.
 * <p><b>Rol en el patrón:</b></p>
 * <ul>
 *   <li>Decorador concreto del patrón {@code Decorator}.</li>
 *   <li>Extiende {@link HonorarioDecorator} y aplica incrementos específicos
 *       de acuerdo a la {@link Especialidad} del médico.</li>
 * </ul>
 *
 * <p><b>Reglas de incremento:</b></p>
 * <ul>
 *   <li>{@code CLINICA} → +5%</li>
 *   <li>{@code CIRUGIA} → +10%</li>
 *   <li>{@code PEDIATRIA} → +7%</li>
 * </ul>
 *
 * <p><b>Casos de uso:</b></p>
 * <ul>
 *   <li>Parte de la cadena de decoradores construida por {@link FabricaHonorario}.</li>
 *   <li>Aplicado automáticamente al calcular honorarios durante la facturación.</li>
 * </ul>
 */

public final class HonorarioPorEspecialidad extends HonorarioDecorator {

    private final Especialidad especialidad;

    /**
     * Crea un decorador que ajusta el honorario según la especialidad médica.
     *
     * @param inner componente {@link Honorario} que representa el cálculo previo al incremento
     * @param especialidad tipo de especialidad médica (CLINICA, CIRUGIA o PEDIATRIA)
     *
     * <p><b>Precondiciones:</b></p>
     * <ul>
     *   <li>{@code inner} debe ser una instancia válida de {@link Honorario}.</li>
     *   <li>{@code especialidad} no debe ser {@code null}.</li>
     * </ul>
     *
     * <p><b>Postcondición:</b> se crea un decorador configurado para aplicar el incremento de especialidad.</p>
     */
    public HonorarioPorEspecialidad(Honorario inner, Especialidad especialidad) {
        super(inner);
        this.especialidad = especialidad;
    }

    /**
     * Calcula el honorario total aplicando el incremento correspondiente a la especialidad del médico.
     *
     * @return monto total del honorario con el incremento aplicado
     *
     * <p><b>Precondición:</b> el componente {@code inner} y la {@code especialidad} deben estar correctamente inicializados.</p>
     * <p><b>Postcondición:</b> el valor retornado es mayor o igual al resultado de {@code inner.calcular()}.</p>
     *
     * <p><b>Ejemplo:</b></p>
     * <pre>
     * Honorario h = new HonorarioBasico(20000);
     * h = new HonorarioPorEspecialidad(h, Especialidad.CIRUGIA);
     * h.calcular(); // → 22000.0 (10% de incremento)
     * </pre>
     */
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
