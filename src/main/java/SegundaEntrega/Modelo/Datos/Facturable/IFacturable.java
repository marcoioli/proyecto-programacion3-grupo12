package SegundaEntrega.Modelo.Datos.Facturable;

/**
 * Define el contrato común para todos los conceptos que pueden incluirse
 * dentro de una factura del sistema (por ejemplo, {@link Consulta} o {@code ItemInternacion}).
 * <p>
 * Permite aplicar polimorfismo en el módulo de facturación, ya que cada tipo de
 * servicio define su propio modo de calcular el subtotal.
 * </p>
 * (Javadoc de tu Etapa I...)
 */
public interface IFacturable {
    /**
     * Calcula el subtotal del concepto.
     * @return subtotal en {@code double}
     * <p><b>Invariante:</b> El valor devuelto debe ser >= 0.</p>
     */
    double subtotal(); // Dejamos solo este método

    // Eliminamos getFacturableSubtotal() para evitar duplicados
}
