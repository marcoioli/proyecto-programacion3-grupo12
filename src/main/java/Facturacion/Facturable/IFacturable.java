package Facturacion.Facturable;

/**
 * Representa un concepto facturable (consulta, internación, etc.).
 */
public interface IFacturable {
    /**
     * Calcula el subtotal del concepto.
     * @return subtotal en {@code double}
     */
    double subtotal();



}
