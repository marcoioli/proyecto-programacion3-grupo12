package SegundaEntrega.Vista.JFramePrincipal;

import javax.swing.*;
import java.awt.event.ActionListener; // Import para ActionListener

/**
 * Ventana principal de la aplicación de gestión de la clínica.
 * Utiliza Swing para la interfaz gráfica (JFrame).
 * Contiene el menú para acceder a las diferentes funcionalidades.
 */
public class VentanaPrincipal extends JFrame {

    // --- Componentes del Menú ---
    private JMenuBar menuBar;
    private JMenu menuArchivo, menuGestion, menuSimulacion;
    private JMenuItem itemSalir, itemGestionAsociados, itemIniciarSimulacion,itemInicializarBD;

    // Podríamos añadir otros componentes si fueran necesarios (ej: un panel de estado)
    /**
     * Constructor de la ventana principal.
     */
    public VentanaPrincipal() {
        super("Sistema de Gestión Clínica - Etapa II"); // Título de la ventana
        inicializarComponentes();
        configurarVentana();
    }

    /**
     * Crea y configura los componentes de la ventana (menú, etc.).
     */
    private void inicializarComponentes() {
        menuBar = new JMenuBar();

        // Menú Archivo
        menuArchivo = new JMenu("Archivo");
        itemSalir = new JMenuItem("Salir");
        itemSalir.setActionCommand("SALIR"); // Comando para el ActionListener
        menuArchivo.add(itemSalir);
        menuBar.add(menuArchivo);

        // Menú Gestión
        menuGestion = new JMenu("Gestión");
        itemGestionAsociados = new JMenuItem("Gestionar Asociados");
        itemGestionAsociados.setActionCommand("GESTION_ASOCIADOS"); // Comando
        menuGestion.add(itemGestionAsociados);
        menuBar.add(menuGestion);

        // Menú Simulación
        menuSimulacion = new JMenu("Simulación");
        itemIniciarSimulacion = new JMenuItem("Iniciar/Configurar Simulación");
        itemIniciarSimulacion.setActionCommand("SIMULACION"); // Comando
        menuSimulacion.add(itemIniciarSimulacion);
        menuBar.add(menuSimulacion);

        JMenuBar menuBar = new JMenuBar();
        JMenu menuSistema = new JMenu("Sistema");
        itemInicializarBD = new JMenuItem("Inicializar Base de Datos");
        itemInicializarBD.setActionCommand("INICIALIZAR_BD");
        itemSalir = new JMenuItem("Salir");
        itemSalir.setActionCommand("SALIR");

        menuSistema.add(itemInicializarBD);
        menuSistema.addSeparator();
        menuSistema.add(itemSalir);

        menuBar.add(menuSistema);
        this.setJMenuBar(menuBar);

        // Asignar la barra de menú al JFrame
        this.setJMenuBar(menuBar);

        // Aquí podrías añadir otros paneles o componentes al contentPane si la ventana principal tuviera más elementos
        // Ejemplo: JLabel statusLabel = new JLabel("Listo");
        // getContentPane().add(statusLabel, java.awt.BorderLayout.SOUTH);
    }

    /**
     * Configura propiedades básicas del JFrame.
     */
    private void configurarVentana() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Termina la app al cerrar
        setSize(600, 400); // Tamaño inicial
        setLocationRelativeTo(null); // Centrar en pantalla
        // setVisible(true); // Hacerla visible se hará desde fuera (ej: main)
    }

    /**
     * Asigna el ActionListener (Controlador) a los componentes interactivos (items de menú).
     * @param listener El ActionListener que manejará los eventos.
     */
    public void setControlador(ActionListener listener) {
        itemSalir.addActionListener(listener);
        itemGestionAsociados.addActionListener(listener);
        itemIniciarSimulacion.addActionListener(listener);
        itemInicializarBD.addActionListener(listener);
        itemSalir.addActionListener(listener);
    }

    /**
     * Método para hacer visible la ventana (llamado desde el controlador o main).
     */
    public void mostrarVentana() {
        setVisible(true);
    }

    /**
     * Método para cerrar la ventana (llamado desde el controlador).
     */
    public void cerrarVentana() {
        dispose(); // Libera los recursos de la ventana
        System.exit(0); // Asegura que la aplicación termine
    }
}