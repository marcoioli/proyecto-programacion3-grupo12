package SegundaEntrega.Persistencia.ConexionBD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Gestiona la conexión a la base de datos utilizando el patrón Singleton
 * para asegurar una única instancia y punto de acceso a la conexión.
 */
public class ConexionSingleton {

    private static ConexionSingleton instancia; // La única instancia
    private Connection connection; // La conexión JDBC

    // **¡IMPORTANTE! Configura estos valores según tu base de datos**
    private static final String DB_URL = "jdbc:mysql://localhost:3306/clinica_db"; // Ejemplo para MySQL
    private static final String DB_USER = "tu_usuario_db";
    private static final String DB_PASSWORD = "tu_contraseña_db";
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver"; // Driver MySQL (Asegúrate que esté en el pom.xml)


    /**
     * Constructor privado para prevenir instanciación externa.
     * Intenta establecer la conexión al crear la instancia.
     * @throws SQLException Si ocurre un error al conectar con la base de datos.
     */
    private ConexionSingleton() throws SQLException {
        try {
            // Cargar el driver (necesario en versiones antiguas de JDBC, buena práctica incluirlo)
            Class.forName(DB_DRIVER);
            // Establecer la conexión
            this.connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Conexión a la base de datos establecida."); // Log
        } catch (ClassNotFoundException e) {
            System.err.println("Error: Driver JDBC no encontrado.");
            throw new SQLException("Driver no encontrado", e);
        } catch (SQLException e) {
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
            throw e; // Relanzar para que el getInstance() maneje el fallo
        }
    }

    /**
     * Método estático para obtener la única instancia de la clase (Singleton).
     * Utiliza inicialización perezosa y es synchronized para seguridad en hilos.
     * @return La instancia única de ConexionSingleton.
     * @throws SQLException Si no se puede establecer la conexión la primera vez.
     */
    public static synchronized ConexionSingleton getInstance() throws SQLException {
        if (instancia == null) {
            instancia = new ConexionSingleton();
        }
        // Podríamos añadir una verificación aquí si la conexión se cerró por alguna razón
        // else if (instancia.getConnection().isClosed()) {
        //     instancia = new ConexionSingleton(); // Reintentar conexión
        // }
        return instancia;
    }

    /**
     * Devuelve la conexión JDBC activa.
     * @return La objeto Connection.
     */
    public Connection getConnection() {
        // Podríamos añadir verificación de si está cerrada y reabrirla si es necesario
        return connection;
    }

    /**
     * Cierra la conexión a la base de datos.
     * Debería llamarse al finalizar la aplicación.
     */
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Conexión a la base de datos cerrada."); // Log
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
        } finally {
            instancia = null; // Permitir recrear la instancia si se vuelve a pedir
        }
    }
}