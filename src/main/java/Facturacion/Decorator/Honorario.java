package Facturacion.Decorator;

import Configuracion.*;
import Personas.Medico;

/**
 * Interfaz base para el cálculo de honorarios médicos dentro del sistema de facturación.
 * <p>
 * Define el contrato común que deben cumplir todas las clases que participan en la
 * estructura de <b>decoradores de honorarios</b>, permitiendo extender el comportamiento
 * del cálculo sin modificar las clases existentes.
 * </p>
 *
 * <p><b>Rol en el patrón:</b></p>
 * <ul>
 *   <li>Componente del patrón <b>Decorator</b>.</li>
 *   <li>Implementada por {@link HonorarioBasico} (componente concreto).</li>
 *   <li>Extendida por los decoradores: {@link HonorarioPorEspecialidad},
 *       {@link HonorarioPorPosgrado} y {@link HonorarioPorContratacion}.</li>
 * </ul>
 *
 * <p><b>Casos de uso:</b></p>
 * <ul>
 *   <li>Calcular el honorario total de un médico al facturar una consulta o internación.</li>
 *   <li>Componer decoradores dinámicamente desde {@link FabricaHonorario}.</li>
 * </ul>
 *
 * <p><b>Invariante:</b></p>
 * <ul>
 *   <li>El método {@link #calcular()} debe retornar siempre un valor no negativo.</li>
 * </ul>
 */
public interface Honorario {

    /**
     * Calcula el monto total del honorario según las reglas aplicadas.
     * <p>
     * En implementaciones concretas, este método puede sumar o multiplicar
     * sobre el resultado del componente base según el decorador aplicado.
     * </p>
     *
     * @return monto total del honorario, expresado en {@code double}
     *
     * <p><b>Precondiciones:</b></p>
     * <ul>
     *   <li>Los parámetros de configuración o decoradores asociados deben estar correctamente inicializados.</li>
     * </ul>
     *
     * <p><b>Postcondiciones:</b></p>
     * <ul>
     *   <li>Devuelve un valor mayor o igual a cero.</li>
     *   <li>El resultado refleja todas las reglas de incremento configuradas.</li>
     * </ul>
     */
    double calcular();

}
