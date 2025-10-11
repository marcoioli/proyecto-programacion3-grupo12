package Sala;

import java.util.*;
import Personas.Paciente;


/**
 * Representa la sala de espera privada de la clínica.
 * <p>
 * Solo puede haber un paciente dentro de la sala al mismo tiempo.
 * Si otro paciente intenta ingresar mientras ya hay un ocupante,
 * se aplica la comparación de prioridades (Niño, Joven, Mayor)
 * mediante el patrón <b>Double Dispatch</b>, definido en {@link PrioridadSala}.
 * </p>
 *
 * <p><b>Reglas del sistema:</b></p>
 * <ul>
 *   <li>Si la sala está vacía → el paciente ingresa directamente.</li>
 *   <li>Si ya hay un ocupante → se comparan sus rangos de prioridad.</li>
 *   <li>Si el nuevo paciente <b>gana</b> → reemplaza al ocupante actual (que pasa al {@link Patio}).</li>
 *   <li>Si <b>pierde</b> o <b>empata</b> → el nuevo paciente es derivado al {@link Patio}.</li>
 * </ul>
 *
 * <p><b>Rol en el sistema:</b></p>
 * <ul>
 *   <li>Parte del módulo de resolución de conflictos de sala de espera.</li>
 *   <li>Aplica el patrón <b>Double Dispatch</b> para evitar estructuras condicionales anidadas.</li>
 *   <li>Colabora con {@link Patio} para manejar los pacientes desplazados.</li>
 * </ul>
 *
 * <p><b>Casos de uso:</b></p>
 * <ul>
 *   <li>Ingreso de pacientes a la clínica mediante {@code SistemaClinica.ingresarPaciente()}.</li>
 *   <li>Resolución automática de conflictos entre pacientes de distinto rango etario.</li>
 *   <li>Liberación de la sala tras la atención o egreso del paciente.</li>
 * </ul>
 *
 * @see PrioridadSala
 * @see Patio
 * @see Resolucion
 */

public class SalaDeEsperaPrivada {
    public Paciente ocupante;
    //referencia unica?? preguntar
    private final Patio patio = Patio.getInstance();

    /**
     * Intenta ingresar un paciente a la sala.
     * <p>
     * Si la sala está vacía, el paciente ingresa directamente.
     * Si ya hay un ocupante, se utiliza {@link PrioridadSala#resolverContra(PrioridadSala)}
     * para determinar el resultado del conflicto.
     * </p>
     *
     * @param nuevo paciente que desea ingresar (no nulo)
     *
     * <p><b>Precondiciones:</b></p>
     * <ul>
     *   <li>{@code nuevo} debe ser una instancia válida de {@link Paciente}.</li>
     *   <li>El paciente debe tener configurado su rango de prioridad (no nulo).</li>
     * </ul>
     *
     * <p><b>Postcondición:</b></p>
     * <ul>
     *   <li>Si la sala estaba vacía, {@code nuevo} pasa a ser el ocupante.</li>
     *   <li>Si estaba ocupada:
     *     <ul>
     *       <li>El paciente con mayor prioridad queda en la sala.</li>
     *       <li>El otro es derivado al {@link Patio}.</li>
     *     </ul>
     *   </li>
     * </ul>
     *
     */
    public void ingresar(Paciente nuevo) {
        boolean salaVacia = (ocupante == null);
        Resolucion resultado = null;

        if (!salaVacia) {
            resultado = nuevo.getRango().resolverContra(ocupante.getRango());
        }

        if (salaVacia) {
            ocupante = nuevo;
        } else {
            switch (resultado) {
                case GANA_ESTE:
                    patio.moverAlPatio(ocupante);
                    ocupante = nuevo;
                    break;
                case GANA_OTRO:
                case EMPATE:
                default:
                    patio.moverAlPatio(nuevo);
                    break;
            }
        }

    }

    /**
     * Libera la sala solo si el paciente indicado es el ocupante actual.
     *
     * @param paciente paciente que se desea liberar (no nulo)
     *
     * <p><b>Precondición:</b></p>
     * <ul>
     *   <li>El paciente debe ser una instancia válida y haber ocupado la sala.</li>
     * </ul>
     *
     * <p><b>Postcondición:</b> si coincide con el ocupante actual, la sala queda vacía.</p>
     */
    public void liberarSiEs(Paciente paciente) {
        boolean mismoPaciente = (paciente != null && paciente.equals(ocupante));
        if (mismoPaciente) {
            ocupante = null;
        }
    }

    /**
     /**
     * Obtiene el paciente actual ocupando la sala.
     *
     * @return el paciente ocupante o {@code null} si la sala está vacía
     */

    public Paciente getOcupante() {
        return ocupante;
    }

    /**
     * Indica si la sala está vacia.
     *
     * @return true si no hay ocupante
     */
    public boolean estaVacia() {
        boolean vacia = false;
        if (ocupante == null) {
            vacia = true;
        }
        return vacia;
    }


}
