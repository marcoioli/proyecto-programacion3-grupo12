package SegundaEntrega.Vista.JFramePrincipal;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.FlowLayout;

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
    private JButton btnGestionAsociados;
    private JButton btnSimulacion;

    /**
     * Constructor de la ventana principal.
     */
    public VentanaPrincipal() {
        super("Sistema de Gestión Clínica - Etapa II"); // Título de la ventana
        setLayout(new BorderLayout(10, 10));
        inicializarComponentes();
        configurarVentana();
    }

    /**
     * Crea e inicializa todos los componentes de la ventana principal.
     */
    private void inicializarComponentes() {

        JPanel panelTitulo = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel lblTitulo = new JLabel("Sistema de Gestión de Clínica");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        panelTitulo.add(lblTitulo);
        panelTitulo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel para los botones principales
        JPanel panelBotones = new JPanel(new GridLayout(2, 1, 10, 10)); // 2 filas, 1 columna
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50)); // Margen

        btnGestionAsociados = new JButton("Gestionar Asociados");
        btnGestionAsociados.setActionCommand("GESTION_ASOCIADOS");
        btnGestionAsociados.setFont(new Font("Arial", Font.PLAIN, 18));

        btnSimulacion = new JButton("Iniciar Simulación");
        btnSimulacion.setActionCommand("SIMULACION");
        btnSimulacion.setFont(new Font("Arial", Font.PLAIN, 18));

        panelBotones.add(btnGestionAsociados);
        panelBotones.add(btnSimulacion);

        menuBar = new JMenuBar();

        // Menú Archivo
        menuArchivo = new JMenu("Archivo");
        itemSalir = new JMenuItem("Salir");
        itemSalir.setActionCommand("SALIR");
        menuArchivo.add(itemSalir);
        menuBar.add(menuArchivo); // Se añade a la barra principal

        // Menú Gestión
        menuGestion = new JMenu("Gestión");
        itemGestionAsociados = new JMenuItem("Gestionar Asociados");
        itemGestionAsociados.setActionCommand("GESTION_ASOCIADOS");
        menuGestion.add(itemGestionAsociados);
        menuBar.add(menuGestion); // Se añade a la barra principal

        // Menú Simulación
        menuSimulacion = new JMenu("Simulación");
        itemIniciarSimulacion = new JMenuItem("Iniciar/Configurar Simulación");
        itemIniciarSimulacion.setActionCommand("SIMULACION");
        menuSimulacion.add(itemIniciarSimulacion);
        menuBar.add(menuSimulacion); // Se añade a la barra principal

        // Menú Sistema (No redeclares menuBar aquí)
        JMenu menuSistema = new JMenu("Sistema");
        itemInicializarBD = new JMenuItem("Inicializar Base de Datos");
        itemInicializarBD.setActionCommand("INICIALIZAR_BD");
        menuSistema.add(itemInicializarBD);
        menuBar.add(menuSistema); // Se añade a la barra principal

        this.setJMenuBar(menuBar);

        add(panelTitulo, BorderLayout.NORTH);
        add(panelBotones, BorderLayout.CENTER);
    }

    /**
     * Configura propiedades básicas del JFrame.
     */
    private void configurarVentana() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Termina la app al cerrar
        setSize(600, 400); // Tamaño inicial
        setLocationRelativeTo(null); // Centrar en pantalla
    }

    /**
     * Asigna el ActionListener (Controlador) a los componentes interactivos (items de menú).
     * @param listener El ActionListener que manejará los eventos.
     */
    public void setControlador(ActionListener listener) {
        btnGestionAsociados.addActionListener(listener);
        btnSimulacion.addActionListener(listener);

        itemSalir.addActionListener(listener);
        itemGestionAsociados.addActionListener(listener);
        itemIniciarSimulacion.addActionListener(listener);
        itemInicializarBD.addActionListener(listener);
    }

    /**
     * Habilita o deshabilita los controles de gestión de asociados.
     * @param habilitada true para habilitar, false para deshabilitar.
     */
    public void setGestionHabilitada(boolean habilitada) {
        this.btnGestionAsociados.setEnabled(habilitada);
        this.menuGestion.setEnabled(habilitada);
        this.itemInicializarBD.setEnabled(habilitada);
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