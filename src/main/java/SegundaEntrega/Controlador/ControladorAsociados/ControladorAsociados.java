package SegundaEntrega.Controlador.ControladorAsociados;

import SegundaEntrega.Modelo.Datos.Personas.Asociado;
import SegundaEntrega.Modelo.Negocio.GestorAsociados;
import SegundaEntrega.Modelo.Excepciones.AsociadoDuplicadoException;
import SegundaEntrega.Vista.JFrameAsociados.VentanaAsociados;

import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;

/**
 * Controlador para la VentanaAsociados. Maneja las acciones de los botones
 * e interactúa con el GestorAsociados.
 */
public class ControladorAsociados implements ActionListener {

    private VentanaAsociados vista;
    private GestorAsociados modelo;

    /**
     * Constructor.
     * @param vista La VentanaAsociados que controla.
     * @param modelo El GestorAsociados con la lógica de negocio.
     */
    public ControladorAsociados(VentanaAsociados vista, GestorAsociados modelo) {
        this.vista = vista;
        this.modelo = modelo;

        // Vincula este controlador con los botones de la vista
        this.vista.setControlador(this);
    }

    /**
     * Maneja los eventos de acción de la VentanaAsociados.
     * @param e El evento.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
            case "AGREGAR_ASOCIADO":
                agregarAsociado();
                break;
            case "ELIMINAR_ASOCIADO":
                eliminarAsociado();
                break;
            case "LIMPIAR_CAMPOS":
                vista.limpiarCampos();
                break;
            default:
                System.err.println("Comando no reconocido en ControladorAsociados: " + command);
                break;
        }
    }

    /**
     * Lógica para agregar un asociado. Toma datos de la vista,
     * llama al modelo y maneja posibles excepciones informando a la vista.
     */
    private void agregarAsociado() {
        // 1. Obtener datos de la vista
        String dni = vista.getDni();
        String nombre = vista.getNombre();
        String apellido = vista.getApellido();
        // Validar datos básicos (podría ser más exhaustivo)
        if (dni.isEmpty() || nombre.isEmpty() || apellido.isEmpty()) {
            vista.mostrarError("Datos incompletos", "DNI, Nombre y Apellido son obligatorios.");
            return;
        }

        // 2. Crear objeto del modelo
        Asociado nuevo = new Asociado(
                nombre, apellido, dni,
                vista.getDomicilio(), vista.getTelefono(), vista.getCiudad()
        );

        // 3. Llamar al modelo (lógica de negocio)
        try {
            modelo.altaAsociado(nuevo);
            vista.mostrarMensaje("Éxito", "Asociado agregado correctamente.");
            vista.limpiarCampos();
            //se actualiza la lista
        } catch (AsociadoDuplicadoException | IllegalArgumentException ex) {
            vista.mostrarError("Error al agregar", ex.getMessage());
        } catch (Exception ex) { // Captura genérica para otros posibles errores
            vista.mostrarError("Error inesperado", "Ocurrió un error: " + ex.getMessage());
            ex.printStackTrace(); // Es buena idea loguear el stack trace
        }
    }

    /**
     * Lógica para eliminar el asociado seleccionado en la vista.
     */
    private void eliminarAsociado() {
        // 1. Obtener DNI seleccionado de la vista
        String dniSeleccionado = vista.getDniSeleccionado();

        if (dniSeleccionado == null) {
            vista.mostrarError("Error", "Debe seleccionar un asociado de la lista para eliminar.");
            return;
        }

        // 2. Preguntar confirmación (buena práctica)
        int confirmacion = JOptionPane.showConfirmDialog(
                vista,
                "¿Está seguro de que desea eliminar al asociado con DNI " + dniSeleccionado + "?",
                "Confirmar Eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirmacion != JOptionPane.YES_OPTION) {
            return; // No hacer nada si el usuario cancela
        }

        // 3. Crear un objeto Asociado temporal solo con el DNI para la baja
        // (GestorAsociados usa equals() basado en DNI)
        Asociado aEliminar = new Asociado();
        aEliminar.setDni(dniSeleccionado);

        // 4. Llamar al modelo
        try {
            modelo.bajaAsociado(aEliminar);
            vista.mostrarMensaje("Éxito", "Asociado eliminado correctamente.");
            // La vista se actualizará automáticamente via Observer
        } catch (AsociadoDuplicadoException | IllegalArgumentException ex) {
            vista.mostrarError("Error al eliminar", ex.getMessage());
        } catch (Exception ex) {
            vista.mostrarError("Error inesperado", "Ocurrió un error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}