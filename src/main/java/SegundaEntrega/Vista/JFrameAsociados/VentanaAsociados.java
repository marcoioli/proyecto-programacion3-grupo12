package SegundaEntrega.Vista.JFrameAsociados;

import SegundaEntrega.Modelo.Datos.Personas.Asociado; // Importar Asociado

import javax.swing.*;
import java.awt.*; // Para Layouts
import java.awt.event.ActionListener; // Para Controlador
import java.util.List;
import java.util.Observable; // Para Observer
import java.util.Observer;   // Para Observer

/**
 * Ventana para la gestión (Alta, Baja, Listado) de Asociados.
 * Implementa Observer para actualizarse cuando cambia la lista en el modelo.
 */
public class VentanaAsociados extends JFrame implements Observer { // Implementa Observer

    // --- Componentes ---
    private JTextField txtDni, txtNombre, txtApellido, txtDomicilio, txtTelefono, txtCiudad;
    private JButton btnAgregar, btnEliminar, btnLimpiar;
    private JList<String> listaAsociadosVisual; // JList para mostrar asociados
    private DefaultListModel<String> listModel; // Modelo para el JList

    /**
     * Constructor.
     */
    public VentanaAsociados() {
        super("Gestión de Asociados");
        inicializarComponentes();
        configurarVentana();
    }

    /**
     * Crea y organiza los componentes de la ventana.
     */
    private void inicializarComponentes() {
        // Usar Layout Managers para organizar
        setLayout(new BorderLayout(10, 10)); // Layout principal

        // Panel para el formulario de entrada
        JPanel panelFormulario = new JPanel(new GridLayout(0, 2, 5, 5)); // Grid para etiquetas y campos
        txtDni = new JTextField(15);
        txtNombre = new JTextField(15);
        txtApellido = new JTextField(15);
        txtDomicilio = new JTextField(15);
        txtTelefono = new JTextField(15);
        txtCiudad = new JTextField(15);

        panelFormulario.add(new JLabel("DNI:")); panelFormulario.add(txtDni);
        panelFormulario.add(new JLabel("Nombre:")); panelFormulario.add(txtNombre);
        panelFormulario.add(new JLabel("Apellido:")); panelFormulario.add(txtApellido);
        panelFormulario.add(new JLabel("Domicilio:")); panelFormulario.add(txtDomicilio);
        panelFormulario.add(new JLabel("Teléfono:")); panelFormulario.add(txtTelefono);
        panelFormulario.add(new JLabel("Ciudad:")); panelFormulario.add(txtCiudad);

        // Panel para los botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        btnAgregar = new JButton("Agregar");
        btnAgregar.setActionCommand("AGREGAR_ASOCIADO");
        btnEliminar = new JButton("Eliminar Seleccionado");
        btnEliminar.setActionCommand("ELIMINAR_ASOCIADO");
        btnLimpiar = new JButton("Limpiar Campos");
        btnLimpiar.setActionCommand("LIMPIAR_CAMPOS");
        panelBotones.add(btnAgregar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);

        // Lista para mostrar asociados
        listModel = new DefaultListModel<>();
        listaAsociadosVisual = new JList<>(listModel);
        JScrollPane scrollLista = new JScrollPane(listaAsociadosVisual); // Para que tenga scroll

        // Añadir paneles al layout principal
        add(panelFormulario, BorderLayout.NORTH);
        add(scrollLista, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        // Deshabilitar botón eliminar inicialmente (hasta que se seleccione algo)
        btnEliminar.setEnabled(false);
        listaAsociadosVisual.addListSelectionListener(e -> {
            btnEliminar.setEnabled(!listaAsociadosVisual.isSelectionEmpty());
        });
    }

    /**
     * Configura propiedades básicas del JFrame.
     */
    private void configurarVentana() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Solo cierra esta ventana, no la app
        setSize(500, 600);
        setLocationRelativeTo(null); // Centrar
    }

    /**
     * Asigna el ActionListener (Controlador) a los botones.
     * @param listener El controlador.
     */
    public void setControlador(ActionListener listener) {
        btnAgregar.addActionListener(listener);
        btnEliminar.addActionListener(listener);
        btnLimpiar.addActionListener(listener);
    }

    /**
     * Limpia los campos de texto del formulario.
     */
    public void limpiarCampos() {
        txtDni.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtDomicilio.setText("");
        txtTelefono.setText("");
        txtCiudad.setText("");
        txtDni.requestFocus(); // Poner foco en DNI
    }

    // --- Métodos para que el Controlador obtenga datos de la vista ---
    public String getDni() { return txtDni.getText().trim(); }
    public String getNombre() { return txtNombre.getText().trim(); }
    public String getApellido() { return txtApellido.getText().trim(); }
    public String getDomicilio() { return txtDomicilio.getText().trim(); }
    public String getTelefono() { return txtTelefono.getText().trim(); }
    public String getCiudad() { return txtCiudad.getText().trim(); }

    /**
     * Obtiene el DNI del asociado seleccionado en la lista.
     * @return El DNI seleccionado, o null si no hay selección.
     */
    public String getDniSeleccionado() {
        String seleccionado = listaAsociadosVisual.getSelectedValue();
        if (seleccionado != null && seleccionado.contains("DNI: ")) {
            // Extraer DNI (asumiendo formato "Apellido, Nombre (DNI: xxx)")
            int inicioDni = seleccionado.indexOf("DNI: ") + 5;
            int finDni = seleccionado.indexOf(")", inicioDni);
            if (finDni > inicioDni) {
                return seleccionado.substring(inicioDni, finDni);
            }
        }
        return null;
    }


    /**
     * Muestra un mensaje de error al usuario.
     * @param mensaje El mensaje a mostrar.
     */
    public void mostrarError(String titulo, String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Muestra un mensaje informativo al usuario.
     * @param mensaje El mensaje a mostrar.
     */
    public void mostrarMensaje(String titulo, String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Método del Observer: Se llama cuando GestorAsociados notifica un cambio.
     * @param o El objeto Observable (GestorAsociados).
     * @param arg El argumento pasado (la lista actualizada de Asociado).
     */
    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof List) {
            // Actualizar el JList
            List<Asociado> asociados = (List<Asociado>) arg;
            listModel.clear();
            for (Asociado a : asociados) {
                // Formato para mostrar en la lista
                listModel.addElement(a.getApellido() + ", " + a.getNombre() + " (DNI: " + a.getDni() + ")");
            }
        }
    }

    /** Muestra la ventana */
    public void mostrar() {
        setVisible(true);
    }
}