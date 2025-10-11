package Configuracion;

/**
 * Catalogo centralizado de costos y parámetros del sistema.
 * <p>Separa datos de configuración de la lógica de negocio.</p>
 */

public final class CatalogoCostos {
    private double costoAsignacion = 0 ;
    private double costoHabCompartida = 0;
    private double costoHabPrivada = 0;
    private double costoTerapiaIntensiva = 0;


    // Factores para HabPrivada
    private double factorPrivadaDia1 =1.0 ; // p.ej., 1.0
    private double factorPrivadaHasta5Dias = 1.3; // p.ej., 1.3
    private double factorPrivadaMasDe5Dias = 2.0; // p.ej., 2.0

    // Parámetro de potencia para TI (p.ej., 2 -> cuadrático)
    private int potenciaTI = 2;

    // ---------------- Getters ----------------
    public double getCostoAsignacion() { return costoAsignacion; }
    public double getCostoHabCompartida() { return costoHabCompartida; }
    public double getCostoHabPrivada() { return costoHabPrivada; }
    public double getCostoTerapiaIntensiva() { return costoTerapiaIntensiva; }
    public double getFactorPrivadaDia1() { return factorPrivadaDia1; }
    public double getFactorPrivadaHasta5Dias() { return factorPrivadaHasta5Dias; }
    public double getFactorPrivadaMasDe5Dias() { return factorPrivadaMasDe5Dias; }

    public int getPotenciaTI() { return potenciaTI; }


    // ---------------- Setters fluidos ----------------
    public CatalogoCostos setCostoAsignacion(double valor) {
        this.costoAsignacion = valor;
        return this;
    }

    public CatalogoCostos setCostoHabCompartida(double valor) {
        this.costoHabCompartida = valor;
        return this;
    }

    public CatalogoCostos setCostoHabPrivada(double valor) {
        this.costoHabPrivada = valor;
        return this;
    }

    public CatalogoCostos setCostoTerapiaIntensiva(double valor) {
        this.costoTerapiaIntensiva = valor;
        return this;
    }

    public CatalogoCostos setFactorPrivadaDia1(double valor) {
        this.factorPrivadaDia1 = valor;
        return this;
    }

    public CatalogoCostos setFactorPrivadaHasta5Dias(double valor) {
        this.factorPrivadaHasta5Dias = valor;
        return this;
    }

    public CatalogoCostos setFactorPrivadaMasDe5Dias(double valor) {
        this.factorPrivadaMasDe5Dias = valor;
        return this;
    }

    public CatalogoCostos setPotenciaTI(int potencia) {
        this.potenciaTI = potencia;
        return this;
    }




}
