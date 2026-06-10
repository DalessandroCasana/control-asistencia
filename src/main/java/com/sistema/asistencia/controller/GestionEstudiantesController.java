package com.sistema.asistencia.controller;

import com.sistema.asistencia.view.FrmGestionEstudiantes;
import org.apache.commons.lang3.StringUtils;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GestionEstudiantesController implements ActionListener {

    private final FrmGestionEstudiantes vista;
    private int contadorIdSimulado = 1;

    public GestionEstudiantesController(FrmGestionEstudiantes vista) {
        this.vista = vista;

        
        this.vista.btnRegistrar.addActionListener(this);
        this.vista.btnModificar.addActionListener(this);
        this.vista.btnEliminar.addActionListener(this);
        this.vista.btnLimpiar.addActionListener(this);

        
        this.vista.tblEstudiantes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                recuperarFilaSeleccionada();
            }
        });

        inicializarTablaPrueba();
    }

    private void inicializarTablaPrueba() {
        
        vista.modeloTabla.addRow(new Object[]{contadorIdSimulado++, "U20261102", "Juan Alberto", "Pérez Ramos", "U20261102@utp.edu.pe", "Activo"});
        vista.modeloTabla.addRow(new Object[]{contadorIdSimulado++, "U20261105", "María Fernanda", "Sánchez Vega", "U20261105@utp.edu.pe", "Activo"});
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object origen = e.getSource();

        if (origen == vista.btnRegistrar) {
            ejecutarRegistro();
        } else if (origen == vista.btnModificar) {
            ejecutarModificacion();
        } else if (origen == vista.btnEliminar) {
            ejecutarEliminacion();
        } else if (origen == vista.btnLimpiar) {
            limpiarFormulario();
        }
    }

    private void ejecutarRegistro() {
        String codigo = vista.txtCodigo.getText().trim();
        String nombres = vista.txtNombres.getText().trim();
        String apellidos = vista.txtApellidos.getText().trim();
        String correo = vista.txtCorreo.getText().trim();
        String estado = vista.cboEstado.getSelectedItem().toString();

        
        if (StringUtils.isBlank(codigo) || StringUtils.isBlank(nombres) || 
            StringUtils.isBlank(apellidos) || StringUtils.isBlank(correo)) {
            JOptionPane.showMessageDialog(vista, "Por favor, complete todos los campos de la ficha del estudiante.", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        vista.modeloTabla.addRow(new Object[]{contadorIdSimulado++, codigo, nombres, apellidos, correo, estado});
        JOptionPane.showMessageDialog(vista, "Estudiante matriculado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        limpiarFormulario();
    }

    private void ejecutarModificacion() {
        String idStr = vista.txtIdEstudiante.getText();
        if (StringUtils.isBlank(idStr)) {
            JOptionPane.showMessageDialog(vista, "Seleccione un estudiante de la tabla para modificar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int fila = vista.tblEstudiantes.getSelectedRow();
        if (fila != -1) {
            vista.modeloTabla.setValueAt(vista.txtCodigo.getText().trim(), fila, 1);
            vista.modeloTabla.setValueAt(vista.txtNombres.getText().trim(), fila, 2);
            vista.modeloTabla.setValueAt(vista.txtApellidos.getText().trim(), fila, 3);
            vista.modeloTabla.setValueAt(vista.txtCorreo.getText().trim(), fila, 4);
            vista.modeloTabla.setValueAt(vista.cboEstado.getSelectedItem().toString(), fila, 5);

            JOptionPane.showMessageDialog(vista, "Ficha del estudiante actualizada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarFormulario();
        }
    }

    private void ejecutarEliminacion() {
        int fila = vista.tblEstudiantes.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "Seleccione el alumno que desea remover.", "Selección Requerida", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int opcion = JOptionPane.showConfirmDialog(vista, "¿Está seguro de eliminar permanentemente a este estudiante?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            vista.modeloTabla.removeRow(fila);
            JOptionPane.showMessageDialog(vista, "Registro eliminado satisfactoriamente.", "Completado", JOptionPane.INFORMATION_MESSAGE);
            limpiarFormulario();
        }
    }

    private void recuperarFilaSeleccionada() {
        int fila = vista.tblEstudiantes.getSelectedRow();
        if (fila != -1) {
            vista.txtIdEstudiante.setText(vista.modeloTabla.getValueAt(fila, 0).toString());
            vista.txtCodigo.setText(vista.modeloTabla.getValueAt(fila, 1).toString());
            vista.txtNombres.setText(vista.modeloTabla.getValueAt(fila, 2).toString());
            vista.txtApellidos.setText(vista.modeloTabla.getValueAt(fila, 3).toString());
            vista.txtCorreo.setText(vista.modeloTabla.getValueAt(fila, 4).toString());
            vista.cboEstado.setSelectedItem(vista.modeloTabla.getValueAt(fila, 5).toString());
        }
    }

    private void limpiarFormulario() {
        vista.txtIdEstudiante.setText("");
        vista.txtCodigo.setText("");
        vista.txtNombres.setText("");
        vista.txtApellidos.setText("");
        vista.txtCorreo.setText("");
        vista.cboEstado.setSelectedIndex(0);
        vista.tblEstudiantes.clearSelection();
    }
}