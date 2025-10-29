package SegundaEntrega.Persistencia.DAOAsociado;

import SegundaEntrega.Modelo.Datos.Personas.Asociado;
import SegundaEntrega.Persistencia.ConexionBD.ConexionSingleton;
import SegundaEntrega.Persistencia.PersistenciaExcepciones.DAOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementación concreta de IAsociadoDAO utilizando JDBC.
 */
public class AsociadoDAOImpl implements IAsociadoDAO {

    private Connection getConnection() throws SQLException {
        // Obtiene la conexión del Singleton
        return ConexionSingleton.getInstance().getConnection();
    }

    @Override
    public void guardar(Asociado asociado) throws DAOException {
        // Sentencia SQL parametrizada para evitar inyección SQL
        String sql = "INSERT INTO asociados (dni, nombre, apellido, domicilio, telefono, ciudad) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, asociado.getDni());
            pstmt.setString(2, asociado.getNombre());
            pstmt.setString(3, asociado.getApellido());
            pstmt.setString(4, asociado.getDomicilio());
            pstmt.setString(5, asociado.getTelefono());
            pstmt.setString(6, asociado.getCiudad());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Error al guardar asociado, ninguna fila afectada.");
            }
            System.out.println("DAO: Asociado guardado: " + asociado.getDni()); // Log

        } catch (SQLException e) {
            // Podríamos verificar códigos de error específicos (ej: clave duplicada)
            System.err.println("Error SQL al guardar asociado: " + e.getMessage());
            throw new DAOException("Error al guardar asociado con DNI: " + asociado.getDni(), e);
        }
    }

    @Override
    public void eliminar(Asociado asociado) throws DAOException {
        String sql = "DELETE FROM asociados WHERE dni = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, asociado.getDni());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                // Podría ser normal si intentas borrar algo que ya no está,
                // pero lanzamos excepción según el contrato de GestorAsociados
                throw new DAOException("No se encontró asociado para eliminar con DNI: " + asociado.getDni());
            }
            System.out.println("DAO: Asociado eliminado: " + asociado.getDni()); // Log

        } catch (SQLException e) {
            System.err.println("Error SQL al eliminar asociado: " + e.getMessage());
            throw new DAOException("Error al eliminar asociado con DNI: " + asociado.getDni(), e);
        }
    }

    @Override
    public Optional<Asociado> buscarPorDNI(String dni) throws DAOException {
        String sql = "SELECT dni, nombre, apellido, domicilio, telefono, ciudad FROM asociados WHERE dni = ?";
        Asociado asociado = null;

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, dni);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                // Mapear el ResultSet a un objeto Asociado
                asociado = new Asociado();
                asociado.setDni(rs.getString("dni"));
                asociado.setNombre(rs.getString("nombre"));
                asociado.setApellido(rs.getString("apellido"));
                asociado.setDomicilio(rs.getString("domicilio"));
                asociado.setTelefono(rs.getString("telefono"));
                asociado.setCiudad(rs.getString("ciudad"));
                System.out.println("DAO: Asociado encontrado: " + dni); // Log
            } else {
                System.out.println("DAO: Asociado NO encontrado: " + dni); // Log
            }

        } catch (SQLException e) {
            System.err.println("Error SQL al buscar asociado: " + e.getMessage());
            throw new DAOException("Error al buscar asociado con DNI: " + dni, e);
        }
        // Devolver un Optional para manejar elegantemente el caso de no encontrarlo
        return Optional.ofNullable(asociado);
    }

    @Override
    public List<Asociado> listarTodos() throws DAOException {
        String sql = "SELECT dni, nombre, apellido, domicilio, telefono, ciudad FROM asociados ORDER BY apellido, nombre";
        List<Asociado> lista = new ArrayList<>();

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Asociado asociado = new Asociado();
                asociado.setDni(rs.getString("dni"));
                asociado.setNombre(rs.getString("nombre"));
                asociado.setApellido(rs.getString("apellido"));
                asociado.setDomicilio(rs.getString("domicilio"));
                asociado.setTelefono(rs.getString("telefono"));
                asociado.setCiudad(rs.getString("ciudad"));
                lista.add(asociado);
            }
            System.out.println("DAO: Listados " + lista.size() + " asociados."); // Log

        } catch (SQLException e) {
            System.err.println("Error SQL al listar asociados: " + e.getMessage());
            throw new DAOException("Error al listar todos los asociados", e);
        }
        return lista;
    }

    @Override
    public void actualizar(Asociado asociado) throws DAOException {
        String sql = "UPDATE asociados SET nombre = ?, apellido = ?, domicilio = ?, telefono = ?, ciudad = ? WHERE dni = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, asociado.getNombre());
            pstmt.setString(2, asociado.getApellido());
            pstmt.setString(3, asociado.getDomicilio());
            pstmt.setString(4, asociado.getTelefono());
            pstmt.setString(5, asociado.getCiudad());
            pstmt.setString(6, asociado.getDni()); // Cláusula WHERE

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("No se encontró asociado para actualizar con DNI: " + asociado.getDni());
            }
            System.out.println("DAO: Asociado actualizado: " + asociado.getDni()); // Log

        } catch (SQLException e) {
            System.err.println("Error SQL al actualizar asociado: " + e.getMessage());
            throw new DAOException("Error al actualizar asociado con DNI: " + asociado.getDni(), e);
        }
    }
}