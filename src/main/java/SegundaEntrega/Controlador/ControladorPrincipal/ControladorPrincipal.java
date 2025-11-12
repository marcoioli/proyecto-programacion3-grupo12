package SegundaEntrega.Controlador.ControladorPrincipal;

import SegundaEntrega.Modelo.Negocio.Clinica; // Acceso al Modelo (Singleton/Facade)
import SegundaEntrega.Vista.JFramePrincipal.VentanaPrincipal;
// Imports para las otras ventanas y controladores que necesitará crear/mostrar
import SegundaEntrega.Vista.JFrameAsociados.VentanaAsociados;
import SegundaEntrega.Controlador.ControladorAsociados.ControladorAsociados;
import SegundaEntrega.Vista.JFrameSimulacion.VentanaSimulacion;
import SegundaEntrega.Controlador.ControladorSimulacion.ControladorSimulacion;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import SegundaEntrega.Persistencia.PersistenciaExcepciones.DAOException;

/**
 * Controlador para la VentanaPrincipal. Maneja las acciones del menú.
 */
public class ControladorPrincipal implements ActionListener {

    private VentanaPrincipal ventanaPrincipal;
    private Clinica clinica; // Referencia al modelo principal (Facade/Singleton)

    /**
     * Constructor.
     * @param ventanaPrincipal La vista que controla.
     * @param clinica El modelo principal.
     */
    public ControladorPrincipal(VentanaPrincipal ventanaPrincipal, Clinica clinica) {
        this.ventanaPrincipal = ventanaPrincipal;
        this.clinica = clinica; // Asume que Clinica ya tiene GestorAsociados, Ambulancia, etc.

        // Vincula este controlador con la vista
        this.ventanaPrincipal.setControlador(this);
    }

    /**
     * Maneja los eventos de acción (clics en items de menú).
     * @param e El evento de acción.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
            case "SALIR":
                ventanaPrincipal.cerrarVentana();
                // Considerar guardar datos antes de salir si es necesario
                // clinica.guardarDatos();
                break;
            case "GESTION_ASOCIADOS":
                abrirVentanaAsociados();
                break;
            case "SIMULACION":
                abrirVentanaSimulacion();
                break;
            case "INICIALIZAR_BD":
                int respuesta = JOptionPane.showConfirmDialog(
                        ventanaPrincipal,
                        "¿Seguro que deseas inicializar la base de datos?\n" +
                                "Se borrarán todos los datos existentes.",
                        "Confirmar Inicialización",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );
                if (respuesta == JOptionPane.YES_OPTION) {
                    inicializarBaseDeDatos();
                }
                break;
            default:
                System.err.println("Comando no reconocido en ControladorPrincipal: " + command);
                break;
        }
    }

    /**
     * Crea y muestra la ventana de gestión de asociados con su controlador.
     */
    private void abrirVentanaAsociados() {
        VentanaAsociados vAsociados = new VentanaAsociados();
        // Pasar la vista y la parte relevante del modelo (GestorAsociados) al controlador específico
        ControladorAsociados cAsociados = new ControladorAsociados(vAsociados, clinica.getGestorAsociados()); // Asume getGestorAsociados() en Clinica
        vAsociados.mostrar();
        // Importante: Registrar la vista como observador del gestor
        clinica.getGestorAsociados().addObserver(vAsociados);
        // Cargar datos iniciales en la vista (llamando al update o un método específico)
        vAsociados.update(clinica.getGestorAsociados(), clinica.getGestorAsociados().getListadoAsociados());
    }

    /**
     * Crea y muestra la ventana de simulación con su controlador.
     */
    private void abrirVentanaSimulacion() {
        VentanaSimulacion vSimulacion = new VentanaSimulacion();
        ControladorSimulacion cSimulacion = new ControladorSimulacion(
                vSimulacion,
                clinica.getSimulador(),
                clinica.getAmbulancia(),
                this.ventanaPrincipal
        );
        vSimulacion.mostrar();
        clinica.getAmbulancia().addObserver(vSimulacion);
        vSimulacion.update(clinica.getAmbulancia(), clinica.getAmbulancia().getNombreEstadoActual());
    }

    /**
     * Inicia la aplicación.
     * Carga los datos iniciales desde la BD y muestra la ventana principal.
     */
    public void iniciar() {
        try {
            clinica.getGestorAsociados().cargarAsociadosDesdeBD();
        } catch (DAOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                    ventanaPrincipal,
                    "Error crítico al conectar o cargar la Base de Datos.\n" + e.getMessage() +
                            "\nVerifica que el servidor MySQL esté corriendo.",
                    "Error de Persistencia",
                    JOptionPane.ERROR_MESSAGE
            );
        }
        ventanaPrincipal.mostrarVentana();
    }

    /**
     * Ejecuta la inicialización de la BD en un hilo separado
     * para no bloquear la GUI.
     */
    private void inicializarBaseDeDatos() {
        ventanaPrincipal.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.WAIT_CURSOR));
        new Thread(() -> { // Tarea pesada en hilo nuevo
            try {
                clinica.getGestorAsociados().inicializarTablas();
                SwingUtilities.invokeLater(() -> // Resultado en hilo de GUI
                        JOptionPane.showMessageDialog(
                                ventanaPrincipal,
                                "Base de datos inicializada correctamente.",
                                "Éxito",
                                JOptionPane.INFORMATION_MESSAGE
                        )
                );
            } catch (DAOException e) {
                e.printStackTrace();
                SwingUtilities.invokeLater(() ->
                        JOptionPane.showMessageDialog(
                                ventanaPrincipal,
                                "Error al inicializar la base de datos:\n" + e.getMessage(),
                                "Error de Persistencia",
                                JOptionPane.ERROR_MESSAGE
                        )
                );
            } finally {
                SwingUtilities.invokeLater(() ->
                        ventanaPrincipal.setCursor(java.awt.Cursor.getDefaultCursor())
                );
            }
        }).start();
    }


}