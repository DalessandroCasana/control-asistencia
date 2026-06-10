package com.sistema.asistencia.controller;

import com.sistema.asistencia.model.dao.CursoDAO;       
import com.sistema.asistencia.model.entity.Curso;   
import com.sistema.asistencia.view.FrmGestionCursos;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class GestionCursosController implements ActionListener {

    private final FrmGestionCursos vista;
    private final CursoDAO cursoDAO;

    public GestionCursosController(FrmGestionCursos vista) {
        this.vista = vista;
        this.cursoDAO = new CursoDAO(); 

        
        this.vista.btnRegistrar.addActionListener(this);
        this.vista.btnModificar.addActionListener(this);
        this.vista.btnEliminar.addActionListener(this);
        this.vista.btnLimpiar.addActionListener(this);

        
        this.vista.tblCursos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                recuperarFilaSeleccionada();
            }
        });

        
        listarCursosEnTabla();
    }

    private void listarCursosEnTabla() {
        
        vista.modeloTabla.setRowCount(0);

        try {
            
            List<Curso> lista = cursoDAO.listar();
            
            
            for (Curso curso : lista) {
                vista.modeloTabla.addRow(new Object[]{
                    curso.getIdCurso(),
                    curso.getCodigo(),
                    curso.getNombre(),
                    curso.getCreditos(),
                    curso.getHorasTotales()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, 
                "Error crítico al conectar con PostgreSQL para listar la grilla:\n" + ex.getMessage(), 
                "Error del Sistema", JOptionPane.ERROR_MESSAGE);
        }
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
        String codigo = vista.txtCodigoCurso.getText().trim();
        String nombre = vista.txtNombreCurso.getText().trim();
        int creditos = (int) vista.spnCreditos.getValue();
        int horas = (int) vista.spnHoras.getValue();

        
        if (StringUtils.isBlank(codigo) || StringUtils.isBlank(nombre)) {
            JOptionPane.showMessageDialog(vista, 
                "Por favor, rellene los campos de Código y Nombre para continuar.", 
                "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Curso nuevoCurso = new Curso();
        nuevoCurso.setCodigo(codigo);
        nuevoCurso.setNombre(nombre);
        nuevoCurso.setCreditos(creditos);
        nuevoCurso.setHorasTotales(horas);

        
        if (cursoDAO.registrar(nuevoCurso)) {
            JOptionPane.showMessageDialog(vista, "Curso registrado y guardado con éxito en PostgreSQL.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarFormulario();
            listarCursosEnTabla(); 
        } else {
            JOptionPane.showMessageDialog(vista, "No se pudo registrar la asignatura. Revise la consola del servidor.", "Error de Persistencia", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void ejecutarModificacion() {
        String idStr = vista.txtIdCurso.getText();
        if (StringUtils.isBlank(idStr)) {
            JOptionPane.showMessageDialog(vista, "Debe seleccionar un curso de la grilla para aplicar cambios.", "Selección Requerida", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Curso cursoEditar = new Curso();
        cursoEditar.setIdCurso(Integer.parseInt(idStr));
        cursoEditar.setCodigo(vista.txtCodigoCurso.getText().trim());
        cursoEditar.setNombre(vista.txtNombreCurso.getText().trim());
        cursoEditar.setCreditos((int) vista.spnCreditos.getValue());
        cursoEditar.setHorasTotales((int) vista.spnHoras.getValue());

        if (cursoDAO.modificar(cursoEditar)) {
            JOptionPane.showMessageDialog(vista, "Registro modificado satisfactoriamente en la base de datos.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarFormulario();
            listarCursosEnTabla(); 
        } else {
            JOptionPane.showMessageDialog(vista, "Error al intentar actualizar la información del curso.", "Error de Actualización", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void ejecutarEliminacion() {
        String idStr = vista.txtIdCurso.getText();
        if (StringUtils.isBlank(idStr)) {
            JOptionPane.showMessageDialog(vista, "Por favor, haga clic sobre un curso de la tabla para eliminarlo.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(vista, 
            "¿Está seguro de eliminar esta asignatura permanentemente de PostgreSQL?\nNota: Si el curso ya tiene secciones creadas, la base de datos rechazará la acción.", 
            "Confirmar Eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            
        if (confirmacion == JOptionPane.YES_OPTION) {
            int idCurso = Integer.parseInt(idStr);
            
            if (cursoDAO.eliminar(idCurso)) {
                JOptionPane.showMessageDialog(vista, "El registro ha sido removido de la base de datos.", "Eliminado", JOptionPane.INFORMATION_MESSAGE);
                limpiarFormulario();
                listarCursosEnTabla(); 
            } else {
                JOptionPane.showMessageDialog(vista, "Error: No se pudo eliminar. El curso posee integridad referencial activa (llave foránea en uso).", "Restricción de BD", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void recuperarFilaSeleccionada() {
        int fila = vista.tblCursos.getSelectedRow();
        if (fila != -1) {
            vista.txtIdCurso.setText(vista.modeloTabla.getValueAt(fila, 0).toString());
            vista.txtCodigoCurso.setText(vista.modeloTabla.getValueAt(fila, 1).toString());
            vista.txtNombreCurso.setText(vista.modeloTabla.getValueAt(fila, 2).toString());
            vista.spnCreditos.setValue(vista.modeloTabla.getValueAt(fila, 3));
            vista.spnHoras.setValue(vista.modeloTabla.getValueAt(fila, 4));
        }
    }

    private void limpiarFormulario() {
        vista.txtIdCurso.setText("");
        vista.txtCodigoCurso.setText("");
        vista.txtNombreCurso.setText("");
        vista.spnCreditos.setValue(4);
        vista.spnHoras.setValue(64);
        vista.tblCursos.clearSelection();
    }
}