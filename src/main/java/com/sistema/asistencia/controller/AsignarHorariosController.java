package com.sistema.asistencia.controller;

import com.sistema.asistencia.model.dao.HorarioDAO;
import com.sistema.asistencia.model.entity.Horario;
import com.sistema.asistencia.model.entity.Seccion;
import com.sistema.asistencia.view.FrmAsignarHorarios;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;
import java.util.List;

public class AsignarHorariosController implements ActionListener {

    private final FrmAsignarHorarios vista;
    private final HorarioDAO horarioDAO;
    private List<Seccion> listaSeccionesCache;

    public AsignarHorariosController(FrmAsignarHorarios vista) {
        this.vista = vista;
        this.horarioDAO = new HorarioDAO();

        this.vista.btnGuardar.addActionListener(this);

        inicializarComboSecciones();
        refrescarTabla();
    }

    private void inicializarComboSecciones() {
        try {
            vista.cboSecciones.removeAllItems();
            vista.cboSecciones.addItem("-- Seleccione una Sección --");
            listaSeccionesCache = horarioDAO.listarSeccionesAux();
            for (Seccion s : listaSeccionesCache) {
                vista.cboSecciones.addItem("Sec: " + s.getCodigoSeccion() + " - " + s.getNombreCurso());
            }
        } catch (Exception ex) {
            System.err.println("Error al cargar combo secciones: " + ex.getMessage());
        }
    }

    private void refrescarTabla() {
        vista.modeloTabla.setRowCount(0);
        try {
            List<Horario> lista = horarioDAO.listar();
            for (Horario h : lista) {
                Object[] fila = {
                    h.getIdHorario(),
                    h.getCodigoSeccion(),
                    h.getNombreCurso(),
                    h.getDiaSemana(),
                    h.getHoraInicio(),
                    h.getHoraFin(),
                    h.getAula()
                };
                vista.modeloTabla.addRow(fila);
            }
        } catch (Exception ex) {
            System.err.println("Error al listar horarios en la tabla: " + ex.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnGuardar) {
            ejecutarAsignacionHorario();
        }
    }

    private void ejecutarAsignacionHorario() {
        int indexSec = vista.cboSecciones.getSelectedIndex();
        int indexDia = vista.cboDias.getSelectedIndex();
        String hInicioRaw = vista.txtHoraInicio.getText().trim();
        String hFinRaw = vista.txtHoraFin.getText().trim();
        String aula = vista.txtAula.getText().trim();

        if (indexSec == 0 || indexDia == 0 || StringUtils.isBlank(hInicioRaw) || StringUtils.isBlank(hFinRaw) || StringUtils.isBlank(aula)) {
            JOptionPane.showMessageDialog(vista, "Todos los parámetros de tiempo son mandatorios.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            
            Time horaInicio = Time.valueOf(hInicioRaw + ":00");
            Time horaFin = Time.valueOf(hFinRaw + ":00");

            int idSeccionReal = listaSeccionesCache.get(indexSec - 1).getIdSeccion();
            String diaSeleccionado = vista.cboDias.getSelectedItem().toString();

            Horario h = new Horario();
            h.setIdSeccion(idSeccionReal);
            h.setDiaSemana(diaSeleccionado);
            h.setHoraInicio(horaInicio);
            h.setHoraFin(horaFin);
            h.setAula(aula);

            horarioDAO.insertar(h);
            JOptionPane.showMessageDialog(vista, "Horario agendado de manera exitosa.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            
            vista.txtHoraInicio.setText("");
            vista.txtHoraFin.setText("");
            vista.txtAula.setText("");
            refrescarTabla();

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(vista, "Formato de hora incorrecto. Use formato de 24 horas (HH:MM).", "Error de Entrada", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Conflicto transaccional de PostgreSQL:\n" + ex.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
        }
    }
}