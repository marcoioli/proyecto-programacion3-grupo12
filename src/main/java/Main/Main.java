package Main;

import Configuracion.*;
import Facturacion.Factura;
import Habitaciones.*;
import Personas.*;
import Sistema.SistemaClinica;
import Sala.*;
import Excepciones.*;
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
        Medico m1 = new Medico("101", "Juan", "Pérez",
                "San Martín 123", "Mar del Plata", "2235112233",
                "M001", Especialidad.CIRUGIA,
                TipoContratacion.PLANTEL_PERMANENTE, Posgrado.DOCTOR);

        Medico m2 = new Medico("102", "Ana", "Suárez",
                "Av. Colón 850", "Mar del Plata", "2235234567",
                "M002", Especialidad.CLINICA,
                TipoContratacion.RESIDENTE, Posgrado.MAGISTER);

        Medico m3 = new Medico("103", "Carlos", "López",
                "Rivadavia 742", "Balcarce", "2266432123",
                "M003", Especialidad.PEDIATRIA,
                TipoContratacion.RESIDENTE, Posgrado.NINGUNO);

        Medico m4 = new Medico("104", "Lucía", "Fernández",
                "Belgrano 950", "Mar del Plata", "2235987456",
                "M004", Especialidad.CLINICA,
                TipoContratacion.PLANTEL_PERMANENTE, Posgrado.MAGISTER);

        Medico m5 = new Medico("105", "Ricardo", "Gómez",
                "Italia 630", "Miramar", "2291523488",
                "M005", Especialidad.CIRUGIA,
                TipoContratacion.RESIDENTE, Posgrado.NINGUNO);

        Medico m6 = new Medico("106", "Valeria", "Martínez",
                "Independencia 1100", "Tandil", "2494425678",
                "M006", Especialidad.PEDIATRIA,
                TipoContratacion.PLANTEL_PERMANENTE, Posgrado.DOCTOR);

        sistema.registraMedico(m1);
        sistema.registraMedico(m2);
        sistema.registraMedico(m3);
        sistema.registraMedico(m4);
        sistema.registraMedico(m5);
        sistema.registraMedico(m6);

        // Pacientes
        Paciente p1 = new Paciente("201", "Lucas", "Martín","Calle San Martín 123", "Mar del Plata", "2235123456" ,"1000", new Nino());
        Paciente p2 = new Paciente("202", "Sofía", "Gómez", "Av. Colón 850", "Mar del Plata", "2235345678", "1001", new Joven());
        Paciente p3 = new Paciente("203", "Martín", "Fernández", "Calle Belgrano 742", "Miramar", "2291523476", "1002", new Mayor());
        Paciente p4 = new Paciente("204", "Valentina", "Ruiz", "Rivadavia 1030", "Balcarce", "2266412398", "1003", new Joven());
        Paciente p5 = new Paciente("205", "Joaquín", "López", "Italia 620", "Mar del Plata", "2235987456", "1004", new Nino());
        Paciente p6 = new Paciente("206", "Carolina", "Pérez", "Independencia 900", "Tandil", "2494432100", "1005", new Mayor());


        sistema.registraPaciente(p1);
        sistema.registraPaciente(p2);
        sistema.registraPaciente(p3);
        sistema.registraPaciente(p4);
        sistema.registraPaciente(p5);
        sistema.registraPaciente(p6);

        // Flujo del sistema
        try {
            sistema.ingresaPaciente(p1); // niño
            sistema.ingresaPaciente(p2); // joven
            sistema.ingresaPaciente(p3); // mayor
            // Niño gana prioridad -> entra primero a sala

            sistema.atiendePaciente(m1, p1);
            sistema.atiendePaciente(m2, p1);
            sistema.egresaPaciente(p1);

            sistema.atiendePaciente(m3, p2);
            sistema.internaPaciente(p2, new HabPrivada("H1"), 3);
            sistema.egresaPaciente(p2, 3);

            sistema.atiendePaciente(m4, p3);
            sistema.internaPaciente(p3, new HabCompartida("H2"), 5);
            sistema.egresaPaciente(p3, 5);
        }catch (PacienteNoRegistradoException e) {
            System.out.println("Error: " + e.getMessage());

        }
        sistema.imprimirFacturas();

        /* sistema.reporteActividad(
                m3,
                LocalDate.of(2025, 10, 1),
                LocalDate.of(2025, 10, 31)
        );
        */
    }
}
