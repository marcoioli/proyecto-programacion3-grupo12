package SegundaEntrega.Patrones.Decorator;

import SegundaEntrega.Modelo.Datos.Configuraciones.TipoContratacion;

/**
 * Decorador concreto que incrementa el honorario médico según el tipo de contratación del profesional.
 *
 * <p><b>Rol en el patrón:</b></p>
 * <ul>
 *   <li>Decorador concreto del patrón {@code Decorator}.</li>
 *   <li>Extiende {@link HonorarioDecorator} y aplica un incremento sobre {@link #inner}.</li>
 * </ul>
 *
 * <p><b>Reglas de incremento:</b></p>
 * <ul>
 *   <li>{@code PLANTEL_PERMANENTE} → +10% sobre el honorario actual.</li>
 *   <li>{@code RESIDENTE} → +5% sobre el honorario actual.</li>
 * </ul>
 *
 * <p><b>Casos de uso:</b></p>
 * <ul>
 *   <li>Composición de cadena de decoradores en {@link FabricaHonorario}.</li>
 *   <li>Aplicación de incrementos contractuales durante la facturación de consultas o internaciones.</li>
 * </ul>
 */

public final class HonorarioPorContratacion extends HonorarioDecorator {

    private final TipoContratacion tipo;

    /**
     * Crea un decorador que ajusta el honorario según el tipo de contratación del médico.
     *
     * @param inner componente {@link Honorario} que representa el cálculo previo al incremento
     * @param tipo modalidad de contratación del médico
     *
     * <p><b>Precondiciones:</b></p>
     * <ul>
     *   <li>{@code inner} debe ser una instancia válida de {@link Honorario}.</li>
     *   <li>{@code tipo} no debe ser {@code null}.</li>
     * </ul>
     *
     * <p><b>Postcondición:</b> se crea un decorador listo para aplicar el incremento contractual correspondiente.</p>
     */

    public HonorarioPorContratacion(Honorario inner, TipoContratacion tipo) {
        super(inner);
        this.tipo = tipo;
    }

    /**
     * Calcula el honorario total aplicando el incremento según la modalidad de contratación.
     *
     * @return honorario total con el incremento aplicado
     *
     * <p><b>Precondición:</b> el componente {@code inner} y el {@code tipo} deben haber sido correctamente inicializados.</p>
     * <p><b>Postcondición:</b> el valor retornado es mayor o igual al resultado de {@code inner.calcular()}.</p>
     *
     * <p><b>Ejemplo:</b></p>
     * <pre>
     * Honorario h = new HonorarioBasico(20000);
     * h = new HonorarioPorContratacion(h, TipoContratacion.PLANTEL_PERMANENTE);
     * h.calcular(); // → 22000.0 (10% de incremento)
     * </pre>
     */

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