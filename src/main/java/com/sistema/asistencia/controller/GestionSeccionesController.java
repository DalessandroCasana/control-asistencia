package com.sistema.asistencia.controller;

import com.sistema.asistencia.model.dao.SeccionDAO;
import com.sistema.asistencia.model.dao.CursoDAO;
import com.sistema.asistencia.model.entity.Seccion;
import com.sistema.asistencia.model.entity.Curso;
import com.sistema.asistencia.view.FrmGestionSecciones;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class GestionSeccionesController implements ActionListener {

    private final FrmGestionSecciones vista;
    private final SeccionDAO seccionDAO;
    private final CursoDAO cursoDAO;
    
    // Colección en caché local para emparejar la selección del ComboBox con su ID real de BD
    private List<Curso> listaCursosCache;

    public GestionSeccionesController(FrmGestionSecciones vista) {
        this.vista = vista;
        this.seccionDAO = new SeccionDAO();
        this.cursoDAO = new CursoDAO();

        // Suscribir eventos de botones
        this.vista.btnRegistrar.addActionListener(this);
        this.vista.btnModificar.addActionListener(this);
        this.vista.btnEliminar.addActionListener(this);
        this.vista.btnLimpiar.addActionListener(this);

        // Suscribir evento de la tabla
        this.vista.tblSecciones.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                recuperarFilaSeleccionada();
            }
        });

        // Inicializar componentes dinámicos de la base de datos
        cargarCursosEnComboBox();
        listarSeccionesEnTabla();
    }

    private void cargarCursosEnComboBox() {
        try {
            vista.cboCursos.removeAllItems();
            vista.cboCursos.addItem("-- Seleccione Curso --");
            
            // Traemos los cursos reales de PostgreSQL
            listaCursosCache = cursoDAO.listar();
            for (Curso c : listaCursosCache) {
                vista.cboCursos.addItem(c.getCodigo() + " - " + c.getNombre());
            }
        } catch (Exception ex) {
            System.err.println("Error al cargar combos de cursos: " + ex.getMessage());
        }
    }

    private void listarSeccionesEnTabla() {
        vista.modeloTabla.setRowCount(0);
        try {
            List<Seccion> lista = seccionDAO.listar();
            for (Seccion s : lista) {
                vista.modeloTabla.addRow(new Object[]{
                    s.getIdSeccion(),
                    s.getCodigoSeccion(),
                    s.getNombreCurso(),
                    s.getNombreDocente(),
                    s.getAula()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al listar secciones: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
        String codigo = vista.txtCodigoSeccion.getText().trim();
        String aula = vista.txtAula.getText().trim();
        int idxCurso = vista.cboCursos.getSelectedIndex();
        int idxDocente = vista.cboDocentes.getSelectedIndex();

        if (StringUtils.isBlank(codigo) || StringUtils.isBlank(aula) || idxCurso == 0 || idxDocente == 0) {
            JOptionPane.showMessageDialog(vista, "Complete todos los campos de selección.", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Obtener el ID del curso seleccionado mapeando el índice de la caché (-1 por el ítem por defecto index 0)
        int idCursoReal = listaCursosCache.get(idxCurso - 1).getIdCurso();
        
        // ID temporal del docente fijo (por ahora asignaremos el ID 2 correspondientes a los docentes cargados en la BD)
        int idDocenteReal = 2; 

        Seccion nuevaSeccion = new Seccion();
        nuevaSeccion.setCodigoSeccion(codigo);
        nuevaSeccion.setIdCurso(idCursoReal);
        nuevaSeccion.setIdDocente(idDocenteReal);
        nuevaSeccion.setAula(aula);

        if (seccionDAO.registrar(nuevaSeccion)) {
            JOptionPane.showMessageDialog(vista, "Sección aperturada en PostgreSQL con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarFormulario();
            listarSeccionesEnTabla();
        }
    }

    private void ejecutarModificacion() {
        String idStr = vista.txtIdSeccion.getText();
        if (StringUtils.isBlank(idStr)) {
            JOptionPane.showMessageDialog(vista, "Seleccione un registro para modificar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idxCurso = vista.cboCursos.getSelectedIndex();
        if (idxCurso == 0) return;

        int idCursoReal = listaCursosCache.get(idxCurso - 1).getIdCurso();

        Seccion seccionEditar = new Seccion();
        seccionEditar.setIdSeccion(Integer.parseInt(idStr));
        seccionEditar.setCodigoSeccion(vista.txtCodigoSeccion.getText().trim());
        seccionEditar.setIdCurso(idCursoReal);
        seccionEditar.setIdDocente(2); // ID temporal fijo de prueba docente
        seccionEditar.setAula(vista.txtAula.getText().trim());

        if (seccionDAO.modificar(seccionEditar)) {
            JOptionPane.showMessageDialog(vista, "Sección actualizada en la base de datos.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarFormulario();
            listarSeccionesEnTabla();
        }
    }

    private void ejecutarEliminacion() {
        String idStr = vista.txtIdSeccion.getText();
        if (StringUtils.isBlank(idStr)) {
            JOptionPane.showMessageDialog(vista, "Seleccione una fila para eliminar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(vista, "¿Desea eliminar la sección de PostgreSQL?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirmacion == JOptionPane.YES_OPTION) {
            if (seccionDAO.eliminar(Integer.parseInt(idStr))) {
                JOptionPane.showMessageDialog(vista, "Registro removido.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarFormulario();
                listarSeccionesEnTabla();
            }
        }
    }

    private void recuperarFilaSeleccionada() {
        int fila = vista.tblSecciones.getSelectedRow();
        if (fila != -1) {
            vista.txtIdSeccion.setText(vista.modeloTabla.getValueAt(fila, 0).toString());
            vista.txtCodigoSeccion.setText(vista.modeloTabla.getValueAt(fila, 1).toString());
            vista.cboCursos.setSelectedItem(vista.modeloTabla.getValueAt(fila, 2).toString());
            vista.cboDocentes.setSelectedItem(vista.modeloTabla.getValueAt(fila, 3).toString());
            vista.txtAula.setText(vista.modeloTabla.getValueAt(fila, 4).toString());
        }
    }

    private void limpiarFormulario() {
        vista.txtIdSeccion.setText("");
        vista.txtCodigoSeccion.setText("");
        vista.cboCursos.setSelectedIndex(0);
        vista.cboDocentes.setSelectedIndex(0);
        vista.txtAula.setText("");
        vista.tblSecciones.clearSelection();
    }
}