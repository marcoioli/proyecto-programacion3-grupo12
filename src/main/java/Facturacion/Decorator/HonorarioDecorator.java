package Facturacion.Decorator;

/**
 * Clase abstracta que define la estructura general de un <b>Decorator</b> de honorario médico.
 * <p>
 * Forma parte del patrón <b>Decorator</b>, proporcionando la base común
 * para todos los incrementos de honorarios aplicables sobre un componente
 * {@link Honorario}. Cada subclase concreta extiende esta clase para modificar
 * o ampliar el resultado del método {@link #calcular()} del componente interno.
 * </p>
 *
 * <p><b>Rol en el patrón:</b></p>
 * <ul>
 *   <li>Actúa como <i>clase abstracta decoradora</i> (Decorator base).</li>
 *   <li>Contiene una referencia al componente interno {@link Honorario inner}.</li>
 *   <li>Las subclases definen la lógica del incremento concreto (por especialidad, posgrado, contratación, etc.).</li>
 * </ul>
 *
 * <p><b>Casos de uso:</b></p>
 * <ul>
 *   <li>Extendida por {@link HonorarioPorEspecialidad}, {@link HonorarioPorPosgrado},
 *       {@link HonorarioPorContratacion}, entre otros.</li>
 *   <li>Utilizada por {@link FabricaHonorario} para construir la cadena de cálculo completa.</li>
 * </ul>
 */

public abstract class HonorarioDecorator implements Honorario {

    protected final Honorario inner;

    /**
     * Crea un nuevo decorador que envuelve a otro componente {@link Honorario}.
     *
     * @param inner instancia del componente a decorar
     *Inner es una clase que esta dentro de otra clase
     * <p><b>Precondiciones:</b></p>
     * <ul>
     *   <li>{@code inner} debe ser una instancia válida de {@link Honorario}.</li>
     * </ul>
     *
     * <p><b>Postcondición:</b> se crea un decorador listo para extender el cálculo del honorario.</p>
     */
    protected HonorarioDecorator(Honorario inner) {
        this.inner = inner;
    }

    @Override
    public abstract double calcular();
}
