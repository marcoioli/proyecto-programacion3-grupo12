package SegundaEntrega.Patrones.Decorator;

import SegundaEntrega.Modelo.Datos.Configuraciones.Posgrado;

/**
 * Decorador concreto que incrementa el honorario médico según su nivel de posgrado académico.
 *
 * <p><b>Rol en el patrón:</b></p>
 * <ul>
 *   <li>Decorador concreto dentro del patrón {@code Decorator}.</li>
 *   <li>Extiende {@link HonorarioDecorator} y aplica un incremento basado en {@link Posgrado}.</li>
 * </ul>
 *
 * <p><b>Reglas de incremento:</b></p>
 * <ul>
 *   <li>{@code NINGUNO} → sin incremento.</li>
 *   <li>{@code MAGISTER} → +5%.</li>
 *   <li>{@code DOCTOR} → +10%.</li>
 * </ul>
 *
 * <p><b>Casos de uso:</b></p>
 * <ul>
 *   <li>Composición automática en {@link FabricaHonorario} para determinar el honorario final del médico.</li>
 *   <li>Aplicación de incrementos por formación académica en la facturación de consultas o internaciones.</li>
 * </ul>
 */

public final class HonorarioPorPosgrado extends HonorarioDecorator {

    private final Posgrado posgrado;


    /**
     * Crea un decorador que ajusta el honorario según el nivel de posgrado del médico.
     *
     * @param inner componente {@link Honorario} que representa el cálculo previo al incremento
     * @param posgrado nivel académico del médico

     * <p><b>Precondiciones:</b></p>
     * <ul>
     *   <li>{@code inner} debe ser una instancia válida de {@link Honorario}.</li>
     *   <li>{@code posgrado} no debe ser {@code null}.</li>
     * </ul>
     *
     * <p><b>Postcondición:</b> se crea un decorador configurado para aplicar el incremento académico correspondiente.</p>
     */
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
