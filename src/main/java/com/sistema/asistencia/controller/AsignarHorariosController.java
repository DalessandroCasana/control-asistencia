package com.sistema.asistencia.controller;

import com.sistema.asistencia.model.dao.HorarioDAO;
import com.sistema.asistencia.model.dao.SeccionDAO;
import com.sistema.asistencia.model.entity.Horario;
import com.sistema.asistencia.model.entity.Seccion;
import com.sistema.asistencia.view.FrmAsignarHorarios;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AsignarHorariosController implements ActionListener {

    private final FrmAsignarHorarios vista;
    private final HorarioDAO horarioDAO;
    private final SeccionDAO seccionDAO;
    private final SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm");
    
    private List<Seccion> listaSeccionesCache;

    public AsignarHorariosController(FrmAsignarHorarios vista) {
        this.vista = vista;
        this.horarioDAO = new HorarioDAO();
        this.seccionDAO = new SeccionDAO();

        // Suscribir escuchadores de botones
        this.vista.btnAsignar.addActionListener(this);
        this.vista.btnModificar.addActionListener(this);
        this.vista.btnEliminar.addActionListener(this);
        this.vista.btnLimpiar.addActionListener(this);

        // Escuchador de clics en la tabla
        this.vista.tblHorarios.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                recuperarFilaSeleccionada();
            }
        });

        // Carga activa de componentes relacionales
        cargarSeccionesInComboBox();
        listarHorariosEnTabla();
    }

    private void cargarSeccionesInComboBox() {
        try {
            vista.cboSecciones.removeAllItems();
            vista.cboSecciones.addItem("-- Seleccione Sección --");
            
            listaSeccionesCache = seccionDAO.listar();
            for (Seccion s : listaSeccionesCache) {
                vista.cboSecciones.addItem("Sección " + s.getCodigoSeccion() + " (" + s.getNombreCurso() + ")");
            }
        } catch (Exception ex) {
            System.err.println("Error al poblar combo de secciones: " + ex.getMessage());
        }
    }

    private void listarHorariosEnTabla() {
        vista.modeloTabla.setRowCount(0);
        try {
            List<Horario> lista = horarioDAO.listar();
            for (Horario h : lista) {
                vista.modeloTabla.addRow(new Object[]{
                    h.getIdHorario(),
                    "Sección " + h.getCodigoSeccion(),
                    h.getDia(),
                    h.getHoraInicio(),
                    h.getHoraFin()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al listar horarios: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object origen = e.getSource();

        if (origen == vista.btnAsignar) {
            ejecutarAsignacion();
        } else if (origen == vista.btnModificar) {
            ejecutarModificacion();
        } else if (origen == vista.btnEliminar) {
            ejecutarEliminacion();
        } else if (origen == vista.btnLimpiar) {
            limpiarFormulario();
        }
    }

    private void ejecutarAsignacion() {
        int idxSeccion = vista.cboSecciones.getSelectedIndex();
        int idxDia = vista.cboDias.getSelectedIndex();
        Date hInicio = (Date) vista.spnHoraInicio.getValue();
        Date hFin = (Date) vista.spnHoraFin.getValue();

        if (idxSeccion == 0 || idxDia == 0) {
            JOptionPane.showMessageDialog(vista, "Seleccione una sección y un día válido.", "Campos Incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (hFin.before(hInicio) || hFin.equals(hInicio)) {
            JOptionPane.showMessageDialog(vista, "La hora de finalización debe ser posterior a la de inicio.", "Error Horario", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int idSeccionReal = listaSeccionesCache.get(idxSeccion - 1).getIdSeccion();

        Horario nuevoHorario = new Horario();
        nuevoHorario.setIdSeccion(idSeccionReal);
        nuevoHorario.setDia(vista.cboDias.getSelectedItem().toString());
        nuevoHorario.setHoraInicio(formatoHora.format(hInicio));
        nuevoHorario.setHoraFin(formatoHora.format(hFin));

        if (horarioDAO.registrar(nuevoHorario)) {
            JOptionPane.showMessageDialog(vista, "Bloque horario guardado en PostgreSQL.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarFormulario();
            listarHorariosEnTabla();
        }
    }

    private void ejecutarModificacion() {
        String idStr = vista.txtIdHorario.getText();
        if (StringUtils.isBlank(idStr)) {
            JOptionPane.showMessageDialog(vista, "Seleccione un horario de la grilla.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idxSeccion = vista.cboSecciones.getSelectedIndex();
        if (idxSeccion == 0) return;

        Date hInicio = (Date) vista.spnHoraInicio.getValue();
        Date hFin = (Date) vista.spnHoraFin.getValue();

        if (hFin.before(hInicio) || hFin.equals(hInicio)) {
            JOptionPane.showMessageDialog(vista, "La hora fin debe ser posterior a la de inicio.", "Error Horario", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int idSeccionReal = listaSeccionesCache.get(idxSeccion - 1).getIdSeccion();

        Horario hEditar = new Horario();
        hEditar.setIdHorario(Integer.parseInt(idStr));
        hEditar.setIdSeccion(idSeccionReal);
        hEditar.setDia(vista.cboDias.getSelectedItem().toString());
        hEditar.setHoraInicio(formatoHora.format(hInicio));
        hEditar.setHoraFin(formatoHora.format(hFin));

        if (horarioDAO.modificar(hEditar)) {
            JOptionPane.showMessageDialog(vista, "Horario actualizado en PostgreSQL.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarFormulario();
            listarHorariosEnTabla();
        }
    }

    private void ejecutarEliminacion() {
        String idStr = vista.txtIdHorario.getText();
        if (StringUtils.isBlank(idStr)) {
            JOptionPane.showMessageDialog(vista, "Seleccione una fila para eliminar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int opcion = JOptionPane.showConfirmDialog(vista, "¿Desea borrar este horario del servidor?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            if (horarioDAO.eliminar(Integer.parseInt(idStr))) {
                JOptionPane.showMessageDialog(vista, "Registro removido satisfactoriamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarFormulario();
                listarHorariosEnTabla();
            }
        }
    }

    private void recuperarFilaSeleccionada() {
        int fila = vista.tblHorarios.getSelectedRow();
        if (fila != -1) {
            try {
                vista.txtIdHorario.setText(vista.modeloTabla.getValueAt(fila, 0).toString());
                vista.cboSecciones.setSelectedItem(vista.modeloTabla.getValueAt(fila, 1).toString());
                vista.cboDias.setSelectedItem(vista.modeloTabla.getValueAt(fila, 2).toString());
                
                Date dateInicio = formatoHora.parse(vista.modeloTabla.getValueAt(fila, 3).toString());
                Date dateFin = formatoHora.parse(vista.modeloTabla.getValueAt(fila, 4).toString());
                
                vista.spnHoraInicio.setValue(dateInicio);
                vista.spnHoraFin.setValue(dateFin);
            } catch (Exception ex) {
                System.out.println("Error parseador temporal: " + ex.getMessage());
            }
        }
    }

    private void limpiarFormulario() {
        vista.txtIdHorario.setText("");
        vista.cboSecciones.setSelectedIndex(0);
        vista.cboDias.setSelectedIndex(0);
        vista.spnHoraInicio.setValue(new Date());
        vista.spnHoraFin.setValue(new Date());
        vista.tblHorarios.clearSelection();
    }
}