package Main;

import Configuracion.*;
import Facturacion.Factura;
import Habitaciones.*;
import Personas.*;
import Sistema.SistemaClinica;
import Sala.*;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        // Datos de la clínica
        Clinica clinica = new Clinica("Clínica San Martín", "Av. Siempreviva 742",
                "223-4567890", "Mar del Plata");

        // Costos base
        CatalogoCostos costos = new CatalogoCostos()
                .setCostoAsignacion(20000)
                .setCostoHabCompartida(8000)
                .setCostoHabPrivada(15000)
                .setCostoTerapiaIntensiva(25000);

        // Sistema
        SistemaClinica sistema = new SistemaClinica(clinica, costos);

        // Médicos
        Medico m1 = new Medico("111", "Juan", "Pérez", "M001",
                Especialidad.CIRUGIA, TipoContratacion.RESIDENTE, Posgrado.MAGISTER);
        Medico m2 = new Medico("222", "Ana", "Suárez", "M002",
                Especialidad.CLINICA, TipoContratacion.PLANTEL_PERMANENTE, Posgrado.DOCTOR);
        Medico m3 = new Medico("333", "Carlos", "López", "M003",
                Especialidad.PEDIATRIA, TipoContratacion.RESIDENTE, Posgrado.NINGUNO);

        sistema.registraMedico(m1);
        sistema.registraMedico(m2);
        sistema.registraMedico(m3);

        // Pacientes
        Paciente p1 = new Paciente("555", "Luis", "Martínez", "HC001", "9999", new Nino());
        Paciente p2 = new Paciente("666", "Sofía", "Gómez", "HC002","8411", new Mayor());

        sistema.registraPaciente(p1);
        sistema.registraPaciente(p2);

        // Flujo del sistema (exactamente como pediste)
        sistema.ingresaPaciente(p1);
        sistema.ingresaPaciente(p2);

        sistema.atiendePaciente(m1, p1);
        sistema.atiendePaciente(m2, p1);
        Factura factura1 = sistema.egresaPaciente(p1);

        sistema.atiendePaciente(m3, p2);
        sistema.internaPaciente(p2, new HabPrivada("H3"), 4);
        Factura factura2 = sistema.egresaPaciente(p2, 4);

        sistema.imprimirFacturas();

        sistema.reporteActividad(
                m3,
                LocalDate.of(2025, 10, 1),
                LocalDate.of(2025, 10, 31)
        );

    }
}
