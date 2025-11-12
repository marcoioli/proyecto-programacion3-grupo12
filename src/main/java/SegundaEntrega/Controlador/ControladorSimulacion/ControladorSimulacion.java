package SegundaEntrega.Controlador.ControladorSimulacion;

import SegundaEntrega.Modelo.Negocio.Ambulancia; // Para pasar al observer (aunque ya está registrado)
import SegundaEntrega.Modelo.Negocio.Simulador;
import SegundaEntrega.Vista.JFrameSimulacion.VentanaSimulacion;
import SegundaEntrega.Vista.JFramePrincipal.VentanaPrincipal;


import javax.swing.*; // Para SwingUtilities
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
/**
 * Controlador para la VentanaSimulacion. Maneja el inicio y fin de la simulación.
 */
public class ControladorSimulacion implements ActionListener {

    private VentanaSimulacion vista;
    private Simulador modeloSimulador;
    private Ambulancia modeloAmbulancia; // Para registrar observer (aunque se hace en C.Principal)
    private VentanaPrincipal vistaPrincipal;

    /**
     * Constructor.
     * @param vista La ventana de simulación.
     * @param modeloSimulador El objeto que maneja la simulación.
     * @param modeloAmbulancia La ambulancia (para el observer).
     * @param vistaPrincipal La ventana principal (para bloquear/desbloquear).
     */
    public ControladorSimulacion(VentanaSimulacion vista, Simulador modeloSimulador, Ambulancia modeloAmbulancia, VentanaPrincipal vistaPrincipal) {
        this.vista = vista;
        this.modeloSimulador = modeloSimulador;
        this.modeloAmbulancia = modeloAmbulancia;
        this.vistaPrincipal = vistaPrincipal;
        this.vista.setControlador(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
            case "INICIAR_SIMULACION":
                iniciar();
                break;
            case "FINALIZAR_SIMULACION":
                finalizar();
                break;
            case "SOLICITAR_MANTENIMIENTO": // Este es el nuevo ActionCommand
                solicitarMantenimiento();
                break;
            default:
                System.err.println("Comando no reconocido en ControladorSimulacion: " + command);
                break;
        }
    }

    /** Inicia la simulación */
    private void iniciar() {
        int cantSolicitudes = vista.getCantidadSolicitudes();
        if (cantSolicitudes > 0) {
            vista.limpiarLogs(); // Limpiar logs antes de empezar
            vista.setSimulacionActiva(true); // Actualizar botones
            // simula en un hilo separado
            this.vistaPrincipal.setGestionHabilitada(false);
            new Thread(() -> {
                modeloSimulador.iniciarSimulacion(cantSolicitudes);
                SwingUtilities.invokeLater(() -> {
                    vista.setSimulacionActiva(false);
                    this.vistaPrincipal.setGestionHabilitada(true);
                });
            }).start();
            vista.agregarLogGeneral("--> Simulación iniciada...");
        } else {
            vista.mostrarError("Configuración inválida", "La cantidad de solicitudes debe ser mayor a 0.");
        }
    }

    /** Finaliza la simulación */
    private void finalizar() {
        vista.agregarLogGeneral("--> Solicitando finalización de la simulación...");
        // Finalizar también en hilo separado si puede tardar
        new Thread(() -> {
            modeloSimulador.finalizarSimulacion();
            // Actualizar la GUI cuando termine
            SwingUtilities.invokeLater(() -> {
                vista.setSimulacionActiva(false);
                this.vistaPrincipal.setGestionHabilitada(true); // <-- AÑADIR ESTA LÍNEA
            });
            SwingUtilities.invokeLater(() -> vista.agregarLogGeneral("--> Simulación finalizada."));
        }).start();
    }

    /** * Llama al método del simulador para solicitar mantenimiento "por demanda".
     * Esta acción es invocada por el usuario (Operario) desde la GUI.
     */
    private void solicitarMantenimiento() {
        vista.agregarLogGeneral("OPERARIO (GUI): Solicitando mantenimiento...");

        // Llamamos directamente al método del simulador.
        // Es seguro hacerlo desde el hilo de la GUI (EDT) porque
        // 'solicitarMantenimientoPorDemanda' está diseñado para ser NO bloqueante
        // (ya que usa el ExecutorService internamente).
        modeloSimulador.solicitarMantenimientoPorDemanda("Operario (GUI)");
    }
}

