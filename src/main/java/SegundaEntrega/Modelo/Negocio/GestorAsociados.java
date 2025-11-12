package SegundaEntrega.Modelo.Negocio;

import SegundaEntrega.Modelo.Datos.Personas.Asociado;
import SegundaEntrega.Modelo.Excepciones.AsociadoDuplicadoException;
import SegundaEntrega.Persistencia.DAOAsociado.IAsociadoDAO;
import SegundaEntrega.Persistencia.PersistenciaExcepciones.DAOException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Optional;

/**
 * Gestiona el alta, baja y listado de asociados.
 * Notifica a los observadores cuando la lista de asociados cambia.
 */
public class GestorAsociados extends Observable {

    // **¡Importante! Acceso a la lista interna**
    // Para que Clinica pueda cargar datos directamente o para el método
    // altaAsociadoSinNotificar, necesitamos una forma de añadir sin notificar.
    // Opción 1: Hacer la lista 'protected' (menos encapsulado).
    // Opción 2: Crear el método altaAsociadoSinNotificar (preferible).
    private List<Asociado> asociados;
    private IAsociadoDAO dao;

    /**
     * Constructor.
     * @param dao La implementación del DAO que se usará para persistencia.
     */
    public GestorAsociados(IAsociadoDAO dao) { // <-- MODIFICAR CONSTRUCTOR
        this.asociados = new ArrayList<>();
        this.dao = dao; // Guardar la referencia al DAO
    }

    /**
     * Da de alta un nuevo asociado. Verifica duplicados por DNI.
     * Notifica a los observadores si el alta es exitosa.
     * @param nuevoAsociado El asociado a agregar.
     * @throws AsociadoDuplicadoException Si ya existe un asociado con el mismo DNI.
     * @throws IllegalArgumentException Si el asociado es nulo o inválido.
     */
    public synchronized void altaAsociado(Asociado nuevoAsociado) throws AsociadoDuplicadoException,DAOException {
        if (nuevoAsociado == null || nuevoAsociado.getDni() == null || nuevoAsociado.getDni().trim().isEmpty()) {
            throw new IllegalArgumentException("El asociado o su DNI no pueden ser nulos o vacíos.");
        }
        if (this.asociados.contains(nuevoAsociado)) {
            throw new AsociadoDuplicadoException("Ya existe un asociado con el DNI: " + nuevoAsociado.getDni());
        }
        try {
            dao.guardar(nuevoAsociado); // guardar en  bd
        } catch (DAOException e) {
            System.err.println("Error de DAO al guardar asociado: " + e.getMessage());
            throw new DAOException("Error al persistir el nuevo asociado.", e);
        }

        this.asociados.add(nuevoAsociado);
        setChanged();
        notifyObservers(getListadoAsociados()); //notifico a la vista
    }

    /**
     * Da de baja un asociado, elimina de la base de datos y actualiza memoria
     * @param asociado El asociado a agregar.
     * @throws AsociadoDuplicadoException Si ya existe (opcional, podrías omitir la verificación aquí si confías en la carga inicial).
     * @throws IllegalArgumentException Si es inválido.
     */

    public synchronized void altaAsociadoSinNotificar(Asociado asociado) throws AsociadoDuplicadoException {
        if (asociado == null || asociado.getDni() == null || asociado.getDni().trim().isEmpty()) {
            throw new IllegalArgumentException("El asociado o su DNI no pueden ser nulos o vacíos (carga inicial).");
        }
        if (this.asociados.contains(asociado)) {
            System.err.println("WARN (GestorAsociados): Intentando agregar asociado duplicado durante carga: " + asociado.getDni());
            return; //ignoro un duplicado
        }
        this.asociados.add(asociado)    ;
    }

    /**
     * **NUEVO MÉTODO:** Notifica a los observadores que la carga (o una modificación masiva) ha terminado.
     * Debe llamarse manualmente después de usar `altaAsociadoSinNotificar` repetidamente.
     */
    public synchronized void notificarCargaCompleta() {
        System.out.println("GestorAsociados: Notificando carga completa a observadores.");
        setChanged();
        notifyObservers(getListadoAsociados()); // Envía la lista final
    }

    /**
     * Da de baja un asociado.
     * Elimina de la BD y luego actualiza la memoria.
     * @param asociadoABajar el asociado que se va a eliminar
     * @throws DAOException Si ocurre un error al eliminar de la base de datos.
     */
    public synchronized void bajaAsociado(Asociado asociadoABajar) throws AsociadoDuplicadoException, DAOException {
        if (asociadoABajar == null || asociadoABajar.getDni() == null || asociadoABajar.getDni().trim().isEmpty()) {
            throw new IllegalArgumentException("El asociado a bajar o su DNI no pueden ser nulos o vacíos.");
        }
        try {
            dao.eliminar(asociadoABajar); //elimino de la bd
        } catch (DAOException e) {
            System.err.println("Error de DAO al eliminar asociado: " + e.getMessage());
            throw new DAOException("Error al eliminar el asociado de la BD.", e);
        }
        boolean eliminado = this.asociados.remove(asociadoABajar);
        if (!eliminado) {
            System.err.println("WARN: Se eliminó de BD pero no se encontró en memoria: " + asociadoABajar.getDni());
        }
        setChanged();
        notifyObservers(getListadoAsociados()); //notifico
    }

    /**
     * Delega la inicialización de las tablas al DAO.
     * Después de inicializar, recarga la lista (que ahora tendrá los datos de ejemplo).
     * @throws DAOException Si ocurre un error durante el DROP/CREATE/INSERT.
     */
    public synchronized void inicializarTablas() throws DAOException {
        System.out.println("GestorAsociados: Solicitando inicialización de tablas al DAO...");
        dao.inicializarTablaAsociados();
        System.out.println("GestorAsociados: Tablas inicializadas. Recargando datos...");
        cargarAsociadosDesdeBD();
    }

    /**
     * Devuelve una copia de la lista de todos los asociados.
     * @return Una nueva lista conteniendo todos los asociados.
     */
    public synchronized List<Asociado> getListadoAsociados() {
        return new ArrayList<>(this.asociados);
    }

    /**
     * Busca un asociado por su DNI.
     * @param dni El DNI a buscar.
     * @return Un Optional conteniendo el Asociado si se encuentra, o un Optional vacío si no.
     */
    public synchronized Optional<Asociado> buscarAsociadoPorDNI(String dni) {
        if (dni == null || dni.trim().isEmpty()) {
            return Optional.empty();
        }
        return this.asociados.stream()
                .filter(a -> dni.equals(a.getDni()))
                .findFirst();
    }

    /**
     * Carga todos los asociados desde la base de datos (vía DAO)
     * a la lista en memoria.
     * @throws DAOException Si ocurre un error al leer la base de datos.
     */
    public synchronized void cargarAsociadosDesdeBD() throws DAOException {
        System.out.println("GestorAsociados: Cargando asociados desde la BD...");
        try {
            List<Asociado> asociadosBD = dao.listarTodos();
            this.limpiarListaInterna();
            for (Asociado a : asociadosBD) {
                altaAsociadoSinNotificar(a);
            }
            System.out.println("GestorAsociados: Carga completa. " + asociadosBD.size() + " asociados cargados.");
            notificarCargaCompleta(); //se notifica una sola vez

        } catch (DAOException e) {
            System.err.println("Error crítico al cargar asociados desde la BD: " + e.getMessage());
            throw e;
        } catch (AsociadoDuplicadoException e) {
            System.err.println("Error de lógica: duplicado encontrado durante carga: " + e.getMessage());
        }
    }

    /**
     * Limpia la lista interna de asociados.
     * Útil antes de una recarga completa desde la BD.
     */
    public synchronized void limpiarListaInterna() {
        this.asociados.clear();
        System.out.println("GestorAsociados: Lista interna limpiada.");
        // Considera si notificar observers aquí o esperar a la recarga
        // setChanged();
        // notifyObservers(getListadoAsociados()); // Notifica lista vacía
    }



}