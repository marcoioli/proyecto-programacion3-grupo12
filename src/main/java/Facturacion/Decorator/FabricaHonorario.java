package Facturacion.Decorator;

import Configuracion.CatalogoCostos;
import Personas.Medico;

/**
 *  Fábrica responsable de construir la cadena completa de objetos {@link Honorario}
 *   Esta clase centraliza la creación de los incrementos de honorarios
 *   según las reglas del sistema
 */

public class FabricaHonorario {
    private final CatalogoCostos costos;

    /**
     * Crea una nueva fábrica con la configuración de costos actual.
     *
     * @param costos catálogo con los valores base del sistema
     *
     */
    public FabricaHonorario(CatalogoCostos costos) {
        this.costos = costos;
    }

    /**
     * Crea el objeto honorario completo para un medico, aplicando los incrementos
     * @param medico
     * @return
     */

    public Honorario crearPara(Medico medico) {
        // 1️⃣ Base
        Honorario h = new HonorarioBasico(costos.getCostoAsignacion());

        // 2️⃣ Decoradores (orden correcto)
        h = new HonorarioPorEspecialidad(h, medico.getEspecialidad());
        h = new HonorarioPorPosgrado(h, medico.getPosgrado());
        h = new HonorarioPorContratacion(h, medico.getTipoContratacion());

        // Devuelve la cadena completa
        return h;
    }

}
