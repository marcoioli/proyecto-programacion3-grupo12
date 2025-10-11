package Facturacion.Decorator;

import Configuracion.CatalogoCostos;
import Personas.Medico;

/**
 * Fábrica responsable de construir la cadena completa de objetos {@link Honorario}
 * aplicando el patrón <b>Decorator</b> según las reglas de cálculo definidas
 * para los médicos de la clínica.
 * <p>
 * Esta clase centraliza la creación y composición dinámica de los incrementos
 * de honorarios (por especialidad, posgrado y tipo de contratación),
 * desacoplando el código de facturación de los detalles de cálculo.
 * </p>
 *
 * <p><b>Casos de uso:</b></p>
 * <ul>
 *   <li>Durante la facturación de una consulta, para calcular el honorario del médico.</li>
 *   <li>En reportes o simulaciones de costos médicos.</li>
 *   <li>Como parte del flujo de negocio de {@code SistemaClinica.egresarPaciente()}.</li>
 * </ul>
 *
 * <p><b>Relaciones:</b></p>
 * <ul>
 *   <li>Depende de {@link CatalogoCostos} para obtener el valor base de honorarios.</li>
 *   <li>Utiliza decoradores de tipo {@link HonorarioPorEspecialidad},
 *       {@link HonorarioPorPosgrado} y {@link HonorarioPorContratacion}.</li>
 * </ul>
 */

public class FabricaHonorario {
    private final CatalogoCostos costos;

    /**
     * Crea una nueva fábrica con la configuración de costos actual.
     *
     * @param costos catálogo con los valores base del sistema
     *      <p><b>Precondiciones:</b></p>
     *      <ul>
     *        <li>{@code costos} debe ser una instancia válida y configurada del sistema, no puede ser nulo</li>
     *      </ul>
     *
     *      <p><b>Postcondición:</b> se crea una fábrica lista para generar estructuras de honorarios.</p>
     *
     */
    public FabricaHonorario(CatalogoCostos costos) {
        this.costos = costos;
    }

    /**
     * Construye la cadena completa de decoradores de {@link Honorario} para un médico determinado.
     * <p>
     * El cálculo se realiza en el siguiente orden:
     * </p>
     * <ol>
     *   <li>Se crea un {@link HonorarioBasico} con el valor base del catálogo.</li>
     *   <li>Se aplica {@link HonorarioPorEspecialidad} según la especialidad del médico.</li>
     *   <li>Se aplica {@link HonorarioPorPosgrado} según su título de posgrado (si corresponde).</li>
     *   <li>Se aplica {@link HonorarioPorContratacion} según su tipo de contratación.</li>
     * </ol>
     *
     * @param medico instancia del médico para el cual se calculará el honorario
     * @return un objeto {@link Honorario} que representa el honorario completo con todos los incrementos aplicados
     *
     * <p><b>Precondiciones:</b></p>
     * <ul>
     *   <li>{@code medico} debe estar correctamente inicializado y contener sus atributos de especialidad, posgrado y contratación.</li>
     *   <li>El {@link CatalogoCostos} debe tener configurados los valores base y factores correspondientes.</li>
     * </ul>
     *
     * <p><b>Postcondición:</b> se obtiene una instancia funcional de {@link Honorario}
     * lista para calcular el monto final mediante {@code subtotal()}.</p>
     */

    public Honorario crearPara(Medico medico) {
        // 1️⃣ Base
        Honorario h = new HonorarioBasico(medico.getHonorarioBase());

        h = new HonorarioPorEspecialidad(h, medico.getEspecialidad());
        h = new HonorarioPorPosgrado(h, medico.getPosgrado());
        h = new HonorarioPorContratacion(h, medico.getTipoContratacion());

        // Devuelve la cadena completa
        return h;
    }

}
