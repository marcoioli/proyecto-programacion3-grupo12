package Configuracion;

/**
 * Catalogo centralizado de costos y parametros del sistema.
 * <p>Separa datos de configuracion de la logica de negocio.</p>
 *
 *   <p>
 *   Esta clase encapsula los valores de configuración que intervienen en el cálculo
 *   de aranceles por internación y costos de habitaciones, separando los datos
 *   de configuración de la lógica de negocio.
 *   </p>
 *
 *  <p><b>Ejemplo de uso:</b></p>
 *   <pre>
 *   CatalogoCostos costos = new CatalogoCostos()
 *       .setCostoAsignacion(1000)
 *       .setCostoHabPrivada(2000)
 *       .setFactorPrivadaHasta5Dias(1.3);
 *   </pre
 *
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

    /**
     * Establece el costo base por asignación de habitación.
     * @param valor costo en pesos
     * @return esta instancia para encadenar llamadas
     */
    public CatalogoCostos setCostoAsignacion(double valor) {
        this.costoAsignacion = valor;
        return this;
    }

    /**
     * Establece el costo diario de habitación compartida.
     * @param valor costo en pesos
     * @return esta instancia para encadenar llamadas
     */
    public CatalogoCostos setCostoHabCompartida(double valor) {
        this.costoHabCompartida = valor;
        return this;
    }

    /**
     * Establece el costo diario de habitación compartida.
     * @param valor costo en pesos
     * @return esta instancia para encadenar llamadas
     */
    public CatalogoCostos setCostoHabPrivada(double valor) {
        this.costoHabPrivada = valor;
        return this;
    }

    /**
     * Establece el costo diario de habitación compartida.
     * @param valor costo en pesos
     * @return esta instancia para encadenar llamadas
     */
    public CatalogoCostos setCostoTerapiaIntensiva(double valor) {
        this.costoTerapiaIntensiva = valor;
        return this;
    }

    /**
     * Define el factor de multiplicación para habitación privada (día 1).
     * @param valor factor multiplicador
     * @return esta instancia
     */
    public CatalogoCostos setFactorPrivadaDia1(double valor) {
        this.factorPrivadaDia1 = valor;
        return this;
    }

    /**
     * Define el factor de multiplicación para habitación privada (2 a 5 dias).
     * @param valor factor multiplicador
     * @return esta instancia
     */
    public CatalogoCostos setFactorPrivadaHasta5Dias(double valor) {
        this.factorPrivadaHasta5Dias = valor;
        return this;
    }
    /**
     * Define el factor de multiplicación para habitación privada (más de 5 días).
     * @param valor factor multiplicador
     * @return esta instancia para encadenar llamadas
     */
    public CatalogoCostos setFactorPrivadaMasDe5Dias(double valor) {
        this.factorPrivadaMasDe5Dias = valor;
        return this;
    }

    /**
     * Define la potencia de crecimiento usada para calcular costos de terapia intensiva.
     * @param potencia exponente aplicado al número de días de internación
     * @return esta instancia para encadenar llamadas
     */
    public CatalogoCostos setPotenciaTI(int potencia) {
        this.potenciaTI = potencia;
        return this;
    }




}
