package Facturacion.Decorator;

import Configuracion.*;
import Personas.Medico;

/**
 * Interfaz base para el cálculo de honorarios médicos.
 */

public interface Honorario {

    /**
     * Calcula el monto total del honorario según las reglas aplicadas.
     * @return monto total en {@code double}
     */
    double calcular();

}
