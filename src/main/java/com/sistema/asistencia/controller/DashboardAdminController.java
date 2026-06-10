package com.sistema.asistencia.controller;

import com.sistema.asistencia.view.FrmDashboardAdmin;
import com.sistema.asistencia.view.FrmGestionCursos;
import com.sistema.asistencia.view.FrmGestionSecciones;
import com.sistema.asistencia.view.FrmAsignarHorarios;
import com.sistema.asistencia.view.FrmReportes;
import com.sistema.asistencia.view.FrmLogin;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DashboardAdminController implements ActionListener {

    private final FrmDashboardAdmin vista;

    public DashboardAdminController(FrmDashboardAdmin vista) {
        this.vista = vista;

        this.vista.btnCursos.addActionListener(this);
        this.vista.btnSecciones.addActionListener(this);
        this.vista.btnHorarios.addActionListener(this);
        this.vista.btnReportes.addActionListener(this); 
        this.vista.btnCerrarSesion.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object origen = e.getSource();

        if (origen == vista.btnCursos) {
            vista.areaContenido.removeAll();
            FrmGestionCursos panelCursos = new FrmGestionCursos();
            new GestionCursosController(panelCursos);
            vista.areaContenido.add(panelCursos, java.awt.BorderLayout.CENTER);
            vista.areaContenido.revalidate();
            vista.areaContenido.repaint();
        }
        else if (origen == vista.btnSecciones) {
            vista.areaContenido.removeAll();
            FrmGestionSecciones panelSecciones = new FrmGestionSecciones();
            new GestionSeccionesController(panelSecciones);
            vista.areaContenido.add(panelSecciones, java.awt.BorderLayout.CENTER);
            vista.areaContenido.revalidate();
            vista.areaContenido.repaint();
        }
        else if (origen == vista.btnHorarios) {
            vista.areaContenido.removeAll();
            FrmAsignarHorarios panelHorarios = new FrmAsignarHorarios();
            new AsignarHorariosController(panelHorarios);
            vista.areaContenido.add(panelHorarios, java.awt.BorderLayout.CENTER);
            vista.areaContenido.revalidate();
            vista.areaContenido.repaint();
        }
        else if (origen == vista.btnReportes) { 
            vista.areaContenido.removeAll();
            FrmReportes panelReportes = new FrmReportes();
            new ReportesController(panelReportes);
            vista.areaContenido.add(panelReportes, java.awt.BorderLayout.CENTER);
            vista.areaContenido.revalidate();
            vista.areaContenido.repaint();
        }
        else if (origen == vista.btnCerrarSesion) {
            vista.dispose();
            
            FrmLogin login = new FrmLogin();
            new LoginController(login);
            login.setVisible(true);
        }
}
}