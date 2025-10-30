package SegundaEntrega.Persistencia.DAOAsociado;

import SegundaEntrega.Modelo.Datos.Personas.Asociado; // Importar Asociado
import SegundaEntrega.Persistencia.PersistenciaExcepciones.DAOException; // Importar Excepción DAO

import java.util.List;
import java.util.Optional; // Para búsquedas que pueden no encontrar nada

/**
 * Interfaz que define las operaciones de persistencia (CRUD) para la entidad Asociado.
 * Patrón Data Access Object (DAO).
 */
public interface IAsociadoDAO {

    /**
     * Guarda un nuevo asociado en la fuente de datos.
     * Si el asociado ya existe (basado en DNI), podría actualizarlo o lanzar excepción.
     * @param asociado El objeto Asociado a persistir.
     * @throws DAOException Si ocurre un error durante la operación de guardado.
     */
    void guardar(Asociado asociado) throws DAOException;

    /**
     * Elimina un asociado de la fuente de datos, usualmente identificado por su DNI.
     * @param asociado El objeto Asociado a eliminar (se usará su DNI).
     * @throws DAOException Si ocurre un error durante la operación de eliminación o si no se encuentra.
     */
    void eliminar(Asociado asociado) throws DAOException;

    /**
     * Busca un asociado por su número de DNI.
     * @param dni El DNI del asociado a buscar.
     * @return Un Optional<Asociado> que contiene el asociado si se encontró,
     * o un Optional vacío si no se encontró.
     * @throws DAOException Si ocurre un error durante la búsqueda.
     */
    Optional<Asociado> buscarPorDNI(String dni) throws DAOException;

    /**
     * Recupera una lista con todos los asociados almacenados.
     * @return Una lista de objetos Asociado. Puede estar vacía si no hay ninguno.
     * @throws DAOException Si ocurre un error al recuperar la lista.
     */
    List<Asociado> listarTodos() throws DAOException;

    /**
     * Actualiza los datos de un asociado existente en la fuente de datos.
     * La identificación del asociado a actualizar suele hacerse por DNI.
     * @param asociado El objeto Asociado con los datos actualizados.
     * @throws DAOException Si ocurre un error durante la actualización o si no se encuentra.
     */
    void actualizar(Asociado asociado) throws DAOException;

}