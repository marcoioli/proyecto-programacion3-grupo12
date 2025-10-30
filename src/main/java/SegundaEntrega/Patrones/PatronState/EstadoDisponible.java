package SegundaEntrega.Patrones.PatronState;

import SegundaEntrega.Modelo.Negocio.Ambulancia;

public class EstadoDisponible implements IEstadoAmbulancia {

    private final Ambulancia ambulancia; // Referencia al contexto

    public EstadoDisponible(Ambulancia ambulancia) {
        this.ambulancia = ambulancia;
    }

    @Override
    public void solicitarAtencionDomicilio() {
        System.out.println("   Ambulancia Disponible -> Atendiendo Domicilio");
        ambulancia.setEstado(new EstadoAtendiendoDomicilio(ambulancia));
    }

    @Override
    public void solicitarTraslado() {
        System.out.println("   Ambulancia Disponible -> Trasladando Paciente");
        ambulancia.setEstado(new EstadoTrasladandoPaciente(ambulancia));
    }

    @Override
    public void solicitarMantenimiento() {
        System.out.println("   Ambulancia Disponible -> En Taller");
        ambulancia.setEstado(new EstadoEnTaller(ambulancia));
    }

    @Override
    public void retornarAClinica() {
        // Ya está disponible en la clínica, no hace nada
        System.out.println("   Ambulancia ya está Disponible. No requiere retorno.");
    }

    // --- Verificaciones ---
    @Override
    public boolean puedeAtenderDomicilio() { return true; }
    @Override
    public boolean puedeTrasladar() { return true; }
    @Override
    public boolean puedeIrATaller() { return true; }
}