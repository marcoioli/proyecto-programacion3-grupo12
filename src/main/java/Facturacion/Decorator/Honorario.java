package Facturacion.Decorator;

import Configuracion.*;
import Personas.Medico;

/**
 * Interfaz base para el calculo de honorarios medicos.
 */

public interface Honorario {

    /**
     * Calcula el monto total del honorario según las reglas aplicadas.
     * @return monto total en {@code double}
     */
    double calcular();

}
