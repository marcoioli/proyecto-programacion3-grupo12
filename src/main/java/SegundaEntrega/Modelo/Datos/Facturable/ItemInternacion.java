package SegundaEntrega.Modelo.Datos.Facturable;

// Imports actualizados (¡Asegúrate que las clases de Habitacion estén en este paquete!)
import SegundaEntrega.Modelo.Datos.Habitaciones.Habitacion;
import SegundaEntrega.Modelo.Datos.Habitaciones.HabCompartida;
import SegundaEntrega.Modelo.Datos.Habitaciones.HabPrivada;
import SegundaEntrega.Modelo.Datos.Habitaciones.TerapiaIntensiva;
import SegundaEntrega.Modelo.Negocio.Clinica;
import SegundaEntrega.Modelo.Datos.Configuraciones.CatalogoCostos;

/**
 * Representa un ítem de internación (días en una habitación).
 * Implementa IFacturable para ser cobrada en una Factura.
 */
public class ItemInternacion implements IFacturable {

    private Habitacion habitacion;
    private int cantDias;

    public ItemInternacion(Habitacion habitacion, int cantDias) {
        assert habitacion != null : "La habitación no puede ser nula";
        assert cantDias > 0 : "La cantidad de días debe ser positiva";
        this.habitacion = habitacion;
        this.cantDias = cantDias;
    }

    // --- GETTERS ---
    public Habitacion getHabitacion() { return habitacion; }
    public int getCantDias() { return cantDias; }

    // --- IMPLEMENTACIÓN DE IFacturable (CORREGIDA) ---

    /**
     * Devuelve el costo total de la internación (costo diario * días).
     * Utiliza 'instanceof' para determinar el tipo de habitación y
     * obtener el costo correcto del CatalogoCostos.
     * @return El costo (subtotal) de la internación.
     */
    @Override
    public double subtotal() { // <-- Nombre de método corregido

        double costoDiario = 0.0;
        double subtotal = 0.0;

        try {
            CatalogoCostos costos = Clinica.getInstance().getCatalogoCostos();

            // Esta lógica simula el Double Dispatch de Etapa I
            // (La clase actual decide cómo usar el catálogo de costos)

            if (this.habitacion instanceof HabPrivada) {
                costoDiario = costos.getCostoHabPrivada();
                // APLICAR LÓGICA DE FACTORES DE ETAPA I
                if (cantDias == 1) {
                    subtotal = costoDiario * costos.getFactorPrivadaDia1(); // Asumimos costoDiario * factor
                } else if (cantDias <= 5) {
                    subtotal = costoDiario * cantDias * costos.getFactorPrivadaHasta5Dias();
                } else {
                    subtotal = costoDiario * cantDias * costos.getFactorPrivadaMasDe5Dias();
                }

            } else if (this.habitacion instanceof HabCompartida) {
                costoDiario = costos.getCostoHabCompartida();
                subtotal = costoDiario * this.cantDias;

            } else if (this.habitacion instanceof TerapiaIntensiva) {
                costoDiario = costos.getCostoTerapiaIntensiva();
                // APLICAR LÓGICA DE POTENCIA DE ETAPA I
                subtotal = costoDiario * Math.pow(this.cantDias, costos.getPotenciaTI());

            } else {
                // Si es solo "Habitacion" genérica o un tipo no reconocido
                System.err.println("Advertencia: Tipo de habitación no reconocido para facturación.");
                subtotal = 0.0; // O un costo base
            }

            // Sumar el costo de asignación
            subtotal += costos.getCostoAsignacion();

        } catch (Exception e) {
            System.err.println("Error al obtener costo de internación: " + e.getMessage());
            return 0.0; // Devuelve 0 si falla
        }

        assert subtotal >= 0 : "Subtotal de internación negativo";
        return subtotal;
    }

    /**
     * Descripción para mostrar en la factura.
     */
    @Override
    public String toString() {
        // Asumimos que Habitacion tiene un método getId() o similar
        // (Deberás añadir getId() a tu clase Habitacion de Etapa I si no lo tiene)
        return "Internación (" + cantDias + " días) - Habitación: " + habitacion.getId();
    }
}