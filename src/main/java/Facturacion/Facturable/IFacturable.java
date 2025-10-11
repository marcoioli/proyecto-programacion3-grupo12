package Facturacion.Facturable;

/**
 * Define el contrato común para todos los conceptos que pueden incluirse
 * dentro de una factura del sistema (por ejemplo, {@link Consulta} o {@code ItemInternacion}).
 * <p>
 * Permite aplicar polimorfismo en el módulo de facturación, ya que cada tipo de
 * servicio define su propio modo de calcular el subtotal.
 * </p>
 *
 * <p><b>Rol en el sistema:</b></p>
 * <ul>
 *   <li>Interfaz raíz del módulo de facturación.</li>
 *   <li>Implementada por todas las clases que representan servicios o prestaciones cobrables.</li>
 *   <li>Facilita la composición de facturas con distintos tipos de ítems (consultas, internaciones, etc.).</li>
 * </ul>
 *
 * <p><b>Casos de uso:</b></p>
 * <ul>
 *   <li>Construcción de facturas en el módulo {@code Facturacion}.</li>
 *   <li>Registro y cálculo de subtotales en {@code Factura.calcularTotal()}.</li>
 * </ul>
 *
 * <p><b>Invariante:</b></p>
 * <ul>
 *   <li>El método {@link #subtotal()} debe devolver siempre un valor mayor o igual a 0.</li>
 * </ul>
 */

public interface IFacturable {
    /**
     * Calcula el subtotal del concepto.
     * @return subtotal en {@code double}
     */
    double subtotal();



}
