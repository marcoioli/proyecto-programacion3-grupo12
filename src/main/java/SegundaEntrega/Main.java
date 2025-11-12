// Paquete donde se ubica la clase Main
package SegundaEntrega; // Puedes cambiarlo a SegundaEntrega.Main si prefieres

// Imports del Modelo, Vista y Controlador
import SegundaEntrega.Modelo.Negocio.Clinica;
import SegundaEntrega.Vista.JFramePrincipal.VentanaPrincipal;
import SegundaEntrega.Controlador.ControladorPrincipal.ControladorPrincipal;

// Imports de Swing y AWT para la GUI y eventos
import javax.swing.*;
import javax.swing.SwingUtilities; // Para iniciar la GUI en el hilo correcto
import javax.swing.JOptionPane; // Para mostrar errores fatales
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Clase principal que inicia la aplicación del Sistema de Gestión de Clínica (Etapa II).
 * Configura el Modelo (Clinica Singleton), la Vista (VentanaPrincipal)
 * y el Controlador (ControladorPrincipal) siguiendo el patrón MVC.
 */
public class Main {

    /**
     * Punto de entrada principal de la aplicación.
     * @param args Argumentos de línea de comandos (no se utilizan).
     */
    public static void main(String[] args) {

        // Se recomienda iniciar todas las aplicaciones Swing en el
        // Hilo de Despacho de Eventos (Event Dispatch Thread - EDT)
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    // 1. Inicializar el Modelo (Singleton)
                    // Al llamar a getInstance() por primera vez, se crea la instancia:
                    // - Se conecta a la BD (vía ConexionSingleton)
                    // - Se instancia el DAO
                    // - Se cargan los asociados (vía cargarDatosAsociados())
                    System.out.println("Main: Obteniendo instancia de Clínica (Modelo)...");
                    Clinica modelo = Clinica.getInstance();
                    System.out.println("Main: Modelo inicializado.");

                    // 2. Inicializar la Vista principal
                    System.out.println("Main: Creando VentanaPrincipal (Vista)...");
                    VentanaPrincipal vista = new VentanaPrincipal();

                    // Asignar el nombre de la clínica al título de la ventana
                    // (Usando el getter que fusionamos)
                    vista.setTitle(modelo.getNombreClinica() + " - Gestión Principal");

                    // 3. Inicializar el Controlador principal
                    System.out.println("Main: Creando ControladorPrincipal...");
                    // El controlador recibe la vista y el modelo para conectarlos
                    ControladorPrincipal controlador = new ControladorPrincipal(vista, modelo);
                    System.out.println("Main: Controlador listo.");


                    // 4. Añadir "Shutdown Hook" (manejador de cierre) a la ventana
                    // Esto asegura que guardemos los datos y cerremos la conexión
                    // ANTES de que la aplicación termine.
                    vista.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosing(WindowEvent e) {
                            System.out.println("Main: Detectado cierre de ventana...");

                            // Pedir confirmación (opcional pero recomendado)
                            int confirm = JOptionPane.showConfirmDialog(
                                    vista,
                                    "¿Está seguro de que desea salir?\nSe guardarán los datos de asociados.",
                                    "Confirmar Salida",
                                    JOptionPane.YES_NO_OPTION,
                                    JOptionPane.QUESTION_MESSAGE
                            );

                            if (confirm == JOptionPane.YES_OPTION) {
                                // 1. Guardar datos (podría mostrar error si falla)
                                System.out.println("Main: Guardando datos de asociados...");
                                try {
                                    modelo.guardarDatosAsociados();
                                } catch (Exception ex) {
                                    System.err.println("ERROR al guardar asociados al cerrar: " + ex.getMessage());
                                    ex.printStackTrace();
                                    // Opcional: preguntar al usuario si quiere salir igualmente
                                }

                                // 2. Cerrar conexión BD
                                System.out.println("Main: Cerrando conexión a la BD...");
                                try {
                                    modelo.cerrarConexionBD();
                                } catch (Exception ex) {
                                    System.err.println("ERROR al cerrar conexión BD: " + ex.getMessage());
                                    ex.printStackTrace();
                                }

                                System.out.println("Main: Proceso de cierre completado. Saliendo.");

                                // Permite que la ventana se cierre (ya que el default es EXIT_ON_CLOSE)
                                vista.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                            } else {
                                // Si el usuario dice NO, evitamos que la ventana se cierre
                                vista.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                            }
                        }
                    });

                    // 5. Iniciar la aplicación (hacer visible la ventana)
                    // Delegamos esto al controlador para que tenga el control final
                    System.out.println("Main: Iniciando aplicación...");
                    controlador.iniciar();
                    System.out.println("Main: Aplicación iniciada y visible.");

                } catch (Exception e) {
                    // Capturar cualquier error fatal durante la inicialización (ej. Falla de BD)
                    System.err.println("ERROR FATAL AL INICIAR LA APLICACIÓN:");
                    e.printStackTrace();

                    // Mostrar un mensaje de error gráfico al usuario antes de salir
                    JOptionPane.showMessageDialog(null,
                            "No se pudo iniciar la aplicación.\nError: " + e.getMessage() + "\nLa aplicación se cerrará. Consulte la consola para más detalles.",
                            "Error Crítico de Inicialización",
                            JOptionPane.ERROR_MESSAGE);

                    System.exit(1); // Salir con código de error
                }
            }
        });
    }
}