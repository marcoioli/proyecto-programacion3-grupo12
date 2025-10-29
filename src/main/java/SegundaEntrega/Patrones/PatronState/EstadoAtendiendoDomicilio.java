package SegundaEntrega.Patrones.PatronState;

import SegundaEntrega.Modelo.Negocio.Ambulancia;

public class EstadoAtendiendoDomicilio implements IEstadoAmbulancia {

    private final Ambulancia ambulancia;

    public EstadoAtendiendoDomicilio(Ambulancia ambulancia) {
        this.ambulancia = ambulancia;
    }

    @Override
    public void solicitarAtencionDomicilio() {
        System.out.println("   Ambulancia Atendiendo Domicilio: Ya está en una atención.");
        // permanece en 3)
    }

    @Override
    public void solicitarTraslado() {
        System.out.println("   Ambulancia Atendiendo Domicilio: No puede iniciar un traslado ahora.");
        // informa que no puede
    }

    @Override
    public void solicitarMantenimiento() {
        System.out.println("   Ambulancia Atendiendo Domicilio: No puede ir a taller ahora.");
        // informa que no puede
    }

    @Override
    public void retornarAClinica() {
        // Asumimos que retornarAClinica() se llama cuando termina la atención y no requiere traslado
        System.out.println("   Ambulancia Atendiendo Domicilio -> Regresando Sin Paciente");
        ambulancia.setEstado(new EstadoRegresandoSinPaciente(ambulancia));
        // Aquí podríamos iniciar un timer/hilo simple para simular el viaje de regreso
        // y que llame a ambulancia.retornarAClinica() nuevamente cuando termine.
        // Por ahora, el cambio de estado es inmediato para simplificar.
    }

    // --- Verificaciones ---
    @Override
    public boolean puedeAtenderDomicilio() { return false; } // Ya está atendiendo
    @Override
    public boolean puedeTrasladar() { return false; }
    @Override
    public boolean puedeIrATaller() { return false; }
}