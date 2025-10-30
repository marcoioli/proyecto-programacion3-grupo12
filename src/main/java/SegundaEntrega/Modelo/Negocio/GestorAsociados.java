package SegundaEntrega.Modelo.Negocio;

import SegundaEntrega.Modelo.Datos.Personas.Asociado;
import SegundaEntrega.Modelo.Excepciones.AsociadoDuplicadoException;


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

    public GestorAsociados() {
        this.asociados = new ArrayList<>();
        // La carga desde persistencia se hará desde Clinica después de instanciar
    }

    /**
     * Da de alta un nuevo asociado. Verifica duplicados por DNI.
     * Notifica a los observadores si el alta es exitosa.
     * @param nuevoAsociado El asociado a agregar.
     * @throws AsociadoDuplicadoException Si ya existe un asociado con el mismo DNI.
     * @throws IllegalArgumentException Si el asociado es nulo o inválido.
     */
    public synchronized void altaAsociado(Asociado nuevoAsociado) throws AsociadoDuplicadoException {
        if (nuevoAsociado == null || nuevoAsociado.getDni() == null || nuevoAsociado.getDni().trim().isEmpty()) {
            throw new IllegalArgumentException("El asociado o su DNI no pueden ser nulos o vacíos.");
        }
        if (this.asociados.contains(nuevoAsociado)) {
            throw new AsociadoDuplicadoException("Ya existe un asociado con el DNI: " + nuevoAsociado.getDni());
        }

        this.asociados.add(nuevoAsociado);
        System.out.println("GestorAsociados: Asociado agregado: " + nuevoAsociado.getNombreCompleto());

        // Notificar a los observadores (la Vista)
        setChanged(); // Marca que hubo un cambio
        notifyObservers(getListadoAsociados()); // Envía una copia de la lista actualizada

        // TODO: Persistir el nuevo asociado (DAO) - La llamada al DAO debería hacerse desde Clinica o un servicio superior
    }

    /**
     * **NUEVO MÉTODO:** Agrega un asociado a la lista interna SIN notificar a los observadores.
     * Útil para la carga inicial desde la base de datos para evitar notificaciones masivas.
     * Asegúrate de llamar a notificarCargaCompleta() después de usar este método repetidamente.
     * @param asociado El asociado a agregar.
     * @throws AsociadoDuplicadoException Si ya existe (opcional, podrías omitir la verificación aquí si confías en la carga inicial).
     * @throws IllegalArgumentException Si es inválido.
     */
    public synchronized void altaAsociadoSinNotificar(Asociado asociado) throws AsociadoDuplicadoException {
        if (asociado == null || asociado.getDni() == null || asociado.getDni().trim().isEmpty()) {
            throw new IllegalArgumentException("El asociado o su DNI no pueden ser nulos o vacíos (carga inicial).");
        }
        // Opcional: Verificar duplicados también en la carga inicial
        if (this.asociados.contains(asociado)) {
            System.err.println("WARN (GestorAsociados): Intentando agregar asociado duplicado durante carga: " + asociado.getDni());
            // Lanzar excepción o simplemente ignorar el duplicado
            // throw new AsociadoDuplicadoException("Duplicado durante carga: " + asociado.getDni());
            return; // Ignorar duplicado en este caso
        }
        this.asociados.add(asociado);
        // ¡NO llama a setChanged() ni notifyObservers()!
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
     * Notifica a los observadores si la baja es exitosa.
     * @param asociadoABajar El asociado a eliminar (se busca por DNI usando equals).
     * @throws AsociadoDuplicadoException Si el asociado no se encuentra en la lista.
     * @throws IllegalArgumentException Si el asociado es nulo o inválido.
     */
    public synchronized void bajaAsociado(Asociado asociadoABajar) throws AsociadoDuplicadoException {
        if (asociadoABajar == null || asociadoABajar.getDni() == null || asociadoABajar.getDni().trim().isEmpty()) {
            throw new IllegalArgumentException("El asociado a bajar o su DNI no pueden ser nulos o vacíos.");
        }
        boolean eliminado = this.asociados.remove(asociadoABajar);
        if (!eliminado) {
            throw new AsociadoDuplicadoException("No se encontró al asociado con DNI: " + asociadoABajar.getDni());
        }
        System.out.println("GestorAsociados: Asociado eliminado: " + asociadoABajar.getNombreCompleto());

        setChanged();
        notifyObservers(getListadoAsociados());

        // TODO: Eliminar asociado de la persistencia (DAO) - Llamada desde Clinica o servicio
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
     * **NUEVO (Opcional):** Limpia la lista interna de asociados.
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