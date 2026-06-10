package com.sistema.asistencia.controller;

import com.sistema.asistencia.model.dao.SeccionDAO;
import com.sistema.asistencia.model.entity.Seccion;
import com.sistema.asistencia.model.entity.Curso;
import com.sistema.asistencia.model.entity.Usuario;
import com.sistema.asistencia.view.FrmGestionSecciones;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class GestionSeccionesController implements ActionListener {

    private final FrmGestionSecciones vista;
    private final SeccionDAO seccionDAO;
    
    
    private List<Curso> listaCursosCache;
    private List<Usuario> listaDocentesCache;

    public GestionSeccionesController(FrmGestionSecciones vista) {
        this.vista = vista;
        this.seccionDAO = new SeccionDAO();

        
        this.vista.btnGuardar.addActionListener(this);
        this.vista.btnLimpiar.addActionListener(this);

        
        inicializarCombos();
        refrescarTabla();
    }

    private void inicializarCombos() {
        try {
            
            vista.cboCursos.removeAllItems();
            vista.cboCursos.addItem("-- Seleccione un Curso --");
            listaCursosCache = seccionDAO.listarCursosAux();
            for (Curso c : listaCursosCache) {
                vista.cboCursos.addItem(c.getNombreCurso() + " (" + c.getCodigoCurso() + ")");
            }

            
            vista.cboDocentes.removeAllItems();
            vista.cboDocentes.addItem("-- Seleccione un Docente --");
            listaDocentesCache = seccionDAO.listarDocentesAux();
            for (Usuario u : listaDocentesCache) {
                vista.cboDocentes.addItem(u.getApellidos() + ", " + u.getNombres());
            }

        } catch (Exception ex) {
            System.err.println("Error al poblar combos del módulo secciones: " + ex.getMessage());
        }
    }

    private void refrescarTabla() {
        vista.modeloTabla.setRowCount(0);
        try {
            List<Seccion> lista = seccionDAO.listar();
            for (Seccion s : lista) {
                Object[] fila = {
                    s.getIdSeccion(),
                    s.getCodigoSeccion(),
                    s.getNombreCurso(),
                    s.getNombreDocente(),
                    s.getPeriodoAcademico()
                };
                vista.modeloTabla.addRow(fila);
            }
        } catch (Exception ex) {
            System.err.println("Error al listar JTable Secciones: " + ex.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnLimpiar) {
            limpiarFormulario();
        } else if (e.getSource() == vista.btnGuardar) {
            ejecutarRegistroSeccion();
        }
    }

    private void limpiarFormulario() {
        vista.cboCursos.setSelectedIndex(0);
        vista.cboDocentes.setSelectedIndex(0);
        vista.txtCodigoSeccion.setText("");
        vista.txtPeriodo.setText("");
    }

    private void ejecutarRegistroSeccion() {
        int indexCurso = vista.cboCursos.getSelectedIndex();
        int indexDocente = vista.cboDocentes.getSelectedIndex();
        String codigoSec = vista.txtCodigoSeccion.getText().trim();
        String periodo = vista.txtPeriodo.getText().trim();

        if (indexCurso == 0 || indexDocente == 0 || StringUtils.isBlank(codigoSec) || StringUtils.isBlank(periodo)) {
            JOptionPane.showMessageDialog(vista, "Todos los campos de planificación son obligatorios.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        
        int idCursoReal = listaCursosCache.get(indexCurso - 1).getIdCurso();
        int idDocenteReal = listaDocentesCache.get(indexDocente - 1).getIdUsuario();

        Seccion nuevaSeccion = new Seccion();
        nuevaSeccion.setIdCurso(idCursoReal);
        nuevaSeccion.setIdDocente(idDocenteReal);
        nuevaSeccion.setCodigoSeccion(codigoSec);
        nuevaSeccion.setPeriodoAcademico(periodo);

        try {
            seccionDAO.insertar(nuevaSeccion);
            JOptionPane.showMessageDialog(vista, "Sección aperturada y vinculada con éxito en PostgreSQL.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarFormulario();
            refrescarTabla();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error de persistencia transaccional:\n" + ex.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
        }
    }
}