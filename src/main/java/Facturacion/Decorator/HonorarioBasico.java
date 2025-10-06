package Facturacion.Decorator;
import Configuracion.CatalogoCostos;

/**
 * Honorario base: representa el valor inicial antes de aplicar incrementos.
 */

public class HonorarioBasico implements Honorario {

    private final double base;

    public HonorarioBasico(CatalogoCostos costos) {
        this.base = costos.getCostoAsignacion(); // o un valor específico de honorario base
    }

    public HonorarioBasico(double base) {
        this.base = base;
    }

    @Override
    public double calcular() {
        return base;
    }
}
