package Facturacion;

import Configuracion.Clinica;
import Personas.Paciente;
import Facturacion.Facturable.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Factura emitida al egresar un paciente.
 * Contiene los datos de la clínica, paciente, internaciones y consultas.
 */
public final class Factura {

    private final long numero;
    private final Clinica clinica;
    private final Paciente paciente;
    private final LocalDate fechaIngreso;
    private final LocalDate fechaEgreso;
    private final List<IFacturable> items = new ArrayList<>();

    public Factura(long numero, Clinica clinica, Paciente paciente,
                   LocalDate fechaIngreso, LocalDate fechaEgreso) {
        this.numero = numero;
        this.clinica = clinica;
        this.paciente = paciente;
        this.fechaIngreso = fechaIngreso;
        this.fechaEgreso = fechaEgreso;
    }

    public void addItem(IFacturable item) {
        items.add(item);
    }

    public double total() {
        return items.stream().mapToDouble(IFacturable::subtotal).sum();
        //devuelve la suma total de los subtotales de todos los objetos facturables que hay dentro de la colección
    }

    public int cantidadDias() {
        return (int) java.time.temporal.ChronoUnit.DAYS.between(fechaIngreso, fechaEgreso);
        //calcula la cantidad de dias entre dos fechas y lo devuelce como un entero
    }

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

    public long getNumero() {
        return numero;
    }

}

