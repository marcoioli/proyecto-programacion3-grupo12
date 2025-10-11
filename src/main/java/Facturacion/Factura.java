package Facturacion;

import Configuracion.Clinica;
import Personas.Paciente;
import Facturacion.Facturable.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa una factura emitida al egresar un {@link Paciente} de la {@link Clinica}.
 * <p>
 * Contiene la información del paciente, las fechas de internación, las consultas médicas,
 * los ítems facturables y el cálculo total. Cada ítem implementa la interfaz {@link IFacturable},
 * lo que permite incluir distintos tipos de conceptos (consultas, internaciones, etc.)
 * sin acoplar la clase a sus implementaciones concretas.
 * </p>
 *
 * <p><b>Rol en el sistema:</b></p>
 * <ul>
 *   <li>Componente principal del módulo de facturación.</li>
 *   <li>Generada por {@code SistemaClinica} al egresar un paciente.</li>
 * </ul>
 *
 * <p><b>Casos de uso:</b></p>
 * <ul>
 *   <li>Emitir facturas con consultas e internaciones asociadas al egreso de un paciente.</li>
 *   <li>Calcular subtotales y total de cobro mediante composición de objetos {@link IFacturable}.</li>
 *   <li>Generar reportes de facturación o historial de atención del paciente.</li>
 * </ul>
 *
 */

public final class Factura {

    private final long numero;
    private final Clinica clinica;
    private final Paciente paciente;
    private final LocalDate fechaIngreso;
    private final LocalDate fechaEgreso;
    private final List<IFacturable> items = new ArrayList<>();


    /**
     * Crea una nueva factura con los datos de la clínica, paciente y período de internación.
     *
     * @param numero número único de la factura
     * @param clinica clínica emisora
     * @param paciente paciente egresado
     * @param fechaIngreso fecha de ingreso del paciente
     * @param fechaEgreso fecha de egreso del paciente
     *
     * <p><b>Precondiciones:</b></p>
     * <ul>
     *   <li>{@code clinica} y {@code paciente} deben estar registrados en el sistema.</li>
     *   <li>{@code fechaIngreso} y {@code fechaEgreso} no pueden ser {@code null}.</li>
     *   <li>{@code fechaEgreso} debe ser igual o posterior a {@code fechaIngreso}.</li>
     * </ul>
     *
     * <p><b>Postcondición:</b> se crea una factura vacía, lista para agregar ítems facturables.</p>
     */
    public Factura(long numero, Clinica clinica, Paciente paciente,
                   LocalDate fechaIngreso, LocalDate fechaEgreso) {
        this.numero = numero;
        this.clinica = clinica;
        this.paciente = paciente;
        this.fechaIngreso = fechaIngreso;
        this.fechaEgreso = fechaEgreso;
    }

    /**
     * Agrega un ítem facturable (consulta, internación, etc.) a la factura.
     *
     * @param item ítem facturable a agregar
     *
     * <p><b>Precondición:</b> el ítem debe haber sido correctamente calculado
     * y pertenecer al paciente asociado a la factura.</p>
     *
     * <p><b>Postcondición:</b> el ítem queda incluido en la lista {@code items}.</p>
     */
    public void addItem(IFacturable item) {
        items.add(item);
    }

    /**
     * Calcula el total de la factura sumando los subtotales de todos los ítems facturables.
     *
     * @return monto total de la factura
     *
     * <p><b>Precondición:</b> todos los ítems incluidos deben estar correctamente inicializados.</p>
     * <p><b>Postcondición:</b> el valor retornado es mayor o igual a la suma de los subtotales individuales.
     *     Devuelve la suma total de los subtotales de todos los objetos facturables que hay dentro de la colección
     * </p>
     */

    public double total() {
        return items.stream().mapToDouble(IFacturable::subtotal).sum();
        //devuelve la suma total de los subtotales de todos los objetos facturables que hay dentro de la colección
    }

    /**
     * Calcula la cantidad de días entre la fecha de ingreso y egreso del paciente.
     *
     * @return número de días de estadía en la clínica
     *
     * <p><b>Precondición:</b> las fechas de ingreso y egreso deben ser válidas y no nulas.</p>
     * <p><b>Postcondición:</b> el resultado es un número entero mayor o igual a 0.</p>
     */
    public int cantidadDias() {
        return (int) java.time.temporal.ChronoUnit.DAYS.between(fechaIngreso, fechaEgreso);
        //calcula la cantidad de dias entre dos fechas y lo devuelce como un entero
    }

    /**
     * Imprime por consola una representación legible de la factura, incluyendo
     * los datos del paciente, clínica, ítems facturados y total.
     *
     * <p><b>Precondición:</b> la factura debe contener al menos un ítem o internación.</p>
     * <p><b>Postcondición:</b> se muestra el detalle completo en la salida estándar.</p>
     */

    public void imprimir() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        System.out.println("----------------------------------------------------------");
        System.out.println("Clínica: " + clinica.getNombre());
        System.out.println("Dirección: " + clinica.getDireccion() + " - " + clinica.getCiudad());
        System.out.println("Teléfono: " + clinica.getTelefono());
        System.out.println("----------------------------------------------------------");
        System.out.println("Nº Factura: " + numero);
        System.out.println("Nombre Paciente: " + paciente.getNombre() + " " + paciente.getApellido());
        System.out.println("Fecha Ingreso: " + fechaIngreso.format(fmt));
        System.out.println("Fecha Egreso: " + fechaEgreso.format(fmt));
        System.out.println("Cantidad de días: " + cantidadDias());
        System.out.println();

        boolean mostroInternacion = false;
        System.out.println("Consultas Médicas:");
        for (IFacturable item : items) {
            if (item instanceof ItemInternacion) {
                ItemInternacion i = (ItemInternacion) item;
                System.out.printf("Habitación tipo: %-25s Costo: $%10.2f%n",
                        i.getInternacion().getHabitacion().getClass().getSimpleName(),
                        i.subtotal());
                mostroInternacion = true;
            } else if (item instanceof Consulta ) {
                Consulta c = (Consulta) item;
                System.out.printf("Nombre Médico: %-20s Especialidad: %-12s Subtotal: $%10.2f%n",
                        c.getMedico().getNombre() + " " + c.getMedico().getApellido(),
                        c.getMedico().getEspecialidad(),
                        c.subtotal());
            }
        }

        if (!mostroInternacion) {
            System.out.println("(sin internación)");
        }

        System.out.println("----------------------------------------------------------");
        System.out.printf("Total: $%.2f%n", total());
        System.out.println("----------------------------------------------------------\n");
    }

    public List<IFacturable> getItems() {
        return items;
    }

    public LocalDate getFecha() {
        return fechaEgreso;
    }


}

