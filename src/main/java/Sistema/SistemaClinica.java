package Sistema;

import Configuracion.*;
import Facturacion.*;
import Facturacion.Decorator.*;
import Facturacion.Facturable.Consulta;
import Facturacion.Facturable.ItemInternacion;
import Habitaciones.*;
import Personas.*;
import Reportes.ActividadMedica;
import Reportes.ReporteActividad;
import Sala.*;
import java.time.LocalDate;
import java.util.*;
import Excepciones.*;


/**
 * Fachada principal del sistema. Gestiona medicos, pacientes, sala, internaciones y facturacion.
 */
public final class SistemaClinica {

    private final Clinica clinica;
    private final CatalogoCostos costos;
    private final FabricaHonorario fabrica;

    private final SalaDeEsperaPrivada sala = new SalaDeEsperaPrivada();
    private final Patio patio = Patio.getInstance();

    private final Map<String, Medico> medicos = new HashMap<>();
    private final Map<String, Paciente> pacientes = new HashMap<>();
    private final Map<Paciente, List<Medico>> enAtencion = new HashMap<>();
    private final Map<Paciente, Internacion> internaciones = new HashMap<>();
    private final List<Factura> facturas = new ArrayList<>();

    private long secuenciaFactura = 1; // para contar n de facturas

    public SistemaClinica(Clinica clinica, CatalogoCostos costos) {
        this.clinica = clinica;
        this.costos = costos;
        this.fabrica = new FabricaHonorario(costos);
    }

    // ---------------- Registro ----------------

    public void registraMedico(Medico m) { medicos.put(m.getMatricula(), m); }
    public void registraPaciente(Paciente p) { pacientes.put(p.getDni(), p); }

    // ---metodo auxiliar para centralizar la validación----

    private void verificarPacienteRegistrado(Paciente p) {
        if (!pacientes.containsKey(p.getDni())) {
            throw new PacienteNoRegistradoException(p.getDni());
        }
    }
    private void verificarMedicoRegistrado(Medico m) {
        if (!medicos.containsKey(m.getDni())) {
            throw new MedicoNoRegistradoException(m.getDni());
        }
    }

    // ---------------- Ingreso ----------------

    public void ingresaPaciente(Paciente p) {
        verificarPacienteRegistrado(p);
        sala.ingresar(p);
    }



    // ---------------- Atención ----------------

    public void atiendePaciente(Medico m, Paciente p) {
        verificarPacienteRegistrado(p);
        verificarMedicoRegistrado(m);
        enAtencion.computeIfAbsent(p, k -> new ArrayList<>()).add(m);
        sala.liberarSiEs(p);

        //si el paciente no tiene lista de medicos, crea una, y agrega el medico a la lista
    }

    // ---------------- Internación ----------------

    public void internaPaciente(Paciente p, Habitacion h, int dias) {
        verificarPacienteRegistrado(p);
        Internacion i = new Internacion(h, dias);
        internaciones.put(p, i);
    }

    // ---------------- Egreso y Facturación ----------------

    public Factura egresaPaciente(Paciente p) {
        verificarPacienteRegistrado(p);
        return egresaPaciente(p, 1);
    }

    public Factura egresaPaciente(Paciente p, int diasInternacion) {
        LocalDate fechaIngreso = LocalDate.now().minusDays(diasInternacion);
        LocalDate fechaEgreso = LocalDate.now();

        Factura f = new Factura(secuenciaFactura++, clinica, p, fechaIngreso, fechaEgreso);

        List<Medico> atendieron = enAtencion.getOrDefault(p, new ArrayList<>());
        //devuelve la lista de medicos que atendieron el paciente

        for (Medico m : atendieron) {
            double honorario = fabrica.crearPara(m).calcular();
            f.addItem(new Consulta(m, p, LocalDate.now(), honorario));
        }

        if (internaciones.containsKey(p)) {
            Internacion i = internaciones.remove(p);
            double costo = i.getHabitacion().calcularCosto(i.getDias(), costos);
            f.addItem(new ItemInternacion(i, costo));
        }
        facturas.add(f);
        enAtencion.remove(p);
        sala.liberarSiEs(p);
        return f;
    }

    // ---------------- Reportes ----------------

    public void imprimirFacturas() {
        for (Factura f : facturas) {
            f.imprimir();
        }
    }

    // ---------------------- Reportes ----------------------

    public void reporteActividad(Medico medico, LocalDate desde, LocalDate hasta) {
        ActividadMedica service = new ActividadMedica();
        ReporteActividad reporte = service.generar(medico, desde, hasta, facturas);
        service.imprimir(reporte, desde, hasta);
    }



}
