package SegundaEntrega.Controlador.ControladorPrincipal;

import SegundaEntrega.Modelo.Negocio.Clinica; // Acceso al Modelo (Singleton/Facade)
import SegundaEntrega.Vista.JframePrincipal.VentanaPrincipal;
// Imports para las otras ventanas y controladores que necesitará crear/mostrar
import SegundaEntrega.Vista.JframeAsociados.VentanaAsociados;
import SegundaEntrega.Controlador.ControladorAsociados.ControladorAsociados;
import SegundaEntrega.Vista.JFrameSimulacion.VentanaSimulacion;
import SegundaEntrega.Controlador.ControladorSimulacion.ControladorSimulacion;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        // Pasar vista y modelo (Simulador y Ambulancia) al controlador específico
        ControladorSimulacion cSimulacion = new ControladorSimulacion(vSimulacion, clinica.getSimulador(), clinica.getAmbulancia()); // Asume métodos get en Clinica
        vSimulacion.mostrar();
        // Registrar la vista como observador de la ambulancia
        clinica.getAmbulancia().addObserver(vSimulacion);
        // Mostrar estado inicial
        vSimulacion.update(clinica.getAmbulancia(), clinica.getAmbulancia().getNombreEstadoActual());
    }

    /**
     * Inicia la aplicación mostrando la ventana principal.
     */
    public void iniciar() {
        // Cargar datos iniciales si es necesario
        // clinica.cargarDatos();
        ventanaPrincipal.mostrarVentana();
    }
}