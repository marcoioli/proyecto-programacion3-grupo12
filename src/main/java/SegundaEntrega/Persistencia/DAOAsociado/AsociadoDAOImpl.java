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

    public void guardar(Asociado asociado) throws DAOException {
        String sql = "INSERT INTO asociados (dni, nombre, apellido, domicilio, telefono, ciudad) VALUES (?, ?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);

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
            System.err.println("Error SQL al guardar asociado: " + e.getMessage());
            throw new DAOException("Error al guardar asociado con DNI: " + asociado.getDni(), e);
        } finally {
            try {
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar PreparedStatement: " + e.getMessage());
            }
        }
    }
    public void eliminar(Asociado asociado) throws DAOException {
        String sql = "DELETE FROM asociados WHERE dni = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, asociado.getDni());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("No se encontró asociado para eliminar con DNI: " + asociado.getDni());
            }
            System.out.println("DAO: Asociado eliminado: " + asociado.getDni()); // Log

        } catch (SQLException e) {
            System.err.println("Error SQL al eliminar asociado: " + e.getMessage());
            throw new DAOException("Error al eliminar asociado con DNI: " + asociado.getDni(), e);
        } finally {
            try {
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar PreparedStatement: " + e.getMessage());
            }
        }
    }

    public Optional<Asociado> buscarPorDNI(String dni) throws DAOException {
        String sql = "SELECT dni, nombre, apellido, domicilio, telefono, ciudad FROM asociados WHERE dni = ?";
        Asociado asociado = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, dni);
            rs = pstmt.executeQuery();

            if (rs.next()) {
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
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos: " + e.getMessage());
            }

        }
        return Optional.ofNullable(asociado);
    }

    @Override
// En AsociadoDAOImpl.java
    public List<Asociado> listarTodos() throws DAOException {
        String sql = "SELECT dni, nombre, apellido, domicilio, telefono, ciudad FROM asociados ORDER BY apellido, nombre";
        List<Asociado> lista = new ArrayList<>();

        Connection conn = null; // Declarar afuera
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();

            try (Statement stmtTmp = conn.createStatement();
                 ResultSet rsTmp = stmtTmp.executeQuery(sql)) {

                stmt = stmtTmp; // Asignar para logging
                rs = rsTmp;     // Asignar para logging

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
            }

        } catch (SQLException e) {
            System.err.println("Error SQL al listar asociados: " + e.getMessage());
            throw new DAOException("Error al listar todos los asociados", e);
        }

        return lista;
    }

    @Override
    public void actualizar(Asociado asociado) throws DAOException {
        String sql = "UPDATE asociados SET nombre = ?, apellido = ?, domicilio = ?, telefono = ?, ciudad = ? WHERE dni = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);

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
        } finally {
            try {
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar PreparedStatement: " + e.getMessage());
            }
        }
    }

    /**
     * Inicializa la base de datos para la gestión de asociados.
     * 1. Elimina la tabla 'asociados' si existe (DROP TABLE)[cite: 100, 349].
     * 2. Crea la tabla 'asociados' (CREATE TABLE).
     * 3. Inserta datos iniciales de ejemplo (INSERT).
     *
     * @throws DAOException Si ocurre un error de SQL durante la inicialización.
     */
    @Override
    public void inicializarTablaAsociados() throws DAOException {
        // Sentencias SQL
        String sqlDrop = "DROP TABLE IF EXISTS asociados";

        String sqlCreate = "CREATE TABLE asociados ("
                + " dni VARCHAR(10) PRIMARY KEY,"
                + " nombre VARCHAR(50) NOT NULL,"
                + " apellido VARCHAR(50) NOT NULL,"
                + " domicilio VARCHAR(100),"
                + " telefono VARCHAR(20),"
                + " ciudad VARCHAR(50)"
                + ")";

        String sqlInsert1 = "INSERT INTO asociados (dni, nombre, apellido, domicilio, telefono, ciudad) VALUES "
                + "('12345678', 'Juan', 'Perez', 'Calle Falsa 123', '2235001122', 'Mar del Plata')";
        String sqlInsert2 = "INSERT INTO asociados (dni, nombre, apellido, domicilio, telefono, ciudad) VALUES "
                + "('87654321', 'Maria', 'Gomez', 'Av. Siempre Viva 742', '2235112233', 'Springfield')";


        Connection conn = null;
        try {
            conn = getConnection(); // obtenemos la conexion


            try (Statement stmt = conn.createStatement()) {

                System.out.println("DAO: Ejecutando DROP TABLE...");
                stmt.executeUpdate(sqlDrop); // 1. Eliminar

                System.out.println("DAO: Ejecutando CREATE TABLE...");
                stmt.executeUpdate(sqlCreate); // 2. Crear

                System.out.println("DAO: Ejecutando INSERTs iniciales...");
                stmt.executeUpdate(sqlInsert1); // 3. Insertar datos
                stmt.executeUpdate(sqlInsert2);

                System.out.println("DAO: Inicialización de tabla 'asociados' completada.");
            }

        } catch (SQLException e) {
            System.err.println("Error SQL al inicializar la base de datos: " + e.getMessage());
            throw new DAOException("Error al inicializar la tabla 'asociados'", e);
        }

    }
}