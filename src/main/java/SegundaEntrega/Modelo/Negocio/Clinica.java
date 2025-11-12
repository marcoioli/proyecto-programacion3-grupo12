
package SegundaEntrega.Modelo.Negocio;

import SegundaEntrega.Modelo.Datos.Configuraciones.CatalogoCostos;
import SegundaEntrega.Modelo.Excepciones.PacienteNoRegistradoException;
import SegundaEntrega.Modelo.Datos.Facturable.IFacturable;
import SegundaEntrega.Modelo.Datos.Habitaciones.*;
import SegundaEntrega.Modelo.Datos.Personas.Medico;
import SegundaEntrega.Modelo.Datos.Personas.Paciente;
// import SegundaEntrega.Modelo.Datos.Sala.*; // Si tienes clases de Sala

import SegundaEntrega.Modelo.Datos.Personas.Asociado;
import SegundaEntrega.Modelo.Datos.Personas.Operario;
import SegundaEntrega.Persistencia.DAOAsociado.AsociadoDAOImpl; // DAO Concreto
import SegundaEntrega.Persistencia.DAOAsociado.IAsociadoDAO;   // DAO Interfaz
import SegundaEntrega.Persistencia.PersistenciaExcepciones.DAOException;
import SegundaEntrega.Modelo.Excepciones.AsociadoDuplicadoException;
import SegundaEntrega.Persistencia.ConexionBD.ConexionSingleton;

// Imports Java Standard
import java.sql.SQLException;
import java.time.LocalDate; // Para Factura
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * Clase principal de la lógica de negocio (Facade y Singleton).
 * Centraliza el acceso a las funcionalidades de la clínica para ambas etapas.
 */
public class Clinica {

    // --- Singleton ---
    private static Clinica instancia;

    // --- Atributos básicos de la Clínica ---
    private final String nombreClinica = "Clínica Los Alamos"; // Datos ejemplo
    private final String direccionClinica = "Avenida Siempre Viva 742";
    private final String telefonoClinica = "223-555-0123";
    private final String ciudadClinica = "Mar del Plata";

    // --- Componentes Modelo Etapa I (DEBES AJUSTAR A TU ETAPA 1) ---
    private ArrayList<Paciente> pacientes;
    private ArrayList<Medico> medicos;
    private ArrayList<Habitacion> habitaciones;
    // private SalaDeEsperaPrivada salaDeEspera; // Si usas esto
    private CatalogoCostos catalogoCostos;

    // --- Componentes Modelo Etapa II ---
    private GestorAsociados gestorAsociados;
    private Ambulancia ambulancia;
    private Simulador simulador;
    private Operario operarioPredeterminado;
    private IAsociadoDAO asociadoDAO;

    /**
     * Constructor privado para Singleton. Inicializa componentes.
     */
    private Clinica() {
        // Inicialización Etapa I (Ajusta según tu código)
        this.pacientes = new ArrayList<>();
        this.medicos = new ArrayList<>();
        this.habitaciones = new ArrayList<>();
        this.catalogoCostos = new CatalogoCostos(); // Asume constructor vacío
        // ... inicializa otros componentes de Etapa I si los hay ...

        // Inicialización Etapa II
        try {
            // Es crucial que la conexión se establezca antes de usar el DAO
            ConexionSingleton.getInstance(); // Intenta conectar al instanciar Clinica
            this.asociadoDAO = new AsociadoDAOImpl();
        } catch (SQLException e) {
            System.err.println("ERROR FATAL: No se pudo establecer conexión con la BD o instanciar DAO: " + e.getMessage());
            throw new RuntimeException("Error inicializando la capa de persistencia", e);
        } catch (Exception e) { // Captura genérica por si DAOImpl lanza otra cosa
            System.err.println("ERROR FATAL: No se pudo instanciar el DAO de Asociados: " + e.getMessage());
            throw new RuntimeException("Error inicializando DAO", e);
        }

        IAsociadoDAO dao = new AsociadoDAOImpl();
        this.gestorAsociados = new GestorAsociados(dao);
        this.ambulancia = new Ambulancia();
        this.simulador = new Simulador(ambulancia, this.gestorAsociados); // Pasa el gestor al simulador

        this.operarioPredeterminado = new Operario("Operario", "Sistema", "00000000", "Clinica Central", "N/A", "Ciudad Central");
        this.simulador = new Simulador(this.ambulancia, this.gestorAsociados);

      //  cargarDatosAsociados();
    }

    /** Obtiene la instancia única (Singleton). */
    public static synchronized Clinica getInstance() {
        if (instancia == null) {
            instancia = new Clinica();
        }
        return instancia;
    }

    // --- Getters Datos Básicos Clínica ---
    public String getNombreClinica() { return nombreClinica; }
    public String getDireccionClinica() { return direccionClinica; }
    public String getTelefonoClinica() { return telefonoClinica; }
    public String getCiudadClinica() { return ciudadClinica; }

    // --- Getters Componentes Modelo (Etapa I y II) ---
    public List<Paciente> getPacientes() { return new ArrayList<>(pacientes); } // Devuelve copia
    public List<Medico> getMedicos() { return new ArrayList<>(medicos); }       // Devuelve copia
    public List<Habitacion> getHabitaciones() { return new ArrayList<>(habitaciones); } // Devuelve copia
    public CatalogoCostos getCatalogoCostos() { return catalogoCostos; }
    // ... otros getters Etapa I ...

    public GestorAsociados getGestorAsociados() { return gestorAsociados; }
    public Ambulancia getAmbulancia() { return ambulancia; }
    public Simulador getSimulador() { return simulador; }


    // --- Métodos Facade (Etapa I - ¡¡COPIA Y ADAPTA TUS MÉTODOS AQUÍ!!) ---
    // Asegúrate de que usen los atributos de esta clase (this.pacientes, etc.)
    // y que los imports sean correctos.

    /** Ejemplo: Registrar un paciente */
    public synchronized void registrarPaciente(Paciente p) {
        // Validaciones...
        if (p != null && !this.pacientes.contains(p)) { // Asume equals en Paciente
            this.pacientes.add(p);
            System.out.println("Clinica: Paciente registrado: " + p.getNombreCompleto());
        } else {
            System.out.println("Clinica: Intento de registrar paciente nulo o duplicado.");
        }
    }

    /** Ejemplo: Registrar un médico */
    public synchronized void registrarMedico(Medico m) {
        // Validaciones...
        if (m != null && !this.medicos.contains(m)) { // Asume equals en Medico
            this.medicos.add(m);
            System.out.println("Clinica: Médico registrado: " + m.getNombreCompleto());
        } else {
            System.out.println("Clinica: Intento de registrar médico nulo o duplicado.");
        }
    }

    /** Ejemplo: Generar Factura (adaptado) */
    public synchronized Factura generarFactura(String dniPaciente, LocalDate fecha) throws PacienteNoRegistradoException {
        // Busca al paciente en la lista en memoria
        Paciente paciente = this.pacientes.stream()
                .filter(p -> p.getDni().equals(dniPaciente))
                .findFirst()
                .orElseThrow(() -> new PacienteNoRegistradoException("Paciente con DNI " + dniPaciente + " no encontrado."));

        // Crea la factura (podrías necesitar lógica adicional para obtener items facturables)
        Factura factura = new Factura(paciente, null, fecha); // Asume médico nulo por simplicidad

        // Aquí iría la lógica para buscar consultas, internaciones, etc., del paciente y agregarlas
        // List<IFacturable> itemsParaFacturar = buscarItemsFacturables(paciente);
        // for (IFacturable item : itemsParaFacturar) {
        //     factura.agregarItem(item);
        // }

        factura.calcularTotal(); // Calcula el total basado en los items agregados
        System.out.println("Clinica: Factura generada para DNI " + dniPaciente + " por $" + factura.getImporteTotal());
        return factura;
    }

    // --- Métodos Facade (Etapa II - Delegar a GestorAsociados) ---

    /** Da de alta un asociado usando el Gestor y persistiendo */
    public synchronized void altaAsociado(Asociado a) throws AsociadoDuplicadoException, DAOException {
        // 1. Lógica de negocio (validación duplicados en memoria)
        gestorAsociados.altaAsociado(a); // Esto ya notifica observers si tiene éxito
        // 2. Persistencia
        try {
            asociadoDAO.guardar(a); // Guarda en BD
        } catch (DAOException e) {
            // Rollback en memoria si falla la persistencia? (O manejar de otra forma)
            System.err.println("ERROR BD: Falló el guardado del asociado " + a.getDni() + ". Revirtiendo en memoria.");
            try {
                gestorAsociados.bajaAsociado(a); // Intenta quitarlo de memoria
            } catch (AsociadoDuplicadoException ignored) {} // Ignorar si no se llegó a añadir
            throw e; // Relanzar la excepción del DAO
        }
    }

    /** Da de baja un asociado usando el Gestor y persistiendo */
    public synchronized void bajaAsociado(Asociado a) throws AsociadoDuplicadoException, DAOException {
        // 1. Lógica de negocio (validación existencia y eliminación en memoria)
        gestorAsociados.bajaAsociado(a); // Esto ya notifica observers si tiene éxito
        // 2. Persistencia
        try {
            asociadoDAO.eliminar(a); // Elimina de BD
        } catch (DAOException e) {
            // Rollback en memoria? (Re-agregar el asociado?)
            System.err.println("ERROR BD: Falló la eliminación del asociado " + a.getDni() + ". Asociado sigue en memoria.");
            // Podríamos intentar re-agregarlo a memoria, pero podría fallar si ya no está.
            // Considerar una estrategia de manejo de errores más robusta.
            throw e; // Relanzar la excepción del DAO
        }
    }

    /** Obtiene el listado actual de asociados desde el Gestor */
    public synchronized List<Asociado> getListadoAsociados() {
        return gestorAsociados.getListadoAsociados();
    }


    // --- Métodos Persistencia (Carga/Guardado general) ---
    private void cargarDatosAsociados() {
        System.out.println("Clínica: Cargando datos de asociados desde BD...");
        if (this.asociadoDAO == null) return;
        try {
            List<Asociado> asociadosDB = asociadoDAO.listarTodos();
            gestorAsociados.limpiarListaInterna(); // Limpiar memoria
            for (Asociado a : asociadosDB) {
                gestorAsociados.altaAsociadoSinNotificar(a); // Cargar sin notificar individualmente
            }
            gestorAsociados.notificarCargaCompleta(); // Notificar una vez
            System.out.println("Clínica: " + asociadosDB.size() + " asociados cargados.");
        } catch (DAOException e) {
            System.err.println("ERROR al cargar asociados desde BD: " + e.getMessage());
            e.printStackTrace(); // Podría mostrarse en GUI
        } catch (Exception e) { // Captura AsociadoDuplicadoException u otras
            System.err.println("ERROR inesperado durante carga de asociados: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public synchronized void guardarDatosAsociados() {
        System.out.println("Clínica: Guardando datos de asociados en BD...");
        if (this.asociadoDAO == null) return;
        List<Asociado> asociadosMemoria = gestorAsociados.getListadoAsociados();
        try {
            List<Asociado> asociadosDB = asociadoDAO.listarTodos();

            // Eliminar de DB los que no están en memoria
            for (Asociado enDB : asociadosDB) {
                if (!asociadosMemoria.contains(enDB)) {
                    try { asociadoDAO.eliminar(enDB); }
                    catch (DAOException eDel) { System.err.println("WARN: Error eliminando asociado " + enDB.getDni() + ": " + eDel.getMessage()); }
                }
            }
            // Actualizar o Insertar los de memoria en DB
            for (Asociado actual : asociadosMemoria) {
                try {
                    if (asociadosDB.contains(actual)) { // Si ya estaba en la lista original de DB
                        // Podríamos comparar campos para ver si realmente cambió antes de actualizar
                        asociadoDAO.actualizar(actual);
                    } else { // Si es nuevo (no estaba en la lista original de DB)
                        asociadoDAO.guardar(actual);
                    }
                } catch (DAOException eUpdIns) {
                    System.err.println("WARN: Error guardando/actualizando asociado " + actual.getDni() + ": " + eUpdIns.getMessage());
                }
            }
            System.out.println("Clínica: Datos de asociados guardados.");
        } catch (DAOException eList) {
            System.err.println("ERROR: No se pudo listar asociados de BD para guardar: " + eList.getMessage());
            eList.printStackTrace(); // Podría mostrarse en GUI
        }
    }

    /** Cierra la conexión a la BD. Llamar al salir. */
    public void cerrarConexionBD() {
        System.out.println("Clínica: Solicitando cierre de conexión BD...");
        try {
            ConexionSingleton.getInstance().closeConnection();
        } catch (SQLException e) {
            System.err.println("WARN: Problema al obtener/cerrar conexión BD: " + e.getMessage());
        }
    }

}