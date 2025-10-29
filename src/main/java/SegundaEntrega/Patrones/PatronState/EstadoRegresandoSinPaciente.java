package SegundaEntrega.Patrones.PatronState;

import SegundaEntrega.Modelo.Negocio.Ambulancia;

public class EstadoRegresandoSinPaciente implements IEstadoAmbulancia {

    private final Ambulancia ambulancia;

    public EstadoRegresandoSinPaciente(Ambulancia ambulancia) {
        this.ambulancia = ambulancia;
    }

    @Override
    public void solicitarAtencionDomicilio() {
        // Puede interrumpir el regreso para ir a otro domicilio
        System.out.println("   Ambulancia Regresando Sin Paciente -> Atendiendo Domicilio (nueva solicitud)");
        ambulancia.setEstado(new EstadoAtendiendoDomicilio(ambulancia));
    }

    @Override
    public void solicitarTraslado() {
        // Puede interrumpir el regreso para iniciar un traslado
        System.out.println("   Ambulancia Regresando Sin Paciente -> Trasladando Paciente (nueva solicitud)");
        ambulancia.setEstado(new EstadoTrasladandoPaciente(ambulancia));
    }

    @Override
    public void solicitarMantenimiento() {
        System.out.println("   Ambulancia Regresando Sin Paciente: No puede ir a taller ahora (debe llegar primero).");
        // informa que no puede
    }

    @Override
    public void retornarAClinica() {
        // Se llama cuando finaliza el viaje de regreso
        System.out.println("   Ambulancia Regresando Sin Paciente -> Disponible (Llegó a la clínica)");
        ambulancia.setEstado(new EstadoDisponible(ambulancia));
    }

    // --- Verificaciones ---
    // Aunque puede atender nuevas solicitudes, mientras está regresando, estrictamente no está *disponible*
    // para que el wait() funcione correctamente. Una vez que acepta la nueva solicitud, cambia de estado.
    @Override
    public boolean puedeAtenderDomicilio() { return true; } // Puede aceptar una nueva solicitud
    @Override
    public boolean puedeTrasladar() { return true; } // Puede aceptar una nueva solicitud
    @Override
    public boolean puedeIrATaller() { return false; }
}