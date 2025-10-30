package SegundaEntrega.Vista.JFrameSimulacion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

/**
 * Ventana para configurar, iniciar, detener y visualizar la simulación de la ambulancia.
 * Implementa Observer para mostrar el estado actualizado de la ambulancia.
 */
public class VentanaSimulacion extends JFrame implements Observer {

    // --- Componentes ---
    private JSpinner spinnerSolicitudes; // Para configurar cant. solicitudes
    private JButton btnIniciar, btnFinalizar;
    private JTextArea areaLogAmbulancia; // Muestra estado/actividad de la ambulancia
    private JTextArea areaLogGeneral;   // Muestra actividad general (asociados/operario)
    private JLabel lblEstadoAmbulancia; // Muestra el estado actual simple

    /**
     * Constructor.
     */
    public VentanaSimulacion() {
        super("Simulación Ambulancia");
        inicializarComponentes();
        configurarVentana();
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout(10, 10));

        // Panel de Configuración y Controles (Arriba)
        JPanel panelControles = new JPanel(new FlowLayout());
        panelControles.add(new JLabel("Solicitudes por asociado:"));
        // Spinner para números enteros, valor inicial 1, mínimo 1
        spinnerSolicitudes = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        panelControles.add(spinnerSolicitudes);
        btnIniciar = new JButton("Iniciar Simulación");
        btnIniciar.setActionCommand("INICIAR_SIMULACION");
        btnFinalizar = new JButton("Finalizar Simulación");
        btnFinalizar.setActionCommand("FINALIZAR_SIMULACION");
        btnFinalizar.setEnabled(false); // Deshabilitado hasta que inicie
        panelControles.add(btnIniciar);
        panelControles.add(btnFinalizar);

        // Panel de Visualización (Centro) - Dividido
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setResizeWeight(0.3); // Dar más espacio al log general

        // Área Estado Ambulancia (Arriba del Split)
        JPanel panelAmbulancia = new JPanel(new BorderLayout());
        lblEstadoAmbulancia = new JLabel("Estado Ambulancia: Desconocido");
        lblEstadoAmbulancia.setFont(new Font("Arial", Font.BOLD, 14));
        panelAmbulancia.add(lblEstadoAmbulancia, BorderLayout.NORTH);
        areaLogAmbulancia = new JTextArea(10, 40);
        areaLogAmbulancia.setEditable(false);
        panelAmbulancia.add(new JScrollPane(areaLogAmbulancia), BorderLayout.CENTER);
        splitPane.setTopComponent(panelAmbulancia);

        // Área Log General (Abajo del Split)
        areaLogGeneral = new JTextArea(20, 40);
        areaLogGeneral.setEditable(false);
        splitPane.setBottomComponent(new JScrollPane(areaLogGeneral));

        // Añadir paneles principales a la ventana
        add(panelControles, BorderLayout.NORTH);
        add(splitPane, BorderLayout.CENTER);
    }

    private void configurarVentana() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 600);
        setLocationRelativeTo(null);
    }

    /** Asigna el controlador a los botones */
    public void setControlador(ActionListener listener) {
        btnIniciar.addActionListener(listener);
        btnFinalizar.addActionListener(listener);
    }

    // --- Métodos para interacción con el Controlador ---

    public int getCantidadSolicitudes() {
        return (Integer) spinnerSolicitudes.getValue();
    }

    /** Actualiza el estado de los botones (Iniciar/Finalizar) */
    public void setSimulacionActiva(boolean activa) {
        btnIniciar.setEnabled(!activa);
        spinnerSolicitudes.setEnabled(!activa); // No cambiar config mientras corre
        btnFinalizar.setEnabled(activa);
    }

    /** Añade texto al log general (hilos asociados/operario) */
    public void agregarLogGeneral(String mensaje) {
        SwingUtilities.invokeLater(() -> { // Asegurar actualización en el hilo de UI
            areaLogGeneral.append(mensaje + "\n");
            areaLogGeneral.setCaretPosition(areaLogGeneral.getDocument().getLength()); // Auto-scroll
        });
    }

    /** Añade texto al log de la ambulancia */
    public void agregarLogAmbulancia(String mensaje) {
        SwingUtilities.invokeLater(() -> {
            areaLogAmbulancia.append(mensaje + "\n");
            areaLogAmbulancia.setCaretPosition(areaLogAmbulancia.getDocument().getLength());
        });
    }


    /** Limpia las áreas de log */
    public void limpiarLogs() {
        SwingUtilities.invokeLater(() -> {
            areaLogAmbulancia.setText("");
            areaLogGeneral.setText("");
            lblEstadoAmbulancia.setText("Estado Ambulancia: Simulación no iniciada");
        });
    }

    /**
     * Método del Observer: Actualiza la etiqueta de estado de la ambulancia.
     * @param o El Observable (la Ambulancia).
     * @param arg El argumento (String con el nombre del estado).
     */
    /**
     * Muestra un cuadro de diálogo de error modal.
     * @param titulo El título de la ventana de error.
     * @param mensaje El mensaje de error a mostrar.
     */
    public void mostrarError(String titulo, String mensaje) {
        // Asegura que se muestre en el hilo de la GUI
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(this, mensaje, titulo, JOptionPane.ERROR_MESSAGE);
        });
    }

    /**
     * Muestra un cuadro de diálogo informativo modal.
     * @param titulo El título de la ventana de mensaje.
     * @param mensaje El mensaje a mostrar.
     */
    public void mostrarMensaje(String titulo, String mensaje) {
        // Asegura que se muestre en el hilo de la GUI
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(this, mensaje, titulo, JOptionPane.INFORMATION_MESSAGE);
        });
    }
    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof String) {
            String nuevoEstado = (String) arg;
            SwingUtilities.invokeLater(() -> { // Actualizar UI en su hilo
                lblEstadoAmbulancia.setText("Estado Ambulancia: " + nuevoEstado);
                // También podríamos añadirlo al log de ambulancia si queremos un historial
                // agregarLogAmbulancia("Nuevo estado: " + nuevoEstado);
            });
        }
    }

    /** Muestra la ventana */
    public void mostrar() {
        setVisible(true);
    }
}