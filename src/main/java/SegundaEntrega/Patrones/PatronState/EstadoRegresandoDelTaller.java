package SegundaEntrega.Patrones.PatronState;

import SegundaEntrega.Modelo.Negocio.Ambulancia;

public class EstadoRegresandoDelTaller implements IEstadoAmbulancia {

    private final Ambulancia ambulancia;

    public EstadoRegresandoDelTaller(Ambulancia ambulancia) {
        this.ambulancia = ambulancia;
    }

    @Override
    public void solicitarAtencionDomicilio() {
        System.out.println("   Ambulancia Regresando Del Taller: No puede atender domicilio ahora.");
        // permanece en 6)
    }

    @Override
    public void solicitarTraslado() {
        System.out.println("   Ambulancia Regresando Del Taller: No puede realizar traslado ahora.");
        // informa que no puede
    }

    @Override
    public void solicitarMantenimiento() {
        System.out.println("   Ambulancia Regresando Del Taller: No puede volver a taller ahora.");
        // informa que no puede
    }

    @Override
    public void retornarAClinica() {
        // Se llama cuando finaliza el viaje de regreso del taller
        System.out.println("   Ambulancia Regresando Del Taller -> Disponible (Llegó a la clínica)");
        ambulancia.setEstado(new EstadoDisponible(ambulancia));
    }

    // --- Verificaciones ---
    @Override
    public boolean puedeAtenderDomicilio() { return false; }
    @Override
    public boolean puedeTrasladar() { return false; }
    @Override
    public boolean puedeIrATaller() { return false; }
}