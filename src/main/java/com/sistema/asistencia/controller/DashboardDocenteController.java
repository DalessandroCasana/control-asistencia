package com.sistema.asistencia.controller;

import com.sistema.asistencia.model.dao.SeccionDAO;
import com.sistema.asistencia.model.entity.Seccion;
import com.sistema.asistencia.view.FrmDashboardDocente;
import com.sistema.asistencia.view.FrmLogin;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

public class DashboardDocenteController implements ActionListener {

    private final FrmDashboardDocente vista;
    private final SeccionDAO seccionDAO;
    private final int idDocenteLogueado; 

    public DashboardDocenteController(FrmDashboardDocente vista, int idDocente, String nombreDocente) {
        this.vista = vista;
        this.seccionDAO = new SeccionDAO();
        this.idDocenteLogueado = idDocente;

        this.vista.lblBienvenida.setText("Bienvenido(a): " + nombreDocente);

        this.vista.btnIniciarAsistencia.addActionListener(this);
        this.vista.btnCerrarSesion.addActionListener(this);

        determinarDiaYListarAgenda();
    }

    private void determinarDiaYListarAgenda() {
        LocalDate hoy = LocalDate.now();
        String diaSemana = hoy.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.forLanguageTag("es-ES"));
        
        diaSemana = diaSemana.substring(0, 1).toUpperCase() + diaSemana.substring(1);
        
        vista.lblFechaActual.setText("Agenda de Hoy: " + diaSemana + " " + hoy);

        vista.modeloTabla.setRowCount(0);
        
        List<Seccion> agendaHoy = seccionDAO.listarPorDocenteYDia(idDocenteLogueado, diaSemana);
        
        if (agendaHoy.isEmpty()) {
            vista.modeloTabla.addRow(new Object[]{"", "No registra clases programadas para el día de hoy", "", ""});
            vista.btnIniciarAsistencia.setEnabled(false);
        } else {
            vista.btnIniciarAsistencia.setEnabled(true);
            for (Seccion s : agendaHoy) {
                vista.modeloTabla.addRow(new Object[]{
                    s.getIdSeccion(),
                    s.getCodigoSeccion() + " - " + s.getNombreCurso(),
                    s.getNombreDocente(), 
                    s.getAula()
                });
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object origen = e.getSource();

        if (origen == vista.btnCerrarSesion) {
            int opc = JOptionPane.showConfirmDialog(vista, "¿Desea cerrar su sesión docente?", "Salir", JOptionPane.YES_NO_OPTION);
            if (opc == JOptionPane.YES_OPTION) {
                vista.dispose();
                FrmLogin loginVista = new FrmLogin();
                new LoginController(loginVista);
                loginVista.setVisible(true);
            }
        } 
        else if (origen == vista.btnIniciarAsistencia) {
            ejecutarAperturaAsistencia();
        }
    }

    private void ejecutarAperturaAsistencia() {
    int fila = vista.tblClasesHoy.getSelectedRow();
    
    if (fila == -1) {
        JOptionPane.showMessageDialog(vista, "Por favor, seleccione una clase de su agenda para iniciar el control.", "Selección Requerida", JOptionPane.WARNING_MESSAGE);
        return;
    }

    Object idObj = vista.modeloTabla.getValueAt(fila, 0);
    if (idObj == null || idObj.toString().isEmpty()) return;

    int idSeccionSeleccionada = Integer.parseInt(idObj.toString());
    String datosClase = vista.modeloTabla.getValueAt(fila, 1).toString();

    
    com.sistema.asistencia.view.FrmTomaAsistencia frmToma = new com.sistema.asistencia.view.FrmTomaAsistencia(vista, datosClase);
    new TomaAsistenciaController(frmToma, idSeccionSeleccionada);
    frmToma.setVisible(true); 
    }
}